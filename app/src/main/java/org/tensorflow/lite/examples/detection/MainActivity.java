package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.tensorflow.lite.examples.detection.customview.OverlayView;
import org.tensorflow.lite.examples.detection.env.ImageUtils;
import org.tensorflow.lite.examples.detection.env.Logger;
import org.tensorflow.lite.examples.detection.env.Utils;
import org.tensorflow.lite.examples.detection.tflite.Classifier;
import org.tensorflow.lite.examples.detection.tflite.YoloV4Classifier;
import org.tensorflow.lite.examples.detection.tracking.MultiBoxTracker;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;

//    private GraphicalView mChartView;
//
//    private String[] mWeek = new String[]{
//            "Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent loading_intent = new Intent(this, LoadingActivity.class);
//        startActivity(loading_intent); // 로딩화면 관련

//        cameraButton = findViewById(R.id.routine_Button);
//        detectButton = findViewById(R.id.exercise_Button); // 일단 임시로 버튼 격리
        imageView = findViewById(R.id.imageView); //이미지뷰 관련인듯?

        Button exerciseButton = findViewById(R.id.exercise_Button); //운동화면 버튼 (아마 개별운동이 될 것)
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExerciseActivity.class)));

        Button routineButton = findViewById(R.id.routine_Button); //루틴 버튼 선언
        routineButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RoutineActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button); //설정창 버튼 선언?
        settingButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Preference.class)));

        Button exercisemoreButton=findViewById(R.id.exercise_more_button);
        exercisemoreButton.setOnClickListener(V -> startActivity(new Intent(MainActivity.this, more_exercise.class)));

        Button weekmoreButton=findViewById(R.id.week_more_button);
        weekmoreButton.setOnClickListener(V -> startActivity(new Intent(MainActivity.this, more_calendar.class)));

//        cameraButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DetectorActivity.class)));

//        detectButton.setOnClickListener(v -> {
//            Handler handler = new Handler();
//
//            new Thread(() -> {
//                final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        handleResult(cropBitmap, results);
//                    }
//                });
//            }).start();
//
//        });    여기도 원래 있던 코드, 아마 그 카메라 화면 넘어가는 것들로 추정

        this.sourceBitmap = Utils.getBitmapFromAsset(MainActivity.this, "test.png");

        this.cropBitmap = Utils.processBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE);

        this.imageView.setImageBitmap(cropBitmap);

        initBox();

//        drawChart();
    }

//    private void drawChart() {
//
//        int[] x = {0,1,2,3,4,5,6};
//
//        int[]income = {10,20,0,10,20,0,10};
//
//        int[]
//    }

    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 416;

    private static final boolean TF_OD_API_IS_QUANTIZED = false;

    private static final String TF_OD_API_MODEL_FILE = "yolov4-416-fp32.tflite";

    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/coco.txt";

    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = false;
    private Integer sensorOrientation = 90;

    private Classifier detector;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    private OverlayView trackingOverlay;

    protected int previewWidth = 0;
    protected int previewHeight = 0;

    private Bitmap sourceBitmap;
    private Bitmap cropBitmap;

    private Button cameraButton, detectButton;
    private ImageView imageView;

    private void initBox() {
        previewHeight = TF_OD_API_INPUT_SIZE;
        previewWidth = TF_OD_API_INPUT_SIZE;
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        tracker = new MultiBoxTracker(this);
        trackingOverlay = findViewById(R.id.tracking_overlay);
        trackingOverlay.addCallback(
                canvas -> tracker.draw(canvas));

        tracker.setFrameConfiguration(TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, sensorOrientation);

        try {
            detector =
                    YoloV4Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED);
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    private void handleResult(Bitmap bitmap, List<Classifier.Recognition> results) {
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);

        final List<Classifier.Recognition> mappedRecognitions =
                new LinkedList<Classifier.Recognition>();

        for (final Classifier.Recognition result : results) {
            final RectF location = result.getLocation();
            if (location != null && result.getConfidence() >= MINIMUM_CONFIDENCE_TF_OD_API) {
                canvas.drawRect(location, paint);
//                cropToFrameTransform.mapRect(location);
//
//                result.setLocation(location);
//                mappedRecognitions.add(result);
            }
        }
//        tracker.trackResults(mappedRecognitions, new Random().nextInt());
//        trackingOverlay.postInvalidate();
        imageView.setImageBitmap(bitmap);
    }
}
