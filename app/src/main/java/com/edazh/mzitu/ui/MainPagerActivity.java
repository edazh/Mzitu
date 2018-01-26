package com.edazh.mzitu.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.edazh.mzitu.R;
import com.edazh.mzitu.databinding.ActivityMainPagerBinding;
import com.edazh.mzitu.ui.fragment.HottestFragment;
import com.edazh.mzitu.ui.fragment.NewestFragment;
import com.edazh.mzitu.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/19 0019
 * 描述：主页 view pager
 */

public class MainPagerActivity extends AppCompatActivity {
    private ActivityMainPagerBinding mBinding;
    private List<Fragment> mFragmentList;

    private List<String> mTitleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_pager);

        mTitleList = new ArrayList<>();
        mTitleList.add("最新");
        mTitleList.add("最热");
        mTitleList.add("推荐");

        mFragmentList = new ArrayList<>();
        mFragmentList.add(NewestFragment.newInstance());
        mFragmentList.add(HottestFragment.newInstance());
        mFragmentList.add(RecommendFragment.newInstance());

        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        });
    }
}
