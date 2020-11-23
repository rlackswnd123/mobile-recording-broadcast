package com.example.jsj.streaming;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jsj on 2017-10-18.
 */

public class Sign_up extends AppCompatActivity {
    private static final String BASE = "http://203.237.81.68:3000/";
    Retrofit retrofit;
    ApiService apiService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

    }

    public void onButton(View v) {
        final EditText id = (EditText) findViewById(R.id.id);
        final EditText password =(EditText)findViewById(R.id.pw);
        switch (v.getId()) {
            case R.id.commit:
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .build();
                apiService = retrofit.create(ApiService.class);


                Call<ResponseBody> comment = apiService.getPostCommentstr(id.getText().toString(),password.getText().toString());
                comment.enqueue(new Callback<ResponseBody>() {
                    BufferedReader reader =null;
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        /*
                             ResponseBody repo = response.body();
                            int a = response.body();
                          //  a = (Log.v("test",response.body().string()));
                            Toast.makeText(Sign_up.this,Integer.toString(a),Toast.LENGTH_LONG).show();*/

                        if(response.isSuccessful() ){
                            try {
                                String str = response.body().string();
                               // Toast.makeText(Sign_up.this, str, Toast.LENGTH_SHORT).show();
                               // Toast.makeText(Sign_up.this, password.getText().toString(), Toast.LENGTH_SHORT).show();
                                if(str.equals("OK")){
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                }else if(id.getText().toString().equals("") ){
                                    Toast.makeText(Sign_up.this,"아이디를 입력해주세요",Toast.LENGTH_LONG).show();



                                }else if(str.equals("null")){
                                Toast.makeText(Sign_up.this,"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();



                            }else if(str.equals("NO")) {
                                    Toast.makeText(Sign_up.this, "같은 아이디가 있습니다", Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }



                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                //서버에 회원정보 전송
                /*
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService;
                apiService = retrofit.create(ApiService.class);




                List<Sing> person = new ArrayList<Sing>();

                person.add(new Sing(id.getText().toString(),password.getText().toString()));
                Gson gson = new Gson();
                String userLisResult = gson.toJson(person,ArrayList.class);


                Call<Sing> con = apiService.getComment(0);
*/

                break;
            case R.id.exit:
               // Toast.makeText(Sign_up.this, "djilai", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

}


/*
        Button.OnClickListener onClickListener = new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.commit:

                        break;
                    case R.id.exit:
                        Toast.makeText(Sign_up.this, "djilai", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }
        };

*/





