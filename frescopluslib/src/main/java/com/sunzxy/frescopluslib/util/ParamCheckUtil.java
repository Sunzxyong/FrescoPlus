package com.sunzxy.frescopluslib.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunzxy.frescopluslib.core.FrescoUri;
import com.sunzxy.frescopluslib.exception.FPIllegalArgumentException;
import com.sunzxy.frescopluslib.exception.FPNullPointerException;
import com.sunzxy.frescopluslib.exception.FPRuntimeException;


/**
 * Created by zhengxiaoyong on 16/1/5.
 */
public final class ParamCheckUtil {
    private ParamCheckUtil() {
    }

    /**
     * check reference not null
     *
     * @param reference
     * @param <T>
     * @return reference
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null)
            throw new FPNullPointerException("The reference is null!");
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new FPNullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * @param uri uri
     * @return true if the uri is legal,false otherwise.
     */
    public static void checkUriIsLegal(Uri uri) {
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        String scheme = UriUtil.getSchemeOrNull(uri);
        if (TextUtils.isEmpty(scheme))
            throw new FPIllegalArgumentException("uri is illegal,cause:This scheme is null or empty");
        switch (scheme) {
            case FrescoUri.HTTP_SCHEME:
            case FrescoUri.HTTPS_SCHEME:
            case FrescoUri.LOCAL_FILE_SCHEME:
            case FrescoUri.LOCAL_ASSET_SCHEME:
            case FrescoUri.LOCAL_CONTENT_SCHEME:
            case FrescoUri.LOCAL_RESOURCE_SCHEME:
            case FrescoUri.DATA_SCHEME:
                break;
            default:
                throw new FPIllegalArgumentException("uri is illegal,cause:This scheme not supported");
        }
        validate(uri);
    }
    private static void validate(Uri uri) {
        // make sure that the source uri is set correctly.
        if (UriUtil.isLocalResourceUri(uri)) {
            if (!uri.isAbsolute()) {
                throw new FPRuntimeException("Resource URI path must be absolute.");
            }
            if (uri.getPath().isEmpty()) {
                throw new FPRuntimeException("Resource URI must not be empty");
            }
            try {
                Integer.parseInt(uri.getPath().substring(1));
            } catch (NumberFormatException ignored) {
                throw new FPRuntimeException("Resource URI path must be a resource id.",ignored.getCause());
            }
        }
        if (UriUtil.isLocalAssetUri(uri) && !uri.isAbsolute()) {
            throw new FPRuntimeException("Asset URI path must be absolute.");
        }
    }
}
