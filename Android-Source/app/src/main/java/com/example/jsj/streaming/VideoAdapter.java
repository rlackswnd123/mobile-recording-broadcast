package com.example.jsj.streaming;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by jsj on 2017-11-06.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private ArrayList<Item>dataList ;
    private Context context;
    Handler handler = new Handler();  // 외부쓰레드 에서 메인 UI화면을 그릴때 사용


     //뷰홀더는 하나의 뷰를 보존하는 역할을 한다
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView)v.findViewById(R.id.imageView);
            mTextView = (TextView)v.findViewById(R.id.textView);

        }
    }

    public VideoAdapter(Context activity, ArrayList<Item> arrayList) {
        this.context =activity;
        this.dataList = arrayList;
        Log.d("Item size",Integer.toString(this.dataList.size()));
    }
//뷰올더 생성
    //row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //    ImageView v;
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
     //   v = itemview.findViewById(R.id.imageView);
        ViewHolder holder = new ViewHolder(itemview);

        return holder;
    }
//만들어진 뷰홀더에 데이터 삽입 리스트뷰의 갯뷰와 동일
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
         final ViewHolder holder1 = (ViewHolder) holder;

         /*
                URL url = null;
                try {
                    Log.d("bit", dataList.get(position).getPhotoId());
                    Toast.makeText(context, dataList.get(position).getPhotoId(), Toast.LENGTH_SHORT).show();

                    url = new URL(dataList.get(position).getPhotoId());
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    //InputStream bis = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(bis);
                    int height = bm.getHeight();
                    int width = bm.getWidth();

                    Bitmap resized = null;
                    while(height>50){
                        resized = Bitmap.createScaledBitmap(bm,(width*50)/height,50,true);
                        height = resized.getHeight();
                        width = resized.getWidth();
                    }
                   // bis.close();
                    final Bitmap finalResized = resized;

                    holder1.mImageView.setImageBitmap(finalResized);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


*/
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                   // final ImageView iv = holder1.mImageView;
                    URL url = new URL(dataList.get(position).getPhotoId());
                    InputStream is = url.openStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize=1;
                    final Bitmap bm = BitmapFactory.decodeStream(is,null,options);

                    Log.d("map", String.valueOf(is));
                    // final Bitmap bm = BitmapFactory.decodeStream(is);

                   /* int height = bm.getHeight();
                    int width = bm.getWidth();

                    Bitmap resized = null;
                    while(height>50) {
                        resized = Bitmap.createScaledBitmap(bm, (width * 50) / height, 50, true);
                        height = resized.getHeight();
                        width = resized.getWidth();
                    }

                    final Bitmap finalResized = resized;*/
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            holder1.mImageView.setImageBitmap(bm);
                        }
                    });
                    holder1.mImageView.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){

                }

            }
        });

        t.start();


     //   Toast.makeText(context, dataList.get(position).getPhotoId(), Toast.LENGTH_SHORT).show();


/*
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(dataList.get(position).getPhotoId(), MediaStore.Video.Thumbnails.MINI_KIND);
        holder1.mImageView.setImageBitmap(bitmap);*/
        // holder1.mImageView.setImageResource(dataList.get(position).getPhotoId());
      //  holder1.mImageView.setImageResource(dataList.get(position).getPhotoId());
         holder1.mTextView.setText(dataList.get(position).getCaption());
        holder1.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent intent =new Intent(context,Web_view2.class);
                intent.putExtra("title",dataList.get(pos).getCaption());
                Toast.makeText(context, dataList.get(pos).getCaption(), Toast.LENGTH_LONG).show();

                context.startActivity(intent);
        }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
