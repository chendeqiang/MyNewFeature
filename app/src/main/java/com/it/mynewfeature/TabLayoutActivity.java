package com.it.mynewfeature;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TabLayoutActivity extends AppCompatActivity {

    private static final String TAG = "TabLayout";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int[] mStraggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};
    private FloatingActionButton mFloatingActionButton;
    private boolean isStart;//是否自动播放幻灯片
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置滚动模式为可滚动
        mViewPager.setAdapter(new MyPagerAdapter());
        //让TabLayout跟ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让mFloatingActionButton旋转一周
                ObjectAnimator.ofFloat(mFloatingActionButton, "rotation", 0, 360).setDuration(1000).start();
                Snackbar.make(mTabLayout, isStart ? "要停止播放吗?" : "要开始播放吗?", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isStart) {
                            //停止
                            stopPlay();
                        } else {
                            //开始
                            startPlay();
                        }
                        isStart = !isStart;
                    }
                }).show();
            }
        });
    }

    private void startPlay() {
        //在让Handler执行定时任务之前，为了防止有老的任务没有被销毁，因此先清空一下Handler的任务
        mHandler.removeCallbacksAndMessages(null);
        //mViewPager.setCurrentItem();
        //让Handler执行定时周期性的任务，不断的更新ViewPager当前的页数
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取ViewPager当前的页数然后+1
                final int currentItem = mViewPager.getCurrentItem();
                int newItem = (currentItem + 1)%mViewPager.getAdapter().getCount();
                Log.d(TAG, "翻页: "+newItem);
                mViewPager.setCurrentItem(newItem,true);
                //上面的任务执行完了，让Handler再执行一个延时任务
                mHandler.postDelayed(this,1000);
            }
        },1000);
    }

    private void stopPlay() {
        //清空Handler发送的任务，这样所有Handler发送的不管是什么任何和消息都没有了
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源（handler的定时任务）
        mHandler.removeCallbacksAndMessages(null);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mStraggeredIcons.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "美女" + position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(TabLayoutActivity.this);
            imageView.setImageResource(mStraggeredIcons[position]);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            container.addView(imageView);
            return imageView;
        }
    }
}
