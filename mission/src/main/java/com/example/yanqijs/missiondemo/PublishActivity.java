package com.example.yanqijs.missiondemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import single.Mission;
import single.User;
import vo.MissionData;

public class PublishActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private long date;
    private File file, mFile;
    private EditText contentEditText;
    private TextView numberTextView;
    private String imagePath = null;
    private Boolean isFirest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();

        imageButton.setOnClickListener(this);
        contentEditText.setInputType(InputType.TYPE_NULL);

        contentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirest) {
                    contentEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    Log.i("aa", "first");
                    isFirest = false;
                    inputMethodManager.showSoftInput(contentEditText, 0);
                    contentEditText.callOnClick();
                } else {
                    Log.i("aa", "again");
                    contentEditText.setCursorVisible(true);
                }
            }
        });
        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("aa", "beforeText");
//                numberTextView.setText(s.length() + "/140");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("aa", "textChange");

//                numberTextView.setText(s.length() + "/140");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("aa", "afterText");
                if ((140 - s.length()) >= 0) {
                } else {
                    s.delete(140, s.length());
                }
                numberTextView.setText(s.length() + "/140");
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_publish;
    }

    private void initView() {
        imageButton = (ImageButton) findViewById(R.id.publish_camera);
        contentEditText = (EditText) findViewById(R.id.publish_content_edit);
        numberTextView = (TextView) findViewById(R.id.publish_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_camera:
                String[] choose = new String[]{"相机", "相册"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("");
                builder.setSingleChoiceItems(choose, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                date = System.currentTimeMillis();
                                mFile = Environment.getExternalStorageDirectory();
                                String Path = mFile.getPath() + "/demo";
                                mFile = new File(Path);
                                if (!mFile.exists()) {
                                    mFile.mkdir();
                                }
                                file = new File(mFile.getPath(), date + ".jpg");
                                Uri uri = Uri.fromFile(file);
                                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(i, 1);
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                intent.putExtra("crop", "true");
                                intent.putExtra("aspectX", 1);
                                intent.putExtra("aspectY", 1);
                                intent.putExtra("outputX", 300);
                                intent.putExtra("outputY", 300);
                                intent.putExtra("return-data", true);
                                startActivityForResult(intent, 11);
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.create();
                builder.show();


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (data == null && resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 1:
                Log.i("aa", resultCode + "");
                if (resultCode == RESULT_OK) {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        Log.i("TestFile",
                                "SD card is not avaiable/writeable right now.");
                        return;
                    }

                    Bitmap imageBitmap = BitmapFactory.decodeFile(String.valueOf(file));
                    ByteArrayOutputStream baos = null;
                    baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    try {
                        file = new File(mFile, date + "cpmpress.jpg");
                        imagePath = String.valueOf(file);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(baos.toByteArray());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = initImage(imageBitmap);
                    imageButton.setImageBitmap(bitmap);
                }
                break;
            case 11:

                if (resultCode == RESULT_OK) {
//                    Bitmap bitmap = initImage((Bitmap) data.getParcelableExtra("data"));
                    Uri uri=data.getData();
//                    imageButton.setImageBitmap(bitmap);
                    imageButton.setImageURI(uri);
                } else if (resultCode == RESULT_CANCELED) {

                }
                break;
            default:
                break;
        }

    }

    private Bitmap initImage(Bitmap imageBitmap) {
        int px = dip2px(this, 96);
        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();

        float scWidth = ((float) px) / width;
        float scHeight = ((float) px) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scWidth, scHeight);
        return Bitmap.createBitmap(imageBitmap, 0, 0, width, height, matrix, true);
    }

    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish:
                MissionData missionData = new MissionData();
                missionData.setContent(contentEditText.getText().toString());
                missionData.setStatus(0);
                missionData.setContentImage(imagePath);
                missionData.setPublisher(User.getInstance().getName());
                long tempDate = System.currentTimeMillis();
                missionData.setPublishTime(tempDate);
                Mission.getInstance().add(0, missionData);
                Log.i("mission", Mission.getInstance().get(0).getContentImage() + "");
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
