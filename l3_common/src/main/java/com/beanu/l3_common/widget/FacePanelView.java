package com.beanu.l3_common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beanu.arad.widget.MeasuredViewPager;
import com.beanu.l3_common.R;
import com.beanu.l3_common.adapter.SimplePagerAdapter;
import com.beanu.l3_common.util.FaceUtil;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author by lizhihua
 * @date 2017/3/17.
 */
public class FacePanelView extends LinearLayout {
    public static final String KEY_DELETE = "delete";

    MeasuredViewPager faceViewPager;
    InkPageIndicator faceIndicator;

    private FacePanelAdapter adapter;
    private OnFaceItemClickListener listener;

    public FacePanelView(Context context) {
        super(context);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_face_panel, this);
        faceViewPager = findViewById(R.id.face_view_pager);
        faceIndicator = findViewById(R.id.face_indicator);

        adapter = new FacePanelAdapter(getPagedFaces());
        faceViewPager.setAdapter(adapter);
        faceIndicator.setViewPager(faceViewPager);
    }

    public void setOnFaceItemClickListener(OnFaceItemClickListener listener){
        this.listener = listener;
    }

    //获取表情数组
    private List<List<Pair<Integer, String>>> getPagedFaces() {
        final int pageSize = 20;
        List<List<Pair<Integer, String>>> faces = new ArrayList<>();
        int[] ids = Arrays.copyOf(FaceUtil.getFaceIds(), 92);
        String[] faceNames = Arrays.copyOf(FaceUtil.getFaceNames(), 92);
        int page = Math.round(ids.length / (float) pageSize);
        for (int i = 0; i < page; i++) {
            List<Pair<Integer, String>> pageFace = new ArrayList<>();
            for (int j = 0; j < pageSize; ++j) {
                int pos = i * pageSize + j;
                if (pos < 92) {
                    pageFace.add(new Pair<>(ids[pos], faceNames[pos]));
                } else {
                    break;
                }
            }
            pageFace.add(new Pair<>(R.drawable.face_delete, KEY_DELETE));
            faces.add(pageFace);
        }

        return faces;
    }

    private class FacePanelAdapter extends SimplePagerAdapter<List<Pair<Integer, String>>> implements AdapterView.OnItemClickListener {

        FacePanelAdapter(List<List<Pair<Integer, String>>> list) {
            super(list);
        }

        @Override
        protected View createView(ViewGroup container, final List<Pair<Integer, String>> data, int position) {
            Context context = container.getContext();
            GridView panel = (GridView) LayoutInflater.from(context).inflate(R.layout.item_panel_faces, container, false);
            panel.setAdapter(new ArrayAdapter<Pair<Integer, String>>(context, R.layout.item_face, R.id.text, data){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    Pair<Integer, String> item = data.get(position);
                    ImageView imageView = view.findViewById(R.id.image);
                    imageView.setImageResource(item.first);
                    view.setTag(R.id.tag_data, item);
                    return view;
                }
            });

            panel.setOnItemClickListener(this);

            return panel;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listener != null){
                Pair<Integer, String> item = (Pair<Integer, String>) view.getTag(R.id.tag_data);
                listener.onFaceItemClick(FacePanelView.this, item.second, item.first);
            }
        }
    }

    public interface OnFaceItemClickListener{
        /**
         * emoji点击事件
         * @param view
         * @param face
         * @param faceId
         */
        void onFaceItemClick(FacePanelView view, String face, int faceId);
    }

}
