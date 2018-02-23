package moviles2018itesm.quinelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Create extends AppCompatActivity {
    private TextView lobbyName, leagueInput, participantsNumber;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Initialize elements
        lobbyName = (TextView)findViewById(R.id.lobbyName);
        leagueInput = (TextView)findViewById(R.id.leagueInput);
        participantsNumber = (TextView)findViewById(R.id.participansNumber);
        submit = (Button)findViewById(R.id.submit);
    }

    public void submitClick(View v){
        //Recovering text from textviews
        String lobbyNameString = lobbyName.getText().toString();
        String leagueInputString = leagueInput.getText().toString();
        String participantsNumberString = participantsNumber.getText().toString();

        //Creating new lobby with data recovered and putting the loged player in the new game lobby
        //DE ALGUNA FORMA CREAR UN NUEVO LOBBY CON LOS DATOS RECUPERADOS
        //_--------------------------
        Toast.makeText(this,"Lobby created, you have joined the new lobby", Toast.LENGTH_SHORT).show();
    }
}
