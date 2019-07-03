package co.appdev.boilerplate.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.appdev.boilerplate.R
import co.appdev.boilerplate.data.model.UserPost
import co.appdev.boilerplate.databinding.ItemPostBinding
import co.appdev.boilerplate.util.OnUserPostClickListener


class PostListAdapter : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList: List<UserPost>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList: List<UserPost>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root),
        OnUserPostClickListener {
        private val viewModel = UserPostViewModel()

        fun bind(post: UserPost) {
            viewModel.bind(post)
            binding.viewModel = viewModel
            binding.listener = this
        }

        override fun onUserPostClick(view: View, post: UserPostViewModel) {
            val directions = PostListFragmentDirections.actionMainFragmentToPostDetailFragment(post.getUserPost())
            view.findNavController().navigate(directions)
        }
    }
}