package co.appdev.boilerplate.util.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView
import co.appdev.boilerplate.R

class AATextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init(null, context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, context)
    }

    private fun init(attrs: AttributeSet?, context: Context?) {
        if (attrs != null && context != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AATextView)
            val type = a.getInt(R.styleable.AATextView_viewtype, 0)
            val myTypeface: Typeface
            when (type) {
                0//bold
                -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-Bold.otf")
                1//italic
                -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-It.otf")
                2//light
                -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-Light.otf")
                3//regular
                -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-Regular.otf")
                4//semibold
                -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-Semibold.otf")
                else -> myTypeface = Typeface.createFromAsset(getContext().assets, "fonts/SourceSansPro-Light.otf")//default
            }
            typeface = myTypeface
            a.recycle()
        }
    }

}

