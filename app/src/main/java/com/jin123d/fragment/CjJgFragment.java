package com.jin123d.fragment;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jin123d.adapter.CjAdapter;
import com.jin123d.app.BaseFragment;
import com.jin123d.models.CjModels;
import com.jin123d.urp.R;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.JsoupUtil;
import com.jin123d.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jin123d on 2015/9/14.
 **/
public class CjJgFragment extends BaseFragment {
    private ProgressDialog progressDialog;
    private CjAdapter adapter;
    private List<CjModels> lists;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_cjjg, container, false);
    }


    @Override
    protected void initView(View view) {
        ListView lv_zjsj = (ListView) view.findViewById(R.id.lv_zjsj);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.getJg));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        lists = new ArrayList<>();
        adapter = new CjAdapter(lists, getActivity());
        lv_zjsj.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getInfo();
    }


    void getInfo() {
        HttpUtil.getAllCjList(this, new JsoupCallBack() {
            @Override
            public void onSuccess(Document document, Call call, Response response) {
                progressDialog.dismiss();
                if (getText(R.string.webTitle).equals(document.title())) {
                    Toast.makeText(getActivity(), getText(R.string.loginFail),
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    lists.clear();
                    lists.addAll(JsoupUtil.getAllCj(document));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
