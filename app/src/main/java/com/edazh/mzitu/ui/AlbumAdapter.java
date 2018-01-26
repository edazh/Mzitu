package com.edazh.mzitu.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.edazh.mzitu.R;
import com.edazh.mzitu.databinding.ItemAlbumBinding;
import com.edazh.mzitu.vo.Album;

import java.util.List;
import java.util.Objects;

/**
 * Created by edazh on 2018/1/14 0014.
 * e-mail:edazh@qq.com
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> mAlbums;


    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAlbumBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_album, parent, false);

        return new AlbumViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.binding.setAlbum(mAlbums.get(position));
        GlideUrl url = new GlideUrl(mAlbums.get(position).getCoverLink(), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
        Glide.with(holder.itemView).load(url).into(holder.binding.imgCover);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mAlbums == null ? 0 : mAlbums.size();
    }

    public void setAlbums(final List<Album> albums) {
        if (mAlbums == null) {
            mAlbums = albums;
            notifyItemRangeInserted(0, albums.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mAlbums.size();
                }

                @Override
                public int getNewListSize() {
                    return albums.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mAlbums.get(oldItemPosition).getId(), albums.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Album oldAlbum = mAlbums.get(oldItemPosition);
                    Album newAlbum = albums.get(newItemPosition);
                    return Objects.equals(oldAlbum.getId(), newAlbum.getId())
                            && Objects.equals(oldAlbum.getTitle(), newAlbum.getTitle())
                            && Objects.equals(oldAlbum.getLink(), newAlbum.getLink())
                            && Objects.equals(oldAlbum.getCoverLink(), newAlbum.getCoverLink())
                            && Objects.equals(oldAlbum.getTime(), newAlbum.getTime())
                            && Objects.equals(oldAlbum.getView(), newAlbum.getView());
                }
            });
            mAlbums = albums;
            diffResult.dispatchUpdatesTo(this);
        }
    }


    static class AlbumViewHolder extends RecyclerView.ViewHolder {

        private ItemAlbumBinding binding;

        public AlbumViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
