package aakashresearchlab.com.srmlib;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by harshit on 02-12-2017.
 *
 * @author Harshit Gupta
 * @since 2nd Dec 2017
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardView button_sign_up;
    private ProgressDialog pdLoading;
    private TextView text_username, text_password, text_login, text_forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Connect to Firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent i = new Intent(SignUp.this, MainActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        };

        // Prevent user from interacting with the application while processing data
        pdLoading = new ProgressDialog(this);
        pdLoading.setMessage("Signing In...");

        button_sign_up = findViewById(R.id.bsignup); // card view
        text_password = findViewById(R.id.sfield_password);
        text_username = findViewById(R.id.sfield_username);
        text_login = findViewById(R.id.text_login);
        text_forgot_password = findViewById(R.id.sfield_forgpass);

        button_sign_up.setOnClickListener(this);
        text_forgot_password.setOnClickListener(this);
        text_login.setOnClickListener(this);
    }

    // the below function will be called when button_sign_up will be clicked
    private void signUp() {
        pdLoading.show();
        final String email, password;
        email = text_username.getText().toString().trim();
        password = text_password.getText().toString().trim();

        // if the entered email is not an SRM email
        if(!email.endsWith("@srmuniv.edu.in")){
            Toast.makeText(SignUp.this, "Enter an SRM email id", Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();

        } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // if the user leaves an empty field

            Toast.makeText(SignUp.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();

        } else { // good input

            // create a new user in the database
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) { // new account is successfully created
                        Toast.makeText(SignUp.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        pdLoading.dismiss();
                        finish();

                        // launch MainActivity once the new user account has been created
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        pdLoading.dismiss();
                        Toast.makeText(SignUp.this, "An error occurred..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

    @Override
    public void onClick(View v) {
        if (v == button_sign_up) { // if button_sign_up is clicked
            signUp();
        } else if (v == text_forgot_password) { // if text_forgot_password is clicked
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter your email");
            final EditText edit_text_mailId = new EditText(this);
            builder.setView(edit_text_mailId);
            builder.setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String mailId = edit_text_mailId.getText().toString().trim();
                    mAuth.sendPasswordResetEmail(mailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Email Sent...", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUp.this, "An error occurred...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            builder.show();
        } else if (v == text_login) { // if the user wishes to login instead
            // Launch MainActivity
            Intent i = new Intent(SignUp.this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }

    public void filterSwitchOnClick(View view){
        Toast.makeText(this, "aya", Toast.LENGTH_SHORT).show();
    }
}
