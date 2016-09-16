package com.sibichs.android.flyingtext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by Slavon on 04.07.2016.
 */
public class BuyWebPageFlyingTextActivity extends SingleFragmentActivity{

    public static Intent newIntent(Context context, Uri webPageUri) {
        Intent i = new Intent(context, BuyWebPageFlyingTextActivity.class);
        i.setData(webPageUri);
        return i;
    }
    @Override
    protected Fragment createFragment() {
        return BuyWebPageFlyingTextFragment.newInstance(getIntent().getData());
    }
}
