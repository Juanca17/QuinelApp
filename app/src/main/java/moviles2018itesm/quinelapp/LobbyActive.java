package moviles2018itesm.quinelapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyActive extends Fragment {
    private TextView userScore, league, owner, lobbyID, userName;
    private Button history, play, news;
    private String leagueString;
    private ListView list;
    private int score;
    SharedPreferences sharedPreferences;
    private ParticipantsAdapter participantsAdapter;

    public LobbyActive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lobby_active, container, false);

        //Initializing textviews and buttons
        userScore = (TextView)v.findViewById(R.id.score);
        league = (TextView)v.findViewById(R.id.league);
        owner = (TextView)v.findViewById(R.id.owner);
        lobbyID = (TextView)v.findViewById(R.id.lobbyID);
        play = (Button)v.findViewById(R.id.play);
        list = (ListView)v.findViewById(R.id.list);
        userName = (TextView)v.findViewById(R.id.userName);


        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        userName.setText(currentUser.getEmail());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameTemp = "";
                ArrayList<String[]> items = new ArrayList<String[]>(); //INICIALIZAR LOS DATOS AQUI
                participantsAdapter = new ParticipantsAdapter(items, getActivity());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    assert currentUser != null;
                    assert user != null;
                    if (Objects.equals(currentUser.getEmail(), user.name)){
                        userScore.setText("Score: " + user.score);
                        score = user.score;
                        gameTemp = user.game;
                        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("game", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("game", user.game);
                        editor.apply();
                        Log.w("ACTIVE", user.game);
                    }
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    assert currentUser != null;
                    assert user != null;
                    ArrayList<String[]> participants = new ArrayList<String[]>();
                    if (Objects.equals(user.game, gameTemp) && !(Objects.equals(currentUser.getEmail(), user.name))){
                        String[] temp = {user.name, Integer.toString(user.score)};
                        participants.add(temp);
                    }
                    int participantsSize = participants.size();
                    int max = 0;
                    int maxI = 0;
                    for (int i = 0; i < participantsSize; i++){
                        for (int j = 0; j < participants.size(); j++){
                            if (Integer.parseInt(participants.get(j)[1]) >= max ){
                                max = Integer.parseInt(participants.get(j)[1]);
                                maxI = j;
                            }
                        }
                        items.add(participants.get(maxI));
                        participants.remove(maxI);
                    }
                }

                list.setAdapter(participantsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });

        myRef = database.getReference("games");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Game game = snapshot.getValue(Game.class);
                    sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("game", Context.MODE_PRIVATE);
                    String gameSec = sharedPreferences.getString("game", null);

                    assert currentUser != null;
                    assert game != null;
                    if (Objects.equals(gameSec, game.id)){
                        leagueString = game.league;
                        league.setText("League: " + game.league);
                        owner.setText("Owner: " + game.owner);
                        lobbyID.setText("Lobby: " + game.name);

                        //Activity commuication
                        Navigation nav = (Navigation) getActivity();
                        nav.league = game.league;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference leagueRef = database.getReference("leagues");

        leagueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.w("LOBBY", snapshot.child("name").getValue().toString());
                    Log.w("LOBBY", leagueString);
                    if(snapshot.child("name").getValue().toString().equals(leagueString)){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            if (!snapshot1.getKey().equals("name")){
                                //Cambiar despues de definir nuevo sistema de puntos
                                if (snapshot1.child("played").getValue(Boolean.class)){
                                    try {
                                        int newScore1 = snapshot1.child("score1").getValue(Integer.class);
                                        int newScore2 = snapshot1.child("score2").getValue(Integer.class);
                                        int betScore1 = snapshot1.child("users").child(currentUser.getUid()).child("equipo1").getValue(Integer.class);
                                        int betScore2 = snapshot1.child("users").child(currentUser.getUid()).child("equipo2").getValue(Integer.class);

                                        if ((newScore1 == betScore1) && (newScore2 == betScore2)){
                                            score += 5;
                                            database.getReference("users").child(currentUser.getUid()).child("score").setValue(score);
                                            userScore.setText("Score: " + score);
                                        } else if (((newScore1 > newScore2) && (betScore1 > betScore2)) || ((newScore1 < newScore2) && (betScore1 < betScore2)) || ((newScore1 == newScore2) && (betScore1 == betScore2))){
                                            score ++;
                                            database.getReference("users").child(currentUser.getUid()).child("score").setValue(score);
                                            userScore.setText("Score: " + score);
                                        }
                                    } catch (NullPointerException e){
                                        Log.d("LOBBYACTIVE", "Games not played yet");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });

        //Initializing adapter
        //CONSEGUIR UNA LISTA DE NOMBRE CON USUARIOS Y SUS SCORES DE LA DB
        ArrayList<String[]> items = new ArrayList<String[]>(); //INICIALIZAR LOS DATOS AQUI

        participantsAdapter = new ParticipantsAdapter(items, getActivity());

        //-----------------------------------
        //DE ALGUNA FORMA LLENAR LISTVIEW CON PARTICIPANTES AQUI
        //--------------------------------------



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE HISTORIA
                Intent intent = new Intent(LobbyActive.this.getContext(), GameListActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                intent.putExtra("league", leagueString);
                startActivity(intent);
            }
        });



        return v;
    }

    public void playClick(View v){
        //PASAR A LA ACTIVIDAD DE JUEGO
        Intent intent = new Intent(LobbyActive.this.getContext(), GameListActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
        startActivity(intent);
    }

    public void historyClick(View v){
        //PASAR A LA ACTIVIDAD DE HISTORIA
        Intent intent = new Intent(LobbyActive.this.getContext(), HistorialActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
        startActivity(intent);
    }
}
