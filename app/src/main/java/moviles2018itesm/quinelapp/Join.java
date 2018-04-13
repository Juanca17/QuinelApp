package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Join extends AppCompatActivity {
    private TextView lobbyIDInput;
    private Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //Initializing components from layout
        lobbyIDInput = (TextView)findViewById(R.id.lobbyIDInput);
        join = (Button) findViewById(R.id.join);
    }

    public void joinClick(View v){
        final String user = getIntent().getStringExtra("user");
        final Activity activity = this;
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference gamesRef = database.getReference("games");
        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String lobbyIDInputString = lobbyIDInput.getText().toString();
                boolean flag = true;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.w("JOIN", snapshot.getKey());
                    Log.w("JOIN", lobbyIDInputString);
                    if(snapshot.getKey().equals(lobbyIDInputString)){
                        flag = false;
                        if(Integer.parseInt(snapshot.child("numPlayer").getValue().toString()) - Integer.parseInt(snapshot.child("actNum").getValue().toString()) != 0){
                            DatabaseReference myRef = database.getReference("users");
                            final String num = snapshot.child("actNum").getValue().toString();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String lobbyIDInputString = lobbyIDInput.getText().toString();

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        User userObject= snapshot.getValue(User.class);
                                        Log.w("JOIN", userObject.name);
                                        Log.w("JOIN", currentUser.getEmail());
                                        if (userObject.name.equals(currentUser.getEmail())){
                                            database.getReference("games").child(lobbyIDInputString).child("actNum").setValue(Integer.toString(Integer.parseInt(num) + 1));
                                            snapshot.getRef().child("game").setValue(lobbyIDInputString);
                                            finish();
                                            //Intent intent = new Intent(activity, Lobby.class);
                                            //intent.putExtra("user", currentUser.getEmail());
                                            //startActivity(intent);
                                        }
                                    }
                                    Toast.makeText(activity, "You have joined the lobby "+lobbyIDInputString, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("LOBBY", "Failed to read value.", error.toException());
                                }
                            });
                        } else {
                            Toast.makeText(activity, "Uggh, the lobby is full. Tough luck...", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                if (flag){
                    Toast.makeText(activity, "Lobby ID not found, try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });
    }
}
