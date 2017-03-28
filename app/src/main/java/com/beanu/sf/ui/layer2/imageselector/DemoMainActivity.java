package com.beanu.sf.ui.layer2.imageselector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.beanu.l2_imageselector.GlideLoader;
import com.beanu.l2_imageselector.R;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;

public class DemoMainActivity extends AppCompatActivity {

    private Adapter adapter;
    private ArrayList<String> path = new ArrayList<>();

    public static final int REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo);


        Button btn_sigle = (Button) findViewById(R.id.btn);
        Button btn_mutil = (Button) findViewById(R.id.btn_mutil);
        RecyclerView recycler = (RecyclerView) super.findViewById(R.id.recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        adapter = new Adapter(this, path);
        recycler.setAdapter(adapter);

        //单选
        btn_sigle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImgSelConfig imageConfig
                        = new ImgSelConfig.Builder(DemoMainActivity.this, new GlideLoader())

                        // 是否多选, 默认true
                        .multiSelect(false)
                        // “确定”按钮背景色
                        .btnBgColor(Color.GRAY)
                        // “确定”按钮文字颜色
                        .btnTextColor(Color.BLUE)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#3F51B5"))
                        // 返回图标ResId
                        .backResId(R.drawable.ic_back)
                        // 标题
                        .title("图片")
                        // 标题文字颜色
                        .titleColor(Color.WHITE)
                        // TitleBar背景色
                        .titleBgColor(Color.parseColor("#3F51B5"))

                        // 裁剪大小。needCrop为true的时候配置
                        .cropSize(1, 1, 200, 200)
                        .needCrop(true)
                        // 第一个是否显示相机，默认true
                        .needCamera(true)
                        .build();

                ImgSelActivity.startActivity(DemoMainActivity.this, imageConfig, REQUEST_CODE);// 开启图片选择器

            }
        });

        //多选
        btn_mutil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImgSelConfig imageConfig
                        = new ImgSelConfig.Builder(DemoMainActivity.this,
                        // GlideLoader 可用自己用的缓存库
                        new GlideLoader())

                        // 是否多选, 默认true
                        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                        // “确定”按钮背景色
                        .btnBgColor(Color.GRAY)
                        // “确定”按钮文字颜色
                        .btnTextColor(Color.BLUE)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#3F51B5"))
                        // 返回图标ResId
                        .backResId(R.drawable.ic_back)
                        // 标题
                        .title("图片")
                        // 标题文字颜色
                        .titleColor(Color.WHITE)
                        // TitleBar背景色
                        .titleBgColor(Color.parseColor("#3F51B5"))
                        .maxNum(9)
                        .build();


                ImgSelActivity.startActivity(DemoMainActivity.this, imageConfig, REQUEST_CODE);// 开启图片选择器

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);

            for (String path : pathList) {
                Log.i("ImagePathList", path);
            }

            path.clear();
            path.addAll(pathList);
            adapter.notifyDataSetChanged();
        }
    }
}
