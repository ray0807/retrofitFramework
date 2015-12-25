package com.bm.projectxxx.fresco;

import android.content.Context;

import com.bm.projectxxx.App;
import com.bm.projectxxx.utils.constant.Constant;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

/**
 * Fresco图片加载框架的通用配置
 */
public class ImagePipelineConfigFactory {

    private static ImagePipelineConfig sImagePipelineConfig;
    private static ImagePipelineConfig sOkHttpImagePipelineConfig;

    /**
     * 使用 android http 网络支持的配置
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
            // 要传递一个 ProgressiveJpegConfig. 的实例
            configBuilder.setProgressiveJpegConfig(new SimpleProgressiveJpegConfig());
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            sImagePipelineConfig = configBuilder.build();
        }
        return sImagePipelineConfig;
    }

    /**
     * 使用okhttp网络支持的配置
     */
    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            ImagePipelineConfig.Builder configBuilder =
                    OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
            // 要传递一个 ProgressiveJpegConfig. 的实例
            configBuilder.setProgressiveJpegConfig(new SimpleProgressiveJpegConfig());
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            sOkHttpImagePipelineConfig = configBuilder.build();
        }
        return sOkHttpImagePipelineConfig;
    }

    /**
     * Configures disk and memory cache not to exceed common limits
     */
    private static void configureCaches(
            ImagePipelineConfig.Builder configBuilder,
            Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                Constant.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                Constant.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        configBuilder
                .setBitmapMemoryCacheParamsSupplier(
                        new Supplier<MemoryCacheParams>() {
                            public MemoryCacheParams get() {
                                return bitmapCacheParams;
                            }
                        })
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder()
                                // 自定义图片缓存路径
                                .setBaseDirectoryPath(new File(App.getInstance().getDiskCacheDir()))
                                .setBaseDirectoryName(Constant.IMAGE_CACHE_PATH)
                                        // 自定义内存缓存池峰值
                                .setMaxCacheSize(Constant.MAX_DISK_CACHE_SIZE)
                                .build());
    }

    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
        configBuilder.setRequestListeners(
                Sets.newHashSet((RequestListener) new RequestLoggingListener()));
    }
}
