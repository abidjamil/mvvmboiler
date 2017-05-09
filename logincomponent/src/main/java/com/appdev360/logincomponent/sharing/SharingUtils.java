package com.appdev360.logincomponent.sharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by jaffarraza on 01/05/2017.
 * Courtesy to abubakar
 */
public class SharingUtils {

    public static void shareOnMedia(Context context, String packageName, Bitmap bitmap, String msg) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM, createFileForSend(context,bitmap));
        share.putExtra(Intent.EXTRA_TEXT, msg);
        share.setPackage(packageName);
        context.startActivity(Intent.createChooser(share, ""));
    }

    public static void shareImageOnFacebook(Activity activity, Bitmap bitmap, String text) {
        ShareDialog shareDialog;
        shareDialog = new ShareDialog(activity);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption(text)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        shareDialog.show(content);
    }

    public static void shareTextOnFacebook(Activity activity, String text) {
        ShareDialog shareDialog;
        shareDialog = new ShareDialog(activity);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(text))
                .build();
        shareDialog.show(content);
    }

    public static void shareText(Context context , String packageName , String text){

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        sharingIntent.setPackage(packageName);
        context.startActivity(Intent.createChooser(sharingIntent, ""));

    }

    public static Intent shareImage(Context context, String packageName, Bitmap bitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, createFile(bitmap));
        if (packageName != null && isPackageInstalled(packageName, context)) {
            share.setPackage(packageName);
            return share;
        } else {

            Toast.makeText(context, "Please Install Application", Toast.LENGTH_LONG).show();
        }
        return null;
    }


    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Uri createFile(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String url = Environment.getExternalStorageDirectory() + File.separator + "file.jpg";
        File f = new File(url);
        try {
            if (f.exists())
                f.delete();
            f.createNewFile();
            new FileOutputStream(f).write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.parse(url);
    }


    public static Uri createFileForSend(Context context,Bitmap bitmap) {
        Uri uri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.

           // uri = FileProvider.getUriForFile(context, "com.appdev.biopage.FileProvider", file);
             uri= Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }


    public Uri getLocalBitmapUri(Context context, ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri uri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.

            uri = FileProvider.getUriForFile(context, "com.appdev.biopage.FileProvider", file);
           // bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }


    public static List<ResolveInfo> getResolveInfoList(Context context) {

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        return pm.queryIntentActivities(sendIntent, 0);

    }
}
