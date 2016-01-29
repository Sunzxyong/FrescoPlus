package com.sunzxy.frescopluslib.core;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.sunzxy.frescopluslib.util.ParamCheckUtil;
import com.sunzxy.frescopluslib.widget.FrescoPlusView;

/**
 * Created by zhengxiaoyong on 16/1/9.
 */
public class FrescoPlusCore {
    private static PipelineDraweeControllerBuilderSupplier mDraweeControllerBuilderSupplier;

    public static void init(Context context,ImagePipelineConfig imagePipelineConfig){
        ImagePipelineFactory.initialize(imagePipelineConfig);
        mDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context);
        FrescoPlusView.initialize(mDraweeControllerBuilderSupplier);
    }
    /**
     * @return create a new DraweeControllerBuilder instance.
     */
    public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {
        ParamCheckUtil.checkNotNull(mDraweeControllerBuilderSupplier,"FrescoPlusCore not initialize");
        return mDraweeControllerBuilderSupplier.get();
    }

    public static ImagePipelineFactory getImagePipelineFactory() {
        return ImagePipelineFactory.getInstance();
    }

    /**
     * @return get ImagePipeline instance from ImagePipelineFactory.
     */
    public static ImagePipeline getImagePipeline() {
        return getImagePipelineFactory().getImagePipeline();
    }

    /**
     * shut down .
     */
    public static void shutDownDraweeControllerBuilderSupplier(){
        mDraweeControllerBuilderSupplier = null;
    }
}
