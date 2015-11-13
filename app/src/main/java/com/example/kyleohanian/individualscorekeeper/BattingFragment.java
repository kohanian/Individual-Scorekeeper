package com.example.kyleohanian.individualscorekeeper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.MutableData;
import com.firebase.client.snapshot.Index;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.Node;

import java.util.HashMap;

public class BattingFragment extends Fragment {
    GridView gridView;
    String userName,playerName,opponentName,atBatName;
    RadioGroup pitch;
    RadioButton strike, ball, foulBall, inPlay, swingingStrike;
    Button moveOn, moveOn2;
    boolean inPlayAssert = false;
    int balls = 0;
    int strikes = 0;
    int numPitches = 0;
    TextView count;
    Spinner inPlayOptions;
    String atBatResult;
    static final String[] numbers = new String[] {
            "  1","  2","  3","  4","  5","  6","  7","  8","  9"
    };
    static final String[] options = new String[] {
            "Ground Out","Fly Out","Grounded Into Double Play","Fielder's Choice","Reached On Error",
            "Sacrifice Fly","Sacrifice Bunt","Single","Double","Triple","Home Run"
    };
    public static BattingFragment newInstance(String getUserName, String playerName, String opponentName, String atBat) {
        BattingFragment battingFragment = new BattingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userNameBundle", getUserName);
        bundle.putString("playerNameBundle",playerName);
        bundle.putString("opponentNameBundle", opponentName);
        bundle.putString("atBatBundle",atBat);
        battingFragment.setArguments(bundle);
        return battingFragment;
    }

