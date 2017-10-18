package com.it.mynewfeature;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView mTv_title;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    /**
     * 在Activity中覆写该方法，用于给当前Activity添加菜单项
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获取Menu布局填充器(类似于布局管理器)
        MenuInflater menuInflater = getMenuInflater();
        /**
         * 参数1：菜单布局文件的id（位于res/menu目录下的资源文件）
         * 参数2：当前Activity的菜单对象
         * 该方法的含义是将参数1对应的菜单文件填充为菜单项后添加到当前Activity的menu中
         */
        menuInflater.inflate(R.menu.main_menu, menu);
        //如果想让我们的菜单生效，返回值必须为true，否则无效
        if (menu instanceof MenuBuilder) {//如果menu是MenuBuilder类，强转为MenuBuilder类
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);//设置菜单图标可见
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //根据被点击的菜单的条目的id判断是哪个菜单项被点击了
        switch (item.getItemId()) {
            case R.id.add_friend:
                Toast.makeText(this, "添加好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "关于我们", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        /**
         * 注意：要想修改默认的标题，该行代码必须得在setSupportActionBar(mToolbar);之前，否则设置不成功。
         */
        mToolBar.setTitle("主标题");
        setSupportActionBar(mToolBar);//将ToolBar作为ActionBar的替代者
        mToolBar.setSubtitle("子标题");
        mToolBar.setNavigationIcon(R.mipmap.ic_launcher);

        /**
         *  该行代码是设置打开系统默认的导航图标，是个箭头图标
         *  如果设置该行代码了，那么上面代码中设置的自定义图标就会被覆盖
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置导航图标的点击监听
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        /**
         *  给抽屉布局设置监听器,只有设置过了DrawerLayout开关状态改变时ToolBar
         *  的图标才会跟着变化
         */
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.design_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_recyclerview:
                        startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                        break;
                    case R.id.menu_item_tabLayout:
//                        Toast.makeText(MainActivity.this, "跳转到TabLayout界面", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                        break;
                    case R.id.menu_item_appBarLayout:
                        Toast.makeText(MainActivity.this, "跳转到AppBarLayout界面", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
