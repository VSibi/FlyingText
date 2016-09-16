package com.sibichs.android.flyingtext;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

public class FlyingTextFragment extends Fragment {
    public static final String TAG = "Flying_Text";
    public static final String EXTRA_LEVEL_ID =
            "com.bignerdranch.android.flyingtext.level_id";
    private static final String[] TEXTVIEW_TAG = {"LETTER 1", "LETTER 2", "LETTER 3", "LETTER 4",
            "LETTER 5", "LETTER 6", "LETTER 7"};
    private static final String DIALOG_SETTINGS = "settings";
    private static final String DIALOG_WIN = "win";
    private static final String DIALOG_LOSE = "lose";
    private static final String DIALOG_SUPERWIN = "superwin";

    Stack mIndexDelTargetText = new Stack();
    Stack mIndexDelFlyingText = new Stack();

    private MyAudioPlayer soundPlayer;


    private ImageButton mHelp;
    private ImageButton mSettingsDialogButton;
    private Button mDeleteLetterButton;

    private int mLevelImageResId;
    private ImageView mLevelImage;

    private TextView mLevelsCategoryTitle;
    private TextView mLevelTitle;

    private String mLevelsCategoryTitleString;
    private String mLevelTitleString;
    private String mLevelWord;
    private String mLevelWordTransformed = "";
    private String mCurrLevelWord = "";

    TableLayout mTableLayout;
    TableRow mRowTargetTextView;
    TableRow mRowFlyingTextView;

    private ArrayList<Level> mLevels;
    private int mStartLockedLevels;
 //   private AudioManager am;


    public static FlyingTextFragment newInstance(UUID levelId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_LEVEL_ID, levelId);
        FlyingTextFragment fragment = new FlyingTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        soundPlayer = new MyAudioPlayer();

        UUID levelId = (UUID) getArguments().getSerializable(EXTRA_LEVEL_ID);
        Level level = LevelsLab.get(getActivity()).getLevel(levelId);
        mLevelImageResId = level.getLevelImageResId();
        mLevelWord = level.getWord();

      /*  do {
        RandomStringTransformer StringTransformer = new RandomStringTransformer(mLevelWord);
        mLevelWordTransformed = StringTransformer.getTransformedString();
        } while (mLevelWordTransformed.equals(mLevelWord)); */

        mLevelWordTransformed = StringTransformer(mLevelWord);


    /*    RandomStringTransformer StringTransformer = new RandomStringTransformer(mLevelWord);
        mLevelWordTransformed = StringTransformer.getTransformedString();

        if (mLevelWordTransformed.equals(mLevelWord)) {
            StringTransformer = new RandomStringTransformer(mLevelWord);
            mLevelWordTransformed = StringTransformer.getTransformedString();
        } */

        mLevelsCategoryTitleString = level.getCategoryTitle();
        mLevelTitleString = level.getTitle();

        mLevels = LevelsLab.get(getActivity()).getLevels();
        for (int i = 0; i < mLevels.size(); i++) {
            if (mLevels.get(i).getId().equals(levelId)) {
                mStartLockedLevels = i;
                break;
            }
        }
       // am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flying_text, parent, false);

        //    am.setStreamMute(AudioManager.STREAM_MUSIC, MenuFlyingTextFragment.sFlagMusicOff);


