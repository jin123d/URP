package com.jin123d.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jin123d on 11/19 0019.
 **/

public abstract class BaseFragment extends Fragment {
    protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView(View view);

    protected abstract void initData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }
}
