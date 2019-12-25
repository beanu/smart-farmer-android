package io.fchain.ui;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

/**
 * 所有的底部弹窗
 */
public class FireflyBottomSheet extends QMUIBottomSheet {

    public FireflyBottomSheet(Context context) {
        super(context);
    }

    /**
     * 顶部+自定义内容+底部绿色按钮
     */
    public static class FireFlyBottomSheetBuilder {

        private Context mContext;

        private FireflyBottomSheet mDialog;
        private View contentView;
        private String mTitle;
        private String mBottomBtnText;
        private boolean isBottomBtnVisible = false;
        //关闭按钮默认显示
        private boolean isCloseBtnVisible = true;
        private boolean isRightBtnVisible = false;
        @DrawableRes private int rightDrawableRes;
        private OnDismissListener mOnBottomDialogDismissListener;
        private OnSheetCloseListener mOnSheetCloseListener;
        private OnSheetRightButtonClickListener mOnSheetRightButtonClickListener;
        private OnSheetBottomButtonClickListener mOnSheetBottomButtonClickListener;

        public FireFlyBottomSheetBuilder(Context context) {
            mContext = context;
        }

        public FireFlyBottomSheetBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public FireFlyBottomSheetBuilder setBottomButtonText(String btnText) {
            this.mBottomBtnText = btnText;
            return this;
        }

        public FireFlyBottomSheetBuilder setBottomButton(String buttonText, boolean isVisible) {
            this.isBottomBtnVisible = isVisible;
            this.mBottomBtnText = buttonText;
            return this;
        }

        public FireFlyBottomSheetBuilder setCloseButtonVisible(boolean isCloseBtnVisible) {
            this.isCloseBtnVisible = isCloseBtnVisible;
            return this;
        }

        public FireFlyBottomSheetBuilder setRightButton(@DrawableRes int drawableId, boolean isVisible) {
            this.rightDrawableRes = drawableId;
            this.isRightBtnVisible = isVisible;
            return this;
        }

        public FireFlyBottomSheetBuilder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        public FireFlyBottomSheetBuilder setOnBottomDialogDismissListener(OnDismissListener onBottomDialogDismissListener) {
            this.mOnBottomDialogDismissListener = onBottomDialogDismissListener;
            return this;
        }

        public FireFlyBottomSheetBuilder setOnSheetCloseListener(OnSheetCloseListener onSheetCloseListener) {
            this.mOnSheetCloseListener = onSheetCloseListener;
            return this;
        }

        public FireFlyBottomSheetBuilder setOnSheetRightButtonClickListener(OnSheetRightButtonClickListener onSheetRightButtonClickListener) {
            this.mOnSheetRightButtonClickListener = onSheetRightButtonClickListener;
            return this;
        }

        public FireFlyBottomSheetBuilder setOnSheetBottomButtonClickListener(OnSheetBottomButtonClickListener onSheetBottomButtonClickListener) {
            this.mOnSheetBottomButtonClickListener = onSheetBottomButtonClickListener;
            return this;
        }

        private View buildViews() {
            View wrapperView = View.inflate(mContext, R.layout.common_view_bottomsheet, null);
            TextView txtTitle = wrapperView.findViewById(R.id.txt_bottomsheet_title);
            txtTitle.setText(mTitle);
            QMUIAlphaImageButton closeBtn = wrapperView.findViewById(R.id.imgbtn_bottomsheet_close);
            closeBtn.setVisibility(isCloseBtnVisible ? View.VISIBLE : View.GONE);
            closeBtn.setOnClickListener(v -> {
                if (mDialog == null) {
                    return;
                }
                mDialog.dismiss();
                if (mOnSheetCloseListener != null) {
                    mOnSheetCloseListener.onClick(mDialog);
                }
            });
            QMUIAlphaImageButton rightBtn = wrapperView.findViewById(R.id.imgbtn_bottomsheet_top_right);
            rightBtn.setVisibility(isRightBtnVisible ? View.VISIBLE : View.GONE);
            if (isRightBtnVisible) {
                rightBtn.setImageResource(rightDrawableRes);
            }
            rightBtn.setOnClickListener(v -> {
                if (mDialog == null) {
                    return;
                }
                if (mOnSheetRightButtonClickListener != null) {
                    mOnSheetRightButtonClickListener.onClick(mDialog);
                }
            });
            FrameLayout parent = wrapperView.findViewById(R.id.layout_bottomsheet_content);
            if (contentView != null) {
                parent.addView(contentView);
            }
            QMUIRoundButton bottomBtn = wrapperView.findViewById(R.id.btn_bottomsheet_opretion);
            bottomBtn.setVisibility(isBottomBtnVisible ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(mBottomBtnText)) {
                bottomBtn.setText(mBottomBtnText);
            }
            bottomBtn.setOnClickListener(v -> {
                if (mDialog == null) {
                    return;
                }
                if (mOnSheetBottomButtonClickListener != null) {
                    mOnSheetBottomButtonClickListener.onClick(mDialog, bottomBtn);
                }
            });

            return wrapperView;
        }

