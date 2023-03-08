package com.emilio.popularmovie.common

import android.view.View

interface OnSelectItem {
    fun onSelect(view: View, pos: Int, typeClick: TypeClick)
}