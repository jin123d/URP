package com.jin123d.app;

import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.jin123d.urp.R;

/**
 * Created by jin123d on 11/19 0019.
 **/

public abstract class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 重置进度框
     */
    private void resetProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        resetProgressDialog();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
    }
}
