package com.example.kyleohanian.individualscorekeeper;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {
    static String userName;
    Button newPlayer, finishedCreation, lookAtGames;
    Spinner players;
    TextView newPlayerTag;
    EditText typePlayerName;
    String [] listOfPlayers;
    String interFragmentName;
    ArrayList<String> arrayList = new ArrayList<String>();

    public static HomeFragment newInstance(String getUserName) {
        HomeFragment homeFragment = new HomeFragment();
        Log.d("CHECKING USERNAME",getUserName);
        Bundle args = new Bundle();
        args.putString("userNameBundle", getUserName);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userNameBundle");
        }


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.newPlayer = (Button)view.findViewById(R.id.button);
        this.finishedCreation = (Button)view.findViewById(R.id.button2);
        this.lookAtGames = (Button)view.findViewById(R.id.button3);
        this.players = (Spinner)view.findViewById(R.id.spinner);
        this.newPlayerTag = (TextView)view.findViewById(R.id.textView2);
        this.typePlayerName = (EditText)view.findViewById(R.id.textView3);
        listOfPlayers = getResources().getStringArray(R.array.test);
        Firebase.setAndroidContext(getActivity());
        final Firebase playersReference = new Firebase("https://individual-stats.firebaseio.com/Users");
        final Firebase userNameReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+ userName);
        Log.d("TAG",userNameReference.toString());
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        players.setAdapter(adapter);
        playersReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d("TAG", "Hashmap created");
                    Log.d("TAG",userNameReference.getKey().toString());
                    Log.d("TAG",postSnapshot.getKey().toString());
                    Log.d("TAG","HI");
                    Firebase batterReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+
                            userName + "/" + postSnapshot.getKey().toString());
                    Log.d("TAG",batterReference.toString());
                    Firebase testReference = postSnapshot.getRef();
                    Log.d("TAG",testReference.toString());
                    if(testReference.toString().equals(batterReference.toString())) {
                        adapter.add(postSnapshot.getKey().toString());
                        Log.d("TAG", postSnapshot.getKey().toString());
                    }
                    else
                        continue;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d("TAG", "Hasmap created");
                    Log.d("TAG",userNameReference.getKey().toString());
                    Log.d("TAG",postSnapshot.getKey().toString());
                    Firebase batterReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+
                            userName + "/" + postSnapshot.getKey().toString());
                    Log.d("TAG",batterReference.toString());
                    Firebase testReference = postSnapshot.getRef();
                    if(testReference.toString().equals(batterReference.toString())) {
                        adapter.add(postSnapshot.getKey().toString());
                        Log.d("TAG", postSnapshot.getKey().toString());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishedCreation.setVisibility(View.VISIBLE);
                newPlayerTag.setVisibility(View.VISIBLE);
                typePlayerName.setVisibility(View.VISIBLE);
                lookAtGames.setVisibility(View.INVISIBLE);
            }
        });
        finishedCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> userData = new HashMap<String, String>();
                String name = typePlayerName.getText().toString();
                userData.put("Name", name);
                userData.put("At-Bats",Integer.toString(0));
                userData.put("Runs Scored",Integer.toString(0));
                userData.put("Hits",Integer.toString(0));
                userData.put("Doubles",Integer.toString(0));
                userData.put("Triples",Integer.toString(0));
                userData.put("Home Runs",Integer.toString(0));
                userData.put("Runs Batted In",Integer.toString(0));
                userData.put("Stolen Bases",Integer.toString(0));
                userData.put("Caught Stealing",Integer.toString(0));
                userData.put("Walks",Integer.toString(0));
                userData.put("Strikeouts",Integer.toString(0));
                userData.put("Grounded Into Double Play",Integer.toString(0));
                userData.put("Hit By Pitch",Integer.toString(0));
                userData.put("Sacrifice Bunts",Integer.toString(0));
                userData.put("Sacrifice Flies",Integer.toString(0));
                userData.put("Intentional Walks",Integer.toString(0));
                userData.put("Number of Pitches Faced",Integer.toString(0));
                userData.put("Number of Strikes Faced",Integer.toString(0));
                userData.put("Number of Pitches Fouled",Integer.toString(0));
                userData.put("Number of Strikes Missed",Integer.toString(0));
                userNameReference.child(name).setValue(userData);

                finishedCreation.setVisibility(View.INVISIBLE);
                newPlayerTag.setVisibility(View.INVISIBLE);
                typePlayerName.setVisibility(View.INVISIBLE);
                lookAtGames.setVisibility(View.VISIBLE);
                typePlayerName.setText("");
                PlayerImplementation imp = new PlayerImplementation(name,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0);
            }
        });

        players.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = players.getSelectedItemPosition();
                interFragmentName = parent.getItemAtPosition(pos).toString();
                Log.d("UNDERTAG", interFragmentName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lookAtGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment gameFragment = GameFragment.newInstance(userName,interFragmentName);
                getInterFragmentName();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.home, gameFragment);
                ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
        return view;
    }

    public String getInterFragmentName() {
        return interFragmentName;
    }

}
