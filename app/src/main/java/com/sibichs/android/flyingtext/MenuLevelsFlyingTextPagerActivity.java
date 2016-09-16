package com.sibichs.android.flyingtext;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;

public class MenuLevelsFlyingTextPagerActivity extends FragmentActivity {
//public class MenuLevelsFlyingTextPagerActivity extends FragmentActivity
   //     implements BuyDialogFlyingTextFragment.NoticeDialogListener {

    private ViewPager mViewPager;
    private ArrayList<Level> mLevels;

    private MyAudioPlayer musicPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.menu_viewPager);
        setContentView(mViewPager);

        musicPlayer = new MyAudioPlayer();

        mLevels = LevelsLab.get(this).getLevels();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mLevels.size()/9;
            }

            @Override
            public Fragment getItem(int pos) {
                int firstLevelInPage;
                if (pos == 0) firstLevelInPage = pos;
                else firstLevelInPage = pos * 9;
                return MenuLevelsFlyingTextFragment.newInstance(firstLevelInPage);
            }
        });

        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
                musicPlayer.play(getApplicationContext(), R.raw.bethoven);
                musicPlayer.setLoopingPlay(true);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
                musicPlayer.play(getApplicationContext(), R.raw.bethoven);
                musicPlayer.setLoopingPlay(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        musicPlayer.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        musicPlayer.stop();
    }
}
