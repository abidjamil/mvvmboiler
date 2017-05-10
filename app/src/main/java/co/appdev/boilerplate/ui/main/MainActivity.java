package co.appdev.boilerplate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.appdev.fragmentnavigation.FragNavController;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.appdev.boilerplate.R;
import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.ui.base.BaseActivity;
import co.appdev.boilerplate.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    RibotsAdapter mRibotsAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        activityComponent().inject(this);
        ButterKnife.bind(this);
        mRecyclerView.setAdapter(mRibotsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadUsers();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }



    /***** MVP View methods implementation *****/

    @Override
    public void showUsers(List<Users> users) {
        mRibotsAdapter.setRibots(users);
        mRibotsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots)).show();
    }

    @Override
    public void showUsersEmpty() {
        mRibotsAdapter.setRibots(Collections.<Users>emptyList());
        mRibotsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void pushFragment(Fragment fragment, boolean detach) {
        if (fragment != null) {
            mNavController.pushFragment(fragment, detach);
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            mNavController.replaceFragment(fragment);
        }
    }

    @Override
    public void navigationTitle(Fragment fragment) {
        if (fragment != null) {

        }
    }

    @Override
    public void popCurrentFragment() {
        mNavController.popFragment();
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

    }
}
