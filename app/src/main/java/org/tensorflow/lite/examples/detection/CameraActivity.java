/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.os.Trace;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.nio.ByteBuffer;
import java.util.Locale;

import org.tensorflow.lite.examples.detection.env.ImageUtils;
import org.tensorflow.lite.examples.detection.env.Logger;
import android.annotation.SuppressLint;
import android.os.Message;

public abstract class CameraActivity extends AppCompatActivity
        implements OnImageAvailableListener,
        Camera.PreviewCallback,
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {
  private static final Logger LOGGER = new Logger();

  private static final int PERMISSIONS_REQUEST = 1;

  private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
  protected int previewWidth = 0;
  protected int previewHeight = 0;
  private static String title;
  private static float confidence;
  private boolean debug = false;
  private Handler handler;
  private HandlerThread handlerThread;
  private boolean useCamera2API;
  private boolean isProcessingFrame = false;
  private byte[][] yuvBytes = new byte[3][];
  private int[] rgbBytes = null;
  private int yRowStride;
  private Runnable postInferenceCallback;
  private Runnable imageConverter;

  private LinearLayout bottomSheetLayout;
  private LinearLayout gestureLayout;
  private BottomSheetBehavior<LinearLayout> sheetBehavior;

  protected TextView frameValueTextView, cropValueTextView, inferenceTimeTextView;
  protected ImageView bottomSheetArrowImageView;
  private ImageView plusImageView, minusImageView;
  private SwitchCompat apiSwitchCompat;
  private TextView threadsTextView;

  private TextView tv_exercise_name;
  private TextView tv_time_num;
  private TextView tv_set_num;
  private TextView tv_count_num;
  private TextView tv_countdown;

  private TextView tv_situp_num;
  private TextView tv_pushup_num;
  private TextView tv_squat_num;
  private TextView tv_pullup_num;
  private int int_count_num;
  private int int_set_num;

  int max_count_num;
  String exercise_name = "00";
  String time_num = "00";
  String set_num = "00";
  String count_num = "00";

  private Chronometer chronometer;
  private boolean running;
  private long pauseOffset;
  private Chronometer calculator;

  private TextToSpeech tts;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    LOGGER.d("onCreate " + this);
    super.onCreate(null);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    setContentView(R.layout.tfe_od_activity_camera);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    if (hasPermission()) {
      setFragment();
    } else {
      requestPermission();
    }

    threadsTextView = findViewById(R.id.threads);
    plusImageView = findViewById(R.id.plus);
    minusImageView = findViewById(R.id.minus);
    apiSwitchCompat = findViewById(R.id.api_info_switch);
    //[shkim]
//    bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
//    gestureLayout = findViewById(R.id.gesture_layout);
//    sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
//    bottomSheetArrowImageView = findViewById(R.id.bottom_sheet_arrow);
//
//    ViewTreeObserver vto = gestureLayout.getViewTreeObserver();
//    vto.addOnGlobalLayoutListener(
//        new ViewTreeObserver.OnGlobalLayoutListener() {
//          @Override
//          public void onGlobalLayout() {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//              gestureLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            } else {
//              gestureLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//            //                int width = bottomSheetLayout.getMeasuredWidth();
//            int height = gestureLayout.getMeasuredHeight();
//
//            sheetBehavior.setPeekHeight(height);
//          }
//        });
//    sheetBehavior.setHideable(false);
//
//    sheetBehavior.setBottomSheetCallback(
//        new BottomSheetBehavior.BottomSheetCallback() {
//          @Override
//          public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            switch (newState) {
//              case BottomSheetBehavior.STATE_HIDDEN:
//                break;
//              case BottomSheetBehavior.STATE_EXPANDED:
//                {
//                  bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_down);
//                }
//                break;
//              case BottomSheetBehavior.STATE_COLLAPSED:
//                {
//                  bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
//                }
//                break;
//              case BottomSheetBehavior.STATE_DRAGGING:
//                break;
//              case BottomSheetBehavior.STATE_SETTLING:
//                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
//                break;
//            }
//          }
//
//          @Override
//          public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
//        });
//  // [shkim]
//    frameValueTextView = findViewById(R.id.frame_info);
//    cropValueTextView = findViewById(R.id.crop_info);
//    inferenceTimeTextView = findViewById(R.id.inference_info);
//
//    apiSwitchCompat.setOnCheckedChangeListener(this);
//
//    plusImageView.setOnClickListener(this);
//    minusImageView.setOnClickListener(this);

    tv_exercise_name = (TextView) findViewById(R.id.exercise_name);
    //tv_time_num = (TextView) findViewById(R.id.time);
    tv_set_num = (TextView) findViewById(R.id.set);
    tv_count_num = (TextView) findViewById(R.id.count);
    tv_countdown = (TextView) findViewById(R.id.countdown);

    Intent name_intent = getIntent();
    String exercise_name = name_intent.getStringExtra("exercise_name");
    //String count_num = name_intent.getStringExtra("situp_count");
    String count_num = name_intent.getStringExtra("count_num");
    String set_num = name_intent.getStringExtra("set_num");
    //String time_num = name_intent.getStringExtra("time_num");

    tv_exercise_name.setText(exercise_name);
    // tv_time_num.setText(time_num);
    tv_set_num.setText(set_num);
    tv_count_num.setText(count_num);
    tv_countdown.setText("3");

    chronometer = findViewById(R.id.chronometer);
    chronometer.setFormat("%s");

    Button startBtn = findViewById(R.id.startBtn);
    Button pauseBtn = findViewById(R.id.pauseBtn);

    max_count_num = Integer.parseInt(count_num);

    int_count_num = Integer.parseInt(count_num);
    int_set_num = Integer.parseInt(set_num);


    tts = new TextToSpeech(this, new TextToSpeech.OnInitListener()
    {
      @Override
      public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
          int result = tts.setLanguage(Locale.ENGLISH);

          if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
          {
            Toast.makeText(CameraActivity.this, "지원하지 않는 언어입니다", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });

    startBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        tts.speak("3",TextToSpeech.QUEUE_FLUSH,null);
        tv_countdown.setVisibility(View.VISIBLE);
        tv_countdown.setText("3");

        new Handler().postDelayed(new Runnable(){
          @Override
          public void run()
          {
            tts.speak("2",TextToSpeech.QUEUE_FLUSH,null);
            tv_countdown.setText("2");
            new Handler().postDelayed(new Runnable(){
              @Override
              public void run()
              {
                tts.speak("1",TextToSpeech.QUEUE_FLUSH,null);
                tv_countdown.setText("1");

                if(!running){
                  chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                  chronometer.start();
                  running = true;

                  new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                      tv_countdown.setVisibility(View.INVISIBLE);
                      final boolean[] flag = {false};
                      new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                          if(true){
                            handler.postDelayed(this,1000);
                            if( confidence > 0.7 && flag[0] == false) //검출 조건 부분
                              // 여기에 검출 원하는 운동 비교 검출 값 = title / 선택한 운동 exercise_name
                            {
                              flag[0] = true;
                            }
                            else if( confidence < 0.8 && flag[0] == true)   // 위와 동일
                            {
                              flag[0] = false;
                              if(int_count_num > 0) {
                                int_count_num--;
                              }
                              else if(int_count_num == 0) {
                                int_set_num--;
                                int_count_num = max_count_num;
                                tts.speak("휴식시간입니다.",TextToSpeech.QUEUE_FLUSH,null);
                                chronometer.stop();
                                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();

                                int i = 10;
                                while(i>0)
                                {
                                  try { Thread.sleep(1000);}
                                  catch (InterruptedException e) { e.printStackTrace(); }
                                  if(i==3){tts.speak("3",TextToSpeech.QUEUE_FLUSH,null);}
                                  else if(i==2){tts.speak("2",TextToSpeech.QUEUE_FLUSH,null);}
                                  else if(i==1){tts.speak("1",TextToSpeech.QUEUE_FLUSH,null);}
                                  i--;
                                }

                                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                                chronometer.start();
                                tts.speak("시작.",TextToSpeech.QUEUE_FLUSH,null);
                                // 시간 정지
                                // 30초 휴식시간 추가
                              }
                              if(int_set_num == 0 && int_count_num ==0){
                                int time = (int) (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;;
                                String string_time = String.valueOf(time);

                                Intent exercise_intent = new Intent(CameraActivity.this, ReportActivity.class);
                                exercise_intent.putExtra("exercise_name", exercise_name);
                                exercise_intent.putExtra("count_num", count_num);
                                exercise_intent.putExtra("set_num", set_num);
                                exercise_intent.putExtra("time_num", string_time);
                                startActivity(exercise_intent);
                              }
                              tv_count_num.setText(int_count_num + "");
                              tv_set_num.setText(int_set_num + "");

                            }
                          }
                        }
                      }, 1000);
                    }
                  },1000);
                }
              }
            },1000);
          }
        },1000);
      }
    });


    pauseBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(running){
          chronometer.stop();
          pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
          running = false;

          int time = (int) (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;;
          String string_time = String.valueOf(time);

          //tv_exercise_name.setText(string_time);

        }
      }
    });

  }

  protected int[] getRgbBytes() {
    imageConverter.run();
    return rgbBytes;
  }

  protected int getLuminanceStride() {
    return yRowStride;
  }

  protected byte[] getLuminance() {
    return yuvBytes[0];
  }

  /** Callback for android.hardware.Camera API */
  @Override
  public void onPreviewFrame(final byte[] bytes, final Camera camera) {
    if (isProcessingFrame) {
      LOGGER.w("Dropping frame!");
      return;
    }

    try {
      // Initialize the storage bitmaps once when the resolution is known.
      if (rgbBytes == null) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        previewHeight = previewSize.height;
        previewWidth = previewSize.width;
        rgbBytes = new int[previewWidth * previewHeight];
        onPreviewSizeChosen(new Size(previewSize.width, previewSize.height), 90);
      }
    } catch (final Exception e) {
      LOGGER.e(e, "Exception!");
      return;
    }

    isProcessingFrame = true;
    yuvBytes[0] = bytes;
    yRowStride = previewWidth;

    imageConverter =
            new Runnable() {
              @Override
              public void run() {
                ImageUtils.convertYUV420SPToARGB8888(bytes, previewWidth, previewHeight, rgbBytes);
              }
            };

    postInferenceCallback =
            new Runnable() {
              @Override
              public void run() {
                camera.addCallbackBuffer(bytes);
                isProcessingFrame = false;
              }
            };
    processImage();
  }

  /** Callback for Camera2 API */
  @Override
  public void onImageAvailable(final ImageReader reader) {
    // We need wait until we have some size from onPreviewSizeChosen
    if (previewWidth == 0 || previewHeight == 0) {
      return;
    }
    if (rgbBytes == null) {
      rgbBytes = new int[previewWidth * previewHeight];
    }
    try {
      final Image image = reader.acquireLatestImage();

      if (image == null) {
        return;
      }

      if (isProcessingFrame) {
        image.close();
        return;
      }
      isProcessingFrame = true;
      Trace.beginSection("imageAvailable");
      final Plane[] planes = image.getPlanes();
      fillBytes(planes, yuvBytes);
      yRowStride = planes[0].getRowStride();
      final int uvRowStride = planes[1].getRowStride();
      final int uvPixelStride = planes[1].getPixelStride();

      imageConverter =
              new Runnable() {
                @Override
                public void run() {
                  ImageUtils.convertYUV420ToARGB8888(
                          yuvBytes[0],
                          yuvBytes[1],
                          yuvBytes[2],
                          previewWidth,
                          previewHeight,
                          yRowStride,
                          uvRowStride,
                          uvPixelStride,
                          rgbBytes);
                }
              };

      postInferenceCallback =
              new Runnable() {
                @Override
                public void run() {
                  image.close();
                  isProcessingFrame = false;
                }
              };

      processImage();
    } catch (final Exception e) {
      LOGGER.e(e, "Exception!");
      Trace.endSection();
      return;
    }
    Trace.endSection();
  }

  @Override
  public synchronized void onStart() {
    LOGGER.d("onStart " + this);
    super.onStart();
  }

  @Override
  public synchronized void onResume() {
    LOGGER.d("onResume " + this);
    super.onResume();

    handlerThread = new HandlerThread("inference");
    handlerThread.start();
    handler = new Handler(handlerThread.getLooper());
  }

  @Override
  public synchronized void onPause() {
    LOGGER.d("onPause " + this);

    handlerThread.quitSafely();
    try {
      handlerThread.join();
      handlerThread = null;
      handler = null;
    } catch (final InterruptedException e) {
      LOGGER.e(e, "Exception!");
    }

    super.onPause();
  }

  @Override
  public synchronized void onStop() {
    LOGGER.d("onStop " + this);
    super.onStop();
  }

  @Override
  public synchronized void onDestroy() {
    LOGGER.d("onDestroy " + this);
    super.onDestroy();
  }

  protected synchronized void runInBackground(final Runnable r) {
    if (handler != null) {
      handler.post(r);
    }
  }

  @Override
  public void onRequestPermissionsResult(
          final int requestCode, final String[] permissions, final int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PERMISSIONS_REQUEST) {
      if (allPermissionsGranted(grantResults)) {
        setFragment();
      } else {
        requestPermission();
      }
    }
  }

  private static boolean allPermissionsGranted(final int[] grantResults) {
    for (int result : grantResults) {
      if (result != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  private boolean hasPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED;
    } else {
      return true;
    }
  }

  private void requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA)) {
        Toast.makeText(
                CameraActivity.this,
                "Camera permission is required for this demo",
                Toast.LENGTH_LONG)
                .show();
      }
      requestPermissions(new String[] {PERMISSION_CAMERA}, PERMISSIONS_REQUEST);
    }
  }

  // Returns true if the device supports the required hardware level, or better.
  private boolean isHardwareLevelSupported(
          CameraCharacteristics characteristics, int requiredLevel) {
    int deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    if (deviceLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
      return requiredLevel == deviceLevel;
    }
    // deviceLevel is not LEGACY, can use numerical sort
    return requiredLevel <= deviceLevel;
  }

  private String chooseCamera() {
    final CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    try {
      for (final String cameraId : manager.getCameraIdList()) {
        final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

        // We don't use a front facing camera in this sample.
        final Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
          continue;
        }

        final StreamConfigurationMap map =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

        if (map == null) {
          continue;
        }

        // Fallback to camera1 API for internal cameras that don't have full support.
        // This should help with legacy situations where using the camera2 API causes
        // distorted or otherwise broken previews.
        useCamera2API =
                (facing == CameraCharacteristics.LENS_FACING_EXTERNAL)
                        || isHardwareLevelSupported(
                        characteristics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL);
        LOGGER.i("Camera API lv2?: %s", useCamera2API);
        return cameraId;
      }
    } catch (CameraAccessException e) {
      LOGGER.e(e, "Not allowed to access camera");
    }

    return null;
  }

  protected void setFragment() {
    String cameraId = chooseCamera();

    Fragment fragment;
    if (useCamera2API) {
      CameraConnectionFragment camera2Fragment =
              CameraConnectionFragment.newInstance(
                      new CameraConnectionFragment.ConnectionCallback() {
                        @Override
                        public void onPreviewSizeChosen(final Size size, final int rotation) {
                          previewHeight = size.getHeight();
                          previewWidth = size.getWidth();
                          CameraActivity.this.onPreviewSizeChosen(size, rotation);
                        }
                      },
                      this,
                      getLayoutId(),
                      getDesiredPreviewFrameSize());

      camera2Fragment.setCamera(cameraId);
      fragment = camera2Fragment;
    } else {
      fragment =
              new LegacyCameraConnectionFragment(this, getLayoutId(), getDesiredPreviewFrameSize());
    }

    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
  }

  protected void fillBytes(final Plane[] planes, final byte[][] yuvBytes) {
    // Because of the variable row stride it's not possible to know in
    // advance the actual necessary dimensions of the yuv planes.
    for (int i = 0; i < planes.length; ++i) {
      final ByteBuffer buffer = planes[i].getBuffer();
      if (yuvBytes[i] == null) {
        LOGGER.d("Initializing buffer %d at size %d", i, buffer.capacity());
        yuvBytes[i] = new byte[buffer.capacity()];
      }
      buffer.get(yuvBytes[i]);
    }
  }

  public boolean isDebug() {
    return debug;
  }

  protected void readyForNextImage() {
    if (postInferenceCallback != null) {
      postInferenceCallback.run();
    }
  }

  protected int getScreenOrientation() {
    switch (getWindowManager().getDefaultDisplay().getRotation()) {
      case Surface.ROTATION_270:
        return 270;
      case Surface.ROTATION_180:
        return 180;
      case Surface.ROTATION_90:
        return 90;
      default:
        return 0;
    }
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    setUseNNAPI(isChecked);
    if (isChecked) apiSwitchCompat.setText("NNAPI");
    else apiSwitchCompat.setText("TFLITE");
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.plus) {
      String threads = threadsTextView.getText().toString().trim();
      int numThreads = Integer.parseInt(threads);
      if (numThreads >= 9) return;
      numThreads++;
      threadsTextView.setText(String.valueOf(numThreads));
      setNumThreads(numThreads);
    } else if (v.getId() == R.id.minus) {
      String threads = threadsTextView.getText().toString().trim();
      int numThreads = Integer.parseInt(threads);
      if (numThreads == 1) {
        return;
      }
      numThreads--;
      threadsTextView.setText(String.valueOf(numThreads));
      setNumThreads(numThreads);
    }
  }

//  protected void showFrameInfo(String frameInfo) {
//    frameValueTextView.setText(frameInfo);
//  }

  public static void setYoloConfidence(String dectectTitle, float dectectConfidence){
    title = dectectTitle;
    confidence = dectectConfidence;
  }

  protected void showCropInfo(String cropInfo) {
    cropValueTextView.setText(cropInfo);
  }

  protected void showInference(String inferenceTime) {
    inferenceTimeTextView.setText(inferenceTime);
  }

  protected abstract void processImage();

  protected abstract void onPreviewSizeChosen(final Size size, final int rotation);

  protected abstract int getLayoutId();

  protected abstract Size getDesiredPreviewFrameSize();

  protected abstract void setNumThreads(int numThreads);

  protected abstract void setUseNNAPI(boolean isChecked);
}
