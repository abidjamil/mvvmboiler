package co.appdev.boilerplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.ui.main.MainMvpView;
import co.appdev.boilerplate.ui.main.MainPresenter;
import co.appdev.boilerplate.utils.RxSchedulersOverrideRule;
import co.appdev.boilerplate.utils.TestDataFactory;
import rx.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {


    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadUsersReturnsUsers() {
        List<Users> users = TestDataFactory.makeListUsers(10);
        when(mMockDataManager.getUsers())
                .thenReturn(Observable.just(users));

        mMainPresenter.loadUsers();
        verify(mMockMainMvpView).showUsers(users);
        verify(mMockMainMvpView, never()).showUsersEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadUsersReturnsEmptyList() {
        when(mMockDataManager.getUsers())
                .thenReturn(Observable.just(Collections.<Users>emptyList()));

        mMainPresenter.loadUsers();
        verify(mMockMainMvpView).showUsersEmpty();
        verify(mMockMainMvpView, never()).showUsers(ArgumentMatchers.<Users>anyList());
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadUsersFails() {
        when(mMockDataManager.getUsers())
                .thenReturn(Observable.<List<Users>>error(new RuntimeException()));

        mMainPresenter.loadUsers();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showUsersEmpty();
        verify(mMockMainMvpView, never()).showUsers(ArgumentMatchers.<Users>anyList());
    }
}
