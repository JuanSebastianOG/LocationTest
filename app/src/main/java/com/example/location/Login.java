package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth loginAuth;
    private Button btn_login;
    private TextView frm_email;
    private TextView frm_password;
    static FirebaseDatabase dbLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login= findViewById(R.id.btn_loginN);
        frm_email= findViewById(R.id.frm_email);
        frm_password= findViewById(R.id.frm_password);

        dbLoc = FirebaseDatabase.getInstance();
        loginAuth = FirebaseAuth.getInstance();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(frm_email.getText().toString(), frm_password.getText().toString());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = loginAuth.getCurrentUser();
       // updateUI(currentUser);
    }
    private void signInUser(String email, String password) {
        if (validateForm()) {
            loginAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI
                               // Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = loginAuth.getCurrentUser();
                                logInUser(user);
                            } else {
                                // If sign in fails, display a message to the user.
                               // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                logInUser(null);
                            }
                        }
                    });
        }
    }
    //Field Validation

    private boolean validateForm() {
        boolean valid = true;
        String email = frm_email.getText().toString();
        if(!isEmailValid(email)){
            frm_email.setText("");
            frm_email.setError("Required.");
            valid = false;
        }
        else {
            frm_email.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            frm_email.setError("Required.");
            valid = false;
        } else {
            frm_email.setError(null);
        }
        String password = frm_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            frm_password.setError("Required.");
            valid = false;
        } else {
            frm_password.setError(null);
            frm_password.setError(null);
        }
        return valid;
    }
    private boolean isEmailValid(String email) {
        if (!email.contains("@") ||
                !email.contains(".") ||
                email.length() < 5)
            return false;
        return true;
    }
    private void logInUser(FirebaseUser currentUser){
        if(currentUser!=null){

            FirebaseUser user = loginAuth.getCurrentUser();
            String uid= user.getUid();

            Query query = FirebaseDatabase.getInstance().getReference("userClient")
                    .orderByChild("userId").equalTo(uid);
            query.addListenerForSingleValueEvent(valueEventListener);

            Query ref = dbLoc.getReference("userClient").orderByChild("userId").equalTo(uid);

            // Attach a listener to read the data at our posts reference
           /* ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserClient user = dataSnapshot.getValue(UserClient.class);
                    if(user.getTipo().equals("Cliente")){
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }else{
                        Intent intent = new Intent(getBaseContext(), Walker.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });*/


        } else {
            frm_email.setText("");
            frm_password.setText("");
        }
    }
    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UserClient user = snapshot.getValue(UserClient.class);
                    if(user.getTipo().equals("Cliente")){
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }else{
                        Intent intent = new Intent(getBaseContext(), Walker.class);
                        startActivity(intent);
                    }
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
