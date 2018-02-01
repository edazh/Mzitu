package com.edazh.mzitu.ui;

import android.support.v7.widget.RecyclerView;
import com.edazh.mzitu.databinding.NetworkStateItemBinding;
import com.edazh.mzitu.db.NetworkState;
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

    public NetworkStateViewHolder(NetworkStateItemBinding binding, RetryCallback retryCallback) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setCallback(retryCallback);
    }

    public void bindTo(NetworkState networkState) {
        binding.setState(networkState);
    }
}
