package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    private ListView listView ;
    private String league;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        listView = findViewById(R.id.listView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("leagues");
        Intent intent = getIntent();
        final Activity activity = this;
        league = intent.getStringExtra("league");

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList historialArray = new ArrayList();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.w("HISTORY", snapshot.child("name").getValue().toString());
                    if (snapshot.child("name").getValue().equals(league)){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            if(!snapshot1.getKey().equals("name") && ((boolean) snapshot1.child("played").getValue())){
                                Log.w("HISTORY", snapshot1.child("date").getValue().toString());
                                Log.w("HISTORY", snapshot1.child("team1").child("name").getValue().toString());
                                Log.w("HISTORY", snapshot1.child("team2").child("name").getValue().toString());
                                historialArray.add(new Historial(league,
                                        snapshot1.child("date").getValue().toString(),
                                        snapshot1.child("team1").child("name").getValue().toString(),
                                        snapshot1.child("team2").child("name").getValue().toString(),
                                        "Local", "Acierto"));

                            }
                        }
                    }
                }
                AdapterHistorial adapter = new AdapterHistorial(activity, historialArray);

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameList", "Failed to read value.", error.toException());
            }
        });

        /*Historial h1=new Historial("Jornada 8", "Sábado 27 de Febrero", "Tigres", "Atlas", "Local", "Acierto", R.drawable.chivas);
        Historial h2=new Historial("Jornada 8", "Sábado 27 de Febrero", "Tijuana", "Pumas", "Visitante", "Error", R.drawable.chivas);
        Historial h3=new Historial("Jornada 8", "Sábado 27 de Febrero", "Necaxa", "Monterrey", "Empate", "Cancelado", R.drawable.chivas);
        Historial h4=new Historial("Jornada 8", "Domingo 28 de Febrero", "Toluca", "Santos", "", "NA", R.drawable.chivas);
        ArrayList historialArray = new ArrayList();
        historialArray.add(h1);
        historialArray.add(h2);
        historialArray.add(h3);
        historialArray.add(h4);*/



    }
}
