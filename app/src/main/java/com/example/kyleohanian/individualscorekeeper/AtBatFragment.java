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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AtBatFragment extends Fragment {
    String getUserName;
    String playerName;
    String opponentName;
    static boolean leftHandedPitcher;
    static boolean rightHandedPitcher;
    static boolean runnerOnFirst;
    static boolean runnerOnSecond;
    static boolean runnerOnThird;
    static int inningNumberAwesome;
    Button newAtBat, reviewAtBat, righty, lefty,createAtBat;
    TextView pitcherHandness, runnersOn, Inning;
    EditText inningNumber;
    Spinner listOfAtBats;
    RadioButton firstBase,secondBase,thirdBase;
    ArrayList<String> arrayList = new ArrayList<String>();

    public static AtBatFragment newInstance(String getUserName, String playerName, String opponentName) {
        AtBatFragment atBatFragment = new AtBatFragment();
        Bundle args = new Bundle();
        args.putString("userNameBundle", getUserName);
        args.putString("playerNameBundle",playerName);
        args.putString("opponentNameBundle",opponentName);
        atBatFragment.setArguments(args);
        return atBatFragment;
    }

    public AtBatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getUserName = getArguments().getString("userNameBundle");
            playerName = getArguments().getString("playerNameBundle");
            opponentName = getArguments().getString("opponentNameBundle");
        }



    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_at_bat, container, false);
        final Firebase myReference = new Firebase("https://individual-stats.firebaseio.com/Users/"
                + getUserName + "/" + playerName + "/" + "Games/" + opponentName + "/At-Bats");
        final Firebase otherReference = new Firebase("https://individual-stats.firebaseio.com/Users/"
                    + getUserName + "/" + playerName + "/" + "Games/" + opponentName);
        this.newAtBat = (Button)view.findViewById(R.id.button);
        this.reviewAtBat = (Button)view.findViewById(R.id.button3);
        this.listOfAtBats = (Spinner)view.findViewById(R.id.spinner);
        this.righty = (Button)view.findViewById(R.id.button4);
        this.lefty = (Button)view.findViewById(R.id.button5);
        this.createAtBat = (Button)view.findViewById(R.id.button6);
        createAtBat.setVisibility(View.INVISIBLE);
        this.pitcherHandness = (TextView)view.findViewById(R.id.textView6);
        this.runnersOn = (TextView)view.findViewById(R.id.textView7);
        runnersOn.setVisibility(View.INVISIBLE);
        this.firstBase = (RadioButton)view.findViewById(R.id.radioButton);
        this.secondBase = (RadioButton)view.findViewById(R.id.radioButton2);
        this.thirdBase = (RadioButton)view.findViewById(R.id.radioButton3);
        this.Inning = (TextView)view.findViewById(R.id.textView8);
        this.inningNumber = (EditText)view.findViewById(R.id.textView9);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOfAtBats.setAdapter(adapter);
        otherReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Firebase gameReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+
                            getUserName + "/" + playerName + "/Games/" + opponentName +
                            "/At-Bats/"+ postSnapshot.getKey().toString());
                    Firebase testReference = postSnapshot.getRef();
                    Log.d("TAG", "Hashmap created");
                    if(testReference.toString().equals(gameReference.toString())) {
                        adapter.add(postSnapshot.getKey().toString());
                    }
                    else
                        continue;
                    Log.d("TAG", postSnapshot.getValue().toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Firebase gameReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+
                            getUserName + "/" + playerName + "/Games/" + opponentName +
                            "/At-Bats/"+ postSnapshot.getKey().toString());
                    Firebase testReference = postSnapshot.getRef();
                    Log.d("TAG", "Hashmap created");
                    if(testReference.toString().equals(gameReference.toString())) {
                        adapter.add(postSnapshot.getKey().toString());
                    }
                    else
                        continue;
                    Log.d("TAG", postSnapshot.getValue().toString());
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

            }
        });
        newAtBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                righty.setVisibility(View.VISIBLE);
                lefty.setVisibility(View.VISIBLE);
                pitcherHandness.setVisibility(View.VISIBLE);
                reviewAtBat.setVisibility(View.INVISIBLE);
            }
        });
        reviewAtBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(),"TOASTY!!!!!!!!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        righty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightHandedPitcher = true;
                leftHandedPitcher = false;
                righty.setVisibility(View.INVISIBLE);
                lefty.setVisibility(View.INVISIBLE);
                pitcherHandness.setVisibility(View.INVISIBLE);
                firstBase.setVisibility(View.VISIBLE);
                secondBase.setVisibility(View.VISIBLE);
                thirdBase.setVisibility(View.VISIBLE);
                runnersOn.setVisibility(View.VISIBLE);
                createAtBat.setVisibility(View.VISIBLE);
                Inning.setVisibility(View.VISIBLE);
                inningNumber.setVisibility(View.VISIBLE);

            }
        });
        lefty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightHandedPitcher = false;
                leftHandedPitcher = true;
                righty.setVisibility(View.INVISIBLE);
                lefty.setVisibility(View.INVISIBLE);
                pitcherHandness.setVisibility(View.INVISIBLE);
                firstBase.setVisibility(View.VISIBLE);
                secondBase.setVisibility(View.VISIBLE);
                thirdBase.setVisibility(View.VISIBLE);
                runnersOn.setVisibility(View.VISIBLE);
                createAtBat.setVisibility(View.VISIBLE);
                Inning.setVisibility(View.VISIBLE);
                inningNumber.setVisibility(View.VISIBLE);
            }
        });
        createAtBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> atBatData = new HashMap<String, Object>();
                if(firstBase.isChecked()) {
                    runnerOnFirst = true;
                }
                if(secondBase.isChecked()) {
                    runnerOnSecond = true;
                }
                if(thirdBase.isChecked()) {
                    runnerOnThird = true;
                }
                String innings = inningNumber.getText().toString();
                inningNumberAwesome = Integer.parseInt(innings);
                atBatData.put("Right Handed Pitcher", rightHandedPitcher);
                atBatData.put("Left Handed Pitcher", leftHandedPitcher);
                atBatData.put("Runner on First", leftHandedPitcher);
                atBatData.put("Runner on Second", leftHandedPitcher);
                atBatData.put("Runner On Third", leftHandedPitcher);
                atBatData.put("Inning", inningNumberAwesome);
                myReference.child(Integer.toString(inningNumberAwesome)).setValue(atBatData);
                BattingFragment battingFragment = BattingFragment.newInstance(getUserName,playerName,opponentName,Integer.toString(inningNumberAwesome));
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.atBat,battingFragment);
                fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });



        return view;
    }
}