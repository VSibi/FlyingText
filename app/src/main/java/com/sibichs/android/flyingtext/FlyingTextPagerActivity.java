package com.sibichs.android.flyingtext;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.UUID;

public class FlyingTextPagerActivity extends FragmentActivity
        implements SettingsDialogFlyingTextFragment.NoticeDialogListener,
        WinDialogFlyingTextFragment.WinDialogListener,
        SuperWinDialogFlyingTextFragment.SuperWinDialogListener {

    public static int sNumberOfWins;
    public static int sNumberOfSuperWins;
    private boolean mStateOfCheckBox = MenuFlyingTextFragment.sFlagMusicOff;
    private boolean mStateOfCheckBoxAllSounds = MenuFlyingTextFragment.sFlagAllSoundsOff;
    private ViewPager mViewPager;
    private ArrayList<Level> mLevels;

    private MyAudioPlayer musicPlayer;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        mStateOfCheckBox = ((CheckBox) view).isChecked();
       // MenuFlyingTextFragment.sFlagMusicOff = mStateOfCheckBox;
    }

    public void onCheckboxAllSoundsOffClicked(View view) {
        // Is the view now checked?
        mStateOfCheckBoxAllSounds = ((CheckBox) view).isChecked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        musicPlayer = new MyAudioPlayer();
      /*  if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
                musicPlayer.play(getApplicationContext(), R.raw.klassika);
                musicPlayer.setLoopingPlay(true);
            }
        }  */

        mLevels = LevelsLab.get(this).getLevels();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mLevels.size() - 9;
            }

            @Override
            public Fragment getItem(int pos) {
                Level level = mLevels.get(pos);
                return FlyingTextFragment.newInstance(level.getId());
            }

            @Override
            public int getItemPosition(Object object){
                return POSITION_NONE;
            }
        });

        UUID LevelId = (UUID)getIntent()
                .getSerializableExtra(FlyingTextFragment.EXTRA_LEVEL_ID);
        for (int i = 0; i < mLevels.size(); i++) {
            if (mLevels.get(i).getId().equals(LevelId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        sNumberOfWins = 0;
        sNumberOfSuperWins = 0;

        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
                musicPlayer.play(getApplicationContext(), R.raw.klassika);
                musicPlayer.setLoopingPlay(true);
            }
        }
    }


    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        MenuFlyingTextFragment.sFlagMusicOff = mStateOfCheckBox;
        MenuFlyingTextFragment.sFlagAllSoundsOff = mStateOfCheckBoxAllSounds;
        onPause();
        onResume();
    }


    @Override
    public void onAgainClickWin() {
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAgainClickSuperWin () {
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onNextLevelClickWin() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

    }

    @Override
    public void onNextLevelClickSuperWin() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
         /*   mPlayer = MediaPlayer.create(this, R.raw.klassika);
            mPlayer.setLooping(true);
            mPlayer.start(); */
                musicPlayer.play(getApplicationContext(), R.raw.klassika);
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
