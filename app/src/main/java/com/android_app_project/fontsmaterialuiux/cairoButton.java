package com.android_app_project.fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.android_app_project.R;


/**
 * for more visit http://materialuiux.com
 */
public class cairoButton  extends AppCompatButton
{

    public cairoButton(Context context)
    {
        super(context);
        init();
    }

    public cairoButton(Context context, AttributeSet attrs)
    {
        this(context, attrs, androidx.appcompat.R.attr.borderlessButtonStyle);
        init();
    }

    public cairoButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            setTextSize(18);
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cairo-Regular.ttf");
            setTypeface(tf);
        }
    }
}
