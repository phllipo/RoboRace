package de.otto.roboapp.ui.components;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeedLights extends LinearLayout {
    private final int imageLightOff_src_id;
    private final int imageLightGreen_src_id;
    private final int imageLightOrange_src_id;
    private final int imageLightRed_src_id;
    private final int imageWidth;
    private int speed;
    private List<ImageView> imageViews = new ArrayList<>();
    private final int orangeCount;
    private final int imageCount;
    private final int redCount;
    private final int greenCount;

    public SpeedLights(Context context, AttributeSet attr) {
        super(context, attr);
        setOrientation(VERTICAL);
        imageCount = attr.getAttributeIntValue(null, "imageCount", 10);
        orangeCount = attr.getAttributeIntValue(null, "imageLightOrange_count", 2);
        redCount = attr.getAttributeIntValue(null, "imageLightRed_count", 2);
        greenCount = imageCount - orangeCount - redCount;
        speed = attr.getAttributeIntValue(null, "speed", 2);
        imageLightOff_src_id = attr.getAttributeResourceValue(null, "imageLightOff_src", 0);
        imageLightGreen_src_id = attr.getAttributeResourceValue(null, "imageLightGreen_src", 0);
        imageLightOrange_src_id = attr.getAttributeResourceValue(null, "imageLightOrange_src", 0);
        imageLightRed_src_id = attr.getAttributeResourceValue(null, "imageLightRed_src", 0);
        imageWidth = attr.getAttributeIntValue(null, "imageWidth", 200);

        for(int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LayoutParams params = new LayoutParams(imageWidth, 0);
            params.weight = 1;
            imageView.setLayoutParams(params);

            imageView.setImageResource(imageLightOff_src_id);
            imageViews.add(imageView);
            super.addView(imageView);
        }
        Collections.reverse(imageViews);
        setSpeed(speed);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        float orangePercentage = (float)greenCount / imageCount;
        float redPercentage = (float)(greenCount + orangeCount) / imageCount;

        for(int i = 0; i < imageCount; i++) {
            float curPercentage = (float)i / imageCount;
            if(curPercentage * 100 > speed) {
                imageViews.get(i).setImageResource(imageLightOff_src_id);
                continue;
            }
            if (curPercentage < orangePercentage) {
                imageViews.get(i).setImageResource(imageLightGreen_src_id);
                continue;
            }
            if (curPercentage < redPercentage) {
                imageViews.get(i).setImageResource(imageLightOrange_src_id);
                continue;
            }
            imageViews.get(i).setImageResource(imageLightRed_src_id);
        }
    }


}
