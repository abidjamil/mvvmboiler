package co.appdev.boilerplate.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.appdev.boilerplate.data.model.UserPost

class UserPostViewModel : ViewModel() {
    private val title = MutableLiveData<String>()
    private lateinit var userPost: UserPost

    fun bind(post: UserPost) {
        this.userPost = post
        this.title.value = post.title
    }

    fun getPostTitle(): MutableLiveData<String> {
        return title
    }

    fun getUserPost(): UserPost {
        return userPost
    }
}