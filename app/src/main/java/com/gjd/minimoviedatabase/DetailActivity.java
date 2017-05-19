package com.gjd.minimoviedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Holds Fragment containing additional movie details
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment()).commit();
        }
    }
}
