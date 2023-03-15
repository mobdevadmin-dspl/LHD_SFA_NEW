package com.lankahardwared.lankahw.view.vansale;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Sathiyaraja on 12/7/2017.
 */

public class Chatfont  extends androidx.appcompat.widget.AppCompatTextView {

    public Chatfont(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public Chatfont(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public Chatfont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Exo2_Regular.otf", context);
        setTypeface(customFont);
    }
}