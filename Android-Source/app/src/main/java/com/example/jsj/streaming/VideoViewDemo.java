package com.example.jsj.streaming;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoViewDemo  extends Activity implements SurfaceHolder.Callback{
    private Camera cam;
    private MediaRecorder mediaRecorder;
    private Button start;
    private SurfaceView sv;
    private SurfaceHolder sh;
    private static final String BASE = "http://203.237.81.68:3000/upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streaming_start);

        start = (Button)findViewById(R.id.button);



        final boolean[] recording = {false};
        View.OnClickListener captrureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording[0]) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    cam.lock();
                    recording[0] = false;
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VideoViewDemo.this, "succeed", Toast.LENGTH_LONG).show();
                            try {
                                mediaRecorder = new MediaRecorder();
                                cam.unlock();
                                mediaRecorder.setCamera(cam);
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                                mediaRecorder.setAudioEncoder(3);
                                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                                mediaRecorder.setOrientationHint(90);
                                mediaRecorder.setOutputFile(BASE);
                                mediaRecorder.setPreviewDisplay(sh.getSurface());
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                                recording[0] = true;
                                Toast.makeText(VideoViewDemo.this, "start", Toast.LENGTH_LONG).show();
                            } catch (final Exception ex) {
                                ex.printStackTrace();
                                mediaRecorder.release();
                                return;

                                // Log.i("---","Exception in thread");
                            }
                        }
                    });
                }
            }
        };

        start.setOnClickListener(captrureListener);
        setting();


    }
    private void setting() {
        cam = Camera.open();
        cam.setDisplayOrientation(90);
        sv = (SurfaceView) findViewById(R.id.surfaceView);
        sh = sv.getHolder();
        sh.addCallback(this);
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (cam == null) {
                cam.setPreviewDisplay(holder);
                cam.startPreview();
            }
        } catch (IOException e) {
        }
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        refreshCamera(cam);
    }
    public void refreshCamera(Camera camera) {
        if (sh.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            cam.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera);
        try {
            cam.setPreviewDisplay(sh);
            cam.startPreview();
        } catch (Exception e) {
        }
    }

    public void setCamera(Camera camera) {
        //method to set a camera instance
        cam = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // mCamera.release();

    }



}
