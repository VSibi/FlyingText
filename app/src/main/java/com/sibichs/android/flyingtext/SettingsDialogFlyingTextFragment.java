package com.sibichs.android.flyingtext;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;

public class SettingsDialogFlyingTextFragment extends DialogFragment {
   // public static final String EXTRA_FLAG_MUSIC_OFF =
      //      "com.bignerdranch.android.flyingtext.flag_music_off";

    private CheckBox mMusicOffCheckBox;
    private CheckBox mAllSoundsOffCheckBox;
   // private boolean mFlagMusicOff;


    /* The activity that creates an instance of this dialog fragment must
   * implement this interface in order to receive event callbacks.
   * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
     //   public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
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

     //   mFlagMusicOff = (Boolean)getArguments().getSerializable(EXTRA_FLAG_MUSIC_OFF);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_settings_dialog_flying_text, null);

        mMusicOffCheckBox = (CheckBox) v.findViewById(R.id.dialog_musicOffCheckBox);
        mMusicOffCheckBox.setChecked(MenuFlyingTextFragment.sFlagMusicOff);

        mAllSoundsOffCheckBox = (CheckBox) v.findViewById(R.id.dialog_all_soundsOffCheckBox);
        mAllSoundsOffCheckBox.setChecked(MenuFlyingTextFragment.sFlagAllSoundsOff);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        mListener.onDialogPositiveClick(SettingsDialogFlyingTextFragment.this);

                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingsDialogFlyingTextFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}

