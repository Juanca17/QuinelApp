package moviles2018itesm.quinelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Lobby extends AppCompatActivity {
    private TextView userName;
    private LobbyUnactive lobbyUnactive;
    private LobbyActive lobbyActive;

    private String userLoged, userLobby;
    //private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        //OBTENER EL NOMBRE DEL USUARIO LOGEADO
        Intent intent = getIntent();
        userLoged = intent.getStringExtra("user");

        //BUSCAR SI EL USUARIO TIENE JUEGO ACTIVO EN FIREBASE
            //HACER SHIT RARA AQUI
        userLobby = "None"; //PONER AQUI EL RESULTADO DE LA BUSQUEDA

        //Setting the userName with the user loged
        userName = (TextView)findViewById(R.id.userName);
        userName.setText(userLoged);

        //Adding fragment from code
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (userLobby == "None") {
            lobbyUnactive = new LobbyUnactive();
            ft.add(R.id.lobbyContainer, lobbyUnactive, "lobbyUnactive");
        }else {
            lobbyActive = new LobbyActive();
            ft.add(R.id.lobbyContainer, lobbyActive, "lobbyActive");
        }
        ft.commit();
    }

    //NO SE SI ESTO FUNCIONE
    @Override
    protected void onResume() {
        super.onResume();
        //AQUI QUIERO CAMBIAR DE FRAGMENTO
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (userLobby == "None"){
            lobbyUnactive = new LobbyUnactive();
            ft.replace(R.id.lobbyContainer, lobbyUnactive, "lobbyUnactiveResume");
        }else {
            lobbyActive = new LobbyActive();
            ft.replace(R.id.lobbyContainer, lobbyActive, "lobbyActiveResume");
        }

    }
}
