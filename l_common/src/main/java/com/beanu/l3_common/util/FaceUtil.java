package com.beanu.l3_common.util;

import android.graphics.drawable.Drawable;

import com.beanu.arad.Arad;
import com.beanu.l3_common.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lizhihua on 2017/2/15.
 */

public class FaceUtil {
    private static String[] faceNames = {"爱你", "奸笑", "鼓掌", "沮丧", "拜拜", "悲伤", "调皮", "鄙视", "闭嘴", "恶心", "馋嘴", "抱抱", "吃惊", "打哈欠", "打脸", "惊讶", "打头", "憨笑", "害羞", "汗", "口罩", "微笑", "糗", "哼", "色", "挤眼", "可爱", "可怜", "得意", "困", "懒得理你", "大哭", "暴怒", "怒骂", "钱", "亲亲", "傻眼", "生病", "失望", "糗大了", "睡着了", "思考", "太开心", "偷笑", "吐", "抠鼻孔", "委屈", "笑哭", "大笑", "嘘", "阴险", "疑问", "右哼哼", "左哼哼", "晕", "抓狂", "男孩儿", "女孩儿", "围观", "奥特曼", "狗狗", "喵", "神兽", "兔子", "熊猫", "猪头", "不要", "赞", "哈哈", "来啊", "OK", "弱", "握手", "耶", "牛", "作揖", "防毒面具", "肥皂", "旅行", "炸弹", "玫瑰", "礼品", "蛋糕", "给力", "囧", "萌", "神马", "V5", "双喜", "毛线", "飞机", "蜡烛", "干杯", "精华", "加亮", "热门"};
    private static int[] iconIds = new int[faceNames.length];
    private static Map<String, Integer> faceMap = new HashMap<>();

    static {
        for (int i = 0; i < faceNames.length; ++i) {
            String fieldName = String.format(Locale.CHINA, "face_%03d", i + (i <= 92 ? 1 : 2));
            try {
                int id = R.drawable.class.getDeclaredField(fieldName).getInt(null);
                iconIds[i] = id;
                faceMap.put(faceNames[i], id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Drawable getFace(String text) {
        Drawable drawable = null;
        try {
            drawable = Arad.app.getResources().getDrawable(faceMap.get(text));
        } catch (Exception e) {
            //eat it
        }
        return drawable;
    }

    public static int[] getFaceIds(){
        return iconIds;
    }

    public static String[] getFaceNames(){
        return faceNames;
    }
}
