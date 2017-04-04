package com.example.jatingarg.notes;

import android.content.Context;

/**
 * Created by jatingarg on 04/04/17.
 */

public class Note {

    private String id;
    private String Content;
    private String imageName;


    public Note(String id, String content, String imageName) {
        this.id = id;
        Content = content;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getImageResourceID(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
