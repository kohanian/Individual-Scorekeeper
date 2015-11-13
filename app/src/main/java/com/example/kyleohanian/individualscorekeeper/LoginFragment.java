package com.example.kyleohanian.individualscorekeeper;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Stopwatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    LayoutInflater inflater;
    ViewGroup container;
    ImageView baseballField;
    Button login;
    EditText username, password;
    String user1 = "";
    static String user2 = "";

    public static LoginFragment newInstance(String one) {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.user1 = one;
        return loginFragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(inflater,container,savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        // Inflate the layout for this fragment
        int i = 0;
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        baseballField = (ImageView) view.findViewById(R.id.imageView);
        this.username = (EditText)view.findViewById(R.id.textView5);
        this.login = (Button)view.findViewById(R.id.button7);
        Firebase.setAndroidContext(getActivity());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = HomeFragment.newInstance(username.getText().toString());
                getUserName();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.login, homeFragment);
                ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });


        return view;
    }
    public String getUserName(){
        return username.getText().toString();
    }


    }
