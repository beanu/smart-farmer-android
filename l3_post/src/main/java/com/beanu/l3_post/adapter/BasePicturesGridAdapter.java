package com.beanu.l3_post.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.l3_common.util.ArraysUtil;
import com.beanu.l3_post.R;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author by lizhi
 * @date 2017/9/12.
 * 图片选择器显示adapter
 */
public abstract class BasePicturesGridAdapter<T> extends RecyclerView.Adapter<BasePicturesGridAdapter.ViewHolder<T>> implements View.OnClickListener, View.OnLongClickListener {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> pictures;
    protected boolean showAddBtn;
    protected ActionHandler<T> actionHandler;

    private final Map<T, Status> statusMap = new WeakHashMap<>();

    public BasePicturesGridAdapter(Context context, List<T> pictures, boolean showAddBtn, ActionHandler<T> actionHandler) {
        this.context = context;
        this.pictures = pictures;
        this.showAddBtn = showAddBtn;
        this.actionHandler = actionHandler;
        inflater = LayoutInflater.from(context);
    }

    public void setState(T item, @State int state) {
        Status status = getStatus(item);
        if (status.state != state) {
            status.state = state;
            int pos = pictures.indexOf(item);
            if (pos >= 0) {
                notifyItemChanged(pos);
            }
        }
    }

    public void setUpdatePercent(T item, double percent) {
        Status status = getStatus(item);
        status.state = State.UPLOADING;
        status.percent = percent;
        TextView shower = status.percentShower;
        if (shower != null) {
            shower.setText(String.format(Locale.CHINA, "%.2f%%", percent * 100));
        }
    }

    public List<T> getPictures() {
        return pictures;
    }

    @Override
    public ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder<>(inflater.inflate(R.layout.item_grid_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int picturesCount = ArraysUtil.length(pictures);
        if (position >= picturesCount) {
            holder.image.setImageResource(R.drawable.btn_add_pic_n);
            holder.error.setVisibility(View.GONE);
            holder.progress.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        } else {
            T item = pictures.get(position);
            holder.item = item;
            displayImage(item, holder.image);
            holder.videoPlayer.setVisibility(isVideo(item) ? View.VISIBLE : View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
            Status status = getStatus(item);
            status.percentShower = holder.progress;
            if (status.state == State.UPLOADING) {
                holder.error.setVisibility(View.GONE);
                holder.progress.setVisibility(View.VISIBLE);
                holder.progress.setText(String.format(Locale.CHINA, "%.2f%%", status.percent * 100));
            } else if (status.state == State.FAILED) {
                holder.error.setVisibility(View.VISIBLE);
                holder.progress.setVisibility(View.GONE);
            } else {
                holder.error.setVisibility(View.GONE);
                holder.progress.setVisibility(View.GONE);
            }
        }

        holder.image.setTag(R.id.tag_view_holder, holder);
        holder.image.setOnClickListener(this);
        holder.image.setOnLongClickListener(this);

        holder.delete.setTag(R.id.tag_view_holder, holder);
        holder.delete.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        int count = ArraysUtil.length(pictures);
        return showAddBtn ? count + 1 : count;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image) {
            if (actionHandler == null) {
                return;
            }
            ViewHolder<T> holder = (ViewHolder<T>) view.getTag(R.id.tag_view_holder);
            int pos = holder.getAdapterPosition();
            if (pos < ArraysUtil.length(pictures)) {
                actionHandler.onPictureClicked(this, holder.item, pos, getStatus(holder.item).state);
            } else {
                actionHandler.onAddPicture(this);
            }
        } else if (id == R.id.delete) {
            ViewHolder<T> holder = (ViewHolder<T>) view.getTag(R.id.tag_view_holder);
            if (actionHandler != null) {
                actionHandler.onPictureDeleteClicked(this, holder.item, holder.getAdapterPosition(), getStatus(holder.item).state);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        if (id == R.id.image) {
            ViewHolder<T> holder = (ViewHolder<T>) view.getTag(R.id.tag_view_holder);
            int pos = holder.getAdapterPosition();
            if (pos < ArraysUtil.length(pictures)) {
                actionHandler.onPictureLongClicked(this, holder.item, pos, getStatus(holder.item).state);
            }
            return true;
        }
        return false;
    }


    private Status getStatus(T item) {
        Status status = statusMap.get(item);
        if (status == null) {
            status = new Status();
            statusMap.put(item, status);
        }
        return status;
    }

    public abstract void displayImage(T item, ImageView imageView);

    public boolean isVideo(T item) {
        return false;
    }

    static class ViewHolder<T> extends RecyclerView.ViewHolder {
        ImageView image;
        TextView progress;
        ImageView error;
        ImageView delete;
        ImageView videoPlayer;

        T item;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            progress = itemView.findViewById(R.id.progress);
            error = itemView.findViewById(R.id.error);
            delete = itemView.findViewById(R.id.delete);
            videoPlayer = itemView.findViewById(R.id.video_player);
        }
    }

    public interface ActionHandler<T> {
        void onPictureClicked(BasePicturesGridAdapter<T> adapter, T picture, int position, @State int state);

        void onPictureLongClicked(BasePicturesGridAdapter<T> adapter, T picture, int position, @State int state);

        void onPictureDeleteClicked(BasePicturesGridAdapter<T> adapter, T picture, int position, @State int state);

        void onAddPicture(BasePicturesGridAdapter<T> adapter);
    }

    @IntDef({State.NORMAL, State.UPLOADING, State.FAILED})
    @Retention(RetentionPolicy.SOURCE)
    @Inherited
    public @interface State {
        int NORMAL = 0;
        int UPLOADING = 1;
        int FAILED = 2;
    }

    private static class Status {
        @State
        int state = State.NORMAL;
        double percent;
        @Nullable
        TextView percentShower;
    }
}
