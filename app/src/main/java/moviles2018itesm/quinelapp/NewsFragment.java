package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import moviles2018itesm.quinelapp.R;

/**
 * Created by JOSECARLOS on 12/04/2018.
 */

public class NewsFragment extends Fragment {
    View myView;
    ListView newsList;
    newsAdapter newsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        newsList = (ListView)getActivity().findViewById(R.id.newsList);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("news");

        final Activity activity = getActivity();

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


        myView = inflater.inflate(R.layout.activity_news, container, false);
        return myView;
    }
}