        public FireflyBottomSheet build() {
            mDialog = new FireflyBottomSheet(mContext);
            View contentView = buildViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        public interface OnSheetCloseListener {
            void onClick(FireflyBottomSheet sheet);
        }

        public interface OnSheetRightButtonClickListener {
            void onClick(FireflyBottomSheet sheet);
        }

        public interface OnSheetBottomButtonClickListener {
            void onClick(FireflyBottomSheet sheet, QMUIRoundButton button);
        }
    }


    /**
     * menu菜单样式
     */
    public static class BottomMenuSheetBuilder {
        private Context mContext;
        private QMUIBottomSheet mDialog;
        private List<String> mItems;
        private BaseAdapter mAdapter;
        private ListView mContainerView;
        private TextView mBottomButton;
        private OnSheetItemClickListener mOnSheetItemClickListener;
        private OnDismissListener mOnBottomDialogDismissListener;

        public BottomMenuSheetBuilder(Context context) {
            mContext = context;
            mItems = new ArrayList<>();
        }

        public BottomMenuSheetBuilder addItem(String text) {
            mItems.add(text);
            return this;
        }

        public BottomMenuSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public BottomMenuSheetBuilder setOnBottomDialogDismissListener(OnDismissListener onBottomDialogDismissListener) {
            mOnBottomDialogDismissListener = onBottomDialogDismissListener;
            return this;
        }

        public interface OnSheetItemClickListener {
            void onClick(QMUIBottomSheet dialog, View itemView, int position);
        }

        public QMUIBottomSheet build() {
            mDialog = new QMUIBottomSheet(mContext);
            View contentView = buildViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        private View buildViews() {
            View wrapperView = View.inflate(mContext, getContentViewLayoutId(), null);
            mBottomButton = wrapperView.findViewById(R.id.bottom_sheet_close_button);
            mContainerView = wrapperView.findViewById(R.id.listview);

            //TODO 添加四个item的时候 高度不对
//            if (needToScroll()) {
//                mContainerView.getLayoutParams().height = getListMaxHeight();
//            }

            mBottomButton.setOnClickListener(v -> mDialog.dismiss());

            mAdapter = new ListAdapter();
            mContainerView.setAdapter(mAdapter);
            return wrapperView;
        }

        protected int getContentViewLayoutId() {
            return R.layout.bottom_sheet_menu;
        }

        private boolean needToScroll() {
            int itemHeight = QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_list_item_height);
            int totalHeight = mItems.size() * itemHeight;

            if (mBottomButton != null) {
                totalHeight += QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_button_height);
            }
            return totalHeight > getListMaxHeight();
        }

        protected int getListMaxHeight() {
            return (int) (QMUIDisplayHelper.getScreenHeight(mContext) * 0.4);
        }

        private class ViewHolder {
            TextView textView;
        }

        private class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public String getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final String data = getItem(position);
                final ViewHolder holder;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.bottom_sheet_menu_item, parent, false);
                    holder = new ViewHolder();
                    holder.textView = convertView.findViewById(R.id.bottom_dialog_list_item_title);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.textView.setText(data);

