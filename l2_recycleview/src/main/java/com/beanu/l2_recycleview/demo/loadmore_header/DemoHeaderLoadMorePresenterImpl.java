package com.beanu.l2_recycleview.demo.loadmore_header;

import com.beanu.l2_recycleview.demo.support.IndexImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beanu on 2016/12/20
 */

public class DemoHeaderLoadMorePresenterImpl extends DemoHeaderLoadMoreContract.Presenter {

    private List<IndexImage> mImageList = new ArrayList<>();


    @Override
    public List<IndexImage> getTopImageList() {
        if (mImageList.size() == 0) {
            IndexImage image1 = new IndexImage();
            image1.setId("1");
            image1.setTitle("信息变通");
            image1.setImgPath("http://ww4.sinaimg.cn/large/610dc034jw1fbffqo6jjoj20u011hgpx.jpg");
            mImageList.add(image1);

            IndexImage image2 = new IndexImage();
            image2.setId("2");
            image2.setTitle("美女");
            image2.setImgPath("http://ww4.sinaimg.cn/large/610dc034jw1fbeerrs7aqj20u011htec.jpg");
            mImageList.add(image2);


            IndexImage image3 = new IndexImage();
            image3.setId("3");
            image3.setTitle("无敌状态");
            image3.setImgPath("http://ww3.sinaimg.cn/large/610dc034jw1fbd818kkwjj20u011hjup.jpg");
            mImageList.add(image3);

        }

        return mImageList;
    }
}