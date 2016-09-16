package com.sibichs.android.flyingtext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MenuFlyingTextFragment extends Fragment{
    private static final String DIALOG_BUY = "buy";
    private static final String DIALOG_SETTINGS = "settings";
    public static boolean sFlagMusicOff;
    public static boolean sFlagAllSoundsOff;
  //  private ArrayList<Level> mLevels;
    private Button mPlayButton;
    private Button mSettingsButton;
    private Button mHelpButton;
    private Button mBuyButton;


    private Animation playAnimation, settingsAnimation,
       helpAnimation, buyAnimation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     mLevels = LevelsLab.get(getActivity()).getLevels();
        sFlagMusicOff = false;
        sFlagAllSoundsOff = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_flying_text, parent, false);

        mPlayButton = (Button) v.findViewById(R.id.button_play);

        playAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_anim);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Level level = mLevels.get(0);
                Intent i = new Intent(getActivity(), FlyingTextPagerActivity.class);
                i.putExtra(FlyingTextFragment.EXTRA_LEVEL_ID, level.getId());*/
               /* Intent i = new Intent(getActivity(), MenuLevelsFlyingTextActivity.class);*/
                Intent i = new Intent(getActivity(), MenuLevelsFlyingTextPagerActivity.class);
                startActivity(i);
            }
        });

        mSettingsButton = (Button) v.findViewById(R.id.button_settings);

        settingsAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_anim);
        settingsAnimation.setStartOffset(200);

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(getActivity(), SettingsFlyingTextActivity.class);
                startActivity(i); */
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                SettingsDialogFlyingTextFragment dialog = new SettingsDialogFlyingTextFragment();
                dialog.show(fm, DIALOG_SETTINGS);
            }
        });

        mHelpButton = (Button) v.findViewById(R.id.button_help);

        helpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_anim);
        helpAnimation.setStartOffset(400);

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent i = new Intent(getActivity(), HelpFlyingTextActivity.class);
                startActivity(i); */
                Intent i = BuyWebPageFlyingTextActivity.newIntent(getActivity(), Uri.parse("http://google.com.ua"));
                startActivity(i);
            }
        });
        mBuyButton = (Button) v.findViewById(R.id.button_buy);

        buyAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_anim);
        buyAnimation.setStartOffset(600);


        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                BuyDialogFlyingTextFragment dialog = new BuyDialogFlyingTextFragment();
                dialog.show(fm, DIALOG_BUY);
             /*   Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ru.alexanderklimov.crib"));
                startActivity(i); */
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayButton.startAnimation(playAnimation);
        mSettingsButton.startAnimation(settingsAnimation);
        mHelpButton.startAnimation(helpAnimation);
        mBuyButton.startAnimation(buyAnimation);
    }
}

