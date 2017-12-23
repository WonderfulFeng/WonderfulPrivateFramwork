package com.wonderful.framework.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.ImageView;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {
    public static final int FROM_ALBUM = 0;
    public static final int TAKE_PHOTO = 1;
    private static final int OPEN_CROP = 2;
    private static final int OPEN_PICK_FROM_ALBUM = 3;
    private static final int OPEN_PICK_FROM_CAMERA = 4;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.ivImage)
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.test;
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        takePhoto();
    }

    private Uri imageUri;

    private void takePhoto() {
        getUri();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, OPEN_PICK_FROM_CAMERA);
    }

    private void getUri() {
        try {
            File imagePath = new File(Environment.getExternalStorageDirectory(), "headerImage.jpg");
            if (imagePath.exists()) {
                imagePath.delete();
            }
            imagePath.createNewFile();
            imageUri = Uri.fromFile(imagePath);// 将File对象转化成Uri对象
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_PICK_FROM_CAMERA:
                //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
                UCrop uCrop = UCrop.of(imageUri, imageUri);
                //TODO 初始化UCrop配置
                UCrop.Options options = new UCrop.Options();
                //设置裁剪图片可操作的手势
//                options.setAllowedGestures();
                options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                //是否隐藏底部容器，默认显示
                options.setHideBottomControls(false);
                //设置toolbar颜色
                options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
                //设置状态栏颜色
                options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
                //设置Toolbar标题
                options.setToolbarTitle("裁剪图片");
                //设置Toolbar标题等文字颜色
                options.setToolbarWidgetColor(Color.parseColor("#ff1111"));
                //设置裁剪的图片格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置裁剪的图片质量，取值0-100
                options.setCompressionQuality(100);
                //设置图片压缩最大值
//                options.setMaxBitmapSize(10000);
                //设置最多缩放的比例尺
//                options.setMaxScaleMultiplier(100.0f);
                //动画时间
//                options.setImageToCropBoundsAnimDuration(100);
                //是否显示椭圆裁剪框阴影
//                options.setOvalDimmedLayer(true);
                options.setCircleDimmedLayer(true);
                //设置椭圆裁剪框阴影颜色
                options.setDimmedLayerColor(Color.parseColor("#66666666"));
                //是否显示裁剪框
                options.setShowCropFrame(false);
                //设置裁剪框边的宽度
                options.setCropFrameStrokeWidth(0);
                //是否显示裁剪框网格
                options.setShowCropGrid(false);
                //设置裁剪框网格颜色
                options.setCropGridColor(Color.parseColor("#1111ff"));
                //设置裁剪框网格宽
//                options.setCropGridStrokeWidth(1);
                //是否能调整裁剪框
                options.setFreeStyleCropEnabled(true);
//                options.setActiveWidgetColor(Color.parseColor("#111111"));
                //TODO UCrop配置
                uCrop.withOptions(options);
//                uCrop.withMaxResultSize(200, 200);
                //设置裁剪图片的宽高比，比如1：1
//                uCrop.withAspectRatio(16, 9);
                //原始尺寸
//                uCrop.useSourceImageAspectRatio();
                //跳转裁剪页面2
                uCrop.start(this, UCrop.REQUEST_CROP);
                break;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            File fiel = new File(resultUri.getPath());
            ivImage.setImageBitmap(BitmapFactory.decodeFile(resultUri.getPath()));
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
