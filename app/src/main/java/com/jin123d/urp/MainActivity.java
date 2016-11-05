package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jin123d.Interface.GetNetDataListener;
import com.jin123d.adapter.MainAdapter;
import com.jin123d.models.MainModels;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.UrpSp;
import com.jin123d.util.UrpUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetNetDataListener {
    private Toolbar toolbar;
    private List<MainModels> lists;
    private ListView listView;
    private MainAdapter adapter;
    private MainModels[] mainModele;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private TextView tv_head;
    private String tv;
    private ProgressDialog progressDialog;
    private UrpSp sp;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UrpUrl.DATA_SUCCESS:
                    if (tv == null) {
                        System.out.println("空");
                    } else {
                        progressDialog.dismiss();
                        Document doc = Jsoup.parse(tv);
                        if (getString(R.string.webTitle).equals(doc.title())) {
                            Toast.makeText(MainActivity.this, getString(R.string.loginFail),
                                    Toast.LENGTH_SHORT).show();
                            reLogin();

                        } else {
                            Elements es = doc.select("[width=275]");
                            if (es.size() > 0) {
                                tv_head.setText(es.get(1).text() + "  " + es.get(0).text());
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                                reLogin();
                            }
                        }
                    }
                    break;
                case UrpUrl.DATA_FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, getString(R.string.getDataTimeOut), Toast.LENGTH_SHORT).show();
                    reLogin();

                    break;
                case UrpUrl.SESSION:
                    Toast.makeText(MainActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    reLogin();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // cookie = intent.getStringExtra("cookie");
        initView();
        getData();
        getInfo();
    }


    void initView() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.getUserData));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        /**
         * 初始化各种控件
         * */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        listView = (ListView) findViewById(R.id.lv_main);
        tv_head = (TextView) findViewById(R.id.tv_head);

        lists = new ArrayList<>();
        adapter = new MainAdapter(lists, MainActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //学籍信息
                        start(XjxxActivity.class);
                        break;
                    case 1:
                        //成绩查询
                        start(CjActivity.class);
                        break;
                    case 2:
                        //自主实践
                        start(ZjsjActivity.class);
                        break;
                    case 3:
                        //学分绩点
                        start(XfjdActivity.class);
                        break;
                    case 4:
                        //本学期课表
                        Snackbar.make(listView, "开发中1111", Snackbar.LENGTH_SHORT).show();
                        // start(KbActivity.class);
                        break;
                    case 5:
                        //一键评教
                        // Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        start(JxpgListActivity.class);
                        break;

                    case 6:
                        start(TzggActivity.class);
                        break;
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navItem1:
                       /* String str = urlUtil.URL;
                        Snackbar.make(listView, str, Snackbar.LENGTH_SHORT)
                                .show();*/
                        Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem2:
                        // start(ChangeColor.class);
                        Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem3:
                        start(AboutActivity.class);
                        break;
                    case R.id.navItem4:
                        //退出
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("URP助手").setMessage("退出登录").setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UrpSp.setCookie(null);
                                UrpSp.setAuto(false);
                                finish();
                                start(LoginActivity.class);
                            }
                        }).create().show();
                        break;
                }
                return false;
            }
        });
    }

    private void getData() {
        mainModele = new MainModels[7];
        mainModele[0] = new MainModels(String.valueOf(R.mipmap.xjxx_2), getString(R.string.title_activity_xjxx));
        mainModele[1] = new MainModels(String.valueOf(R.mipmap.cj_2), getString(R.string.cj));
        mainModele[2] = new MainModels(String.valueOf(R.mipmap.zzsj_2), getString(R.string.title_activity_zjsj));
        mainModele[3] = new MainModels(String.valueOf(R.mipmap.xfjd_2), getString(R.string.title_activity_xfjd));
        mainModele[4] = new MainModels(String.valueOf(R.mipmap.kb_2), getString(R.string.bxqkb));
        mainModele[5] = new MainModels(String.valueOf(R.mipmap.pj_2), getString(R.string.yjpj));
        mainModele[6] = new MainModels(String.valueOf(R.mipmap.tzgg_2), getString(R.string.tzgg));
        for (int i = 0; i < mainModele.length; i++) {
            lists.add(mainModele[i]);
        }
        adapter.notifyDataSetChanged();
    }


    void start(Class cls) {
        Intent intent = new Intent();
        // intent.putExtra("cookie", cookie);
        intent.setClass(MainActivity.this, cls);
        startActivity(intent);
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Snackbar.make(listView, R.string.doubleBack, Snackbar.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.action_settings:
                String str = urlUtil.URL;
                Snackbar.make(listView, str, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_about:
                start(AboutActivity.class);
                break;*/
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)
                        ) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }

        return true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                HttpUtil.doGet(UrpUrl.URL + UrpUrl.URL_XJXX, MainActivity.this);
            }

        }.start();
    }

    private void reLogin() {
        sp.setAuto(false);
        start(LoginActivity.class);
        finish();
    }

    @Override
    public void getDataSuccess(String Data) {
        tv = Data;
        handler.sendEmptyMessage(UrpUrl.DATA_SUCCESS);
    }

    @Override
    public void getDataFail() {
        handler.sendEmptyMessage(UrpUrl.DATA_FAIL);
    }

    @Override
    public void getDataSession() {
        handler.sendEmptyMessage(UrpUrl.SESSION);
    }
}
