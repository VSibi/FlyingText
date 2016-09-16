package com.sibichs.android.flyingtext;

import android.support.v4.app.Fragment;

public class HelpFlyingTextActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new HelpFlyingTextFragment();
    }
}
