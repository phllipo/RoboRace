package de.otto.roboapp.ui.util;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by luca on 14.06.15.
 */
public class BackgroundTextureListener implements TextureView.SurfaceTextureListener {

    private Activity activity;
    private MediaPlayer mMediaPlayer = null;

    public BackgroundTextureListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Surface surface = new Surface(surfaceTexture);

        try {
            AssetFileDescriptor afd = activity.getAssets().openFd("video/bg.mp4");
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepareAsync();

            int videoWidth = mMediaPlayer.getVideoHeight();
            int videoHeight = mMediaPlayer.getVideoWidth();

            //Get the width of the screen
            int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();

            //Get the SurfaceView layout parameters

            //Set the width of the SurfaceView to the width of the screen
            width = screenWidth;

            //Set the height of the SurfaceView to match the aspect ratio of the video
            //be sure to cast these as floats otherwise the calculation will likely be 0
            height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);
            surfaceTexture.setDefaultBufferSize(width, height);


            // Play video when the media source is ready for playback.
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
