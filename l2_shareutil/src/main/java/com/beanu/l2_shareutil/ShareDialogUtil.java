package com.beanu.l2_shareutil;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.widget.dialog.BottomDialog;
import com.beanu.l2_shareutil.share.ShareListener;
import com.beanu.l2_shareutil.share.SharePlatform;


/**
 * 分享弹窗
 * Created by Beanu on 2017/6/20.
 */

public class ShareDialogUtil {

    public static void showShareDialog(final Context context, final String title, final String summary, final String targetUrl, final int thumbResId, final ShareListener shareListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_share_dialog, null);
        final BottomDialog bottomDialog = new BottomDialog(context, view);
        bottomDialog.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        bottomDialog.show();

        // 监听
        final View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int platform = SharePlatform.WX;
                int i = v.getId();
                if (i == R.id.share_wx) {// 分享到微信
                    platform = SharePlatform.WX;

                } else if (i == R.id.share_wx_comment) {// 分享到朋友圈

                    platform = SharePlatform.WX_TIMELINE;


                } else if (i == R.id.share_qq) {
                    platform = SharePlatform.QQ;

                } else if (i == R.id.share_qq_zone) {
                    platform = SharePlatform.QZONE;

                } else if (i == R.id.share_weibo) {
                    platform = SharePlatform.WEIBO;

                } else if (i == R.id.text_cancel) {
                    platform = -1;

                }

                if (platform != -1) {
                    ShareUtil.shareMedia(
                            context,
                            platform,
                            title,
                            summary,
                            targetUrl,
                            BitmapFactory.decodeResource(context.getResources(), thumbResId),
                            shareListener);
                }

                bottomDialog.dismiss();
            }

        };
        TextView mViewWeixin = (TextView) view.findViewById(R.id.share_wx);
        TextView mViewPengyou = (TextView) view.findViewById(R.id.share_wx_comment);
        TextView mViewqq = (TextView) view.findViewById(R.id.share_qq);
        TextView mViewqqzone = (TextView) view.findViewById(R.id.share_qq_zone);
        TextView mViewWeibo = (TextView) view.findViewById(R.id.share_weibo);


        TextView mBtnCancel = (TextView) view.findViewById(R.id.text_cancel);


        mViewWeixin.setOnClickListener(listener);
        mViewPengyou.setOnClickListener(listener);
        mViewqq.setOnClickListener(listener);
        mViewqqzone.setOnClickListener(listener);
        mViewWeibo.setOnClickListener(listener);
        mBtnCancel.setOnClickListener(listener);
    }
}
