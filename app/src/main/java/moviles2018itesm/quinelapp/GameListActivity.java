package moviles2018itesm.quinelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameListActivity extends AppCompatActivity {

    private TextView selecciona, jornada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        selecciona = findViewById(R.id.textView);
        jornada = findViewById(R.id.textView2);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment f1 = GameFragment.newInstance("Viernes 23 de febrero 21:00", "Puebla", "Necaxa", R.drawable.chivas, R.drawable.cruzazul);
        ft.add(R.id.fragment, f1);

        Fragment f2 = GameFragment.newInstance("Viernes 23 de febrero 21:00", "Atlas", "Monterrey", R.drawable.atlas, R.drawable.monterrey);
        ft.add(R.id.fragment2, f2);

        Fragment f3 = GameFragment.newInstance("Sábado 24 de febrero 17:00", "Lobos Buap", "Veracruz", R.drawable.lobos, R.drawable.veracruz);
        ft.add(R.id.fragment3, f3);

        Fragment f4 = GameFragment.newInstance("Sábado 24 de febrero 17:00", "Querétaro", "Toluca", R.drawable.queretaro, R.drawable.toluca);
        ft.add(R.id.fragment4, f4);

        Fragment f5 = GameFragment.newInstance("Sábado 24 de febrero 19:00", "Tigres", "Monarcas", R.drawable.tigres, R.drawable.monarcas);
        ft.add(R.id.fragment5, f5);

        Fragment f6 = GameFragment.newInstance("Sábado 24 de febrero 19:06", "Pachuca", "León", R.drawable.pachuca, R.drawable.leon);
        ft.add(R.id.fragment6, f6);

        Fragment f7 = GameFragment.newInstance("Sábado 24 de febrero 21:00", "América", "Xolos Tijuana", R.drawable.america, R.drawable.tijuana);
        ft.add(R.id.fragment7, f7);

        Fragment f8 = GameFragment.newInstance("Domingo 25 de febrero 12:00", "Pumas", "Chivas", R.drawable.pumas, R.drawable.chivas);
        ft.add(R.id.fragment8, f8);

        Fragment f9 = GameFragment.newInstance("Domingo 25 de febrero 18:00", "Santos", "Cruz Azul", R.drawable.santos, R.drawable.cruzazul);
        ft.add(R.id.fragment9, f9);

        ft.commit();
    }
}
