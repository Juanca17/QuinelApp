package moviles2018itesm.quinelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Join extends AppCompatActivity {
    private TextView lobbyIDInput;
    private Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //Initializing components from layout
        lobbyIDInput = (TextView)findViewById(R.id.lobbyIDInput);
        join = (Button) findViewById(R.id.join);
    }

    public void joinClick(View v){
        String user = getIntent().getStringExtra("user");
        String lobbyIDInputString = lobbyIDInput.getText().toString();

        //CON ESTE STRING BUSCAR EN LA BASE DE DATOS UN JUEGO
        boolean gameFound = false; //EN LUGAR DE FALSE REGRESAR LA RESPUESTA DE LA BUSQUEDA
        if (gameFound){
            //INSERTAR AL JUGADOR EN EL LOBBY ENCONTRADO
            //-------------------PROCEDIMIENTO AQUI
            Toast.makeText(this, "You have joined the lobby "+lobbyIDInputString, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Lobby ID not found, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
