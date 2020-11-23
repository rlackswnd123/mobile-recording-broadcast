package com.example.jsj.streaming;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jsj on 2017-10-17.
 */

public class Login extends AppCompatActivity {
    private static final String BASE = "http://203.237.81.68:3000/";
    Retrofit retrofit;
    ApiService apiService;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        // intent 값을 전달 받을때
        /*
            Intent intent = getIntent();
            String name = intent.getExtras().getString("name");
            int age = intent.getExtras().getInt("age");
         */

        Button button = (Button)findViewById(R.id.sign_up);   //회원 가입 버튼을 눌렀을대
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Sign_up.class);

                startActivity(intent);
            }
        });

        Button button1 =(Button)findViewById(R.id.login2);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final EditText id = (EditText) findViewById(R.id.id);
                final EditText password =(EditText)findViewById(R.id.password);
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService = retrofit.create(ApiService.class);
                //Toast.makeText(getApplicationContext(),id.getText().toString(),Toast.LENGTH_LONG).show();

                Call<ResponseBody> comment = apiService.getPostLogin(id.getText().toString(),password.getText().toString());
                comment.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){


                            try{
                                String s = response.body().string();
                                String str = null;
                            //   Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();



                                JSONObject jsonObject = new JSONObject(s);
                                str = jsonObject.getString("result");
                             //   Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
                                SharedPreferences pref = getSharedPreferences("id_list", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor =pref.edit();
                                editor.putString("id",str);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);




                            }catch (IOException e){
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        else{
                            Toast.makeText(Login.this,"다시 입력하세요",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });



    }

}
