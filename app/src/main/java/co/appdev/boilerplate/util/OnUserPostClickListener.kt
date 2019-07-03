package co.appdev.boilerplate.util

import android.view.View
import co.appdev.boilerplate.ui.list.UserPostViewModel

interface OnUserPostClickListener {
    fun onUserPostClick(view: View, post: UserPostViewModel)
}