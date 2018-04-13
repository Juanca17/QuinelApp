package moviles2018itesm.quinelapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userName;
    private LobbyUnactive lobbyUnactive;
    private LobbyActive lobbyActive;

    private String userLoged, userLobby;
    private FirebaseUser currentUser;
    public String league;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ENTRO AL CREATE", "entro");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TextView userName = (TextView)findViewById(R.id.userEmail);
        //userName.setText(getIntent().getStringExtra("user"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Lobby.class implementation
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        userLoged = intent.getStringExtra("user");

        DatabaseReference myRef = database.getReference("users");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    assert currentUser != null;
                    assert user != null;
                    if (Objects.equals(currentUser.getEmail(), user.name)){
                        if (user.game.equals("0")){
                            Navigation.this.userLobby = "None";
                        } else {
                            Navigation.this.userLobby = user.game;
                        }
                    }
                }
                //Setting the userName with the user loged
                //userName = (TextView)findViewById(R.id.userName);
                //userName.setText(userLoged);

                //Adding fragment from code
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                Log.w("LOBBY", userLobby + " ");
                if (userLobby == "None") {
                    lobbyUnactive = new LobbyUnactive();
                    ft.replace(R.id.content,lobbyUnactive);
                }else {
                    lobbyActive = new LobbyActive();
                    ft.replace(R.id.content,lobbyActive);
                }
                ft.commit();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOBBY", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_lobby) {
        } else if (id == R.id.nav_history) {
            if (league != null){
                Intent intent = new Intent(Navigation.this, HistorialActivity.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
                intent.putExtra("league", league);
                startActivityForResult(intent,3);
            }else{
                Toast.makeText(this,"You need to be in a lobby", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_news) {
            //ft.replace(R.id.content, new NewsFragment());
            //ft.commit();

            Intent intent = new Intent(Navigation.this, News.class);//PONER AQUI INCIALIZADOR DE ACTIVIDAD
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            try{
                Properties properties = new Properties();
                File file = new File(getFilesDir(), "properties.xml");
                if (file.exists()) {
                    FileInputStream fis = openFileInput("properties.xml");
                    properties.loadFromXML(fis);
                    fis.close();

                    Log.wtf("TAGGGGGGGGGGGGGGGG", "LOGGED OUT" + properties.getProperty("email"));
                    properties.put("email","");
                    properties.put("password", "");
                    FileOutputStream fos = openFileOutput("properties.xml", Context.MODE_PRIVATE);
                    properties.storeToXML(fos, null);
                    fos.close();
                    Log.wtf("LOGOUT", "FILE SAVED LOGGING OUT");
                    finish();
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // aquí escuchamos el regreso de una actividad
        if(requestCode == 0 && resultCode == Activity.RESULT_CANCELED){
            lobbyActive = new LobbyActive();
            ft.replace(R.id.content,lobbyActive);
        }
        if(requestCode == 1 && resultCode == Activity.RESULT_CANCELED){
            lobbyActive = new LobbyActive();
            ft.replace(R.id.content,lobbyActive);
        }
    }
}
