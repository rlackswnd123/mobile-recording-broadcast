package com.example.jsj.streaming;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jsj on 2017-10-30.
 */

public class DaejinFragment_sub extends Fragment {



    public DaejinFragment_sub() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("DJ TV");
        // Inflate the layout for this fragment
        Fragment ClubFragment = new ClubFragment_sub();
        Fragment ContentFragment = new ContentFragment_sub();
        Fragment GalleryFragment = new GalleryFragment();
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();


        childFt.replace(R.id.child_fragment_container2,ContentFragment); //학교 홍보
        childFt.replace(R.id.child_fragment_container,ClubFragment); //동아리
   //     childFt.replace(R.id.child_fragment_container3,GalleryFragment); //학과 홍보
        childFt.addToBackStack(null);
        childFt.commit();




        return inflater.inflate(R.layout.daejin_tv, container, false); }





}

