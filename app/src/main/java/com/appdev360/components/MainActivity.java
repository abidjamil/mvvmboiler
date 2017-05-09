package com.appdev360.components;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appdev360.logincomponent.Login;
import com.appdev360.logincomponent.LoginFactory;
import com.appdev360.logincomponent.UserSessionManager;
import com.appdev360.logincomponent.model.User;
import com.appdev360.logincomponent.sharing.PublicSharing;
import com.appdev360.logincomponent.utils.RealPathUtil;
import com.master.permissionhelper.PermissionHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jaffarraza on 27/04/2017.
 */

public class MainActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 12;
    @BindView(R.id.text)
    EditText text;
    private User loggedInUser;
    private Login login;
    private PermissionHelper permissionHelper;
    private String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loggedInUser = UserSessionManager.getCurrentUser(this);
        if (loggedInUser == null) {
            finish();
        }
        login = LoginFactory.build(loggedInUser);

        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Log.d(TAG, "onPermissionGranted() called");
            }

            @Override
            public void onPermissionDenied() {
                Log.d(TAG, "onPermissionDenied() called");
            }

            @Override
            public void onPermissionDeniedBySystem() {
                Log.d(TAG, "onPermissionDeniedBySystem() called");
            }
        });
    }


    @OnClick({R.id.share_text, R.id.share_image, R.id.logout, R.id.linkify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logout:
                boolean result = login.logout(MainActivity.this);
                if (result) {
                    finish();
                    Toast.makeText(MainActivity.this, "User logged out successfully", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_image:
                if (Build.VERSION.SDK_INT < 19) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
                break;
            case R.id.share_text:
                String shareText = text.getText().toString().trim();
                PublicSharing publicSharing = new PublicSharing.PublicSharingBuilder(this, loggedInUser)
                        .setText(shareText).build();
                publicSharing.show();
            case R.id.linkify:
                startActivity(new Intent(this, Linkify.class));
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Uri imageUri = Uri.fromFile(new File(uriToFilename(uri)));
            String captionText = text.getText().toString().trim();

            PublicSharing publicSharing = new PublicSharing.PublicSharingBuilder(this, loggedInUser)
                    .setText(captionText).setBitmapUri(imageUri).build();
            publicSharing.show();
        }
    }

    private String uriToFilename(Uri uri) {
        String path = null;
        if (Build.VERSION.SDK_INT < 11) {
            path = RealPathUtil.getRealPathFromURI_BelowAPI11(this, uri);
        } else if (Build.VERSION.SDK_INT < 19) {
            path = RealPathUtil.getRealPathFromURI_API11to18(this, uri);
        } else {
            path = RealPathUtil.getRealPathFromURI_API19(this, uri);
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
