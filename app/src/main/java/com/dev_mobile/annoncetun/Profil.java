package com.dev_mobile.annoncetun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email,username;
    TextView emailuser,emailus;
    Integer index;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        bottomNavigationView=findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        if (user != null) {
            emailuser = findViewById(R.id.username);
            emailus = findViewById(R.id.email);
            email = user.getEmail();

            for (int i = 0; i < email.length(); i++) {
                index = email.indexOf("@");
            }
            username = email.substring(0, index);
            emailuser.setText(username);
            emailus.setText(user.getEmail());
        }else{
            Toast.makeText(getApplicationContext(),"Vous n'avez pas de compte !",Toast.LENGTH_LONG).show();
            Intent r = new Intent(getApplicationContext(),Register.class);
            startActivity(r);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                    Intent w = new Intent(getApplicationContext(),login.class);
                    switch (menuitem.getItemId()){
                        case R.id.login:startActivity(w);
                    }
                    Intent p = new Intent(getApplicationContext(),Accueil.class);
                    switch (menuitem.getItemId()){
                        case R.id.home:startActivity(p);
                    }
                    Intent o = new Intent(getApplicationContext(),Profil.class);
                    switch (menuitem.getItemId()){
                        case R.id.profil:startActivity(o);
                    }
                    Intent f = new Intent(getApplicationContext(),Listefavoris.class);
                    switch (menuitem.getItemId()){
                        case R.id.favoris:startActivity(f);
                    }
                    return false;
                }
            };

}