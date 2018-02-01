package com.edazh.mzitu.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edazh.mzitu.R;
import com.edazh.mzitu.databinding.FragmentAlbumListBinding;
import com.edazh.mzitu.db.NetworkState;
import com.edazh.mzitu.ui.PagedAlbumAdapter;
import com.edazh.mzitu.ui.callback.RetryCallback;
import com.edazh.mzitu.viewmodel.AlbumListViewModel;
import com.edazh.mzitu.vo.Album;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/19 0019
 * 描述：最新
 */

public class NewestFragment extends Fragment {
    private static final String TAG = "NewestFragment";
    private FragmentAlbumListBinding mBinding;
    private PagedAlbumAdapter mAdapter;
    private DiffCallback<Album> mDiffCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
//        mAdapter = new AlbumAdapter();


        mBinding.albumList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AlbumListViewModel viewModel = ViewModelProviders.of(this).get(AlbumListViewModel.class);


        mAdapter = new PagedAlbumAdapter(new RetryCallback() {
            @Override
            public void onRetry() {
                viewModel.retry();
            }
        });

        if (viewModel.showPage("首页")) {
            mBinding.albumList.setAdapter(mAdapter);
        }


        viewModel.mAlbumListLiveData.observe(this, new Observer<PagedList<Album>>() {
            @Override
            public void onChanged(@Nullable PagedList<Album> albums) {
                mAdapter.setList(albums);
            }
        });

        viewModel.mNetworkStateLiveData.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mAdapter.setNetworkState(networkState);
            }
        });

        viewModel.mRefreshStateLiveData.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mBinding.swipeRefreshLayout.setRefreshing(networkState == NetworkState.loading());
            }
        });

        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
            }
        });
    }

    public static NewestFragment newInstance() {

        Bundle args = new Bundle();

        NewestFragment fragment = new NewestFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
