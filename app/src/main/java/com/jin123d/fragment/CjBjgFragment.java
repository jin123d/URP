package com.jin123d.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jin123d.adapter.CjAdapter;
import com.jin123d.app.BaseFragment;
import com.jin123d.models.CjModels;
import com.jin123d.urp.R;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jin123d on 2015/9/14.
 **/
public class CjBjgFragment extends BaseFragment {
    private ProgressDialog progressDialog;
    private ListView lv_sbjg;
    private ListView lv_cbjg;
    private CjAdapter adapter;
    private CjAdapter adapter2;
    private List<CjModels> lists;
    private List<CjModels> lists2;
    private TextView tv_sbjg, tv_cbjg;
    private boolean mHasLoadedOnce = false;


    @Override
    protected void initView(View view) {
        lv_sbjg = (ListView) view.findViewById(R.id.lv_sbjg);
        lv_cbjg = (ListView) view.findViewById(R.id.lv_cbjg);
        tv_cbjg = (TextView) view.findViewById(R.id.tv_cbjg);
        tv_sbjg = (TextView) view.findViewById(R.id.tv_sbjg);
    }

    @Override
    protected void initData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.getBjg));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        lists = new ArrayList<>();
        lists2 = new ArrayList<>();
        adapter = new CjAdapter(lists, getActivity());
        adapter2 = new CjAdapter(lists2, getActivity());
        lv_cbjg.setAdapter(adapter);
        lv_sbjg.setAdapter(adapter2);
        getInfo();
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_cjbjg, container, false);
    }

    void getInfo() {
        HttpUtil.getBJgList(this, new JsoupCallBack() {
            @Override
            public void onSuccess(Document document, Call call, Response response) {
                progressDialog.dismiss();
                if (getText(R.string.webTitle).equals(document.title())) {
                    Toast.makeText(getActivity(), getText(R.string.loginFail),
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else if (getText(R.string.webTitleError).equals(document.title())) {
                    String ErrorContent = document.body().text();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle(document.title());
                    dialog.setMessage(ErrorContent);
                    dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();
                } else {
                    Elements es = document.select("[class=titleTop2]");
                    if (es.size() != 0) {
                        for (int i = 0; i < es.size(); i++) {
                            Elements es_2 = es.get(i).select("tr").select("td").select("table").get(0).select("tr");
                            for (int j = 0; j < es_2.size(); j++) {
                                Elements es_3 = es_2.get(j).select("td");
                                if (es_3.size() > 0) {
                                    String ckm = es_3.get(2).text();
                                    String xf = es_3.get(4).text();
                                    String cj = es_3.get(6).text();
                                    CjModels cjModels = new CjModels(ckm, xf, cj);
                                    if (i == 0) {
                                        lists.add(cjModels);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        lists2.add(cjModels);
                                        adapter2.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        setListViewHeight(lv_cbjg);
                        setListViewHeight(lv_sbjg);
                        Elements es_count = document.select("[height=21]");
                        tv_sbjg.setText(es_count.get(0).text());
                        tv_cbjg.setText(es_count.get(1).text());
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }
            }
        });
    }

    /**
     * 设置listView高度，注意listView子项必须为LinearLayout才能调用该方法
     *
     * @param listView listView
     */
    public static void setListViewHeight(ListView listView) {
        int totalHeight = 0;
        ListAdapter adapter = listView.getAdapter();
        if (null != adapter) {
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listView);
                if (null != listItem) {
                    listItem.measure(0, 0);//注意listview子项必须为LinearLayout才能调用该方法
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isVisibleToUser && !mHasLoadedOnce && lists == null) {
                // async http request here
                mHasLoadedOnce = true;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
