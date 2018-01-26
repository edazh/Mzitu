package com.edazh.mzitu.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.edazh.mzitu.databinding.NetworkStateItemBinding;
import com.edazh.mzitu.db.NetworkState;
import com.edazh.mzitu.db.Status;
import com.edazh.mzitu.ui.callback.RetryCallback;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/26 0026
 * 描述：
 */

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    private NetworkStateItemBinding binding;
    private final RetryCallback retryCallback;

    public NetworkStateViewHolder(NetworkStateItemBinding binding, RetryCallback retryCallback) {
        super(binding.getRoot());
        this.binding = binding;
        this.retryCallback = retryCallback;
    }

    public void bindTo(NetworkState networkState) {
        binding.progressBar.setVisibility(networkState.status == Status.LOADING ? View.VISIBLE : View.GONE);
        binding.retryButton.setVisibility(networkState.status == Status.ERROR ? View.VISIBLE : View.GONE);
        binding.errorMsg.setVisibility(networkState.message != null ? View.VISIBLE : View.GONE);
        binding.errorMsg.setText(networkState.message);
        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryCallback.onRetry();
            }
        });
    }
}
