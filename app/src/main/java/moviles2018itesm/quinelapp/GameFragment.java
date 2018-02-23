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
import android.widget.TextView;

import javax.security.auth.callback.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private Callback callback;

    private TextView fecha, nombreLocal, nombreVisita;
    private ImageView equipoLocal, equipoVisita;

    private String fechaString;
    private String nlString;
    private String nvString;

    private int resLocal;
    private int resVisita;

    private Button[] btn = new Button[3];
    private int[] btn_id = {R.id.local, R.id.empate, R.id.visita};

    public GameFragment() {

    }

    public static GameFragment newInstance(String fechaString, String nlString, String nvString, int resLocal, int resVisita) {
        Bundle bundle = new Bundle();
        bundle.putString("fechaString", fechaString);
        bundle.putString("nlString", nlString);
        bundle.putString("nvString", nvString);
        bundle.putInt("resLocal", resLocal);
        bundle.putInt("resVisita", resVisita);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(bundle);

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

        nombreLocal = gameView.findViewById(R.id.nombreLocal);
        nombreLocal.setText(nlString);

        nombreVisita = gameView.findViewById(R.id.nombreVisita);
        nombreVisita.setText(nvString);

        equipoLocal = gameView.findViewById(R.id.equipoLocal);
        equipoLocal.setBackgroundResource(resLocal);

        equipoVisita = gameView.findViewById(R.id.equipoVisita);
        equipoVisita.setBackgroundResource(resVisita);

        for(int i = 0; i < btn.length; i++){
            btn[i] = gameView.findViewById(btn_id[i]);
        }

        return gameView;
    }

    public void addCallbackListener(Callback c) {
        callback = c;
    }


    public interface Callback {
    }
}
