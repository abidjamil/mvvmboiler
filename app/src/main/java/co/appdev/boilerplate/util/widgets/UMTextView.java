package co.appdev.boilerplate.util.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import co.appdev.boilerplate.R;

public class UMTextView extends AppCompatTextView {
    public UMTextView(Context context) {
        super(context);
        init(null, context);
    }

    public UMTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public UMTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null && context != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UMTextView);
            String type = a.getString(R.styleable.UMTextView_viewtype);
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAM-BOOK.OTF");
            if (type != null ) {
                switch (type) {
                    case "bold":
                        myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAM-BOLD.OTF");
                        break;
                    case "light":
                        myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAM-LIGHT.OTF");
                        break;
                    case "medium":
                        myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAM-MEDIUM.OTF");
                        break;
                }
            }
            setTypeface(myTypeface);
            a.recycle();
        }
    }

}