    public BattingFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userNameBundle");
            playerName = getArguments().getString("playerNameBundle");
            opponentName = getArguments().getString("opponentNameBundle");
            atBatName = getArguments().getString("atBatBundle");

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container == null) {
            return null;
        }
        final Firebase userNameReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+ userName);
        View view = inflater.inflate(R.layout.fragment_batting,container,false);
        this.gridView = (GridView)view.findViewById(R.id.gridView);
        this.pitch = (RadioGroup)view.findViewById(R.id.radioGroup);
        this.ball = (RadioButton)view.findViewById(R.id.radio1);
        this.strike = (RadioButton)view.findViewById(R.id.radio2);
        this.swingingStrike = (RadioButton)view.findViewById(R.id.radio5);
        this.foulBall = (RadioButton)view.findViewById(R.id.radio3);
        this.inPlay = (RadioButton)view.findViewById(R.id.radio4);
        this.moveOn = (Button)view.findViewById(R.id.button8);
        this.moveOn2 = (Button)view.findViewById(R.id.button9);
        this.count = (TextView)view.findViewById(R.id.textView11);
        count.setText("0 - 0");
        this.inPlayOptions = (Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> inPlayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,options);
        inPlayOptions.setAdapter(inPlayAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, numbers);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ball.setVisibility(View.VISIBLE);
                strike.setVisibility(View.VISIBLE);
                swingingStrike.setVisibility(View.VISIBLE);
                foulBall.setVisibility(View.VISIBLE);
                inPlay.setVisibility(View.VISIBLE);
                moveOn.setVisibility(View.VISIBLE);
            }
        });
        inPlayOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atBatResult = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        moveOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ball.isChecked()) {
                    balls++;
                    numPitches++;
                    if(balls == 4) {
                        Toast toast = Toast.makeText(getActivity(),"WALK",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if(strike.isChecked()) {
                    strikes++;
                    numPitches++;
                    if(strikes == 3) {
                        Toast toast = Toast.makeText(getActivity(),"STRIKEOUT LOOKING",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if(swingingStrike.isChecked()) {
                    strikes++;
                    numPitches++;
                    if(strikes == 3) {
                        Toast toast = Toast.makeText(getActivity(),"STRIKEOUT SWINGING",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if(foulBall.isChecked()) {
                    numPitches++;
                    if(strikes < 2) {
                        strikes++;
                    }
                }
                else if(inPlay.isChecked()) {
                    numPitches++;
                    Toast toast = Toast.makeText(getActivity(),"BALL IN PLAY",Toast.LENGTH_SHORT);
                    toast.show();
                    inPlayAssert = true;
                }
                ball.setVisibility(View.INVISIBLE);
                strike.setVisibility(View.INVISIBLE);
                swingingStrike.setVisibility(View.INVISIBLE);
                foulBall.setVisibility(View.INVISIBLE);
                inPlay.setVisibility(View.INVISIBLE);
                moveOn.setVisibility(View.INVISIBLE);
                count.setText(Integer.toString(balls) + " - " + Integer.toString(strikes));
                if(inPlayAssert == true) {
                    inPlayOptions.setVisibility(View.VISIBLE);
                    moveOn2.setVisibility(View.VISIBLE);
                    inPlayAssert = false;
                }
            }
        });
        moveOn2.setOnClickListener(new View.OnClickListener() {
            //"Ground Out","Fly Out","Grounded Into Double Play","Fielder's Choice","Reached On Error",
              //      "Sacrifice Fly","Sacrifice Bunt","Single","Double","Triple","Home Run"
            @Override
            public void onClick(View v) {
                inPlayOptions.setVisibility(View.INVISIBLE);
                moveOn2.setVisibility(View.INVISIBLE);
                HashMap<String,Object> userData = new HashMap<String,Object>();
                userData.put("Name", playerName);
                userData.put("At-Bats",Integer.toString(0));
                Firebase hotLava = new Firebase("https://individual-stats.firebaseio.com/Users/"+ userName + "/"
                                    + playerName);
                MutableData spiderMan = new MutableData((Node) hotLava);
                Object captainAmerica = spiderMan.getValue();
                int batMan = Integer.parseInt(captainAmerica.toString());
                DataSnapshot ironMan = new DataSnapshot(hotLava, IndexedNode.from((Node) Index.fromQueryDefinition("At-Bats")));
                userData.put("Hits",Integer.toString(0));
//                userData.put("Doubles",Integer.toString(0));
//                userData.put("Triples",Integer.toString(0));
//                userData.put("Home Runs",Integer.toString(0));
//                userData.put("Runs Batted In",Integer.toString(0));
//                userData.put("Stolen Bases",Integer.toString(0));
//                userData.put("Caught Stealing",Integer.toString(0));
//                userData.put("Walks",Integer.toString(0));
//                userData.put("Strikeouts",Integer.toString(0));
//                userData.put("Grounded Into Double Play",Integer.toString(0));
//                userData.put("Hit By Pitch",Integer.toString(0));
//                userData.put("Sacrifice Bunts",Integer.toString(0));
//                userData.put("Sacrifice Flies",Integer.toString(0));
//                userData.put("Intentional Walks",Integer.toString(0));
//                userData.put("Number of Pitches Faced",Integer.toString(0));
//                userData.put("Number of Strikes Faced",Integer.toString(0));
//                userData.put("Number of Pitches Fouled",Integer.toString(0));
//                userData.put("Number of Strikes Missed",Integer.toString(0));
                if(atBatResult.equals("Ground Out")) {

                }
                else if(atBatResult.equals("Fly Out")) {

                }
                else if(atBatResult.equals("Grounded Into Double Play")) {

                }
                else if(atBatResult.equals("Fielder's Choice")) {

                }
                else if(atBatResult.equals("Reached On Error")) {

                }
                else if(atBatResult.equals("Sacrifice Fly")) {

                }
                else if(atBatResult.equals("Sacrifice Bunt")) {

                }
                else if(atBatResult.equals("Single")) {

                }
                else if(atBatResult.equals("Double")) {

                }
                else if(atBatResult.equals("Triple")) {

                }
                else if(atBatResult.equals("Home Run")) {

                }

                userNameReference.child(playerName).updateChildren(userData);
                balls = 0;
                strikes = 0;
                numPitches = 0;
                Toast toast = Toast.makeText(getActivity(),atBatResult,Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        return view;
    }
}