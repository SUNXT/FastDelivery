package com.sun.fastdelivery.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.sun.fastdelivery.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by SUN on 2018/5/28.
 */

public class AppInstallUtils {

    /**
     * 判断用户有没有安装appPackName包名的app
     * @param context
     * @param appPackName
     * @return
     */
    public static boolean isAppHasInstalled(Context context, String appPackName){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> infos = packageManager.getInstalledPackages(0);
        for (PackageInfo info: infos){
            if (info.packageName.equalsIgnoreCase(appPackName)){
                return true;
            }
        }
        return false;
    }

    /**
     * 调用安装app
     * @param context
     * @param apkPath
     */
    public static void installApp(Context context, String apkPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", new File(apkPath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(
                    Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive"
            );
        }
        context.startActivity(intent);
    }

    /**
     * 将apk复制到本地
     * @param context
     * @param fileName
     * @param path
     * @return
     */
    public static boolean copyApkFromAssets(Context context, String fileName, String path) {
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyIsFinish;
    }
}
