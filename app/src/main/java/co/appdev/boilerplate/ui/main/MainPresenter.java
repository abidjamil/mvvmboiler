package co.appdev.boilerplate.ui.main;

import java.util.List;

import javax.inject.Inject;

import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.injection.ConfigPersistent;
import co.appdev.boilerplate.ui.base.BasePresenter;
import co.appdev.boilerplate.util.RxUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

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
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadUsers() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getRibotsService().getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Users>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Users> users) {
                        if (users.isEmpty()) {
                            getMvpView().showUsersEmpty();
                        } else {
                            getMvpView().showUsers(users);
                        }
                    }
                });
    }

}