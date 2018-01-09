package com.edazh.mzitu;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by edazh on 2018/1/9 0009.
 */

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
