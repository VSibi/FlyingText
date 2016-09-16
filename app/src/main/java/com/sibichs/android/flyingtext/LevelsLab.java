package com.sibichs.android.flyingtext;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class LevelsLab {
    private ArrayList<Level> mLevels;
    private static LevelsLab sLevelsLab;
    private Context mAppContext;

    private int[] mLevelsCategory = {
            R.string.Levels_category_animals, R.string.House
    };

    private int[] mLevelWordsEn = {
            R.string.Dog, R.string.Cat, R.string.Fox, R.string.Sky, R.string.Door,
            R.string.Hand, R.string.Phone, R.string.Water, R.string.House,

            R.string.Black, R.string.Green, R.string.White, R.string.Dasha, R.string.Slavik,
            R.string.Natasha, R.string.Window, R.string.Tablet, R.string.Android,

            R.string.Capital, R.string.Water, R.string.Window, R.string.Tablet, R.string.Door,
            R.string.Black, R.string.Green, R.string.Tablet, R.string.Fox
    };

    private int[] mLevelWordsRu = {
            R.string.Cat, R.string.House, R.string.Fox, R.string.Hand, R.string.Water,
            R.string.Sky, R.string.Door, R.string.Dog, R.string.Phone,

            R.string.Window, R.string.White, R.string.Black, R.string.Green, R.string.Dasha,
            R.string.Slavik, R.string.Natasha, R.string.Tablet, R.string.Android,

            R.string.Capital, R.string.Water, R.string.Window, R.string.Tablet, R.string.Door,
            R.string.Black, R.string.Green, R.string.Tablet, R.string.Fox
    };


    private int[] mLevelImagesEn = {
            R.drawable.level_image_1, R.drawable.level_image_2, R.drawable.level_image_3,
            R.drawable.level_image_4, R.drawable.level_image_5, R.drawable.level_image_6,
            R.drawable.level_image_7, R.drawable.level_image_8, R.drawable.level_image_9,

            R.drawable.level_image_10, R.drawable.level_image_11, R.drawable.level_image_12,
            R.drawable.level_image_13, R.drawable.level_image_14, R.drawable.level_image_15,
            R.drawable.level_image_16, R.drawable.level_image_17, R.drawable.level_image_18,

            R.drawable.level_image_19, R.drawable.level_image_20, R.drawable.level_image_21,
            R.drawable.level_image_22, R.drawable.level_image_23, R.drawable.level_image_24,
            R.drawable.level_image_25, R.drawable.level_image_26, R.drawable.level_image_27
    };


    private int[] mLevelImagesRu = {
            R.drawable.level_image_2, R.drawable.level_image_9, R.drawable.level_image_3,
            R.drawable.level_image_6, R.drawable.level_image_8, R.drawable.level_image_4,
            R.drawable.level_image_5, R.drawable.level_image_1, R.drawable.level_image_7,

            R.drawable.level_image_10, R.drawable.level_image_11, R.drawable.level_image_12,
            R.drawable.level_image_13, R.drawable.level_image_14, R.drawable.level_image_15,
            R.drawable.level_image_16, R.drawable.level_image_17, R.drawable.level_image_18,

            R.drawable.level_image_19, R.drawable.level_image_20, R.drawable.level_image_21,
            R.drawable.level_image_22, R.drawable.level_image_23, R.drawable.level_image_24,
            R.drawable.level_image_25, R.drawable.level_image_26, R.drawable.level_image_27
    };

    private LevelsLab(Context appContext) {
        mAppContext = appContext;
      //  String mLevelTitle = mAppContext.getResources().getString(R.string.Level);
       // String mLevelTitle = mAppContext.getString(R.string.Level);
        mLevels = new ArrayList<Level>();
        String locale = mAppContext.getResources().getConfiguration().locale.getLanguage();
        if(locale.equals("ru")) {
            for (int i = 0; i < mLevelWordsRu.length; i++) {
                Level l = new Level();
                if (i < 18) l.setCategoryTitle(mAppContext.getString(mLevelsCategory[0]));
                if (i > 17 & i < 27) l.setCategoryTitle(mAppContext.getString(mLevelsCategory[1]));
                l.setTitle(" " + (i+1));
                l.setWord(mAppContext.getString(mLevelWordsRu[i]));
                //  l.setWord(mAppContext.getString(R.string.Natasha));
                l.setLevelImageResId(mLevelImagesRu[i]);
                mLevels.add(l);
            }
        }
        else {
            for (int i = 0; i < mLevelWordsEn.length; i++) {
                Level l = new Level();
                if (i < 18) l.setCategoryTitle(mAppContext.getString(mLevelsCategory[0]));
                if (i > 17 & i < 27) l.setCategoryTitle(mAppContext.getString(mLevelsCategory[1]));
                l.setTitle(" " + (i + 1));
                l.setWord(mAppContext.getString(mLevelWordsEn[i]));
                //  l.setWord(mAppContext.getString(R.string.Natasha));
                l.setLevelImageResId(mLevelImagesEn[i]);
                mLevels.add(l);
            }
        }
    }
    public static LevelsLab get(Context c) {
        if (sLevelsLab == null) {
        sLevelsLab = new LevelsLab(c.getApplicationContext());
        }
        return sLevelsLab;
    }

    public ArrayList<Level> getLevels() {
        return mLevels;

    }
    public Level getLevel(UUID id) {
        for (Level l : mLevels) {
            if (l.getId().equals(id))
                return l;
        }
        return null;
    }



}
