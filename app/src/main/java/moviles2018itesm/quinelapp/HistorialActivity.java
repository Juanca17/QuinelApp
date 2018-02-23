package moviles2018itesm.quinelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        listView = findViewById(R.id.listView);

        Historial h1=new Historial("Jornada 8", "Sábado 27 de Febrero", "Tigres", "Atlas", "Local", "Acierto", R.drawable.chivas);
        Historial h2=new Historial("Jornada 8", "Sábado 27 de Febrero", "Tijuana", "Pumas", "Visitante", "Error", R.drawable.chivas);
        Historial h3=new Historial("Jornada 8", "Sábado 27 de Febrero", "Necaxa", "Monterrey", "Empate", "Cancelado", R.drawable.chivas);
        Historial h4=new Historial("Jornada 8", "Domingo 28 de Febrero", "Toluca", "Santos", "", "NA", R.drawable.chivas);
        ArrayList historialArray = new ArrayList();
        historialArray.add(h1);
        historialArray.add(h2);
        historialArray.add(h3);
        historialArray.add(h4);

        AdapterHistorial adapter = new AdapterHistorial(this, historialArray);

        listView.setAdapter(adapter);
    }
}
