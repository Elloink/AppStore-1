package com.idreamsky.appstore.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.common.util.SharedUtil;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.ui.adapter.FragmentAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.drawLayout)
    DrawerLayout mDrawLayout;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        initDrawLayout();
        initTabLayout();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityComponent(AppComponent appComponent) {

    }

    private void initDrawLayout() {
        headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.ivPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuUpdate:
                        Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuMessage:
                        Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSetting:
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuLogout:
                        logout();

                        break;
                }
                return false;
            }
        });

        mToolBar.inflateMenu(R.menu.menu_toolbar);
        //toolBar与DrawLayout的结合
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawLayout, mToolBar, R.string.open,
                R.string.close);
        toggle.syncState();
        mDrawLayout.addDrawerListener(toggle);
    }

    private void logout() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("确认退出登录吗")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = SharedUtil.with(MainActivity.this).edit();
                        editor.remove("token");
                        editor.remove("userName");
                        editor.remove("photo");
                        editor.apply();
                        refreshUser();
                        Toast.makeText(MainActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                        mDrawLayout.closeDrawers();
                    }
                })
                .show();
    }

    private void initTabLayout() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary, null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUser();

    }

    /**
     * 刷新用户信息
     */
    private void refreshUser() {
        String url = SharedUtil.with(this).getString("photo", "");
        String name = SharedUtil.with(this).getString("userName", "");
        if (TextUtils.isEmpty(name)) {
            ((TextView) headerView.findViewById(R.id.textName)).setText("请登录");
            ((ImageView) headerView.findViewById(R.id.ivPhoto)).setImageResource(R.mipmap.ic_launcher_round);
            return;
        }
        if (!TextUtils.isEmpty(url)) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CircleCrop());
            Glide.with(this)
                    .load(url)
                    .apply(requestOptions)
                    .into((ImageView) headerView.findViewById(R.id.ivPhoto));
        }
        ((TextView) headerView.findViewById(R.id.textName)).setText(name);
    }
}
