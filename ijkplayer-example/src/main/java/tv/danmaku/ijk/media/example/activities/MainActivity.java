package tv.danmaku.ijk.media.example.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tv.danmaku.ijk.media.example.R;

public class MainActivity extends AppCompatActivity {

    private Button btnTest;
    private Button btnFileExplorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnFileExplorer = (Button) findViewById(R.id.btn_file_explorer);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestActivity.launch(MainActivity.this);
            }
        });
        btnFileExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileExplorerActivity.launch(MainActivity.this);
            }
        });
    }
}
