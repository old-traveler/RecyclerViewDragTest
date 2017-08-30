package com.recyclerviewdragtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvMain;
    TextView tvDelete;
    ImageAdapter adapter;
    List<String> images;
    private int screenWidth;
    private static final int REQUEST_IMAGE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        initRcv();
    }

    private void initRcv() {
        rcvMain = (RecyclerView) findViewById(R.id.rcv_img);
        rcvMain.setLayoutManager(new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL));
        rcvMain.setItemAnimator(new DefaultItemAnimator());
        String plusPath = "android.resource://" + getPackageName() + "/mipmap/" + R.mipmap.mine_btn_plus;
        images = new ArrayList<>();
        images.add(plusPath);
        adapter = new ImageAdapter(images,this,screenWidth/3-2*CommonUtils.getPixelById(R.dimen.dp_2));
        rcvMain.setAdapter(adapter);
        MyCallBack myCallBack = new MyCallBack(adapter,images);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(rcvMain);
        rcvMain.addOnItemTouchListener(new OnRecyclerItemClickListener(rcvMain) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (images.get(vh.getAdapterPosition()).contains("android.resource://")){
                    MultiImageSelector.create()
                            .showCamera(true)
                            .count(9 - images.size() + 1)
                            .multi()
                            .start(MainActivity.this, REQUEST_IMAGE);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != images.size() -1 ){
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        myCallBack.setDragListener(new MyCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete){
                    tvDelete.setText("放开手指，进行删除");
                    tvDelete.setBackgroundResource(R.color.holo_red_dark);
                }else {
                    tvDelete.setText("拖动到此处，进行删除");
                    tvDelete.setBackgroundResource(R.color.holo_red_light);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start){
                    tvDelete.setVisibility(View.VISIBLE);
                }else {
                    tvDelete.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {//从相册选择完图片
            //压缩图片
            ArrayList<String> imageData =  data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            for (int i = 0; i < imageData.size(); i++) {
                images.add(0,imageData.get(i));
            }
            adapter.notifyDataSetChanged();
//            String[] img = new String[imageData.size()];
//            for (int i = 0; i < imageData.size(); i++) {
//                img[i] = imageData.get(i);
//            }
//            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
//            options.width= screenWidth/3;
//            options.height= screenWidth/3;
//            Tiny.getInstance().source(img).asFile().withOptions(options).compress(new FileCallback() {
//                @Override
//                public void callback(boolean isSuccess, String outfile) {
//                    if (isSuccess){
//                        for (String s : Arrays.asList(outfile)) {
//                            images.add(0,s);
//                        }
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(MainActivity.this, "添加数据", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(MainActivity.this, "出错", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        }
    }


}
