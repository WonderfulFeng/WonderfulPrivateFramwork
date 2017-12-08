package com.example.administrator.myprivateframework.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;
import static android.view.SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS;

/**
 * Created by Administrator on 2017/10/12.
 */

public class WonderfulCameraPreview extends SurfaceView {
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private DataCallback dataCallback;
    private InnerCallback innerCallbackl;
    private boolean isCallbacked = true;
    private boolean isSend = false;
    private int cameraId;

    private Rect rect;

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
        innerCallbackl = new InnerCallback();
    }

    public WonderfulCameraPreview(Context context) {
        super(context);
    }

    public WonderfulCameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WonderfulCameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start(Camera camera, int cameraId) {
        this.camera = camera;
        this.cameraId = cameraId;
        camera.setParameters(camera.getParameters());
        camera.setPreviewCallback(innerCallbackl);
        camera.setFaceDetectionListener(new FaceDetectionListener());
        surfaceHolder = getHolder();
        //SURFACE_TYPE_NORMAL：用RAM缓存原生数据的普通Surface
        //SURFACE_TYPE_HARDWARE：适用于DMA(Direct memory access )引擎和硬件加速的Surface
        //SURFACE_TYPE_GPU：适用于GPU加速的Surface
        //SURFACE_TYPE_PUSH_BUFFERS：表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，在Camera图像预览中就使用该类型的Surface，有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。如果设置这种类型则就不能调用lockCanvas来获取Canvas对象了。
        surfaceHolder.setType(SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolderCallback());
    }

    public void setCallbacked(boolean callback) {
        isCallbacked = callback;
    }

    private boolean checkDetection() {
        if (camera != null) return camera.getParameters().getMaxNumDetectedFaces() > 0 ? true : false;
        else return false;
    }

    public void rotation(int rotation) {
        camera.setDisplayOrientation(rotation);
    }

    public void release() {
        if (camera == null) return;
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    class InnerCallback implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (camera == null) return;
            Camera.Size size = camera.getParameters().getPreviewSize();
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
            if (image != null) {
                if (!checkDetection()) isSend = true;
                if (isCallbacked && isSend && dataCallback != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, stream);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                    dataCallback.dataLoaded(bitmap);
//                    isCallbacked = false;
                }
            }
        }
    }

    public void setCameraDisplayOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(1, info);
        int rotation = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    class SurfaceHolderCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                surfaceHolder = holder;
                //0, 90, 180,  270.
//                camera.setDisplayOrientation(90);
                setCameraDisplayOrientation();
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                if (checkDetection()) camera.startFaceDetection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            release();
        }
    }

    class FaceDetectionListener implements Camera.FaceDetectionListener {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if (dataCallback != null) {
                if (faces.length == 0) {
                    dataCallback.onDetected("请靠近摄像头");
                    isSend = false;
                } else if (faces.length == 1) {
                    dataCallback.onDetected("正在识别中...");
                    rect = faces[0].rect;
                    isSend = true;
                } else if (faces.length > 1) {
                    dataCallback.onDetected("检测到有多张人脸");
                    isSend = false;
                }
            }
        }
    }

    public interface DataCallback {
        void dataLoaded(Bitmap bitmap);

        void onDetected(String message);
    }
}
