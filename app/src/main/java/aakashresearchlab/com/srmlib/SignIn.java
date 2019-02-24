package aakashresearchlab.com.srmlib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by harshit on 02-12-2017.
 *
 * @author Harshit Gupta
 * @since 2nd Dec 2017
 */

public class SignIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    private ProgressDialog progressdialog;
    private static final int RC_SIGN_IN = 61;
    private FirebaseAuth mAuth;
    SignInButton button_google;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardView button_sign_in;
    Context context;
    private TextView text_username,text_password,text_orsignup,text_forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {//need to update
                    finish();
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                }
                if(!(SignIn.this instanceof SignIn)){
                    // if i sign out in an auth activity
                    // i want to trigger this to go back to the SignIn Activity
                    Intent mIntent = new Intent(getApplicationContext(), SignIn.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
                    finish();
                    return;
                }
            }
        };
        setContentView(R.layout.activity_sigin);

        progressdialog = new ProgressDialog(SignIn.this);

        mAuth = FirebaseAuth.getInstance();
        button_sign_in = findViewById(R.id.bsignin); // card view
        text_password = findViewById(R.id.field_password);
        text_username = findViewById(R.id.field_username);
        text_orsignup = findViewById(R.id.orsignup);
        text_forgot_password = findViewById(R.id.forgotpass);

        // signing in using a Google Account
//        button_google = findViewById(R.id.gsign);
//        button_google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(SignIn.this).enableAutoManage(SignIn.this, new GoogleApiClient.OnConnectionFailedListener() {
//            @Override
//            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                Toast.makeText(SignIn.this, "Can't Connect", Toast.LENGTH_SHORT).show();
//            }
//        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();

        button_sign_in.setOnClickListener(this);
        text_orsignup.setOnClickListener(this);
        text_forgot_password.setOnClickListener(this);

    }

    private void sign_in_simple() {
        String email,password;

        progressdialog.setMessage("Signing In...");
        progressdialog.show();

        email = text_username.getText().toString().trim();
        password = text_password.getText().toString().trim();

        if(!email.endsWith("@srmuniv.edu.in")){
            //if the email address is not an SRM email address
            progressdialog.dismiss();
            Toast.makeText(SignIn.this, "enter SRM email address", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
            //if the user leaves any empty fields
            progressdialog.dismiss();
            Toast.makeText(SignIn.this, "Empty Fields", Toast.LENGTH_SHORT).show();

        } else { // good input
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressdialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent i = new Intent(SignIn.this, MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Authentication Failed! ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressdialog.setMessage("Please Wait...");
        progressdialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(this, "Sigin In Failed", Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        }
    }

    // Authenticates Google SignIn
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        progressdialog.setMessage("Please Wait...");
        progressdialog.show();

        final Intent intent = new Intent(SignIn.this, MainActivity.class);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finish();
                        // launch MainActivity as authentication was successful
                        startActivity(intent);

                        if (!task.isSuccessful()) {

                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        progressdialog.dismiss();
                    }
                });
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        progressdialog.dismiss();
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == button_sign_in){
            sign_in_simple();

        } else if(v == text_orsignup){
            Intent i=new Intent(SignIn.this,SignUp.class);
            finish();
            startActivity(i);

        } else if(v == text_forgot_password){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Enter your email");

            final EditText edit_text_mailId=new EditText(this);

            builder.setView(edit_text_mailId);
            builder.setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String mailId=edit_text_mailId.getText().toString().trim();
                    mAuth.sendPasswordResetEmail(mailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(SignIn.this, "Email Sent...", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(SignIn.this, "An error occurred...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            builder.show();
        }
    }

    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        exitingapp();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();



    }
    public void exitingapp(){
        ((Activity) context).finish();

    }


}

