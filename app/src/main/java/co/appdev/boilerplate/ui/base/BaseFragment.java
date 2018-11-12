package co.appdev.boilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.appdev.boilerplate.AndroidComponentsApp;
import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.injection.component.ActivityComponent;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public static final String ARGS_INSTANCE = "co.appdev.boilerplate.argsInstance";

    public View parent;
    int mInt = 0;

    @Inject
    public DataManager dataManager;
    public BaseActivity mActivity;
    private Unbinder mUnBinder;
    public FragmentNavigation mFragNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
        AndroidComponentsApp.get(getContext()).getComponent().inject(this);
        if (getActivityComponent() != null)
            getActivityComponent().inject(this);

    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.activityComponent();
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            mFragNavigation = activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
           parent = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, parent);
            parent.setOnClickListener(this);
            initViews(parent);
        return parent;
    }

    public void setActionBarTitle(String title) {
        mActivity.setActionBarTitle(title);
    }


    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public interface FragmentNavigation {

        public void pushFragment(Fragment fragment);

        public void replaceFragment(Fragment fragment);

        public void navigationTitle(Fragment fragment);

        public void popFragment();

        public void switchTab(int tabNumber);

        public void showDialogFragment(DialogFragment dialogFragment);

        public void clearDialogFragment();

        public void popFragment(int count);
    }


    public abstract void initViews(View parentView);

    public abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
