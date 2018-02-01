package com.edazh.mzitu.util;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/27 0027
 * 描述：
 */

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showGone(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleHide")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
