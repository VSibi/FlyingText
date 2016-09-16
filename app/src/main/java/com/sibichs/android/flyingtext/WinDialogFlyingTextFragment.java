package com.sibichs.android.flyingtext;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;


public class WinDialogFlyingTextFragment extends DialogFragment {
   // public static final String EXTRA_FLAG_MUSIC_OFF =
      //      "com.bignerdranch.android.flyingtext.flag_music_off";

    private ImageButton mAgain;
    private ImageButton mNextLevel;
    private ImageView mImageView, mImageViewRays;

    MyAudioPlayer soundsPlayer;


    /* The activity that creates an instance of this dialog fragment must
   * implement this interface in order to receive event callbacks.
   * Each method passes the DialogFragment in case the host needs to query it. */
    public interface WinDialogListener {
       public void onNextLevelClickWin();
        public void onAgainClickWin();
        // public void onDialogPositiveClick(DialogFragment dialog);
     //   public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    WinDialogListener mWinListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mWinListener = (WinDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


  /*  public static SettingsDialogFlyingTextFragment newInstance(Boolean FlagMusicOff) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_FLAG_MUSIC_OFF, FlagMusicOff);
        SettingsDialogFlyingTextFragment fragment = new SettingsDialogFlyingTextFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        soundsPlayer = new MyAudioPlayer();

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_win_dialog_flying_text, null);

        mImageViewRays = (ImageView) v.findViewById(R.id.dialog_win_imageView_sun);
        Animation winAnimationRays = AnimationUtils.loadAnimation(getActivity(), R.anim.win_anim_rays);
        // winAnimationRays.setRepeatCount(4);

        mImageViewRays.startAnimation(winAnimationRays);

        mImageView = (ImageView) v.findViewById(R.id.dialog_win_imageView);
        Animation winAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.win_anim);
        mImageView.startAnimation(winAnimation);

        mAgain = (ImageButton) v.findViewById(R.id.dialog_win_again_imageButton);
        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWinListener.onAgainClickWin();
                WinDialogFlyingTextFragment.this.getDialog().cancel();
            }
        });

        mNextLevel = (ImageButton) v.findViewById(R.id.dialog_win_next_imageButton);
        mNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWinListener.onNextLevelClickWin();
                WinDialogFlyingTextFragment.this.getDialog().cancel();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
           // if (!MenuFlyingTextFragment.sFlagMusicOff) {
           //     mSoundPlayer = MediaPlayer.create(getActivity(), R.raw.success);
              //  mSoundPlayer.setLooping(true);
             //   mSoundPlayer.start();
            soundsPlayer.play(getActivity(), R.raw.success);
          //  }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  if (mSoundPlayer != null) {
            mSoundPlayer.pause();
        }*/
        soundsPlayer.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
      /*  if (mSoundPlayer != null) {
            mSoundPlayer.stop();
            mSoundPlayer.release();
            mSoundPlayer = null;
        }*/
        soundsPlayer.stop();
    }

}

