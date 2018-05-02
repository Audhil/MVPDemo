package com.audhil.medium.mvpdemo.ui.customviews

import android.content.Context
import android.graphics.Typeface
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet
import com.audhil.medium.mvpdemo.util.ConstantsUtil

/*
 * Created by mohammed-2284 on 02/05/18.
 */

class DubaiTextInputEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun setTypeface(tf: Typeface) {
        if (!isInEditMode) {
            super.setTypeface(Typeface.createFromAsset(context.assets, ConstantsUtil.DUBAI_FONT_REGULAR))
        }
    }
}