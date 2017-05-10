package co.appdev.boilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;



public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public static final String ARGS_INSTANCE = "co.appdev.boilerplate.argsInstance";

    public View parent;
    public FragmentNavigation mFragmentNavigation;
    int mInt = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
            mFragmentNavigation.navigationTitle(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            parent = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, parent);
            parent.setOnClickListener(this);
            initViews(parent);
        return parent;
    }

    public interface FragmentNavigation {
        public void pushFragment(Fragment fragment, boolean detach);
        public void replaceFragment(Fragment fragment);
        public void navigationTitle(Fragment fragment);
        public void popCurrentFragment();
    }

    public abstract void initViews(View parentView);

    public abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
