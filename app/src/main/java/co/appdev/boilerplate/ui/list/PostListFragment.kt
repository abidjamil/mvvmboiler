package co.appdev.boilerplate.ui.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.appdev.boilerplate.R
import co.appdev.boilerplate.databinding.PostListFragmentBinding


class PostListFragment : Fragment() {
    private lateinit var viewModel: UserPostListViewModel
    private lateinit var binding: PostListFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_list_fragment, container, false)
        val view = binding.root
        view.isClickable = true
        view.requestFocus()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val actionBar = (activity as AppCompatActivity).getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.title = getString(R.string.app_name)
        viewModel =
            ViewModelProviders.of(this).get(UserPostListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

    }

    private fun showError(@StringRes errorMessage: Int) {
        binding.textError.setText(errorMessage)
        binding.group.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.group.visibility = View.GONE
    }


}
