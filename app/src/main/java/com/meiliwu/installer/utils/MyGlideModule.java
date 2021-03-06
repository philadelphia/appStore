package com.meiliwu.installer.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Author Tao.ZT.Zhang
 * Date   2017/11/1
 */

    @GlideModule
    public final class MyGlideModule extends AppGlideModule {

        @Override
        public void applyOptions(final Context context, GlideBuilder builder) {
            //获取内存的默认配置
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
//        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

            //内存缓存相关,默认是24m
            int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
            builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));


            //设置磁盘缓存及其路径
            //
            int MAX_CACHE_SIZE = 100 * 1024 * 1024;
            String CACHE_FILE_NAME = "imgCache";
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,CACHE_FILE_NAME,MAX_CACHE_SIZE));
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String downloadDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        CACHE_FILE_NAME;
                //路径---->sdcard/imgCache
                builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, MAX_CACHE_SIZE));
            } else {
                //路径---->/sdcard/Android/data/<application package>/cache/imgCache
                builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
            }
        }


//        @Override
//        public void registerComponents(Context context, Registry registry) {
//            //配置glide网络加载框架
//            registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
//        }
        @Override
        public boolean isManifestParsingEnabled() {
            //不使用清单配置的方式,减少初始化时间
            return false;
        }
    }


