package com.example.jsj.streaming;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button stream_bnt;
    private Fragment contentFragment;
    private Fragment galleryFragment;
    private Fragment daejinFragment;
    private Fragment daejinFragment_sub;
    private Fragment clubFragment;
    public Fragment f;
    public Fragment f2;
    public Fragment f3;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentFragment = new ContentFragment();// 학교 홍보
        galleryFragment = new GalleryFragment();// 학과홍보
        daejinFragment = new DaejinFragment();// 메인 화면
        clubFragment = new ClubFragment(); //동아리
        daejinFragment_sub = new DaejinFragment_sub();






        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, daejinFragment_sub);
      //  transaction.addToBackStack(null);
        f =daejinFragment_sub;
        transaction.commit();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);   //네비게이션 뷰에서 해더뷰를 가져온다

        final Button login_btn = (Button) view.findViewById(R.id.login_btn);   //헤더뷰에서 버튼을 찾아서 버튼에 넣은다
        SharedPreferences pref = getSharedPreferences("id_list", Activity.MODE_PRIVATE);
        String id = pref.getString("id",null);
        if(id !=null){
            login_btn.setText("로그아웃");


        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("id_list", Activity.MODE_PRIVATE);
                String id = pref.getString("id",null);
                if(login_btn.getText().equals("로그아웃")){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    login_btn.setText("로그인");
                  //  Toast.makeText(MainActivity.this,id,Toast.LENGTH_LONG).show();



                }else if(login_btn.getText().equals("로그인")){
                  //  Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(
                            getApplicationContext(), //현재 화면의 제어
                            Login.class);//다음 넘어갈 클래스 지정
                    startActivity(intent); //다음 화면으로 넘어간다


                }



            }
        });

        Button stream_butn =(Button)view.findViewById(R.id.stream);   //녹화하기 버튼
        stream_butn.setOnClickListener(new View.OnClickListener() {
            @Override
            /*
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        VideoViewDemo.class);
                startActivity(intent);

            }*/

            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Web_View.class);
                startActivity(intent);

            }
        });





       /*
       * 화면 전환 - 인탠트 나리(StartActivity)
       * 1.다음 넘어갈 화면을 준비 한다 (layout xml,java)
       * 2.AndroidManifest.xml 에 Activity 를 등록한다
       * 3. Intent 객체를 만들어서 StartActivity 한다
       * */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //상단 오른쪽 메뉴
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        Button stream_bnt = (Button) findViewById(R.id.login_btn);
        stream_bnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), //현재 화면의 제어
                        Login.class);//다음 넘어갈 클래스 지정
                startActivity(intent); //다음 화면으로 넘어간다
            }
        });*/

        //noinspection SimplifiableIfStatement

        //위에 메뉴

        /*
        if (id == R.id.action_search) {
            return true;
        }
        else if(id ==R.id.login_btn){

        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {   //네비게이션 바 밑에꺼 쓸때
        // Handle navigation view item clicks here.


       // Fragment mFragemnt = getSupportFragmentManager().findFragmentByTag(fragmentTag);


       // FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        Fragment tag =null;
        transaction.remove(f);

        if (id == R.id.university) {

            tag =contentFragment;

        } else if (id == R.id.department) {

            tag =galleryFragment;
        } else if(id==R.id.daejin){

           tag =daejinFragment;

        }else if(id==R.id.club){
           tag =contentFragment;

        }

       // String fragmentTag = tag.getClass().getSimpleName();
       // getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (id == R.id.university) {
            transaction.replace(R.id.container, contentFragment);
            fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
       /*     transaction.remove(galleryFragment);
            transaction.remove(daejinFragment);
            transaction.remove(clubFragment);
            transaction.remove(daejinFragment_sub);

*/
        f=contentFragment;

        } else if (id == R.id.department) {
            transaction.replace(R.id.container, galleryFragment);
            fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
      /*      transaction.remove(contentFragment);
            transaction.remove(daejinFragment);
            transaction.remove(clubFragment);
            transaction.remove(daejinFragment_sub);

*/
      f=galleryFragment;
        } else if(id==R.id.daejin){
            transaction.replace(R.id.container, daejinFragment);
            fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
     /*       transaction.remove(contentFragment);
            transaction.remove(galleryFragment);
            transaction.remove(clubFragment);
            transaction.remove(daejinFragment_sub);
*/

        }else if(id==R.id.club){
            transaction.replace(R.id.container, clubFragment);
            fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
      /*      transaction.remove(contentFragment);
            transaction.remove(galleryFragment);
            transaction.remove(daejinFragment);
            transaction.remove(daejinFragment_sub);

*/
      f=clubFragment;

        }
    //   Log.d("tag",fragmentTag);

   //     transaction.addToBackStack(null);



        transaction.commit();







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
