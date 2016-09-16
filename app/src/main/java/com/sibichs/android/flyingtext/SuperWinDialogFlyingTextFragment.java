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


public class SuperWinDialogFlyingTextFragment extends DialogFragment {

    private ImageView mSuperWinImage;
    private ImageButton mAgain;
    private ImageButton mNextLevel;

    /* The activity that creates an instance of this dialog fragment must
* implement this interface in order to receive event callbacks.
* Each method passes the DialogFragment in case the host needs to query it. */
    public interface SuperWinDialogListener {
        public void onNextLevelClickSuperWin();
        public void onAgainClickSuperWin();
        // public void onDialogPositiveClick(DialogFragment dialog);
        //   public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    SuperWinDialogListener mWinListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mWinListener = (SuperWinDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        FlyingTextPagerActivity.sNumberOfSuperWins = FlyingTextPagerActivity.sNumberOfSuperWins + 1;

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_superwin_dialog_flying_text, null);

        mSuperWinImage = (ImageView) v.findViewById(R.id.dialog_superwin_imageView);

        switch (FlyingTextPagerActivity.sNumberOfSuperWins) {
            case 1:
                mSuperWinImage.setImageResource(R.drawable.level_image_6);
                break;
            case 2:
                mSuperWinImage.setImageResource(R.drawable.level_image_7);
                break;
            case 3:
                mSuperWinImage.setImageResource(R.drawable.level_image_8);
                break;
            case 4:
                mSuperWinImage.setImageResource(R.drawable.level_image_9);
                break;
            case 5:
                mSuperWinImage.setImageResource(R.drawable.level_image_4);
                break;
        }
        if (FlyingTextPagerActivity.sNumberOfSuperWins == 5) {
            FlyingTextPagerActivity.sNumberOfSuperWins = 0;
        }

        Animation superWinAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.super_win_anim);
        mSuperWinImage.startAnimation(superWinAnimation);


        mAgain = (ImageButton) v.findViewById(R.id.dialog_superwin_again_imageButton);
        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWinListener.onAgainClickSuperWin();
                SuperWinDialogFlyingTextFragment.this.getDialog().cancel();
            }
        });

        mNextLevel = (ImageButton) v.findViewById(R.id.dialog_superwin_next_imageButton);
        mNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWinListener.onNextLevelClickSuperWin();
                SuperWinDialogFlyingTextFragment.this.getDialog().cancel();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }
}

