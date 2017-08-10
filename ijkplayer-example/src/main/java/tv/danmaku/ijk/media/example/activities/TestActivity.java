package tv.danmaku.ijk.media.example.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import tv.danmaku.ijk.media.example.R;
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.ScreenResolution;
import tv.danmaku.ijk.media.example.widget.media.TestVideoView;

public class TestActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private TestVideoView ijkVideoView;
    private String testUrl = "http://domhttp.kksmg.com/2017/05/09/h264_450k_mp4_7f122c71a65219094da3907cbb01e14f_ncm.mp4";
    private AndroidMediaController mediaController;
    private RelativeLayout rlVideoView;
    private int portraitWidth;
    private boolean isLandscape;

    public static void launch(Context context) {
        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        rlVideoView = (RelativeLayout) findViewById(R.id.rl_video_view);
        ijkVideoView = (TestVideoView) findViewById(R.id.ijk_video_view);
        setPortrait();
        ijkVideoView.setVideoPath(testUrl);
        mediaController = new AndroidMediaController(this);
        ijkVideoView.setMediaController(mediaController);
        ijkVideoView.start();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "newConfig.orientation=" + newConfig.orientation);
        if (Configuration.ORIENTATION_LANDSCAPE == newConfig.orientation) {
            isLandscape = true;
            //水平方向
            setLandscape();
        } else {
            isLandscape = false;
            setPortrait();
        }
    }

    private void setPortrait() {
        rlVideoView.post(new Runnable() {
            @Override
            public void run() {
                if (portraitWidth == 0) {
                    portraitWidth = ScreenResolution.getResolution(TestActivity.this.getApplicationContext()).first;
                }
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlVideoView.getLayoutParams();
                params.height = portraitWidth / 16 * 9;
                rlVideoView.setLayoutParams(params);
            }
        });
    }

    private void setLandscape() {
        rlVideoView.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlVideoView.getLayoutParams();
                params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                rlVideoView.setLayoutParams(params);
            }
        });
    }

    private void setFullSensor() {
        rlVideoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        if (isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setFullSensor();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.stopPlayback();
        ijkVideoView.release(true);
    }
}
