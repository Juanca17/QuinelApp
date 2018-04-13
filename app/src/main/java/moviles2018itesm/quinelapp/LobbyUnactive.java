package moviles2018itesm.quinelapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyUnactive extends Fragment {

    Button create, join;
    public LobbyUnactive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_lobby_unactive, container, false);
        create = (Button)v.findViewById(R.id.create);
        join = (Button)v.findViewById(R.id.join);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AUN FALTA REFERENCIAR A LA SIGUIENTE ACTIVIDAD
                Intent intent = new Intent(LobbyUnactive.this.getContext(), Create.class);//CLASE DE ACTIVIDAD DE CREAR AQUI)
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AUN FALTA REFERENCIAR A LA SIGUIENTE ACTIVIDAD
                Intent intent = new Intent(LobbyUnactive.this.getContext(), Join.class);
                startActivity(intent);
            }
        });

        //Activity commuication
        Navigation nav = (Navigation) getActivity();
        nav.league = null;

        return v;
    }

}
