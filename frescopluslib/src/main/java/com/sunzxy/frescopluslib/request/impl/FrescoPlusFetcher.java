package com.sunzxy.frescopluslib.request.impl;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.sunzxy.frescopluslib.core.DefaultConfigCentre;
import com.sunzxy.frescopluslib.core.FrescoPlusCore;
import com.sunzxy.frescopluslib.core.FrescoPriority;
import com.sunzxy.frescopluslib.logger.FrescoLogger;
import com.sunzxy.frescopluslib.request.callback.FPFetchCallback;
import com.sunzxy.frescopluslib.request.callback.FPLoadCallback;
import com.sunzxy.frescopluslib.request.supplier.FetchImageControllerListenerSupplier;
import com.sunzxy.frescopluslib.widget.FrescoPlusView;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public class FrescoPlusFetcher extends AbsBaseFetcher<FrescoPlusView> {
    private boolean autoRotateEnabled;
    private int fadeDuration;
    private Drawable failureDrawable;
    private Drawable defaultDrawable;
    private Drawable overlayDrawable;
    private Drawable progressDrawable;
    private Drawable retryDrawable;
    private Drawable pressedDrawable;
    private int resizeWidth;
    private int resizeHeight;
    private ScalingUtils.ScaleType scaleType;
    private float radius;
    private int requestPriority;
    private ImageRequest.RequestLevel requestLevel;
    private Postprocessor postprocessor;
    private ExecutorService executeBackgroundTask = Executors.newSingleThreadExecutor();
    private FrescoPlusFetcher(Builder builder) {
        autoRotateEnabled = builder.autoRotateEnabled;
        fadeDuration = builder.fadeDuration <= 0 ? DefaultConfigCentre.DEFAULT_FADE_DURATION : builder.fadeDuration;
        failureDrawable = builder.failureDrawable;
        defaultDrawable = builder.defaultDrawable;
        overlayDrawable = builder.overlayDrawable;
        progressDrawable = builder.progressDrawable;
        retryDrawable = builder.retryDrawable;
        pressedDrawable = builder.pressedDrawable;
        resizeWidth = builder.resizeWidth <= 0 ? 0 : builder.resizeWidth;
        resizeHeight = builder.resizeHeight <= 0 ? 0 : builder.resizeHeight;
        scaleType = builder.scaleType == null ? DefaultConfigCentre.DEFAULT_SCALE_TYPE : builder.scaleType;
        radius = builder.radius <= 0 ? DefaultConfigCentre.DEFAULT_RADIUS : builder.radius;
        requestPriority = builder.requestPriority <= 0 ? DefaultConfigCentre.DEFAULT_PRIORITY : builder.requestPriority;
        requestLevel = builder.requestLevel == null ? DefaultConfigCentre.DEFAULT_REQUEST_LEVEL : builder.requestLevel;
        postprocessor = builder.postprocessor;
    }

    public static Builder newFetcher() {
        return new Builder();
    }


    @Override
    public void fetch(FrescoPlusView imageView, Uri uri) {
        super.fetch(imageView, uri);
        FrescoLogger.getLogger().log(uri);
        fetchImage(imageView, uri, null);
    }

    @Override
    public void fetch(FrescoPlusView imageView, Uri uri, FPFetchCallback<ImageInfo> callback) {
        super.fetch(imageView, uri, callback);
        FrescoLogger.getLogger().log(uri);
        fetchImage(imageView, uri, callback);
    }

    @Override
    public void fetch(Uri uri, FPLoadCallback<Bitmap> callback) {
        super.fetch(uri, callback);
        FrescoLogger.getLogger().log(uri);
        fetchImage(uri, callback);
    }


    @Override
    protected Object beforeExecute(Uri uri) {
        return null;
    }

    /**
     * @param frescoPlusView The draweeView is to display the bitmap
     * @param uri         The source uri
     * @param callback    Listening to the success or failure
     */
    private void fetchImage(FrescoPlusView frescoPlusView, Uri uri, FPFetchCallback<ImageInfo> callback) {
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(null);
        hierarchyBuilder.setFadeDuration(fadeDuration);
        hierarchyBuilder.setRoundingParams(RoundingParams.fromCornersRadius(radius));
        hierarchyBuilder.setActualImageScaleType(scaleType);
        if (defaultDrawable != null)
            hierarchyBuilder.setPlaceholderImage(defaultDrawable, scaleType);
        if (pressedDrawable != null)
            hierarchyBuilder.setPressedStateOverlay(pressedDrawable);
        if (retryDrawable != null)
            hierarchyBuilder.setRetryImage(retryDrawable);
        if (overlayDrawable != null)
            hierarchyBuilder.setOverlay(overlayDrawable);
        if (failureDrawable != null)
            hierarchyBuilder.setFailureImage(failureDrawable, scaleType);
        if (progressDrawable != null)
            hierarchyBuilder.setProgressBarImage(progressDrawable);
        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        requestBuilder.setLowestPermittedRequestLevel(requestLevel);
        requestBuilder.setAutoRotateEnabled(autoRotateEnabled);
        if (postprocessor != null)
            requestBuilder.setPostprocessor(postprocessor);
        Priority priority = requestPriority == FrescoPriority.HIGH ? Priority.HIGH : Priority.MEDIUM;
        requestBuilder.setRequestPriority(priority);
        if (resizeWidth > 0 && resizeHeight > 0)
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        ImageRequest imageRequest = requestBuilder.build();

        DraweeController draweeController = FrescoPlusCore.newDraweeControllerBuilder()
                .setOldController(frescoPlusView.getController())
                .setAutoPlayAnimations(true)
                .setRetainImageOnFailure(true)
                .setTapToRetryEnabled(true)
                .setImageRequest(imageRequest)
                .setControllerListener(FetchImageControllerListenerSupplier.newInstance(callback))
                .build();
        frescoPlusView.setHierarchy(hierarchy);
        frescoPlusView.setController(draweeController);
    }

    private void fetchImage(final Uri uri, final FPLoadCallback<Bitmap> callback) {
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        requestBuilder.setLowestPermittedRequestLevel(requestLevel);
        requestBuilder.setAutoRotateEnabled(autoRotateEnabled);
        if (postprocessor != null)
            requestBuilder.setPostprocessor(postprocessor);
        Priority priority = requestPriority == FrescoPriority.HIGH ? Priority.HIGH : Priority.MEDIUM;
        requestBuilder.setRequestPriority(priority);
        if (resizeWidth > 0 && resizeHeight > 0)
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        ImageRequest imageRequest = requestBuilder.build();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = FrescoPlusCore.getImagePipeline().fetchDecodedImage(imageRequest, null);
        //callback event processing thread pool.If you pass the UI thread pool you can not handle time-consuming operation.
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                 @Override
                                 public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                                     if (callback == null)
                                         return;
                                     if (bitmap != null && !bitmap.isRecycled()) {
                                         FrescoLogger.getLogger().log("Bitmap:[height="+bitmap.getHeight()+",width="+bitmap.getWidth()+"]");
                                         handlerBackgroundTask(new Callable<Bitmap>() {
                                             @Override
                                             public Bitmap call() throws Exception {
                                                 final Bitmap resultBitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
                                                 if (resultBitmap != null && !resultBitmap.isRecycled())
                                                     postResult(resultBitmap,uri,callback);
                                                 return resultBitmap;
                                             }
                                         });
                                     }
                                 }

                                 @Override
                                 public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
                                     super.onCancellation(dataSource);
                                     if (callback == null)
                                         return;
                                     FrescoLogger.getLogger().log("onCancel");
                                     callback.onCancel(uri);
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     if (callback == null)
                                         return;
                                     Throwable throwable = null;
                                     if (dataSource != null) {
                                         throwable = dataSource.getFailureCause();
                                     }
                                     FrescoLogger.getLogger().log(throwable);
                                     callback.onFailure(uri, throwable);
                                 }
                             },
                UiThreadImmediateExecutorService.getInstance());
    }

    private <T> Future<T> handlerBackgroundTask(Callable<T> callable){
        return executeBackgroundTask.submit(callable);
    }

    /**
     * 回调UI线程中去
     * @param result
     * @param uri
     * @param callback
     * @param <T>
     */
    private <T> void postResult(final T result, final Uri uri, final FPLoadCallback<T> callback){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(uri,result);
            }
        });
    } 
    
    public static final class Builder {
        private int fadeDuration;
        private Drawable failureDrawable;
        private Drawable defaultDrawable;
        private Drawable overlayDrawable;
        private Drawable progressDrawable;
        private Drawable retryDrawable;
        private Drawable pressedDrawable;
        private int resizeWidth;
        private int resizeHeight;
        private ScalingUtils.ScaleType scaleType;
        private float radius;
        private int requestPriority;
        private boolean autoRotateEnabled = DefaultConfigCentre.DEFAULT_AUTO_ROTATE;
        private ImageRequest.RequestLevel requestLevel;
        private Postprocessor postprocessor;

        private Builder() {
        }

        public Builder withAutoRotateEnabled(boolean val) {
            autoRotateEnabled = val;
            return this;
        }

        public Builder withFadeDuration(int val) {
            fadeDuration = val;
            return this;
        }

        public Builder withFailureDrawable(Drawable val) {
            failureDrawable = val;
            return this;
        }

        public Builder withDefaultDrawable(Drawable val) {
            defaultDrawable = val;
            return this;
        }

        public Builder withOverlayDrawable(Drawable val) {
            overlayDrawable = val;
            return this;
        }

        public Builder withProgressDrawable(Drawable val) {
            progressDrawable = val;
            return this;
        }

        public Builder withRetryDrawable(Drawable val) {
            retryDrawable = val;
            return this;
        }

        public Builder withPressedDrawable(Drawable val) {
            pressedDrawable = val;
            return this;
        }

        public Builder withResizeWidth(int val) {
            resizeWidth = val;
            return this;
        }

        public Builder withResizeHeight(int val) {
            resizeHeight = val;
            return this;
        }

        public Builder withScaleType(ScalingUtils.ScaleType val) {
            scaleType = val;
            return this;
        }

        public Builder withRadius(float val) {
            radius = val;
            return this;
        }

        public Builder withRequestPriority(int val) {
            requestPriority = val;
            return this;
        }

        public Builder withRequestLevel(ImageRequest.RequestLevel val) {
            requestLevel = val;
            return this;
        }

        public Builder withPostprocessor(Postprocessor val) {
            postprocessor = val;
            return this;
        }

        public FrescoPlusFetcher build() {
            return new FrescoPlusFetcher(this);
        }
    }
}
