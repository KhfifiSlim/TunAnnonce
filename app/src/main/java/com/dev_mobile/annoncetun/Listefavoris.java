package com.dev_mobile.annoncetun;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Listefavoris extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
    Button button;
    String userlogged;
    String email;
    String fav = "fav";
    Integer index;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        bottomNavigationView=findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        if (user != null) {
            email = user.getEmail();

            for (int i = 0; i < email.length(); i++) {
                index = email.indexOf("@");
            }

            userlogged = email.substring(0, index);

            recview=findViewById(R.id.recview);
            recview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));



            FirebaseRecyclerOptions<Annonce> options =
                    new FirebaseRecyclerOptions.Builder<Annonce>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("annoncetun").orderByChild("favoris").equalTo(fav.concat(userlogged)), Annonce.class)
                            .build();




            adapter=new myadapter(options,getApplicationContext());
            recview.setAdapter(adapter);
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
    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (user != null) {
        adapter.stopListening();}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Intent i = new Intent(getApplicationContext(),MainActivity.class);


        switch (item.getItemId()) {
            case R.id.ajouter: startActivity(i);
                break;


            //case R.id.quitte: finish();
        }

        Intent j = new Intent(getApplicationContext(),Map.class);


        switch (item.getItemId()) {
            case R.id.map: startActivity(j);
                break;


            //case R.id.quitte: finish();
        }

        Intent k = new Intent(getApplicationContext(),login.class);


        switch (item.getItemId()) {

            case R.id.quite: startActivity(k);
                FirebaseAuth.getInstance().signOut();
                break;


            //case R.id.quitte: finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Annonce> options =
                new FirebaseRecyclerOptions.Builder<Annonce>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("annoncetun").orderByChild("marque").startAt(s).endAt(s+"\uf8ff"), Annonce.class)
                        .build();

        adapter=new myadapter(options,getApplicationContext());
        adapter.startListening();
        recview.setAdapter(adapter);

    }

}





