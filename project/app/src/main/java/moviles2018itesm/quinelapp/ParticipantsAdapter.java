package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JOSECARLOS on 22/02/2018.
 */

public class ParticipantsAdapter extends BaseAdapter {
    private ArrayList<String[]> source;
    private Activity activity;

    public ParticipantsAdapter(ArrayList<String[]> source, Activity activity){
        this.source = source;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int i) {
        return source.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.participant,null);
        }
        //COMO CONSEGUIR LOS OBJETOS
        String[] currentItem = (String[])getItem(i);

        TextView name = (TextView)view.findViewById(R.id.participantName);
        TextView score = (TextView)view.findViewById(R.id.participantScore);

        name.setText(currentItem[0]); //AQUI METER EL VALOR DEL NOMBRE
        score.setText(currentItem[1]); //AQUI METER EL VALOR DEL SCORE

        return view;
    }
}

