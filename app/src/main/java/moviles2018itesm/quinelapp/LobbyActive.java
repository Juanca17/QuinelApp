package moviles2018itesm.quinelapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyActive extends Fragment {
    private TextView userScore, league, owner, lobbyID;
    private Button history, play;
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

        //Initializing adapter
        //CONSEGUIR UNA LISTA DE NOMBRE CON USUARIOS Y SUS SCORES DE LA DB
        ArrayList<String[]> items = new ArrayList<String[]>(); //INICIALIZAR LOS DATOS AQUI

        participantsAdapter = new ParticipantsAdapter(items, getActivity());

        //Filling textviews
        //NECESITAMOS ENCONTRAR LA INFO DE CADA TEXTO EN LA DB
        //PARA ESO TENEMOS QUE RECIBIR EL USERNAME LOGEADO DE ALGUN MODO
        String userName = "UserTest"; //PONER EL RESULTADO AQUI
        String leagueString = "LigaMexicana"; //CON EL USERNAME BUSCAR LA LIGA DEL JUEGO ACTUAL
        String ownerString = "AnotherUser"; //CON EL USERNAME BUSCAR EL DUEÑO DEL JUEGO ACTUAL
        String idString = "FBA123"; //CON EL USERNAME BUSCAR EL ID DEL JUEGO ACTUAL
        String scoreString = "3"; //CON EL USERNAME BUSCAR EL SCORE DEL JUGADOR EN EL JUEGO ACTUAL
        userScore.setText("Score: "+scoreString);
        league.setText("League: "+leagueString);
        owner.setText("Owner: "+ ownerString);
        lobbyID.setText("Lobby: "+ idString);

        //-----------------------------------
        //DE ALGUNA FORMA LLENAR LISTVIEW CON PARTICIPANTES AQUI
        //--------------------------------------

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE HISTORIA
                Intent intent = new Intent();//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                startActivity(intent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PASAR A LA ACTIVIDAD DE JUEGO
                Intent intent = new Intent();//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                startActivity(intent);
            }
        });

        return v;
    }

}
