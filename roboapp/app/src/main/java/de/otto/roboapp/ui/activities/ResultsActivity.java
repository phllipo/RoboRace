package de.otto.roboapp.ui.activities;

import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import de.otto.roboapp.R;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.ui.util.BackgroundTextureListener;
import de.otto.roboapp.ui.util.result.PlacementListAdapter;

/**
 * Created by luca on 24.04.15.
 */
public class ResultsActivity extends AbstractUpdatableActivity  {

    private PlacementListAdapter placementListAdapter;
    private MediaPlayer mp = null;
    TextureView mTextureView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mTextureView = (TextureView) findViewById(R.id.results_background);
        mTextureView.setSurfaceTextureListener(new BackgroundTextureListener(this));

        final ListView resultsListView = (ListView) findViewById(R.id.placement_list);
        TextView t_title = (TextView) findViewById(R.id.title_results_activity);
        TextView t_resultPlacementTitle = (TextView) findViewById(R.id.results_placement_title);
        TextView t_resultNameTitle = (TextView) findViewById(R.id.results_names_title);
        TextView t_resultTimeTitle = (TextView) findViewById(R.id.results_times_title);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Munro.ttf");
        t_title.setTypeface(tf);
        t_resultNameTitle.setTypeface(tf);
        t_resultPlacementTitle.setTypeface(tf);
        t_resultTimeTitle.setTypeface(tf);

        placementListAdapter = new PlacementListAdapter(this);

        resultsListView.setAdapter(placementListAdapter);

        final Button button = (Button) findViewById(R.id.backToLobbyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(RoboRegistrationActivity.class);
            }
        });

    }

    @Override
    public void updateActivity() {

    }
}