        mHelp = (ImageButton) v.findViewById(R.id.button_help);
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HelpFlyingTextActivity.class);
                startActivity(i);
            }
        });

        mSettingsDialogButton = (ImageButton) v.findViewById(R.id.button_dialog_settings);
        mSettingsDialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                SettingsDialogFlyingTextFragment dialog = new SettingsDialogFlyingTextFragment();
                dialog.show(fm, DIALOG_SETTINGS);
            }
        });

        mLevelsCategoryTitle = (TextView) v.findViewById(R.id.levels_category_title);
        mLevelsCategoryTitle.setText(mLevelsCategoryTitleString);

        mLevelTitle = (TextView) v.findViewById(R.id.level_title);
        String titleTextView = (String) mLevelTitle.getText();
        mLevelTitle.setText(titleTextView + mLevelTitleString);
        //    mLevelTitle.setText(mLevelTitleString);


        mTableLayout = (TableLayout) v.findViewById(R.id.flyingtext_tablelayout);
        mRowTargetTextView = (TableRow) mTableLayout.getChildAt(3);
        mRowFlyingTextView = (TableRow) mTableLayout.getChildAt(5);


        int index = 0;
        TextView mNotUsedTargetTextView, mNotUsedFlyingTextView;
        switch (mLevelWordTransformed.length()) {
            case 3:
                index = 2;
                break;
            case 4:
                index = 1;
                mNotUsedTargetTextView = (TextView) mRowTargetTextView.getChildAt(6);
                mNotUsedTargetTextView.setVisibility(View.GONE);
                mNotUsedFlyingTextView = (TextView) mRowFlyingTextView.getChildAt(6);
                mNotUsedFlyingTextView.setVisibility(View.GONE);
                break;
            case 5:
                index = 1;
                break;
            case 6:
                mNotUsedTargetTextView = (TextView) mRowTargetTextView.getChildAt(6);
                mNotUsedTargetTextView.setVisibility(View.GONE);
                mNotUsedFlyingTextView = (TextView) mRowFlyingTextView.getChildAt(6);
                mNotUsedFlyingTextView.setVisibility(View.GONE);
                break;
        }


        for (int i = index; i < mLevelWordTransformed.length() + index; i++) {
            TextView targetTextView = (TextView) mRowTargetTextView.getChildAt(i);
            targetTextView.setVisibility(View.VISIBLE);
            targetTextView.setTag("NotUsed");
            targetTextView.setOnDragListener(new myDragEventListener());

            TextView flyingTextView = (TextView) mRowFlyingTextView.getChildAt(i);
            flyingTextView.setVisibility(View.VISIBLE);
            flyingTextView.setTag(TEXTVIEW_TAG[i]);
            String text = "" + mLevelWordTransformed.charAt(i - index);
            flyingTextView.setText(text);
           // if (mStartLockedLevels < 18) {
                flyingTextView.setOnTouchListener(new myTouchListener());
          //  }
        }

        mLevelWordTransformed = "";

        mLevelImage = (ImageView) v.findViewById(R.id.level_image);
      //  if (mStartLockedLevels < 18) {
            mLevelImage.setImageResource(mLevelImageResId);
      //  }
     /*   else {
            mLevelImage.setBackgroundResource(mLevelImageResId);
            mLevelImage.setImageResource(R.drawable.lock);
        } */

        mDeleteLetterButton = (Button) v.findViewById(R.id.button_delete_letter);
        mDeleteLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIndexDelTargetText.empty()) {
                    TextView mTmpView;
                    mTmpView = (TextView) mIndexDelTargetText.pop();
                    mTmpView.setText("");
                    mTmpView.setBackgroundResource(R.drawable.flying_text_shape_shadowed);
                    mTmpView.setTag("NotUsed");
                }

                if (!mIndexDelFlyingText.empty()) {
                    TextView mTmpView;
                    mTmpView = (TextView) mIndexDelFlyingText.pop();
                    mTmpView.setVisibility(View.VISIBLE);
                }
            }
        });
        return v;
    }

    protected class myDragEventListener implements View.OnDragListener {

        // This is the method that the system calls when it dispatches a drag event to the
        // listener.
        public boolean onDrag(View v, DragEvent event) {

            TextView mTmpView = (TextView) v;

            // Defines a variable to store the action type for the incoming event
            final int action = event.getAction();

            // Handles each of the expected events
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED:

                    // Determines if this View can accept the dragged data
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (mTmpView.getTag().equals("Used")) return false;


                        // As an example of what your application might do,
                        // applies a blue color tint to the View to indicate that it can accept
                        // data.
                        // v.setBackgroundColor(Color.BLUE);

                        // Invalidate the view to force a redraw in the new tint
                        // v.invalidate();

                        // returns true to indicate that the View can accept the dragged data.
                        return true;

                    }

                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:

                    if (mTmpView.getTag().equals("Used")) return true;
                    // Applies a green tint to the View. Return true; the return value is ignored.

                    v.setBackgroundResource(R.drawable.flying_text_shape_shadowed_entered);

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:

                    // Ignore the event
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    // Re-sets the color tint to blue. Returns true; the return value is ignored.
                    v.setBackgroundResource(R.drawable.flying_text_shape_shadowed);

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    return true;

                case DragEvent.ACTION_DROP:

                    soundPlayer.stop();

                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);

                    // Gets the text data from the item.
                    CharSequence dragData = item.getText();

                    // Displays a message containing the dragged data.
                    //  Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_LONG);

                    v.setBackgroundResource(R.drawable.flying_text_shape_shadowed_ended);
                    // Invalidates the view to force a redraw
                    v.invalidate();

                    Log.i(TAG, "  ACTION_DRAG_DROP");
                    mTmpView.setText(dragData);
                    mTmpView.setTag("Used");
                    mIndexDelTargetText.push(mTmpView);

                    String description = event.getClipDescription().getLabel().toString();
                    Log.i(TAG, description);
                    switch (description) {
                        case "LETTER 1":
                            mRowFlyingTextView.getChildAt(0).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(0));
                            break;
                        case "LETTER 2":
                            mRowFlyingTextView.getChildAt(1).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(1));
                            break;
                        case "LETTER 3":
                            mRowFlyingTextView.getChildAt(2).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(2));
                            break;
                        case "LETTER 4":
                            mRowFlyingTextView.getChildAt(3).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(3));
                            break;
                        case "LETTER 5":
                            mRowFlyingTextView.getChildAt(4).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(4));
                            break;
                        case "LETTER 6":
                            mRowFlyingTextView.getChildAt(5).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(5));
                            break;
                        case "LETTER 7":
                            mRowFlyingTextView.getChildAt(6).setVisibility(View.INVISIBLE);
                            mIndexDelFlyingText.push(mRowFlyingTextView.getChildAt(6));
                            break;
                    }

                    for (int i = 0; i < mRowTargetTextView.getChildCount(); i++) {
                        TextView mTmpTextView = (TextView) mRowTargetTextView.getChildAt(i);
                        //     if(mTmpTextView.getVisibility() == View.VISIBLE) {
                        mCurrLevelWord = mCurrLevelWord + mTmpTextView.getText();
                        //     }
                    }
                    if (mCurrLevelWord.length() == mLevelWord.length()) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        if (mCurrLevelWord.equals(mLevelWord)) {
                            // Toast.makeText(getActivity(), R.string.Correct, Toast.LENGTH_SHORT).show();
                            FlyingTextPagerActivity.sNumberOfWins = FlyingTextPagerActivity.sNumberOfWins + 1;
                            if (FlyingTextPagerActivity.sNumberOfWins == 3) {
                                FlyingTextPagerActivity.sNumberOfWins = 0;
                                SuperWinDialogFlyingTextFragment dialog = new SuperWinDialogFlyingTextFragment();
                                dialog.show(fm, DIALOG_SUPERWIN);
                            }
                            else {
                                WinDialogFlyingTextFragment dialog = new WinDialogFlyingTextFragment();
                                dialog.show(fm, DIALOG_WIN);
                            }
                        } else {
                            // Toast.makeText(getActivity(), R.string.TryAgain, Toast.LENGTH_SHORT).show();
                            LossDialogFlyingTextFragment dialog = new LossDialogFlyingTextFragment();
                            dialog.show(fm, DIALOG_LOSE);
                        }
                    }
                    mCurrLevelWord = "";

                    // Returns true. DragEvent.getResult() will return true.
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:

                    soundPlayer.stop();

                    // Does a getResult(), and displays what happened.
                    if (event.getResult()) {
                        Log.i(TAG, "  ACTION_DRAG_ENDED");


                    } else {
                        Log.i(TAG, "  ACTION_DRAG_NOT_ENDED");
                        // Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG);

                    }
                    // returns true; the value is ignored.
                    return true;

                // An unknown action type was received.
                default:
                    Log.e(TAG, "Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    }

    public String StringTransformer (String levelword) {
        String levelwordtransformed = "";
        do {
            RandomStringTransformer StringTransformer = new RandomStringTransformer(levelword);
            levelwordtransformed = StringTransformer.getTransformedString();
        } while (levelwordtransformed.equals(levelword));
        return levelwordtransformed;
    }

    protected class myTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            TextView mTmpView = (TextView) v;
            switch (event.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    Log.i(TAG, "  ACTION_DOWN");

                    if(!MenuFlyingTextFragment.sFlagAllSoundsOff) {
                            soundPlayer.play(getActivity(), R.raw.wings);
                            soundPlayer.setLoopingPlay(true);

                    }

                    ClipData.Item item = new ClipData.Item(mTmpView.getText());
                    ClipDescription description = new ClipDescription(mTmpView.getTag().toString(),
                            new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN});
                    ClipData dragData = new ClipData(description, item);

                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                    v.startDrag(dragData, myShadow, null, 0);
            }
            return true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlayer.stop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        soundPlayer.stop();
    }
}
