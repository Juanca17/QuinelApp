package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdapterHistorial extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Historial> items;

    public AdapterHistorial(Activity activity, ArrayList<Historial> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Historial> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_historial, null);
        }

        Historial dir = items.get(position);

        TextView jornada = (TextView) v.findViewById(R.id.jornada);
        jornada.setText(dir.getJornada());

        TextView fecha = (TextView) v.findViewById(R.id.fecha);
        fecha.setText(dir.getFecha());

        TextView local = (TextView) v.findViewById(R.id.local);
        local.setText(dir.getLocal());

        TextView visita = (TextView) v.findViewById(R.id.visita);
        visita.setText(dir.getVisita());

        TextView voto = (TextView) v.findViewById(R.id.voto);
        voto.setText(dir.getVoto());

        TextView resultado = (TextView) v.findViewById(R.id.resultado);
        resultado.setText(dir.getResultado());
        if (dir.getResultado() == "Acierto") {
            resultado.setBackgroundColor(Color.parseColor("#ff99cc00"));
        }
        else if (dir.getResultado() == "Error") {
            resultado.setBackgroundColor(Color.parseColor("#ffff4444"));
        }

        ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
        imagen.setBackgroundResource(dir.getImagen());

        return v;
    }
}
