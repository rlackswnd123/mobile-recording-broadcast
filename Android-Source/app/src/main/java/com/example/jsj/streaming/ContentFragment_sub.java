package com.example.jsj.streaming;

/**
 * Created by jsj on 2017-10-30.
 */
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContentFragment_sub extends Fragment

{
    ImageView imView;

    Bitmap bmImg;
    // back task;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Item> arrayList = new ArrayList<Item>();
    VideoAdapter adapter;





    private static final String BASE = "http://203.237.81.68:3000/";
    Retrofit retrofit;
    Retrofit retrofit2;
    ApiService apiService;
    ApiService apiService2;

    WebView myWebView;
    final static String imageBASE = "http://203.237.81.68:3000/image/";
    String myUrl;


    public ContentFragment_sub() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


           View view = inflater.inflate(R.layout.fragment_camera,container,false);







        //   View view = inflater.inflate(R.layout.fragment_club,container,false);



        //  container = (ViewGroup) inflater.inflate(R.layout.fragment_club,container,false);
        //imView =(ImageView)view.findViewById(R.id.imageView2);

        recyclerView =(RecyclerView)view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

/*
        retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService2 = retrofit2.create(ApiService.class);
        Call<ResponseBody> comment2 = apiService2.getvideo();
        comment2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                    try {
                        String s1 = response.body().string();  //문자열

                           Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
                        Log.d("text2",s1);






                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

*/


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        //Toast.makeText(getApplicationContext(),id.getText().toString(),Toast.LENGTH_LONG).show();

        Call<ResponseBody> comment = apiService.getuni();
        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                    try {
                        String s = response.body().string();  //문자열

                        //    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();





                        JSONArray s2 =null;
                        s2 = new JSONArray(s);
                        for (int i = 0; i < s2.length(); i++) {
                            String str = null;
                            String str2 =null;
                            JSONObject jsonObject =null;

                            jsonObject = s2.getJSONObject(i);
                            str = jsonObject.getString("title");
                            str2 = jsonObject.getString("image");
                            // arrayList.add(new Item(R.drawable.black , str));  //배열 값에 추가

                            arrayList.add(new Item(imageBASE+str , str));  //배열 값에 추가
                            Log.d("Array size",Integer.toString(arrayList.size()));
                            //  Toast.makeText(getActivity(), str2, Toast.LENGTH_LONG).show();
                        }




                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new VideoAdapter(getActivity(),arrayList);
                    recyclerView.setAdapter(adapter);


                }
                arrayList = new ArrayList<Item>();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



        //     RecyclerView recyclerView = (RecyclerView) container.findViewById(R.id.recycle);

/*
        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),Web_view2.class);
                intent.putExtra("title","동아리");
                startActivity(intent);

            }
        });
*/



        // 리스트뷰 참조 및 Adapter달기
        //listview = (ListView) view.findViewById(R.id.listview1);
        //listview.setAdapter(adapter);



        return view;
        //  return inflater.inflate(R.layout.fragment_club, container, false);
    }


}

