package moviles2018itesm.quinelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {
    ListView newsList;
    newsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsList = (ListView)findViewById(R.id.newsList);

        //AQUI CONSEGUIR LOS DATOS DE LA DB Y METERLOS AL ARAYLIST
        ArrayList<String[]> source= new ArrayList<String[]>();//PONER EL RESULTADO AQUI
        newsAdapter = new newsAdapter(source, this);
        newsList.setAdapter(newsAdapter);
    }
}
