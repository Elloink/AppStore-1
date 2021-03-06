package com.idreamsky.appstore.di.module;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zlc.season.rxdownload2.RxDownload;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by idreamsky on 2017/8/14.
 */

@Module
public class DownloadModule {

    @Provides
    @Singleton
    public RxDownload provideDownload(Application application, Retrofit retrofit, File downDir) {
        return RxDownload.getInstance(application)
                .defaultSavePath(downDir.getPath() + File.separator + "apk")
                .maxDownloadNumber(3)
                .maxThread(3);
    }

    @Provides
    @Singleton
    public File provideDownDir() {
        return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
    }


}
