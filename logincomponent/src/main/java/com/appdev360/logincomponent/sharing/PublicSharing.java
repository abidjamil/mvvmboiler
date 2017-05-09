package com.appdev360.logincomponent.sharing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.appdev360.logincomponent.R;
import com.appdev360.logincomponent.adapter.ShareMediaAdapter;
import com.appdev360.logincomponent.model.FacebookUser;
import com.appdev360.logincomponent.model.User;

import java.util.List;

/**
 * Created by jaffarraza on 01/05/2017.
 * Courtesy to abubakar
 */

public class PublicSharing extends Dialog {

    //required parameters
    private Context mContext;

    //optional parameters
    private Uri bitmapUri;
    private String text;
    private String url;
    private User user;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ShareMediaAdapter mAdapter;
    private List<ResolveInfo> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.social_media_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.installed_media_list);
        setSocialMediaAdapter();
    }

    // setting custom socialMedia  adapter
    private void setSocialMediaAdapter() {

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        infoList = SharingUtils.getResolveInfoList(mContext);
        mAdapter = new ShareMediaAdapter(mContext, infoList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(new ShareMediaAdapter.ClickListener() {
            @Override
            public void onItemClicked(int itemPosition) {
                share(itemPosition);

            }
        });
    }

    private void share(int itemPosition) {
        if (text == null) {
            text = "";
        }
        dismiss();
        String packageName = infoList.get(itemPosition).activityInfo.packageName;
        if (bitmapUri == null) {
            if (text.equalsIgnoreCase("")) {
                Toast.makeText(mContext, "Please type some text to be shared.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (infoList.get(itemPosition).activityInfo.packageName.contains("facebook")) {
                if (user instanceof FacebookUser) {
                    SharingUtils.shareTextOnFacebook((Activity) mContext, text);
                } else {
                    Toast.makeText(mContext, "You should have to login via Facebook first.", Toast.LENGTH_SHORT).show();
                }
            } else {
                SharingUtils.shareText(mContext, packageName, text);
            }
            return;
        }
        if (packageName.contains("facebook")) {
            dismiss();
            if (user instanceof FacebookUser) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), bitmapUri);
                    SharingUtils.shareImageOnFacebook((Activity) mContext, bitmap, text);
                } catch (Exception e) {
                    Toast.makeText(mContext, "The selected file can't be shared", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "You should have to login via Facebook first.", Toast.LENGTH_SHORT).show();
            }
        } else {
            dismiss();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), bitmapUri);
                SharingUtils.shareOnMedia(mContext, infoList.get(itemPosition).activityInfo.packageName, bitmap, text);
            } catch (Exception e) {
                Toast.makeText(mContext, "The selected file can't be shared", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public PublicSharing(PublicSharingBuilder builder) {
        super(builder.mContext);
        this.mContext = builder.mContext;
        this.bitmapUri = builder.bitmapUri;
        this.text = builder.text;
        this.url = builder.url;
        this.user = builder.user;
    }

    public static class PublicSharingBuilder {

        //required parameters
        private Context mContext;
        private User user;

        //optional parameters
        private Uri bitmapUri;
        private String text;
        private String url;

        public PublicSharingBuilder(Context mContext, User user) {
            this.mContext = mContext;
            this.user = user;
        }

        public PublicSharingBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public PublicSharingBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public PublicSharingBuilder setBitmapUri(Uri bitmap) {
            this.bitmapUri = bitmap;
            return this;
        }

        public PublicSharing build() {
            return new PublicSharing(this);
        }

    }

}
