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
    private TextView userScore, league, owner, lobbyID;
    private Button history, play, news;
    private ListView list;
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
        history = (Button)v.findViewById(R.id.history);
        play = (Button)v.findViewById(R.id.play);
        list = (ListView)v.findViewById(R.id.list);
        news = (Button)v.findViewById(R.id.news);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

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
                    if (Objects.equals(user.game, gameTemp) && !(Objects.equals(currentUser.getEmail(), user.name))){
                        String[] temp = {user.name, Integer.toString(user.score)};
                        items.add(temp);
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
                        league.setText("League: " + game.league);
                        owner.setText("Owner: " + game.owner);
                        lobbyID.setText("Lobby: " + game.id);
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

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE HISTORIA
                Intent intent = new Intent(LobbyActive.this.getContext(), HistorialActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                startActivity(intent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE HISTORIA
                Intent intent = new Intent(LobbyActive.this.getContext(), GameListActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE HISTORIA
                Intent intent = new Intent(LobbyActive.this.getContext(), News.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
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
