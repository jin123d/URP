package com.jin123d.urp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jin123d.adapter.JxpgAdapter;
import com.jin123d.app.BaseActivity;
import com.jin123d.models.JxpgModels;
import com.jin123d.models.PgInfoModels;
import com.jin123d.util.ErrorCode;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class JxpgListActivity extends BaseActivity {
    private List<JxpgModels> lists;
    private JxpgAdapter adapter;
    private List<PgInfoModels> list_pg;
    private AlertDialog.Builder dialog;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxpg_list);
        getInfo();
    }

    @Override
    protected void initView() {
        ListView lv_jxpg = (ListView) findViewById(R.id.lv_jxpg_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_car);
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.title_activity_jxpg_list));
        dialog.setMessage("是否进行一键评教,可能会耗时几分钟，请耐心等待");
        lists = new ArrayList<>();
        list_pg = new ArrayList<>();
        adapter = new JxpgAdapter(JxpgListActivity.this, lists);
        lv_jxpg.setAdapter(adapter);

        lv_jxpg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(JxpgListActivity.this).setTitle(R.string.app_name)
                        .setMessage(lists.get(position).getBprm() + "" +
                                "\n" + lists.get(position).getPgnr()).setNegativeButton("OK", null)
                        .create().show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_pg.size() == 0) {
                    dialog.setTitle(getString(R.string.title_activity_jxpg_list));
                    dialog.setMessage("当前没有需要评教的科目");
                    dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    dialog.setNegativeButton("开始评教", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            progressDialog.setTitle("正在评教");
                            progressDialog.show();
                            startPg(i);
                        }
                    });
                    dialog.show();
                }

            }
        });
    }

    @Override
    protected void initData() {

    }

    public void getInfo() {
        HttpUtil.getJxpgList(this, new JsoupCallBack() {
            @Override
            public void onError(ErrorCode errorCode, Response response) {
                super.onError(errorCode, response);
                progressDialogDismiss();
            }

            @Override
            public void onSuccess(Document document) {
                progressDialogDismiss();
                //教学评估列表
                Elements es = document.select("[class=odd]");
                if (es.size() != 0) {
                    for (int i = 0; i < es.size(); i++) {
                        Elements es_2 = es.get(i).select("td");
                        String wjmc = es_2.get(0).text();
                        String pgnr = es_2.get(2).text();
                        String bpr = es_2.get(1).text();
                        String sfpg = es_2.get(3).text();
                        if ("否".equals(sfpg)) {
                            String pginfo = es_2.get(4).select("img").attr("name");
                            String[] strs = pginfo.split("#@");
                            String info_wjbm = strs[0];
                            String info_bpr = strs[1];
                            String info_kcm = strs[4];
                            String info_pgnr = strs[5];
                            PgInfoModels pgInfoModels = new PgInfoModels(info_wjbm, info_bpr, info_kcm, info_pgnr);
                            list_pg.add(pgInfoModels);
                            Log.d("评估", info_kcm);
                        }
                        JxpgModels jxpgModels = new JxpgModels(wjmc, pgnr, bpr, sfpg);
                        lists.add(jxpgModels);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }


    private void startPg(final int position) {
        final int size = list_pg.size();
        if (size == 0) {
            progressDialogDismiss();
            dialog.setMessage("所有科目都评教完成!");
            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    i = 0;
                }
            });
            dialog.setCancelable(false);
            dialog.show();
            return;
        }
        if (position == size) {
            lists.clear();
            list_pg.clear();
            adapter.notifyDataSetChanged();
            getInfo();
            progressDialogDismiss();
            dialog.setMessage("所有科目都评教完成!");
            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    i = 0;
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            progressDialog.setMessage("正在评教" + i + "/" + size + "\n" + list_pg.get(position).getKcm());
            HttpUtil.pg_1(this, list_pg.get(position), true, new JsoupCallBack() {
                @Override
                public void onSuccess(Document document) {
                    progressDialog.setMessage(document.text());
                    i++;
                    startPg(i);
                }
            });
        }
    }


}
