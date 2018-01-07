package com.wonderful.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;
import com.wonderful.framework.ui.VerticalTextView;
import com.wonderful.framework.ui.WonderfulCameraPreview;
import com.wonderful.framework.utils.WonderfulToastUtils;

import butterknife.BindView;
import me.pqpo.smartcropperlib.view.CropImageView;

public class OCRActivity extends BaseActivity {

    public static final int GO_TO_SETTING = 0;
    public static final int PERMISSION_HEADER_CAMERA = 1;

    @BindView(R.id.tvMessage)
    VerticalTextView tvMessage;
    @BindView(R.id.rlRoot)
    RelativeLayout rlRoot;
    @BindView(R.id.surfaceView)
    WonderfulCameraPreview surfaceView;
    @BindView(R.id.ivCrop)
    CropImageView ivCrop;
    @BindView(R.id.ivShow)
    ImageView ivShow;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OCRActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surfaceView.release();
    }


    @Override
    protected void initViews() {
        surfaceView.setDataCallback(new WonderfulCameraPreview.DataCallback() {
            @Override
            public void dataLoaded(Bitmap bitmap) {
//                if (bitmap != null) bitmap = WonderfulBitmapUtils.zoomBitmap(bitmap, 1000, 1000);
                ivCrop.setImageToCrop(bitmap);
                Bitmap bm = null;
                if (ivCrop.canRightCrop()) bm = ivCrop.crop();
                if (bm != null) ivShow.setImageBitmap(bm);
            }

            @Override
            public void onDetected(String message) {

            }
        });
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        surfaceView.start(getCameraInstance(), cameraId);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_ocr;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && Camera.getNumberOfCameras() > 0;
    }

    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public Camera getCameraInstance() {
        Camera camera = null;
        try {  // 0 后置 Camera.CameraInfo.CAMERA_FACING_BACK   1 前置  Camera.CameraInfo.CAMERA_FACING_FRONT
            if (checkCameraHardware(this))
                if (Camera.getNumberOfCameras() > 1) camera = Camera.open(cameraId);
                else {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    camera = Camera.open(cameraId);
                }
        } catch (Exception e) {
            WonderfulToastUtils.showToast("相机已被暂用或无相机!");
            finish();
        }
        return camera;
    }

}
