package com.jin123d.app;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jin123d.urp.R;

/**
 * Created by jin123d on 11/19 0019.
 **/

public abstract class BaseFragment extends Fragment {
    protected ProgressDialog progressDialog;

    protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView(View view);

    protected abstract void initData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetProgressDialog();
    }

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

    /**
     * 重置进度框
     */
    private void resetProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage(getText(R.string.getData));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * dismiss进度框
     */
    protected void progressDialogDismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * show进度框
     */
    protected void progressDialogShow() {
        resetProgressDialog();
        progressDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
    }
}
