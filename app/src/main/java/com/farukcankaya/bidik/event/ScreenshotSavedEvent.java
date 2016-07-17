package com.farukcankaya.bidik.event;

/**
 * Created by Faruk Cankaya on 7/17/16.
 */
public class ScreenshotSavedEvent {
    private String imagePath;

    public ScreenshotSavedEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
