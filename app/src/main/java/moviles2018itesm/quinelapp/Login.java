package moviles2018itesm.quinelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText usernameField;
    private EditText passwordField;

    private Button login;

    private ProgressDialog mProgressDialog;

    Properties properties;
    public static final String PROPERTIES_FILE = "properties.xml";

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        login = (Button) findViewById(R.id.button);

        //Acceder a properties
        try{
            properties = new Properties();
            File file = new File(getFilesDir(), PROPERTIES_FILE);
            if (file.exists()){
                FileInputStream fis = openFileInput(PROPERTIES_FILE);
                properties.loadFromXML(fis);
                fis.close();
                //HACER EL LOGIN FORZADO AQUI
                //-----------------------------
                //-----------------------------
                Toast.makeText(
                        this,
                        "USER LOADED: " + properties.getProperty("email"),
                        Toast.LENGTH_SHORT
                ).show();
                usernameField.setText(properties.getProperty("email"));
                passwordField.setText(properties.getProperty("password"));
                //Lo mismo que en el login
                showProgressDialog();
                mAuth.signInWithEmailAndPassword(usernameField.getText().toString(), passwordField.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    updateUI(null);
                                }
                            }
                        });

            }else{
                saveProperties();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void signIn(View v){
        Log.d(TAG, usernameField.getText().toString() + " " + passwordField.getText().toString());
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(usernameField.getText().toString(), passwordField.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Meter el usuario
                            properties.put("email",usernameField.getText().toString());
                            properties.put("password", passwordField.getText().toString());
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        if (user != null){
            hideProgressDialog();
            //Intent intent = new Intent(this, Lobby.class);
            Intent intent = new Intent(this, Navigation.class);
            intent.putExtra("user", usernameField.getText().toString());
            startActivity(intent);
        } else {
            hideProgressDialog();
            Toast.makeText(Login.this, "Username or password not found.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProperties() throws IOException{
        FileOutputStream fos = openFileOutput(PROPERTIES_FILE, Context.MODE_PRIVATE);
        properties.storeToXML(fos, null);
        fos.close();
        Toast.makeText(this, "FILE SAVED", Toast.LENGTH_SHORT).show();
    }

}
