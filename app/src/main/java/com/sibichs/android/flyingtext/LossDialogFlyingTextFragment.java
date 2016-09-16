package com.sibichs.android.flyingtext;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;


public class LossDialogFlyingTextFragment extends DialogFragment {

    private ImageButton mAgain;
    private ImageView mImageView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_loss_dialog_flying_text, null);

        mImageView = (ImageView) v.findViewById(R.id.dialog_loss_imageView);
        Animation lossAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.loss_anim);
        mImageView.startAnimation(lossAnimation);

        mAgain = (ImageButton) v.findViewById(R.id.dialog_loss_again_imageButton);
        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LossDialogFlyingTextFragment.this.getDialog().cancel();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }
}

