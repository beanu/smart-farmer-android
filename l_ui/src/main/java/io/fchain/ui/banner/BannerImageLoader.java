//package io.fchain.ui.banner;
//
//import android.content.Context;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.beanu.arad.support.log.KLog;
//import com.bumptech.glide.Glide;
//import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
//import com.youth.banner.loader.ImageLoader;
//
//import java.util.Locale;
//
//import io.fchain.common.model.bean.BannerEntity;
//import io.fchain.common.util.LocalManageUtil;
//
///**
// * @author liuhuan
// */
//public class BannerImageLoader extends ImageLoader {
//    @Override
//    public void displayImage(Context context, Object path, ImageView imageView) {
//        /*
//         注意：
//         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
//         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
//         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
//         切记不要胡乱强转！
//         */
//
//        if (path instanceof BannerEntity) {
//
//            BannerEntity bannerItem = (BannerEntity) path;
//            String imageUrl = "";
//            String language = LocalManageUtil.getSetLanguageLocale().getLanguage();
//            if (language.equals(Locale.KOREAN.getLanguage())) {
//                imageUrl = bannerItem.getImage_korean();
//            } else {
//                imageUrl = bannerItem.getImage_english();
//            }
//
//            Glide.with(context)
//                    .load(imageUrl)
//                    .into(imageView);
//        } else if (path instanceof String) {
//            Glide.with(context)
//                    .load(path)
//                    .into(imageView);
//        }
//
//    }
//
//    /**
//     * 提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//     *
//     * @param context
//     * @return
//     */
//    @Override
//    public ImageView createImageView(Context context) {
//        return null;
//    }
//}