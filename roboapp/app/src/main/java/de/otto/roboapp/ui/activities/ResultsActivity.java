package de.otto.roboapp.ui.activities;

import android.os.Bundle;
import android.webkit.WebView;

import de.otto.roboapp.R;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;

/**
 * Created by luca on 24.04.15.
 */
public class ResultsActivity extends AbstractUpdatableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        WebView webView = (WebView) findViewById(R.id.background_webview);
        webView.loadUrl("file:///android_asset/giphy.gif");
    }

    @Override
    public void updateActivity() {

    }
}
