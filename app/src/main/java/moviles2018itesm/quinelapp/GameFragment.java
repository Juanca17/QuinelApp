package moviles2018itesm.quinelapp;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private Callback callback;
    public DatabaseReference equ1, equ2, t;

    private TextView fecha, nombreLocal, nombreVisita;
    private ImageView equipoLocal, equipoVisita;
    private RadioButton local, empate, visita;
    private RadioGroup rGroup;

    private String fechaString;
    private String nlString;
    private String nvString;

    private int s = 0;

    private int resLocal;
    private int resVisita;

    private Button[] btn = new Button[3];
    private int[] btn_id = {R.id.local, R.id.empate, R.id.visita};

    public GameFragment() {

    }

    public static GameFragment newInstance(String fechaString, String nlString, String nvString, int resLocal, int resVisita,  DatabaseReference equipo1, DatabaseReference equipo2, DatabaseReference tie) {
        Bundle bundle = new Bundle();
        bundle.putString("fechaString", fechaString);
        bundle.putString("nlString", nlString);
        bundle.putString("nvString", nvString);
        bundle.putInt("resLocal", resLocal);
        bundle.putInt("resVisita", resVisita);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(bundle);
        fragment.equ1 = equipo1;
        fragment.equ2 = equipo2;
        fragment.t = tie;

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            fechaString = bundle.getString("fechaString");
            nlString = bundle.getString("nlString");
            nvString = bundle.getString("nvString");
            resLocal = bundle.getInt("resLocal");
            resVisita = bundle.getInt("resVisita");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View gameView = inflater.inflate(R.layout.fragment_game, container, false);

        readBundle(getArguments());

        fecha = gameView.findViewById(R.id.fecha);
        fecha.setText(fechaString);
        Log.w("FragGame", equ1.getKey());
        Log.w("FragGame", equ2.getKey());

        nombreLocal = gameView.findViewById(R.id.nombreLocal);
        nombreLocal.setText(nlString);

        nombreVisita = gameView.findViewById(R.id.nombreVisita);
        nombreVisita.setText(nvString);

        equipoLocal = gameView.findViewById(R.id.equipoLocal);
        equipoLocal.setBackgroundResource(resLocal);

        equipoVisita = gameView.findViewById(R.id.equipoVisita);
        equipoVisita.setBackgroundResource(resVisita);

        local = gameView.findViewById(R.id.local);
        empate = gameView.findViewById(R.id.empate);
        visita = gameView.findViewById(R.id.visita);

        for(int i = 0; i < btn.length; i++){
            btn[i] = gameView.findViewById(btn_id[i]);
        }

        rGroup = gameView.findViewById(R.id.votacion);
        RadioButton checkedRadioButton = rGroup.findViewById(rGroup.getCheckedRadioButtonId());

        equ1.getParent().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (dataSnapshot.child("team1").child("users").child(currentUser.getUid()).getValue() != null){
                    local.setChecked(true);
                } else if (dataSnapshot.child("team2").child("users").child(currentUser.getUid()).getValue() != null){
                    visita.setChecked(true);
                } else if (dataSnapshot.child("tie").child(currentUser.getUid()).getValue() != null){
                    empate.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameList", "Failed to read value.", error.toException());
            }
        });

        /*if (this.s == 0){
            local.setChecked(true);
        } else if (this.s == 1){
            visita.setChecked(true);
        } else {
            empate.setChecked(true);
        }*/

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (checkedRadioButton.getText().toString().equals("Visitante")){
                        //User user = new User();
                        //user.name = currentUser.getEmail();
                        Map<String, String> user= new HashMap<>();
                        user.put(currentUser.getProviderId(), currentUser.getEmail());
                        equ2.child("users").child(currentUser.getUid()).setValue(currentUser.getEmail());
                        equ1.child("users").child(currentUser.getUid()).removeValue();
                        t.child(currentUser.getUid()).removeValue();

                    } else if (checkedRadioButton.getText().toString().equals("Local")){
                        Map<String, String> user= new HashMap<>();
                        user.put(currentUser.getUid(), currentUser.getEmail());
                        equ1.child("users").child(currentUser.getUid()).setValue(currentUser.getEmail());
                        equ2.child("users").child(currentUser.getUid()).removeValue();
                        t.child(currentUser.getUid()).removeValue();

                    } else if (checkedRadioButton.getText().toString().equals("Empate")){
                        Map<String, String> user= new HashMap<>();
                        user.put(currentUser.getUid(), currentUser.getEmail());
                        t.child(currentUser.getUid()).setValue(currentUser.getEmail());
                        equ2.child("users").child(currentUser.getUid()).removeValue();
                        equ1.child("users").child(currentUser.getUid()).removeValue();

                    } else {
                        Log.w("FragGame", checkedRadioButton.getText().toString() + " Algo.");
                    }
                    Log.w("FragGame", checkedRadioButton.getText().toString());
                }
            }
        });


        return gameView;
    }

    public void addCallbackListener(Callback c) {
        callback = c;
    }


    public interface Callback {
    }
}
