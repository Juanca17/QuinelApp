package moviles2018itesm.quinelapp;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText eq1, eq2;

    private ImageView equipoLocal, equipoVisita;

    private String fechaString, nlString, nvString, eq1String, eq2String;

    private int s = 0;

    private int resLocal;
    private int resVisita;

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

        eq1 = gameView.findViewById(R.id.eq1);
        eq2 = gameView.findViewById(R.id.eq2);

        final DatabaseReference parentRef = equ1.getParent();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").child(user.getUid()).exists()){
                    Log.d("GAME", "onDataChange: Exists");
                    eq1.setText(dataSnapshot.child("users").child(user.getUid()).child("equipo1").getValue().toString());
                    eq2.setText(dataSnapshot.child("users").child(user.getUid()).child("equipo2").getValue().toString());
                } else {
                    Score score = new Score(Integer.parseInt(eq1.getText().toString()), Integer.parseInt(eq2.getText().toString()));
                    parentRef.child("users").child(user.getUid()).setValue(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eq2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (eq2.getText().toString().equals("")){
                    eq2.setText("0");
                }

            }
        });

        eq2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    Score score = new Score(Integer.parseInt(eq1.getText().toString()), Integer.parseInt(eq2.getText().toString()));
                    parentRef.child("users").child(user.getUid()).setValue(score);
                }
            }
        });

        eq1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (eq1.getText().toString().equals("")){
                    eq1.setText("0");
                }

            }
        });

        eq1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //eq1.setText(charSequence.subSequence(1, i2));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")) {
                    Score score = new Score(Integer.parseInt(eq1.getText().toString()), Integer.parseInt(eq2.getText().toString()));
                    parentRef.child("users").child(user.getUid()).setValue(score);
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
