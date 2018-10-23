package com.example.cas.gestureswipe;

import android.content.Intent;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cas.gestureswipe.SimpleGestureFilter.SimpleGestureListener;

public class MainActivity extends AppCompatActivity implements
        SimpleGestureListener {
    //constants
    private SimpleGestureFilter detector;
    VideoView videoView;
    MainActivity activity;
    MediaController mediaController;
    AudioManager audioManager;
    int currentPosition;
    int currentVolume;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Detect touched area
        detector = new SimpleGestureFilter(MainActivity.this, this);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        videoView = (VideoView) findViewById(R.id.video_view);
        /*//for audio
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");*/
        String path = "android.resource://" + getPackageName() + "/" + R.raw.glitched_smile;
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();
        videoView.start();

    }
    @Override
    protected void onResume() {
        super.onResume();
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSwipe(int direction) {
        // TODO Auto-generated method stub
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_LEFT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() - 10000;
                videoView.seekTo(currentPosition);
                str = "Swipe Left";
                break;

            case SimpleGestureFilter.SWIPE_RIGHT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() + 10000;
                videoView.seekTo(currentPosition);
                str = "Swipe Right";
                break;

            case SimpleGestureFilter.SWIPE_DOWN:

                currentVolume = audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        currentVolume - 1, 0);
                str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP:

                currentVolume = audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        currentVolume + 1, 0);
                str = "Swipe Up";
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    //Toast shown when double tapped on screen
    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "You have Double Tapped.", Toast.LENGTH_SHORT)
                .show();
    }

}
