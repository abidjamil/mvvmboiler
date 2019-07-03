package co.appdev.boilerplate.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.databinding.DataBindingUtil
import co.appdev.boilerplate.R
import co.appdev.boilerplate.databinding.PostActivityBinding


class PostActivity : AppCompatActivity() {

    companion object {
        @NonNull
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PostActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<PostActivityBinding>(this, R.layout.post_activity)
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.nav_host_fragment).navigateUp()

}
