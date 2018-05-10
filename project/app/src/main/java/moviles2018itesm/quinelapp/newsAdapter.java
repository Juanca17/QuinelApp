package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JOSECARLOS on 23/02/2018.
 */

public class newsAdapter extends BaseAdapter {
    ArrayList<String[]> source;
    Activity activity;

    public newsAdapter(ArrayList<String[]> source,Activity activity){
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
            view = activity.getLayoutInflater().inflate(R.layout.news_view,null);
        }

        String[] currentItem = (String[])getItem(i);

        TextView title = (TextView)view.findViewById(R.id.feedTitle);
        TextView description = (TextView)view.findViewById(R.id.feedDescription);
        TextView link = (TextView)view.findViewById(R.id.feedLink);

        title.setText(currentItem[0]);
        description.setText(currentItem[1]);
        link.setText(currentItem[2]);

        return view;
    }
}
