package com.beanu.l3_post.adapter.add_post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.support.recyclerview.divider.VerticalDividerItemDecoration;
import com.beanu.arad.utils.SizeUtils;
import com.beanu.l3_common.support.ActivityHelper;
import com.beanu.l3_common.support.NestedScrollHelper;
import com.beanu.l3_common.ui.AlertFragment;
import com.beanu.l3_post.R;
import com.beanu.l3_post.adapter.AlbumPicturesGridAdapter;
import com.beanu.l3_post.adapter.BasePicturesGridAdapter;
import com.beanu.l3_post.model.bean.PostContent;
import com.beanu.l3_post.upload.UploadPresenter;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * @author lizhi
 * @date 2017/11/13.
 */
public class PostContentViewBinder extends ItemViewBinder<PostContent, PostContentViewBinder.ViewHolder> implements View.OnClickListener, BasePicturesGridAdapter.ActionHandler<AlbumFile> {

    private String contentHint;
    private final UploadPresenter<AlbumFile, JsonObject, ?, ?> presenter;
    private final Collection<AlbumPicturesGridAdapter> adapterHolder;
    private final MultiTypeAdapter listAdapter;
    private final Items items;
    private final View.OnFocusChangeListener listener;
    private final PictureActionCallback pictureActionCallback;
    private Context context;

    private Map<BasePicturesGridAdapter, PostContent> postContentMap = new ArrayMap<>();

    public PostContentViewBinder(String hint, @NonNull MultiTypeAdapter listAdapter,
                                 @NonNull Items items,
                                 @NonNull UploadPresenter<AlbumFile, JsonObject, ?, ?> presenter,
                                 @NonNull Collection<AlbumPicturesGridAdapter> adapterHolder,
                                 @NonNull View.OnFocusChangeListener listener,
                                 @NonNull PictureActionCallback pictureActionCallback) {
        this.contentHint = hint;
        this.listAdapter = listAdapter;
        this.items = items;
        this.presenter = presenter;
        this.adapterHolder = adapterHolder;
        this.listener = listener;
        this.pictureActionCallback = pictureActionCallback;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_post_content, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PostContent postContent) {
        context = holder.itemView.getContext();
        holder.item = postContent;
        holder.editContent.setText(postContent.content);

//        ViewUtils.setVisibility(postContent.canDelete ? View.VISIBLE : View.GONE, holder.imgDelete);
        if (postContent.canDelete) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
        }
        holder.imgDelete.setTag(R.id.tag_view_holder, holder);
        holder.imgDelete.setOnClickListener(this);

        holder.imgAddPicture.setTag(R.id.tag_view_holder, holder);
        holder.imgAddPicture.setOnClickListener(this);

        if (postContent.requireUpdate || holder.listPictures.getTag(R.id.tag_data) != postContent.pictures) {
            postContent.requireUpdate = false;
            holder.listPictures.setTag(R.id.tag_data, postContent.pictures);
            holder.pictures.clear();
            holder.pictures.addAll(postContent.pictures);
            holder.picAdapter.notifyDataSetChanged();
        }
        postContentMap.put(holder.picAdapter, postContent);
    }

    @Override
    protected void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.editContent.clearFocus();
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_view_holder);
        int i = v.getId();
        if (i == R.id.img_delete) {
            deleteContent(holder);

        } else if (i == R.id.img_add_picture) {
            pictureActionCallback.onAddPictureBtnClick(holder.item);

        }
    }

    private void deleteContent(final ViewHolder holder) {
        if (holder.item.pictures.isEmpty() && TextUtils.isEmpty(holder.item.content)) {
            deleteContentWithUnchecked(holder);
        } else {
            FragmentActivity activity = ActivityHelper.getInstance().getTopActivity(FragmentActivity.class);
            if (activity != null) {
                new AlertFragment.Builder(holder.itemView.getContext())
                        .setTitle(R.string.alert_title)
                        .setMessage("是否删除该组图文")
                        .setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteContentWithUnchecked(holder);
                            }
                        })
                        .setNegativeButton(R.string.alert_cancel, null)
                        .create().show(activity.getSupportFragmentManager(), "alert_delete");
            }
        }
    }

    private void deleteContentWithUnchecked(ViewHolder holder) {
        int index = items.indexOf(holder.item);
        if (index != -1) {
            items.remove(holder.item);
            listAdapter.notifyItemRemoved(index);
        }
    }

    @Override
    public void onPictureClicked(BasePicturesGridAdapter<AlbumFile> adapter, AlbumFile picture, int position, int state) {
        if (state == BasePicturesGridAdapter.State.FAILED) {
            presenter.upload(picture);
        } else {
            PostContent postContent = postContentMap.get(adapter);
            if (postContent != null) {
                pictureActionCallback.onPicturePreview(postContent, picture);
            }
        }
    }

    @Override
    public void onPictureLongClicked(BasePicturesGridAdapter<AlbumFile> adapter, AlbumFile picture, int position, int state) {
        onPictureDeleteClicked(adapter, picture, position, state);
    }

    @Override
    public void onPictureDeleteClicked(final BasePicturesGridAdapter<AlbumFile> adapter, final AlbumFile picture, final int position, int state) {
        FragmentActivity activity = ActivityHelper.getInstance().getTopActivity(FragmentActivity.class);
        if (activity != null) {
            new AlertFragment.Builder(context)
                    .setTitle(R.string.alert_title)
                    .setMessage("是否删除该图片或视频")
                    .setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            List<AlbumFile> list = adapter.getPictures();
                            if (list != null) {
                                int index = list.indexOf(picture);
                                if (index != -1) {
                                    list.remove(picture);
                                    PostContent postContent = postContentMap.get(adapter);
                                    if (postContent != null) {
                                        postContent.pictures.remove(picture);
                                    }
                                    adapter.notifyItemRemoved(position);
                                    presenter.removeTask(picture);
                                }
                            }
                        }
                    })
                    .setNegativeButton(R.string.alert_cancel, null)
                    .create().show(activity.getSupportFragmentManager(), "alert_delete_pic");
        }
    }

    @Override
    public void onAddPicture(BasePicturesGridAdapter<AlbumFile> adapter) {
        //do nothing
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText editContent;
        ImageView imgAddPicture;
        RecyclerView listPictures;
        ImageView imgDelete;

        PostContent item;

        final AlbumPicturesGridAdapter picAdapter;
        final List<AlbumFile> pictures = new ArrayList<>();

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
            super(itemView);

            editContent = itemView.findViewById(R.id.edit_content);
            imgAddPicture = itemView.findViewById(R.id.img_add_picture);
            listPictures = itemView.findViewById(R.id.list_pictures);
            imgDelete = itemView.findViewById(R.id.img_delete);

            editContent.setHint(contentHint);
            editContent.setOnTouchListener(new NestedScrollHelper());
            editContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (item != null && !TextUtils.equals(s, item.content)) {
                        item.content = s;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            editContent.setOnFocusChangeListener(listener);

            Context context = itemView.getContext();
            picAdapter = new AlbumPicturesGridAdapter(context, pictures, false, PostContentViewBinder.this);
            listPictures.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            listPictures.setAdapter(picAdapter);
            listPictures.addItemDecoration(new VerticalDividerItemDecoration.Builder(context)
                    .size(SizeUtils.dp2px(10))
                    .color(Color.TRANSPARENT)
                    .build());
            adapterHolder.add(picAdapter);
        }
    }
}
