package com.beanu.l3_post;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.arad.utils.KeyboardUtils;
import com.beanu.arad.utils.SizeUtils;
import com.beanu.arad.utils.ViewUtils;
import com.beanu.l3_common.ui.AlertFragment;
import com.beanu.l3_common.widget.ActionSheet;
import com.beanu.l3_common.widget.FacePanelView;
import com.beanu.l3_post.adapter.AlbumPicturesGridAdapter;
import com.beanu.l3_post.adapter.BasePicturesGridAdapter;
import com.beanu.l3_post.adapter.add_post.PictureActionCallback;
import com.beanu.l3_post.adapter.add_post.PostContentViewBinder;
import com.beanu.l3_post.adapter.add_post.PostTitleViewBinder;
import com.beanu.l3_post.model.bean.PostContent;
import com.beanu.l3_post.model.bean.PostTitle;
import com.beanu.l3_post.mvp.contract.PostContract;
import com.beanu.l3_post.mvp.model.PostModelImpl;
import com.beanu.l3_post.mvp.presenter.PostPresenterImpl;
import com.beanu.l3_post.upload.manager.UploadResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static android.text.style.DynamicDrawableSpan.ALIGN_BOTTOM;

public class L3PostActivity extends ToolBarActivity<PostPresenterImpl, PostModelImpl> implements PostContract.View, View.OnClickListener, FacePanelView.OnFaceItemClickListener, PictureActionCallback {

    private static final int REQ_CAMERA = 321;
    private static final int REQ_PICTURE = 322;
    private static final int REQ_AT_USER = 323;


    RecyclerView listContent;
    LinearLayout llBottomBar;
    TextView textAddIllustrated;
    ImageView imgAt;
    CheckBox checkFace;
    View bottomLine;
    FacePanelView facePanel;

    @Autowired
    boolean addPicFromCamera;
    @Autowired
    boolean addPicFromPick;
    @Autowired
    boolean addVideoFromCamera;
    @Autowired
    boolean addVideoFromLocal;
    @Autowired
    String circleTitle;
    @Autowired
    String hint;

    private final Gson gson = new Gson();
    private MultiTypeAdapter contentAdapter;
    private final Items items = new Items();

    private final PostTitle postTitle = new PostTitle();
    private final PostContent defaultContent = new PostContent(false);
    private final Collection<AlbumPicturesGridAdapter> picAdapterHolder = new HashSet<>();

