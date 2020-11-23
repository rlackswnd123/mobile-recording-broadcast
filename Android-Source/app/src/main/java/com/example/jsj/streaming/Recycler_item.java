package com.example.jsj.streaming;

/**
 * Created by jsj on 2017-11-06.
 */

public class Recycler_item {
    int image;
    String title;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }

    Recycler_item(int image, String title){
        this.image=image;
        this.title=title;
    }

}
