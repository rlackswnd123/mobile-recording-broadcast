package com.example.jsj.streaming;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jsj on 2017-11-06.
 */

public class Item {
   // private  int photoId;
    private String photoId;
    private String caption;

    public Item(String id, String caption) {
        photoId = id;
        this.caption = caption;
    }
    //생성자

    public void setPhotoId(String id){
        photoId = id;
    }
    public void setCaption (String caption) {
        this.caption = caption;
    }
    //셋터

    public String getPhotoId() {
        return  photoId;
    }
    public String getCaption(){
        return caption;
    }
    //겟터
/*
    @Override
    public int describeContents() {
        return 0;
    }

    //데이터 전달. 객체를 전달하기 위해 parcel생성
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photoId);
        parcel.writeString(caption);
    }

    public Item(Parcel parcel){
        photoId = parcel.readInt();
        caption = parcel.readString();
    }
    //넘겨받은 activity에서 parcel에서 객체생성할때 호출. 역으로 꺼내서 읽음

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel); //parcel을 이용해서 객체 생성
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[0];
        }
    };*/

}
