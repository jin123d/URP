package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.jin123d.Interface.GetNetData;
import com.jin123d.adapter.JxpgAdapter;
import com.jin123d.models.JxpgModels;
import com.jin123d.models.PgInfoModels;
import com.jin123d.util.Sp;
import com.jin123d.util.netUtil;
import com.jin123d.util.urlUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class JxpgListActivity extends AppCompatActivity implements GetNetData {
    private String cookie;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private ListView lv_jxpg;
    private String tv;
    private List<JxpgModels> lists;
    private JxpgAdapter adapter;
    private List<PgInfoModels> list_pg;
    private AlertDialog.Builder dialog;
    private int i = 0;
    private boolean zq = true;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case urlUtil.DATA_FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(JxpgListActivity.this, getString(R.string.getDataTimeOut), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case urlUtil.DATA_SUCCESS:
                    if (tv == null) {
                        System.out.println("空");
                    } else {
                        progressDialog.dismiss();
                        Document doc = Jsoup.parse(tv);
                        if (getString(R.string.webTitle).equals(doc.title())) {
                            Toast.makeText(JxpgListActivity.this, getString(R.string.loginFail),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            //教学评估列表
                            Elements es = doc.select("[class=odd]");
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
                    }
                    break;
                case urlUtil.SESSION:
                    Toast.makeText(JxpgListActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    break;
                case urlUtil.PG_SUCCESS:
                    Document doc = Jsoup.parse(tv);
                    progressDialog.setMessage(doc.text());
                    i++;
                    startPg(i);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxpg_list);
        Sp sp = new Sp(JxpgListActivity.this);
        cookie = sp.getCookie();
        initView();
        getInfo();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(JxpgListActivity.this);
        lv_jxpg = (ListView) findViewById(R.id.lv_jxpg_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_car);
        //  fab.attachToListView(lv_jxpg);
        dialog = new AlertDialog.Builder(JxpgListActivity.this);
        dialog.setTitle("一键评估");
        dialog.setMessage("是否进行一键评估,可能会耗时几分钟，请耐心等待");
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        lists = new ArrayList<>();
        list_pg = new ArrayList<>();
        adapter = new JxpgAdapter(JxpgListActivity.this, lists);
        lv_jxpg.setAdapter(adapter);

        lv_jxpg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(JxpgListActivity.this).setTitle("信息")
                        .setMessage(lists.get(position).getBprm() + "" +
                                "\n" + lists.get(position).getPgnr()).setNegativeButton("OK", null)
                        .create().show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_pg.size() == 0) {
                    dialog.setTitle("一键评估");
                    dialog.setMessage("当前没有需要评估的科目");
                    dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create();
                    dialog.show();
                } else {
                    dialog.setNegativeButton("开始评估", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            progressDialog.setTitle("正在评估");
                            progressDialog.show();
                            startPg(i);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }

            }
        });
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                netUtil.getPostData(urlUtil.URL + urlUtil.URL_JXPG_LIST, cookie, JxpgListActivity.this);
            }
        }.start();
    }


    private void startPg(final int position) {
        final int size = list_pg.size();
        if (size == 0) {
            progressDialog.dismiss();
            dialog.setMessage("所有科目都评估完成!");
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
            progressDialog.dismiss();
            dialog.setMessage("所有科目都评估完成!");
            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    i = 0;
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            progressDialog.setMessage("正在评估" + i + "/" + size + "\n" + list_pg.get(position).getKcm());
            new Thread() {
                public void run() {
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("wjbm", list_pg.get(position).getWjbm()));
                    params.add(new BasicNameValuePair("bpr", list_pg.get(position).getBpr()));
                    params.add(new BasicNameValuePair("pgnr", list_pg.get(position).getPgnr()));
                    params.add(new BasicNameValuePair("oper", "wjShow"));
                    loginpg(params);
                    //评估核心代码
                    List<NameValuePair> params2 = new ArrayList<>();
                    params2.add(new BasicNameValuePair("wjbm", list_pg.get(position).getWjbm()));
                    params2.add(new BasicNameValuePair("bpr", list_pg.get(position).getBpr()));
                    params2.add(new BasicNameValuePair("pgnr", list_pg.get(position).getPgnr()));
                    
                    if(zq){
                        params2.add(new BasicNameValuePair("0000000045","10_1"))
                    }else{
                        for (int i = 24; i <= 33; i++) {
                            params2.add(new BasicNameValuePair("00" + i, "100_1"));
                        }
                    }
                    
                    params2.add(new BasicNameValuePair("zgpj", getString(R.string.pgnr)));
                    Log.d("params", params2.toString());
                    login(params2);
                }
            }.start();
        }
    }

    public void loginpg(List<NameValuePair> params) {
        netUtil.doPost(
                urlUtil.URL + urlUtil.URL_PG, cookie, params, new GetNetData() {
                    @Override
                    public void getDataSuccess(String Data) {

                    }

                    @Override
                    public void getDataFail() {

                    }

                    @Override
                    public void getDataSession() {

                    }
                });
    }

    public void login(List<NameValuePair> params) {
        netUtil.doPost(
                urlUtil.URL + urlUtil.URL_JXPG, cookie, params, new GetNetData() {
                    @Override
                    public void getDataSuccess(String Data) {
                        tv = Data;
                        handler.sendEmptyMessage(urlUtil.PG_SUCCESS);
                    }

                    @Override
                    public void getDataFail() {
                        handler.sendEmptyMessage(urlUtil.DATA_FAIL);
                    }

                    @Override
                    public void getDataSession() {
                        handler.sendEmptyMessage(urlUtil.SESSION);
                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(JxpgListActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(JxpgListActivity.this);
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
