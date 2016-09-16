package com.sibichs.android.flyingtext;


import java.util.UUID;

public class Level {
    private UUID mId;
    private String mCategoryTitle;
    private String mTitle;
    private String mWord;
    private int mLevelImageResId;

    public Level() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getCategoryTitle() {
        return mCategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        mCategoryTitle = categoryTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public int getLevelImageResId() {
        return mLevelImageResId;
    }

    public void setLevelImageResId(int levelImageResId) {
        mLevelImageResId = levelImageResId;
    }
}
