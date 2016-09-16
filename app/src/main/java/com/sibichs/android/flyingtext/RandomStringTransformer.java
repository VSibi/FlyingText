package com.sibichs.android.flyingtext;

import java.util.Random;

public class RandomStringTransformer {
    private String mTransformedString;

    public RandomStringTransformer (String string) {
        Random RandomIndex = new Random();
        char[] st = string.toCharArray();
        int n = st.length;
        while(n > 1) {
            int i = RandomIndex.nextInt(n);
            n--;
            char tmp = st[n];
            st[n] = st[i];
            st[i] = tmp;
        }
        mTransformedString = String.valueOf(st);
  //      mTransformedString = string;


   /*     char[] TmpString = new char[string.length()];
        for (int i=0; i<TmpString.length; i++) {
            TmpString[i] = '*';
        }

        Random RandomIndex = new Random();
        for (int i=0; i<string.length(); i++) {
            char letter = string.charAt(i);
            int index = 0;
            while (true) {
                index = RandomIndex.nextInt(string.length());
                if (TmpString[index] == '*') {
                    TmpString[index] = letter;
                    break;
                }
            }
        }
        mTransformedString= String.valueOf(TmpString); */
    }


    public String getTransformedString() {
        return mTransformedString;

    }

}
