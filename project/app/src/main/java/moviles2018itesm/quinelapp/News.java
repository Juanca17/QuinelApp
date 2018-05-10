package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class News extends AppCompatActivity {
    ListView newsList;
    newsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsList = (ListView)findViewById(R.id.newsList);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("news");

        final Activity activity = this;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String[]> items = new ArrayList<String[]>(); //INICIALIZAR LOS DATOS AQUI
                newsAdapter = new newsAdapter(items, activity);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewsObject news = snapshot.getValue(NewsObject.class);
                    String[] temp = {news.title, news.description, news.link};
                    items.add(temp);
                }
                newsList.setAdapter(newsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });
    }
}
