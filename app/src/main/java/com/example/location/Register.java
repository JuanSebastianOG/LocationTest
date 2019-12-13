package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.IOException;
import java.util.List;

public class Register extends AppCompatActivity {
    RadioButton rClient,rPasea;
    Button btn_register;
    EditText email;
    EditText password;
    EditText frm_dirReg;

    String emailClient;
    String passwordClient;
    FirebaseAuth registerAuth;
    DatabaseReference dbClients;
    private Geocoder mGeocoder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        rClient=findViewById(R.id.rClient);
        rPasea=findViewById(R.id.rPasea);
        btn_register=findViewById(R.id.btn_register);
        registerAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.frm_emailReg);
        password=findViewById(R.id.frm_passwordReg);
        frm_dirReg=findViewById(R.id.frm_dirReg);

        mGeocoder = new Geocoder(this);


        FirebaseDatabase dbRats = FirebaseDatabase.getInstance();

        dbClients = dbRats.getReference("userClient");


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void register()
    {
        if(validateForm())
        {
            emailClient = email.getText().toString();
            passwordClient = password.getText().toString();
            registerAuth.createUserWithEmailAndPassword(emailClient, passwordClient)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = registerAuth.getCurrentUser();
                                final String userId = currentUser.getUid();
                                LatLng pos = encontrarLatLng(frm_dirReg.getText().toString());
                                if(pos != null){
                                    UserClient user;
                                    if(rClient.isChecked()){
                                        user = new UserClient(frm_dirReg.getText().toString(), pos.getLatitude(), pos.getLongitude(), userId, "Cliente");
                                        dbClients.child(userId).setValue(user);
                                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                        in.putExtra("type","client");
                                        startActivity(in);
                                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                    }else{
                                        user = new UserClient(frm_dirReg.getText().toString(), pos.getLatitude(), pos.getLongitude(), userId, "Paseador");
                                        dbClients.child(userId).setValue(user);
                                        Intent in = new Intent(getApplicationContext(), Walker.class);
                                        in.putExtra("type","client");
                                        startActivity(in);
                                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                    }

                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Registration Failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }
    private LatLng encontrarLatLng(String dir_value) {
        if(!dir_value.isEmpty()){
            try {
                List<Address> addresses = mGeocoder.getFromLocationName(dir_value, 2);
                Log.i("Posicion", "Obteniendo");
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    LatLng result = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                    Log.i("LatLng",result.toString());
                    return result;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    private boolean validateForm() {
        boolean valid = true;
        emailClient = email.getText().toString();
        passwordClient = password.getText().toString();

        if (TextUtils.isEmpty(emailClient)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(passwordClient)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        };
        if(!isEmailValid(emailClient)){
            email.setText("");
            email.setError("Wrong Email.");
            valid = false;
        }
        else {
            email.setError(null);
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

}