    private PostContent currentPostContent = null;

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            boolean show = v.getId() == R.id.edit_content;
            ViewUtils.setVisibility(show ? View.VISIBLE : View.GONE, llBottomBar);
            ViewUtils.setVisibility(show && checkFace.isChecked() ? View.VISIBLE : View.GONE, bottomLine, facePanel);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l3_post);
        ARouter.getInstance().inject(this);
        initView();
        disableSlideBack();

        AlbumFile albumFile = getIntent().getParcelableExtra("media");
        if (albumFile != null) {
            defaultContent.pictures.add(albumFile);
            mPresenter.upload(albumFile);
        }

        initContent();
        initKeyBoard();

        currentPostContent = defaultContent;
    }


    private void initContent() {
        items.add(postTitle);
        items.add(defaultContent);

        contentAdapter = new MultiTypeAdapter(items);
        contentAdapter.register(PostTitle.class, new PostTitleViewBinder(onFocusChangeListener));
        contentAdapter.register(PostContent.class, new PostContentViewBinder(
                hint,
                contentAdapter,
                items,
                mPresenter,
                picAdapterHolder,
                onFocusChangeListener,
                this
        ));
        listContent.setLayoutManager(new LinearLayoutManager(this));
        listContent.setAdapter(contentAdapter);
        listContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen.lineSize)
                .colorResId(R.color.grey)
                .build());
    }

    private void initKeyBoard() {
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    checkFace.setChecked(false);
                }
            }
        });
        facePanel.setOnFaceItemClickListener(this);
    }

    private void initView() {
        listContent = findViewById(R.id.list_content);
        llBottomBar = findViewById(R.id.ll_bottom_bar);
        textAddIllustrated = findViewById(R.id.text_add_illustrated);
        imgAt = findViewById(R.id.img_at);
        checkFace = findViewById(R.id.check_face);
        bottomLine = findViewById(R.id.bottom_line);
        facePanel = findViewById(R.id.face_panel);
        findViewById(R.id.text_add_illustrated).setOnClickListener(this);
        findViewById(R.id.img_at).setOnClickListener(this);
        checkFace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                openOrCloseFacePanel();
            }
        });
    }


    @Override
    public String setupToolBarTitle() {
        return circleTitle == null ? "" : circleTitle;
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    public boolean setupToolBarRightButton2(View rightButton2) {
        TextView textView = (TextView) rightButton2;
        textView.setText("发布");
        int padding = SizeUtils.dp2px(13);
        textView.setPadding(padding, padding, padding, padding);
        rightButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addPost();
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertFragment.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage("是否退出")
                .setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        L3PostActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.alert_cancel, null)
                .create().show(getSupportFragmentManager(), "alert_quit");
    }


    @Override
    public void beforeUpload(AlbumFile albumFile) {
        for (AlbumPicturesGridAdapter adapter : picAdapterHolder) {
            adapter.setState(albumFile, BasePicturesGridAdapter.State.UPLOADING);
        }
    }

    @Override
    public void onUploadInProgress(AlbumFile albumFile, double percent) {
        for (AlbumPicturesGridAdapter adapter : picAdapterHolder) {
            adapter.setUpdatePercent(albumFile, percent);
        }
    }

    @Override
    public void onUploadFailed(AlbumFile albumFile, Throwable throwable) {
        throwable.printStackTrace();
        for (AlbumPicturesGridAdapter adapter : picAdapterHolder) {
            adapter.setState(albumFile, BasePicturesGridAdapter.State.FAILED);
        }
    }

    @Override
    public void onUploadSuccess(AlbumFile albumFile, JsonObject jsonObject) {
        for (AlbumPicturesGridAdapter adapter : picAdapterHolder) {
            adapter.setState(albumFile, BasePicturesGridAdapter.State.NORMAL);
        }
    }

    @Override
    public void onUploadComplete(List<UploadResponse<AlbumFile, JsonObject>> completeList, List<AlbumFile> failedList) {
        for (AlbumPicturesGridAdapter adapter : picAdapterHolder) {
            for (UploadResponse<AlbumFile, JsonObject> response : completeList) {
                adapter.setState(response.source, BasePicturesGridAdapter.State.NORMAL);
            }
            for (AlbumFile t : failedList) {
                adapter.setState(t, BasePicturesGridAdapter.State.FAILED);
            }
        }
    }

    @Override
    public void onAddPictureBtnClick(final PostContent postContent) {
        new ActionSheet.Builder()
                .addMenu("拍摄", "照片或小视频")
                .addMenu("从相册选择", "照片或小视频")
                .setListener(new ActionSheet.Listener() {
                    @Override
                    public void onMenuClicked(String id, int clickIndex, Map<String, Object> extData) {
                        currentPostContent = postContent;
                        if (clickIndex == 0) {

                            CameraActivity.openCamera(
                                    L3PostActivity.this,
                                    REQ_CAMERA,
                                    2000,
                                    10000,
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/dzlt/");
                            overridePendingTransition(0, 0);

                        } else if (clickIndex == 1) {

                            Album.album(L3PostActivity.this)
                                    .multipleChoice()
                                    .requestCode(REQ_PICTURE)
                                    .columnCount(4)
                                    .selectCount(9)
                                    .camera(false)
                                    .cameraVideoQuality(1)
//                                .checkedList(postContent.pictures)
                                    .onResult(new Action<ArrayList<AlbumFile>>() {
                                        @Override
                                        public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                            if (currentPostContent != null) {
                                                List<AlbumFile> pictures = currentPostContent.pictures;
                                                pictures.addAll(result);
                                                currentPostContent.requireUpdate = true;
                                                contentAdapter.notifyDataSetChanged();
                                                mPresenter.upload(result);
                                            }
                                        }
                                    })
                                    .start();
                        }
                    }
                })
                .create().show(getSupportFragmentManager(), "action_sheet");
    }

    @Override
    public void onPicturePreview(PostContent postContent, AlbumFile albumFile) {

        List<AlbumFile> pictures = currentPostContent.pictures;
        int index = pictures.indexOf(albumFile);
        if (index == -1) {
            index = 1;
        }
        Album.galleryAlbum(this)
                .requestCode(REQ_PICTURE)
                .checkedList(postContent.pictures)
                .currentPosition(index)
                .checkable(false)
//                .onResult((requestCode, result) -> {
//                    List<AlbumFile> needRemove = new ArrayList<>();
//                    for (AlbumFile picture : pictures) {
//                        if (!result.contains(picture)) {
//                            needRemove.add(picture);
//                        }
//                    }
//                    mPresenter.removeTasks(needRemove);
//
//                    pictures.clear();
//                    pictures.addAll(result);
//                    currentPostContent.requireUpdate = true;
//                    contentAdapter.notifyDataSetChanged();
//                })
                .start();
    }

    @Override
    public void onFaceItemClick(FacePanelView view, String face, int faceId) {
        View focusView = getCurrentFocus();
        if (focusView != null && focusView instanceof EditText) {
            EditText editText = (EditText) focusView;
            int index = editText.getSelectionStart();
            if (FacePanelView.KEY_DELETE.equals(face)) {
                //发送删除事件
                if (index > 0) {
                    editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                }
            } else {
                face = "{:" + face + ":}";
                int size = SizeUtils.dp2px(20);
                Drawable drawable = getResources().getDrawable(faceId);
                drawable.setBounds(0, 0, size, size);
                ImageSpan imageSpan = new ImageSpan(drawable, ALIGN_BOTTOM);
                SpannableString spannableString = new SpannableString(face);
                spannableString.setSpan(imageSpan, 0, face.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (index < 0 || index >= editText.getText().length()) {
                    editText.getEditableText().append(spannableString);
                } else {
                    editText.getEditableText().insert(index, spannableString);
                }

                editText.setSelection(index + face.length());
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CAMERA:
                    AlbumFile media = CameraActivity.getVideoOrPicture(data);
                    if (media != null) {
                        if (currentPostContent != null) {
                            currentPostContent.pictures.add(media);
                            currentPostContent.requireUpdate = true;
                            contentAdapter.notifyDataSetChanged();
                            mPresenter.upload(media);
                        }

                        //是否需要裁剪
//                        if (media.getMediaType() == AlbumFile.TYPE_IMAGE){
//                            Uri from = Uri.fromFile(new File(media.getPath()));
//                            Uri to = Uri.fromFile(CropUtil.getCropFileFromPath(media.getPath()));
//                            CropUtil.cropToFile(this, from, to)
//                                    .subscribe(new SimpleObserver<File>() {
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//                                            super.onSubscribe(d);
//                                            mRxManage.add(d);
//                                        }
//
//                                        @Override
//                                        public void onNext(File file) {
//                                            if (currentPostContent != null) {
//                                                currentPostContent.pictures.add(PictureFileUtils.createAlbumFile(file, false));
//                                                currentPostContent.requireUpdate = true;
//                                                contentAdapter.notifyDataSetChanged();
//                                                mPresenter.upload(media);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            e.printStackTrace();
//                                            ToastUtils.showShort("添加图片失败");
//                                        }
//                                    });
//                        } else {
//                            if (currentPostContent != null) {
//                                currentPostContent.pictures.add(media);
//                                currentPostContent.requireUpdate = true;
//                                contentAdapter.notifyDataSetChanged();
//                                mPresenter.upload(media);
//                            }
//                        }
                    }
                    break;

                case REQ_AT_USER: {
                    if (data != null){
//                        AtUser user = data.getParcelableExtra("user");
//                        String name = user.getName();
//                        if (!TextUtils.isEmpty(name)){
//                            name = "@" + name + " ";
//                        }
//                        View focusView = getCurrentFocus();
//                        if (focusView != null && focusView instanceof EditText) {
//                            EditText editText = (EditText) focusView;
//                            int index = editText.getSelectionStart();
//                            TagSpan tagSpan = new TagSpan(name, getResources().getColor(R.color.colorPrimary));
//                            tagSpan.setTag(R.id.tag_data, user);
//                            SpannableString spannableString = new SpannableString(name);
//                            spannableString.setSpan(tagSpan, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                            if (index < 0 || index >= editText.getText().length()) {
//                                editText.getEditableText().append(spannableString);
//                            } else {
//                                editText.getEditableText().insert(index, spannableString);
//                            }
//                            editText.setSelection(index + name.length());
//                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.text_add_illustrated) {
            addIllustrated();

        } else if (i == R.id.img_at) {
            atSomeone();

        }
    }

    private void atSomeone() {
        //TODO 去选择@某人
        //ARouter.getInstance().build(RouterPath.AT_USER).navigation(this, REQ_AT_USER);
    }

    private void addIllustrated() {
        items.add(new PostContent(true));
        contentAdapter.notifyItemRangeInserted(items.size() - 1, 1);
    }

    private void openOrCloseFacePanel() {
        if (checkFace.isChecked()) {
            KeyboardUtils.hideSoftInput(this);
            ViewUtils.setVisibility(View.VISIBLE, facePanel, bottomLine);
        } else {
            ViewUtils.setVisibility(View.GONE, facePanel, bottomLine);
        }
    }
}