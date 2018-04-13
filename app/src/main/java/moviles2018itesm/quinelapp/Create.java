package moviles2018itesm.quinelapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {
    private TextView lobbyName, leagueInput, participantsNumber;
    private TextView gameid;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Initialize elements
        lobbyName = (TextView)findViewById(R.id.lobbyName);
        leagueInput = (TextView)findViewById(R.id.leagueInput);
        participantsNumber = (TextView)findViewById(R.id.participansNumber);
        //gameid = findViewById(R.id.gameid);
        submit = (Button)findViewById(R.id.submit);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lobbyNameString = lobbyName.getText().toString();
                String leagueInputString = leagueInput.getText().toString();
                String participantsNumberString = participantsNumber.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("games");

                Map<String, Game> games = new HashMap<>();

                Game game = new Game();
                game.owner = currentUser.getEmail();
                game.league = leagueInputString;
                game.name = lobbyNameString;
                game.numPlayer = participantsNumberString;
                game.actNum = "1";

                games.put("game_1", game);

                DatabaseReference newRef = myRef.push();
                String postId = newRef.getKey();
                game.id = postId;

                newRef.setValue(game);
                database.getReference("users").child(currentUser.getUid()).child("game").setValue(postId);

                //gameid.setText("Lobby created. Lobby ID: \n" + postId);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text label", postId);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Create.this,"Lobby ID copied to clipboard." + postId, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
