package moviles2018itesm.quinelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        userLoged = intent.getStringExtra("user");

        DatabaseReference myRef = database.getReference("users");

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    assert currentUser != null;
                    assert user != null;
                    if (Objects.equals(currentUser.getEmail(), user.name)){
                        if (user.game.equals("0")){
                            Lobby.this.userLobby = "None";
                        } else {
                            Lobby.this.userLobby = user.game;
                        }
                    }
                }
                //Setting the userName with the user loged
                userName = (TextView)findViewById(R.id.userName);
                userName.setText(userLoged);

                //Adding fragment from code
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                Log.w("LOBBY", userLobby + " ");
                if (userLobby == "None") {
                    lobbyUnactive = new LobbyUnactive();
                    ft.add(R.id.lobbyContainer, lobbyUnactive, "lobbyUnactive");
                }else {
                    lobbyActive = new LobbyActive();
                    ft.add(R.id.lobbyContainer, lobbyActive, "lobbyActive");
                }
                ft.commit();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });

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
