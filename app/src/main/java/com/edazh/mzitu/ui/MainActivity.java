package com.edazh.mzitu.ui;

import android.support.v4.app.Fragment;

import com.edazh.mzitu.SingleFragmentActivity;
import com.edazh.mzitu.ui.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }
}
