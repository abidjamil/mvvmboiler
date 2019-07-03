package co.appdev.boilerplate.ui.list

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.appdev.boilerplate.MyApplication
import co.appdev.boilerplate.R
import co.appdev.boilerplate.data.DataManager
import co.appdev.boilerplate.data.model.UserPost
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserPostListViewModel @Inject
internal constructor(application: Application ) : AndroidViewModel(application) {

    val postListAdapter: PostListAdapter = PostListAdapter()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    @Inject
    lateinit var dataManager: DataManager

    init {
        MyApplication[application].component?.inject(this)
        loadPosts()
    }

    private fun loadPosts() {
        compositeDisposable.add(Observable.fromCallable { dataManager.userPostDao.getAll() }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty())
                    dataManager.remoteApi.getPosts().concatMap { apiPostList ->
                        dataManager.userPostDao.insertAll(apiPostList)
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnTerminate { onTerminate() }
            .subscribe(
                { result -> onDataReceived(result) },
                { onError() }
            ))
    }

    private fun onSubscribe() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onTerminate() {
        loadingVisibility.value = View.GONE
    }

    private fun onDataReceived(postList: List<UserPost>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onError() {
        errorMessage.value = R.string.post_error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}