                convertView.setOnClickListener(v -> {
                    if (mOnSheetItemClickListener != null) {
                        mOnSheetItemClickListener.onClick(mDialog, v, position);
                    }
                });
                return convertView;
            }


        }


    }


    /**
     * 列表选择样式
     */
    public static class BottomListChooseSheetBuilder {

        private Context mContext;

        private QMUIBottomSheet mDialog;
        private List<BottomSheetListChooseItemData> mItems;
        private BaseAdapter mAdapter;
        private ListView mContainerView;
        private boolean mNeedRightMark; //是否需要rightMark,标识当前项
        private int mCheckedIndex;
        private String mTitle;
        private TextView mTitleTv;
        private int rightDrawableRes;
        private int gravity;

        private OnSheetItemClickListener mOnSheetItemClickListener;
        private OnDismissListener mOnBottomDialogDismissListener;
        private View.OnClickListener mOnRightButtonClickListener;

        public BottomListChooseSheetBuilder(Context context) {
            this(context, false);
        }

        /**
         * @param needRightMark 是否需要在被选中的 Item 右侧显示一个勾(使用 {@link #setCheckedIndex(int)} 设置选中的 Item)
         */
        public BottomListChooseSheetBuilder(Context context, boolean needRightMark) {
            mContext = context;
            mItems = new ArrayList<>();
            mNeedRightMark = needRightMark;
        }

        /**
         * 设置要被选中的 Item 的下标。
         * <p>
         * 注意:仅当 {@link #mNeedRightMark} 为 true 时才有效。
         */
        public BottomListChooseSheetBuilder setCheckedIndex(int checkedIndex) {
            mCheckedIndex = checkedIndex;
            return this;
        }

        public BottomListChooseSheetBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public BottomListChooseSheetBuilder addItem(String text) {
            mItems.add(new BottomSheetListChooseItemData(text));
            return this;
        }

        public BottomListChooseSheetBuilder addItem(String text, String desc) {
            mItems.add(new BottomSheetListChooseItemData(text, desc));
            return this;
        }

        public BottomListChooseSheetBuilder addItem(Drawable img, String text, String desc, int gravity) {
            mItems.add(new BottomSheetListChooseItemData(img, text, desc, gravity));
            return this;
        }

        public BottomListChooseSheetBuilder addItem(String text, String desc, boolean disable) {
            mItems.add(new BottomSheetListChooseItemData(null, text, desc, disable));
            return this;
        }

        public BottomListChooseSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public BottomListChooseSheetBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public BottomListChooseSheetBuilder setTitle(int resId) {
            mTitle = mContext.getResources().getString(resId);
            return this;
        }

        public BottomListChooseSheetBuilder setRightButton(@DrawableRes int drawableId, View.OnClickListener listener) {
            this.rightDrawableRes = drawableId;
            this.mOnRightButtonClickListener = listener;
            return this;
        }


        public QMUIBottomSheet build() {
            mDialog = new QMUIBottomSheet(mContext);
            View contentView = buildViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        private View buildViews() {
            View wrapperView = View.inflate(mContext, R.layout.bottom_sheet_list_choose, null);
            mTitleTv = wrapperView.findViewById(R.id.txt_bottomsheet_title);
            mContainerView = wrapperView.findViewById(R.id.listview);
            QMUIAlphaImageButton rightButton = wrapperView.findViewById(R.id.imgbtn_bottomsheet_top_right);
            QMUIAlphaImageButton closeButton = wrapperView.findViewById(R.id.imgbtn_bottomsheet_close);

            if (mTitle != null && mTitle.length() != 0) {
                mTitleTv.setVisibility(View.VISIBLE);
                mTitleTv.setText(mTitle);
            } else {
                mTitleTv.setVisibility(View.GONE);
            }

            closeButton.setOnClickListener(v -> mDialog.dismiss());

            if (rightDrawableRes != 0) {
                rightButton.setImageResource(rightDrawableRes);
                rightButton.setVisibility(View.VISIBLE);
                rightButton.setOnClickListener(mOnRightButtonClickListener);
            } else {
                rightButton.setVisibility(View.GONE);
            }
            QMUIRoundButton bottomBtn = wrapperView.findViewById(R.id.btn_bottomsheet_opretion);
            bottomBtn.setVisibility(View.GONE);

            if (needToScroll()) {
                mContainerView.getLayoutParams().height = getListMaxHeight();
                mDialog.setOnBottomSheetShowListener(() -> mContainerView.setSelection(mCheckedIndex));
            }

            mAdapter = new ListAdapter();
            mContainerView.setAdapter(mAdapter);
            return wrapperView;
        }


        private boolean needToScroll() {
            //TODO 高度计算不正确
            int itemHeight = QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_list_item_height);
            int totalHeight = mItems.size() * itemHeight;

            if (mTitleTv != null && !QMUILangHelper.isNullOrEmpty(mTitle)) {
                totalHeight += QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_title_height);
            }
            return totalHeight > getListMaxHeight();
        }


        private int getListMaxHeight() {
            return (int) (QMUIDisplayHelper.getScreenHeight(mContext) * 0.4);
        }

        public void notifyDataSetChanged() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
            if (needToScroll()) {
                mContainerView.getLayoutParams().height = getListMaxHeight();
                mContainerView.setSelection(mCheckedIndex);
            }
        }


        public interface OnSheetItemClickListener {
            void onClick(QMUIBottomSheet dialog, View itemView, int position);
        }

        private static class ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView descView;
            LinearLayout txtContentLayout;
            View markView;

            RelativeLayout mLayout;
        }

        private class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public BottomSheetListChooseItemData getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final BottomSheetListChooseItemData data = getItem(position);
                final ViewHolder holder;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.bottom_sheet_list_choose_item, parent, false);
                    holder = new ViewHolder();
                    holder.mLayout = convertView.findViewById(R.id.layout_item);
                    holder.imageView = convertView.findViewById(R.id.img_tag);
                    holder.txtContentLayout = convertView.findViewById(R.id.layout_txt_content);
                    holder.textView = convertView.findViewById(R.id.txt_bottom_sheet_item_text);
                    holder.descView = convertView.findViewById(R.id.txt_bottom_sheet_item_desc);
                    holder.markView = convertView.findViewById(R.id.img_bottom_sheet_choose_mark);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (data.image != null) {
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.imageView.setImageDrawable(data.image);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                }
                holder.txtContentLayout.setGravity(data.gravity);

                holder.textView.setText(data.text);
                if (!TextUtils.isEmpty(data.desc)) {
                    holder.descView.setText(data.desc);
                    holder.descView.setVisibility(View.VISIBLE);
                } else {
                    holder.descView.setVisibility(View.GONE);
                }

                if (data.isDisabled) {
                    holder.textView.setEnabled(false);
                    convertView.setEnabled(false);
                } else {
                    holder.textView.setEnabled(true);
                    convertView.setEnabled(true);
                }

                if (mNeedRightMark) {

                    if (mCheckedIndex == position) {
                        holder.textView.setTextColor(ContextCompat.getColor(holder.textView.getContext(), R.color.color_font_green));
                        holder.descView.setTextColor(ContextCompat.getColor(holder.descView.getContext(), R.color.color_font_green));
                        holder.markView.setVisibility(View.VISIBLE);
                        holder.mLayout.setBackgroundResource(R.drawable.bottom_sheet_list_choose_item_bg_selected);
                    } else {
                        holder.textView.setTextColor(ContextCompat.getColor(holder.textView.getContext(), R.color.color_font_app));
                        holder.descView.setTextColor(ContextCompat.getColor(holder.descView.getContext(), R.color.color_font_gray));
                        holder.markView.setVisibility(View.GONE);
                        holder.mLayout.setBackgroundResource(R.color.qmui_config_color_white);
                    }
                } else {
                    holder.markView.setVisibility(View.GONE);
                    holder.mLayout.setBackgroundResource(R.color.qmui_config_color_white);
                }

                convertView.setOnClickListener(v -> {

                    if (mNeedRightMark) {
                        setCheckedIndex(position);
                        notifyDataSetChanged();
                    }
                    if (mOnSheetItemClickListener != null) {
                        mOnSheetItemClickListener.onClick(mDialog, v, position);
                    }
                });
                return convertView;
            }
        }


        private class BottomSheetListChooseItemData {
            Drawable image = null;
            String text;
            String desc;
            int gravity = Gravity.CENTER;
            boolean isDisabled = false;

            public BottomSheetListChooseItemData(String text) {
                this.text = text;
            }

            public BottomSheetListChooseItemData(String text, String desc) {
                this.text = text;
                this.desc = desc;
            }

            public BottomSheetListChooseItemData(Drawable image, String text, String desc) {
                this.image = image;
                this.text = text;
                this.desc = desc;
            }

            public BottomSheetListChooseItemData(Drawable image, String text, String desc, int gravity) {
                this.image = image;
                this.text = text;
                this.desc = desc;
                this.gravity = gravity;
            }

            public BottomSheetListChooseItemData(Drawable image, String text, String desc, boolean isDisabled) {
                this.image = image;
                this.text = text;
                this.desc = desc;
                this.isDisabled = isDisabled;
            }
        }
    }


    /**
     * 多选列表样式
     */
    public static class BottomMultiChoiseSheetBuilder {

        private Context mContext;

        private QMUIBottomSheet mDialog;
        private List<BottomSheetMutliChoiseItemData> mItems;
        private BaseAdapter mAdapter;
        private ListView mContainerView;
        private HashSet<Integer> mCheckedIndex;
        private String mTitle;
        private TextView mTitleTv;
        private String mBottomBtnText;
        private int rightDrawableRes;
        private int gravity;

        private OnSheetItemClickListener mOnSheetItemClickListener;
        private OnDismissListener mOnBottomDialogDismissListener;
        private View.OnClickListener mOnRightButtonClickListener;
        private OnSheetBottomButtonClickListener mOnSheetBottomButtonClickListener;

        public BottomMultiChoiseSheetBuilder(Context context) {
            mContext = context;
            mItems = new ArrayList<>();
            mCheckedIndex = new HashSet<>();
        }

        public BottomMultiChoiseSheetBuilder addCheckedIndex(int checkedIndex) {
            mCheckedIndex.add(checkedIndex);
            return this;
        }

        public BottomMultiChoiseSheetBuilder setUnCheckedIndex(int checkedIndex) {
            mCheckedIndex.remove(checkedIndex);
            return this;
        }

        public BottomMultiChoiseSheetBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public BottomMultiChoiseSheetBuilder addItem(String text) {
            mItems.add(new BottomSheetMutliChoiseItemData(text));
            return this;
        }

        public BottomMultiChoiseSheetBuilder addItem(String text, String desc) {
            mItems.add(new BottomSheetMutliChoiseItemData(text, desc));
            return this;
        }

        public BottomMultiChoiseSheetBuilder addItem(Drawable img, String text, String desc, int gravity) {
            mItems.add(new BottomSheetMutliChoiseItemData(img, text, desc, gravity));
            return this;
        }

        public BottomMultiChoiseSheetBuilder addItem(String text, String desc, boolean disable) {
            mItems.add(new BottomSheetMutliChoiseItemData(null, text, desc, disable));
            return this;
        }

        public BottomMultiChoiseSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public BottomMultiChoiseSheetBuilder setOnSheetBottomButtonClickListener(OnSheetBottomButtonClickListener onSheetBottomButtonClickListener) {
            this.mOnSheetBottomButtonClickListener = onSheetBottomButtonClickListener;
            return this;
        }

        public BottomMultiChoiseSheetBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public BottomMultiChoiseSheetBuilder setTitle(int resId) {
            mTitle = mContext.getResources().getString(resId);
            return this;
        }

        public BottomMultiChoiseSheetBuilder setRightButton(@DrawableRes int drawableId, View.OnClickListener listener) {
            this.rightDrawableRes = drawableId;
            this.mOnRightButtonClickListener = listener;
            return this;
        }

        public BottomMultiChoiseSheetBuilder setBottomButtonText(String btnText) {
            this.mBottomBtnText = btnText;
            return this;
        }

        public QMUIBottomSheet build() {
            mDialog = new QMUIBottomSheet(mContext);
            View contentView = buildViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        private View buildViews() {
            View wrapperView = View.inflate(mContext, R.layout.bottom_sheet_list_choose, null);
            mTitleTv = wrapperView.findViewById(R.id.txt_bottomsheet_title);
            mContainerView = wrapperView.findViewById(R.id.listview);
            QMUIAlphaImageButton rightButton = wrapperView.findViewById(R.id.imgbtn_bottomsheet_top_right);
            QMUIAlphaImageButton closeButton = wrapperView.findViewById(R.id.imgbtn_bottomsheet_close);

            if (mTitle != null && mTitle.length() != 0) {
                mTitleTv.setVisibility(View.VISIBLE);
                mTitleTv.setText(mTitle);
            } else {
                mTitleTv.setVisibility(View.GONE);
            }

            closeButton.setOnClickListener(v -> mDialog.dismiss());

            if (rightDrawableRes != 0) {
                rightButton.setImageResource(rightDrawableRes);
                rightButton.setVisibility(View.VISIBLE);
                rightButton.setOnClickListener(mOnRightButtonClickListener);
            } else {
                rightButton.setVisibility(View.GONE);
            }


            QMUIRoundButton bottomBtn = wrapperView.findViewById(R.id.btn_bottomsheet_opretion);
//            bottomBtn.setVisibility(isBottomBtnVisible ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(mBottomBtnText)) {
                bottomBtn.setText(mBottomBtnText);
            }
            bottomBtn.setOnClickListener(v -> {
                if (mDialog == null) {
                    return;
                }
                if (mOnSheetBottomButtonClickListener != null) {
                    mOnSheetBottomButtonClickListener.onClick(mDialog, bottomBtn, mCheckedIndex);
                }
            });


            if (needToScroll()) {
                mContainerView.getLayoutParams().height = getListMaxHeight();
                mDialog.setOnBottomSheetShowListener(() -> {

//                    for (Integer index : mCheckedIndex) {
//                        mContainerView.setSelection(index);
//                    }

                });
            }

            mAdapter = new ListAdapter();
            mContainerView.setAdapter(mAdapter);
            return wrapperView;
        }


        private boolean needToScroll() {
            //TODO 高度计算不正确
            int itemHeight = QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_list_item_height);
            int totalHeight = mItems.size() * itemHeight;

            if (mTitleTv != null && !QMUILangHelper.isNullOrEmpty(mTitle)) {
                totalHeight += QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_title_height);
            }
            return totalHeight > getListMaxHeight();
        }


        private int getListMaxHeight() {
            return (int) (QMUIDisplayHelper.getScreenHeight(mContext) * 0.4);
        }

        public void notifyDataSetChanged() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
            if (needToScroll()) {
                mContainerView.getLayoutParams().height = getListMaxHeight();
//                for (Integer index : mCheckedIndex) {
//                    mContainerView.setSelection(index);
//                }
            }
        }


        public interface OnSheetItemClickListener {
            void onClick(QMUIBottomSheet dialog, View itemView, int position);
        }

        private static class ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView descView;
            CheckBox markView;

            RelativeLayout mLayout;
        }

        private class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public BottomSheetMutliChoiseItemData getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final BottomSheetMutliChoiseItemData data = getItem(position);
                final ViewHolder holder;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.bottom_sheet_mutli_choise_item, parent, false);
                    holder = new ViewHolder();
                    holder.mLayout = convertView.findViewById(R.id.layout_item);
                    holder.imageView = convertView.findViewById(R.id.img_tag);
                    holder.textView = convertView.findViewById(R.id.txt_bottom_sheet_item_text);
                    holder.descView = convertView.findViewById(R.id.txt_bottom_sheet_item_desc);
                    holder.markView = convertView.findViewById(R.id.bottom_sheet_choose_mark);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (data.image != null) {
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.imageView.setImageDrawable(data.image);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                }

                holder.textView.setText(data.text);
                if (!TextUtils.isEmpty(data.desc)) {
                    holder.descView.setText(data.desc);
                    holder.descView.setVisibility(View.VISIBLE);
                } else {
                    holder.descView.setVisibility(View.GONE);
                }

                if (data.isDisabled) {
                    holder.textView.setEnabled(false);
                    convertView.setEnabled(false);
                } else {
                    holder.textView.setEnabled(true);
                    convertView.setEnabled(true);
                }

                holder.markView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        addCheckedIndex(position);
                    } else {
                        setUnCheckedIndex(position);
                    }
                });

                if (mCheckedIndex.contains(position)) {
                    holder.markView.setChecked(true);
                } else {
                    holder.markView.setChecked(false);
                }

                convertView.setOnClickListener(v -> {

                    holder.markView.setChecked(!holder.markView.isChecked());

                    if (mOnSheetItemClickListener != null) {
                        mOnSheetItemClickListener.onClick(mDialog, v, position);
                    }
                });
                return convertView;
            }
        }


        private class BottomSheetMutliChoiseItemData {
            Drawable image = null;
            String text;
            String desc;
            int gravity = Gravity.CENTER;
            boolean isDisabled = false;

            public BottomSheetMutliChoiseItemData(String text) {
                this.text = text;
            }

            public BottomSheetMutliChoiseItemData(String text, String desc) {
                this.text = text;
                this.desc = desc;
            }

            public BottomSheetMutliChoiseItemData(Drawable image, String text, String desc) {
                this.image = image;
                this.text = text;
                this.desc = desc;
            }

            public BottomSheetMutliChoiseItemData(Drawable image, String text, String desc, int gravity) {
                this.image = image;
                this.text = text;
                this.desc = desc;
                this.gravity = gravity;
            }

            public BottomSheetMutliChoiseItemData(Drawable image, String text, String desc, boolean isDisabled) {
                this.image = image;
                this.text = text;
                this.desc = desc;
                this.isDisabled = isDisabled;
            }
        }

        public interface OnSheetBottomButtonClickListener {
            void onClick(QMUIBottomSheet sheet, QMUIRoundButton button, HashSet<Integer> checkedIndex);
        }
    }


}
