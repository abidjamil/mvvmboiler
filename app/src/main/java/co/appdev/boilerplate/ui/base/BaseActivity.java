package co.appdev.boilerplate.ui.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ncapdevi.fragnav.FragNavController;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.appdev.boilerplate.AndroidComponentsApp;
import co.appdev.boilerplate.injection.component.ActivityComponent;
import co.appdev.boilerplate.injection.component.ConfigPersistentComponent;
import co.appdev.boilerplate.injection.component.DaggerConfigPersistentComponent;
import co.appdev.boilerplate.injection.module.ActivityModule;
import co.appdev.boilerplate.util.widgets.UMTextView;
import timber.log.Timber;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseFragment.FragmentNavigation, FragNavController.TransactionListener {
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap = new LongSparseArray<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;
    private Unbinder mUnBinder;
    public Toolbar mToolbar;
    private int menuResId = -1;
    public FragNavController mNavController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(AndroidComponentsApp.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    public UMTextView getActionBarTitle() {
        return actionBarTitle;
    }

    public UMTextView actionBarTitle;

    public void setActionBarTitle(String actionBarTitle) {
        this.actionBarTitle.setText(actionBarTitle);
    }

    @Override
    public void onBackPressed() {
        if (mNavController != null && mNavController.getCurrentStack().size() > 1) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

    public abstract void initViews(Bundle savedInstanceState);
    public abstract int getLayoutId();

    public void switchTab(int tabNumber) {
        if (mNavController != null) {
            mNavController.switchTab(tabNumber);
        }
    }

    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    public void replaceFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.replaceFragment(fragment);
        }
    }

    public void popFragment() {
        if (mNavController != null) {
            mNavController.popFragment();
        }
    }

    public void showDialogFragment(DialogFragment fragment) {
        if (mNavController != null) {
            mNavController.showDialogFragment(fragment);
        }
    }

    public void clearDialogFragment() {
        if (mNavController != null) {
            mNavController.clearDialogFragment();
        }
    }

    public void popFragment(int count) {
        if (mNavController != null) {
            mNavController.popFragments(count);
        }
    }
}