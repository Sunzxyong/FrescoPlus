package com.sunzxy.frescopluslib.util;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.sunzxy.frescopluslib.core.FrescoUri;
import com.sunzxy.frescopluslib.exception.FPRuntimeException;


/**
 * Created by zhengxiaoyong on 16/1/9.
 */
public class UriUtil {

    /**
     * Check if uri represents network resource
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "http" or "https"
     */
    public static boolean isNetworkUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return FrescoUri.HTTPS_SCHEME.equals(scheme) || FrescoUri.HTTP_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local file
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "file"
     */
    public static boolean isLocalFileUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return FrescoUri.LOCAL_FILE_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local content
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "content"
     */
    public static boolean isLocalContentUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return FrescoUri.LOCAL_CONTENT_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local asset
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "asset"
     */
    public static boolean isLocalAssetUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return FrescoUri.LOCAL_ASSET_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local resource
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "res"
     */
    public static boolean isLocalResourceUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return FrescoUri.LOCAL_RESOURCE_SCHEME.equals(scheme);
    }

    /**
     * Check if the uri is a data uri
     */
    public static boolean isDataUri(Uri uri) {
        return FrescoUri.DATA_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static Uri parseUriFromResId(@DrawableRes int resId){
        return new Uri.Builder()
                .scheme(FrescoUri.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
    }

    /**
     * @param uri uri to extract scheme from, possibly null
     * @return null if uri is null, result of uri.getScheme() otherwise
     */
    public static String getSchemeOrNull(Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    /**
     * @param uriAsString the uri as a string
     * @return the parsed Uri or null if the input was null
     */
    public static Uri parseUri(String uriAsString) {
        Uri uri;
        try {
            uri = Uri.parse(uriAsString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FPRuntimeException("uriPath is null",e.getCause());
        }
        return uri;
    }
}
