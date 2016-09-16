package com.sibichs.android.flyingtext;

import android.content.Context;
import android.media.MediaPlayer;

public class MyAudioPlayer {
    private  MediaPlayer mPlayer;

    public MyAudioPlayer () {
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(Context c, int resId) {
        if (mPlayer != null) {
            mPlayer.start();
        }
        else {
            mPlayer =  MediaPlayer.create(c, resId);
            mPlayer.start();
        }
    }

    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    public void setLoopingPlay(boolean looping) {
        mPlayer.setLooping(looping);
    }
}
