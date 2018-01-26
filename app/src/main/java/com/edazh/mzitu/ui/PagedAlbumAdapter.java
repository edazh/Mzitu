package com.edazh.mzitu.ui;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.edazh.mzitu.R;
import com.edazh.mzitu.databinding.ItemAlbumBinding;
import com.edazh.mzitu.databinding.NetworkStateItemBinding;
import com.edazh.mzitu.db.NetworkState;
import com.edazh.mzitu.ui.callback.RetryCallback;
import com.edazh.mzitu.vo.Album;

import java.util.Objects;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/25 0025
 * 描述：
 */

public class PagedAlbumAdapter extends PagedListAdapter<Album, RecyclerView.ViewHolder> {
    private final RetryCallback mRetryCallback;
    private NetworkState mNetworkState;

    public PagedAlbumAdapter(RetryCallback retryCallback) {
        super(DIFF_CALLBACK);
        mRetryCallback = retryCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_album) {
            ItemAlbumBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_album, parent, false);
            return new PagedAlbumViewHolder(binding);
        } else {
            NetworkStateItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.network_state_item, parent, false);
            return new NetworkStateViewHolder(binding, mRetryCallback);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_album:
                if (holder instanceof PagedAlbumViewHolder) {
                    ((PagedAlbumViewHolder) holder).bindTo(getItem(position));
                }
                break;
            case R.layout.network_state_item:
                if (holder instanceof NetworkStateViewHolder) {
                    ((NetworkStateViewHolder) holder).bindTo(mNetworkState);
                }
                break;
            default:
                break;
        }
    }

    public void setNetworkState(NetworkState networkState) {
        NetworkState previousState = mNetworkState;
        boolean hadExtraRow = hasExtraRow();
        mNetworkState = networkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState != networkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasExtraRow() {
        return mNetworkState != null && mNetworkState != NetworkState.success();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return hasExtraRow() && position == getItemCount() - 1 ? R.layout.network_state_item : R.layout.item_album;
    }

    static class PagedAlbumViewHolder extends RecyclerView.ViewHolder {

        ItemAlbumBinding binding;

        public PagedAlbumViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Album album) {
            binding.setAlbum(album);
            GlideUrl url = new GlideUrl(album.getCoverLink(), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
            Glide.with(itemView).load(url).into(binding.imgCover);
            binding.executePendingBindings();
        }
    }

    private static final DiffCallback<Album> DIFF_CALLBACK = new DiffCallback<Album>() {
        @Override
        public boolean areItemsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId())
                    && Objects.equals(oldItem.getLink(), newItem.getLink())
                    && Objects.equals(oldItem.getTitle(), newItem.getTitle());
        }
    };

}
