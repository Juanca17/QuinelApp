package moviles2018itesm.quinelapp;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mButton;

    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

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
        setContentView(R.layout.activity_sign_up);

        this.mEmail = findViewById(R.id.email);
        this.mPassword = findViewById(R.id.pass);
        this.mButton= findViewById(R.id.signup);

        this.mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.pass);
    }

    public void signUp(View v){
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNUP", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            Map<String, User> toUpdate = new HashMap<>();
                            User newUser = new User();
                            newUser.name = user.getEmail();
                            newUser.id = user.getUid();
                            newUser.game = "0";
                            newUser.score = 0;
                            myRef.child(user.getUid()).setValue(newUser);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGNUP", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user){
        if (user != null){
            hideProgressDialog();
            //Intent intent = new Intent(this, Lobby.class);
            Intent intent = new Intent(this, Navigation.class);
            intent.putExtra("user", mEmail.getText().toString());
            startActivity(intent);
        } else {
            hideProgressDialog();
            Toast.makeText(SignUpActivity.this, "Nooooope",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
