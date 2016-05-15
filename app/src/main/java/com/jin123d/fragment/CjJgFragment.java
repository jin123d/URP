package com.jin123d.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jin123d.Interface.GetNetData;
import com.jin123d.adapter.CjAdapter;
import com.jin123d.models.CjModels;
import com.jin123d.urp.R;
import com.jin123d.util.Sp;
import com.jin123d.util.netUtil;
import com.jin123d.util.urlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin123d on 2015/9/14.
 */
public class CjJgFragment extends Fragment implements GetNetData {
    private String cookie;
    private String tv;
    private ProgressDialog progressDialog;
    private ListView lv_zjsj;
    private CjAdapter adapter;
    private List<CjModels> lists;
    private boolean mHasLoadedOnce = false;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case urlUtil.DATA_FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), getText(R.string.getDataTimeOut), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
                case urlUtil.DATA_SUCCESS:
                    if (tv == null) {
                        System.out.println("空");
                    } else {
                        progressDialog.dismiss();
                        Document doc = Jsoup.parse(tv);
                        if (getText(R.string.webTitle).equals(doc.title())) {
                            Toast.makeText(getActivity(), getText(R.string.loginFail),
                                    Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else {
                            Elements es = doc.select("[class=titleTop2]");

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
                                            lists.add(cjModels);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }

                    }
                    break;
                case urlUtil.SESSION:
                    Toast.makeText(getActivity(), getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cjjg, container, false);

    }

    void getInfo() {
        new Thread() {
            public void run() {
                netUtil.getPostData(urlUtil.URL + urlUtil.URL_QB, cookie, CjJgFragment.this);
            }

            ;
        }.start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Sp sp = new Sp(getActivity());
        cookie = sp.getCookie();
        getInfo();
        lv_zjsj = (ListView) view.findViewById(R.id.lv_zjsj);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.getJg));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        lists = new ArrayList<>();
        adapter = new CjAdapter(lists, getActivity());
        lv_zjsj.setAdapter(adapter);
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

    @Override
    public void getDataSuccess(String Data) {
        tv = Data;
        handler.sendEmptyMessage(urlUtil.DATA_SUCCESS);
    }

    @Override
    public void getDataFail() {
        handler.sendEmptyMessage(urlUtil.DATA_FAIL);
    }

    @Override
    public void getDataSession() {
        handler.sendEmptyMessage(urlUtil.SESSION);
    }
}
