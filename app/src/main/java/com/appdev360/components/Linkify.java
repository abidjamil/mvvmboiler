package com.appdev360.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.appdev360.logincomponent.linkify.LinkifyTags;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaffarraza on 01/05/2017.
 */

public class Linkify extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkify);
        ButterKnife.bind(this);

        LinkifyTags.addLinks(this, text, LinkifyTags.ALL);
    }
}
