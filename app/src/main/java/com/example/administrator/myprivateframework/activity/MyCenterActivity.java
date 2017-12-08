package com.example.administrator.myprivateframework.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.app.MyApplication;
import com.example.administrator.myprivateframework.base.BaseActivity;
import com.example.administrator.myprivateframework.utils.WonderfulBitmapUtils;
import com.example.administrator.myprivateframework.utils.WonderfulFileUtils;
import com.example.administrator.myprivateframework.utils.WonderfulToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class MyCenterActivity extends BaseActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP = 2;
    public static final int PERMISSION_HEADER_ALBUM = 3;
    public static final int GO_TO_SETTING = 4;
    public static final int PERMISSION_HEADER_CAMERA = 5;
    private static final int CHOOSE_AIBUM = 6;
    @BindView(R.id.ivHeader)
    ImageView ivHeader;
    private PopupWindow headerPopup;
    private File imageFile;
    private Uri imageUri;
    private PermissionListener persionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case PERMISSION_HEADER_CAMERA:
                    startCamare();
                    break;
                case PERMISSION_HEADER_ALBUM:
                    choseFromAlbum();
                    break;

            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            AndPermission.defaultSettingDialog(MyCenterActivity.this, GO_TO_SETTING).show();
        }
    };

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            AndPermission.rationaleDialog(MyCenterActivity.this, rationale).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderPopup();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_center;
    }

    @Override
    protected void initViews() {
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerPopup.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void initHeaderPopup() {
        View popup = getLayoutInflater().inflate(R.layout.popup_header_select, null);
        TextView tvTakePhoto = (TextView) popup.findViewById(R.id.tvTakePhoto);
        TextView tvAlbum = (TextView) popup.findViewById(R.id.tvAlbum);
        RelativeLayout rlRoot = (RelativeLayout) popup.findViewById(R.id.rlRoot);
        headerPopup = new PopupWindow(popup, MyApplication.getApp().getmWidth(), MyApplication.getApp().getmHeight());
        headerPopup.setClippingEnabled(false);
        headerPopup.setBackgroundDrawable(new ColorDrawable());
        headerPopup.setOutsideTouchable(true);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerPopup.dismiss();
                AndPermission.with(MyCenterActivity.this).requestCode(PERMISSION_HEADER_CAMERA).permission(Permission.CAMERA).callback(persionListener).rationale(rationaleListener).start();
            }
        });
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerPopup.dismiss();
                AndPermission.with(MyCenterActivity.this).requestCode(PERMISSION_HEADER_ALBUM).permission(Permission.STORAGE).callback(persionListener).rationale(rationaleListener).start();
            }
        });
        rlRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                headerPopup.dismiss();
                return true;
            }
        });
    }

    private void startCamare() {
        imageFile = WonderfulFileUtils.getLongSaveFile(this, "header", "header.jpg_");
        if (imageFile == null) {
            WonderfulToastUtils.showToast("未知异常！");
            return;
        }
        imageUri = FileProvider7.getUriForFile(this, imageFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void startCrop(Uri uri) {
        if (uri == null) {
            WonderfulToastUtils.showToast("uri为空！！！");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
//        FileProvider7.setIntentDataAndType(this, intent, "application/vnd.android.package-archive", file, true);
//        intent.setDataAndType(uri, "image/*");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CROP);
    }

    private void choseFromAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_AIBUM);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    //TODO 相机返回 不裁剪
//                    try {
//                        Glide.with(this).load(imageFile).override(ivHeader.getWidth(), ivHeader.getHeight()).bitmapTransform(new CropCircleTransformation(this)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivHeader);
////                        ivHeader.setImageBitmap(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight()));
//                        WonderfulBitmapUtils.saveBitmapToFile(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight()), imageFile, 100);
//                    } catch (IOException e) {
//                        WonderfulLogUtils.logi("相机拍照返回异常：", e.getMessage());
//                        WonderfulToastUtils.showToast("未知异常！");
//                    }
                    //TODO 相机返回 裁剪
                    startCrop(imageUri);
                    break;
                case CROP:
                    ivHeader.setImageBitmap(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight()));
                    try {
                        WonderfulBitmapUtils.saveBitmapToFile(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight(), ivHeader.getWidth()), imageFile, 100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_AIBUM:
                    imageUri = data.getData();
                    File file = null;
                    if (Build.VERSION.SDK_INT >= 19)
                        file = getUriFromKitKat(imageUri);
                    else
                        file = getUriBeforeKitKat(imageUri);
                    if (file == null) {
                        WonderfulToastUtils.showToast("图库文件返回异常！");
                        return;
                    }
                    try {
                        imageFile = WonderfulFileUtils.getLongSaveFile(this, "header", "header.jpg_");
                        if (imageFile.exists()) {
                            imageFile.delete();
                            imageFile.createNewFile();
                        }
                        imageFile = WonderfulFileUtils.forChannel(file, imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO 相册返回  不裁剪
//                    ivHeader.setImageBitmap(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight()));
//                    WonderfulBitmapUtils.saveBitmapToFile(WonderfulBitmapUtils.zoomBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), ivHeader.getWidth(), ivHeader.getHeight(), ivHeader.getWidth())
                    //TODO  相册返回 裁剪
                    imageUri = FileProvider7.getUriForFile(this, imageFile);
                    startCrop(imageUri);
                    break;
                case GO_TO_SETTING:
                    AndPermission.with(this).requestCode(1).permission(Permission.PHONE).callback(persionListener).rationale(rationaleListener).start();
                    break;
            }
        }
    }

    private File getUriBeforeKitKat(Uri imageUri) {
        String imagePath = getImagePath(imageUri, null);
        if (imagePath != null)
            return new File(imagePath);
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private File getUriFromKitKat(Uri imageUri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, imageUri)) {
            //如果是document类型的Uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(imageUri.getAuthority())) {
                imageUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(imageUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // 如果是content 类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            // 如果是file 类型的Uri直接 获取路径
            imagePath = imageUri.getPath();
        }
        if (imagePath != null)
            return new File(imagePath);
        return null;
    }

    private String getImagePath(Uri uri, String selection) {
        String imagePath = null;
        //通过Uri 和 selection 来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null)
            if (cursor.moveToFirst())
                imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return imagePath;
    }


}
