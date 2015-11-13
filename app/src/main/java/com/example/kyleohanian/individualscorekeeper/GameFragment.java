package com.example.kyleohanian.individualscorekeeper;

import android.app.Activity;
import android.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class GameFragment extends Fragment {
    Button newGame, finishedGameCreation, lookAtStats;
    Spinner games;
    TextView newGameTag;
    EditText typeGameInfo;
    String [] listOfGames;
    TextView gameTag;
    String playerName;
    String adjustedPlayerTag = "";
    String userName = "";
    static String opponent;
    ArrayList<String> arrayList = new ArrayList<String>();
    public static GameFragment newInstance(String userName, String playerName) {
        GameFragment gameFragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString("userNameBundle", userName);
        args.putString("playerNameBundle",playerName);
        gameFragment.setArguments(args);
        return gameFragment;
    }

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userNameBundle");
            playerName = getArguments().getString("playerNameBundle");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        Log.d("OVERTAG", playerName);
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        this.newGame = (Button)view.findViewById(R.id.button);
        this.finishedGameCreation = (Button)view.findViewById(R.id.button2);
        this.lookAtStats = (Button)view.findViewById(R.id.button3);
        this.games = (Spinner)view.findViewById(R.id.spinner);
        this.newGameTag = (TextView)view.findViewById(R.id.textView2);
        this.typeGameInfo = (EditText)view.findViewById(R.id.textView3);
        this.gameTag = (TextView)view.findViewById(R.id.textView4);
        adjustedPlayerTag = String.format(playerName+"'s Game List");
        gameTag.setText(adjustedPlayerTag);
        listOfGames = getResources().getStringArray(R.array.test);
        Firebase.setAndroidContext(getActivity());
        final Firebase playerReference = new Firebase("https://individual-stats.firebaseio.com/Users/"
                            + userName + "/" + playerName);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        games.setAdapter(adapter);
        playerReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Firebase gameReference = new Firebase("https://individual-stats.firebaseio.com/Users/"+
                            userName + "/" + playerName + "/Games/" + postSnapshot.getKey().toString());
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
                            userName + "/" + playerName +"/Games/"+ postSnapshot.getKey().toString());
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
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishedGameCreation.setVisibility(View.VISIBLE);
                newGameTag.setVisibility(View.VISIBLE);
                typeGameInfo.setVisibility(View.VISIBLE);
                lookAtStats.setVisibility(View.INVISIBLE);
            }
        });
        finishedGameCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> gameData = new HashMap<String, String>();
                String name = typeGameInfo.getText().toString();
                gameData.put("Opponent", name);
                gameData.put("Date", Integer.toString(Calendar.DATE));
                finishedGameCreation.setVisibility(View.INVISIBLE);
                newGameTag.setVisibility(View.INVISIBLE);
                typeGameInfo.setVisibility(View.INVISIBLE);
                lookAtStats.setVisibility(View.VISIBLE);
                typeGameInfo.setText("");
                PlayerImplementation imp = new PlayerImplementation(name,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0);
                Firebase myReference = playerReference.child("Games");
                myReference.child(gameData.get("Opponent")).setValue(gameData);
            }
        });
        lookAtStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtBatFragment atBatFragment = AtBatFragment.newInstance(userName,playerName,opponent);
                getOpponentName();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.game,atBatFragment);
                ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
        games.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = games.getSelectedItemPosition();
                opponent = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public String getOpponentName() {
        return opponent;
    }
}

