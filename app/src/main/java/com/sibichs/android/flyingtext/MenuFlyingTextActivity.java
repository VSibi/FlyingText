package com.sibichs.android.flyingtext;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;

public class MenuFlyingTextActivity extends SingleFragmentActivity
        implements SettingsDialogFlyingTextFragment.NoticeDialogListener {

    private boolean mStateOfCheckBox = MenuFlyingTextFragment.sFlagMusicOff;
    private boolean mStateOfCheckBoxAllSounds = MenuFlyingTextFragment.sFlagAllSoundsOff;

    MyAudioPlayer musicPlayer;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        mStateOfCheckBox = ((CheckBox) view).isChecked();
    }

    public void onCheckboxAllSoundsOffClicked(View view) {
        // Is the view now checked?
        mStateOfCheckBoxAllSounds = ((CheckBox) view).isChecked();
    }

    @Override
    protected Fragment createFragment() {
        musicPlayer = new MyAudioPlayer();
        return new MenuFlyingTextFragment();
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
    public void onResume() {
        super.onResume();
        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
            if (!MenuFlyingTextFragment.sFlagMusicOff) {
           /* mPlayer = MediaPlayer.create(this, R.raw.klassika);
            mPlayer.setLooping(true);
            mPlayer.start();*/
                musicPlayer.play(getApplicationContext(), R.raw.bethoven);
                musicPlayer.setLoopingPlay(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        } */
        musicPlayer.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        musicPlayer.stop();
    }

}
