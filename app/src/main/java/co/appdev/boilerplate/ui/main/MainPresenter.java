package co.appdev.boilerplate.ui.main;

import javax.inject.Inject;

import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.injection.ConfigPersistent;
import co.appdev.boilerplate.ui.base.BasePresenter;
import co.appdev.boilerplate.util.RxUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void loadUsers() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getmApiService().getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getUsersResponse -> {
                    getMvpView().showUsers(getUsersResponse.getUsers());
                }, throwable -> {
                    getMvpView().showError();
                });
    }

}