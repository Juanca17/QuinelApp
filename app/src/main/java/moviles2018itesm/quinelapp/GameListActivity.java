package moviles2018itesm.quinelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameListActivity extends AppCompatActivity {

    private TextView selecciona, jornada;
    private String league;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        selecciona = findViewById(R.id.textView);
        jornada = findViewById(R.id.textView2);
        Intent intent = getIntent();
        league = intent.getStringExtra("league");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("leagues");

        this.fm = getFragmentManager();
        this.ft = this.fm.beginTransaction();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue().toString();
                    if (name.equals(league)){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            if (!snapshot1.getKey().equals("name") && !((boolean)snapshot1.child("played").getValue())){
                                Log.w("GameList", snapshot1.getValue().toString());
                                ft.add(R.id.line, GameFragment.newInstance(
                                        snapshot1.child("date").getValue().toString(),
                                        snapshot1.child("team1").child("name").getValue().toString(),
                                        snapshot1.child("team2").child("name").getValue().toString(),
                                        R.drawable.cruzazul,
                                        R.drawable.cruzazul,
                                        snapshot1.child("team1").getRef(),
                                        snapshot1.child("team2").getRef(),
                                        snapshot1.child("tie").getRef()),
                                        snapshot1.child("date").getValue().toString());
                            }
                        }
                        ft.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameList", "Failed to read value.", error.toException());
            }
        });

        /*Fragment f1 = GameFragment.newInstance("Viernes 23 de febrero 21:00", "Puebla", "Necaxa", R.drawable.cruzazul, R.drawable.cruzazul);
        ft.add(R.id.fragment, f1);

        Fragment f2 = GameFragment.newInstance("Viernes 6 de abril 21:00", "Atlas", "Monterrey", R.drawable.atlas, R.drawable.monterrey);
        ft.add(R.id.fragment2, f2);

        Fragment f3 = GameFragment.newInstance("Sábado 7 de abril 17:00", "Lobos Buap", "Veracruz", R.drawable.lobos, R.drawable.veracruz);
        ft.add(R.id.fragment3, f3);

        Fragment f4 = GameFragment.newInstance("Sábado 7 de abril 17:00", "Querétaro", "Toluca", R.drawable.queretaro, R.drawable.toluca);
        ft.add(R.id.fragment4, f4);

        Fragment f5 = GameFragment.newInstance("Sábado 7 de abril 19:00", "Tigres", "Monarcas", R.drawable.tigres, R.drawable.monarcas);
        ft.add(R.id.fragment5, f5);

        Fragment f6 = GameFragment.newInstance("Sábado 7 de abril 19:06", "Pachuca", "León", R.drawable.pachuca, R.drawable.leon);
        ft.add(R.id.fragment6, f6);

        Fragment f7 = GameFragment.newInstance("Sábado 7 de abril 21:00", "América", "Xolos Tijuana", R.drawable.america, R.drawable.tijuana);
        ft.add(R.id.fragment7, f7);

        Fragment f8 = GameFragment.newInstance("Domingo 8 de abril 12:00", "Pumas", "Chivas", R.drawable.pumas, R.drawable.chivas);
        ft.add(R.id.fragment8, f8);

        Fragment f9 = GameFragment.newInstance("Domingo 8 de abril 18:00", "Santos", "Cruz Azul", R.drawable.santos, R.drawable.cruzazul);
        ft.add(R.id.fragment9, f9);

        ft.commit();*/
    }
}
