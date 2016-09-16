package com.sibichs.android.flyingtext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuLevelsFlyingTextFragment extends Fragment {
    public static final String EXTRA_FIRST_LEVEL_IN_PAGE =
            "com.bignerdranch.android.flyingtext.first_level_in_page";
    private static final String DIALOG_BUY = "buy";
    private TextView mLevelsCategoryTitle;
    private ArrayList<Level> mLevels;
    private int mLevelNumber;
    private int mFirstLevelInPage;

    public static MenuLevelsFlyingTextFragment newInstance(int firstLevelInPage) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_FIRST_LEVEL_IN_PAGE, firstLevelInPage);
        MenuLevelsFlyingTextFragment fragment = new MenuLevelsFlyingTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstLevelInPage = (int)getArguments().getSerializable(EXTRA_FIRST_LEVEL_IN_PAGE);
        mLevelNumber = mFirstLevelInPage;
        mLevels = LevelsLab.get(getActivity()).getLevels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_levels_flying_text, parent, false);

        mLevelsCategoryTitle = (TextView) v.findViewById(R.id.menu_levels_category_title);
        Level firstLevelInPage = mLevels.get(mFirstLevelInPage);
        mLevelsCategoryTitle.setText(firstLevelInPage.getCategoryTitle());

        TableLayout tableLayout = (TableLayout)v.findViewById(R.id.menu_levels_tablelayout);
        if (mFirstLevelInPage == 0 | mFirstLevelInPage == 9) {
            tableLayout.setBackgroundResource(R.drawable.background_animals);
        }
        if (mFirstLevelInPage == 18) {
                tableLayout.setBackgroundResource(R.drawable.background_house);
        }
        for (int i = 3; i<(tableLayout.getChildCount()-2); i = i+2) {
            TableRow rowImage = (TableRow)tableLayout.getChildAt(i);
            TableRow rowText = (TableRow)tableLayout.getChildAt(i+1);
            if (rowImage.getChildCount() > 3) {
                for (int j = 0; j < rowImage.getChildCount(); j++) {
                    if (j == 0) {
                        if(mFirstLevelInPage == 0) {
                            ImageView imageview = (ImageView) rowImage.getChildAt(j);
                            imageview.setVisibility(View.INVISIBLE);
                            TextView textview = (TextView) rowText.getChildAt(j);
                            textview.setVisibility(View.INVISIBLE);
                        }
                    }
                    if ((j == rowImage.getChildCount() - 1)) {
                        if(mFirstLevelInPage  == (mLevels.size() - 9)) {
                            ImageView imageview = (ImageView) rowImage.getChildAt(j);
                            imageview.setVisibility(View.INVISIBLE);
                            TextView textview = (TextView) rowText.getChildAt(j);
                            textview.setVisibility(View.INVISIBLE);
                        }
                    }
                    if ((j != 0) & (j != rowImage.getChildCount() - 1)) {
                        Level level = mLevels.get(mLevelNumber);
                        ImageView imageview = (ImageView) rowImage.getChildAt(j);

                       // if ((mLevelNumber < 9) | (mLevelNumber > 17 ) ) {
                        if (mLevelNumber < 18) {
                            imageview.setImageResource(level.getLevelImageResId());
                            imageview.setOnClickListener(new MyImageClickListener(level));

                            TextView textview = (TextView) rowText.getChildAt(j);
                            String titleTextView = (String) textview.getText();
                            textview.setText(titleTextView + level.getTitle());
                        }
                        else {
                            imageview.setBackgroundResource(level.getLevelImageResId());
                            imageview.setImageResource(R.drawable.lock);
                            imageview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FragmentManager fm = getActivity()
                                            .getSupportFragmentManager();
                                    BuyDialogFlyingTextFragment dialog = new BuyDialogFlyingTextFragment();
                                    dialog.show(fm, DIALOG_BUY);
                                }
                            });

                            TextView textview = (TextView) rowText.getChildAt(j);
                            String titleTextView = (String) textview.getText();
                            textview.setText(titleTextView + level.getTitle());
                        }

                        mLevelNumber++;
                    }
                }
            }
            else {
                for (int j = 0; j < rowImage.getChildCount(); j++) {
                    Level level = mLevels.get(mLevelNumber);
                    ImageView imageview = (ImageView) rowImage.getChildAt(j);

                  //  if ((mLevelNumber < 9) | (mLevelNumber > 17 ) ){
                    if (mLevelNumber < 18) {
                        imageview.setImageResource(level.getLevelImageResId());
                        imageview.setOnClickListener(new MyImageClickListener(level));

                        TextView textview = (TextView) rowText.getChildAt(j);
                        String titleTextView = (String) textview.getText();
                        textview.setText(titleTextView + level.getTitle());
                    }
                    else {
                        imageview.setBackgroundResource(level.getLevelImageResId());
                        imageview.setImageResource(R.drawable.lock);
                        imageview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fm = getActivity()
                                        .getSupportFragmentManager();
                                BuyDialogFlyingTextFragment dialog = new BuyDialogFlyingTextFragment();
                                dialog.show(fm, DIALOG_BUY);
                            }
                        });

                        TextView textview = (TextView) rowText.getChildAt(j);
                        String titleTextView = (String) textview.getText();
                        textview.setText(titleTextView + level.getTitle());
                    }

                    mLevelNumber++;
                }
            }

          //  else {
            /*if (i % 2 == 0) {
                for (int j = 0; j < row.getChildCount(); j++) {
                    TextView textview = (TextView)row.getChildAt(j);
                    Level level = mLevels.get(number);
                    textview.setText(level.getTitle());
                    number++;
                }
            }*/
        }
        mLevelNumber = 0;
        return v;
    }

    protected class MyImageClickListener implements View.OnClickListener {
        private Level mLevel;

        public MyImageClickListener(Level level) {
            mLevel = level;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), FlyingTextPagerActivity.class);
            i.putExtra(FlyingTextFragment.EXTRA_LEVEL_ID, mLevel.getId());
            startActivity(i);
        }
    }

}
