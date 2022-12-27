package com.dev_mobile.annoncetun;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;

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

public class Accueil extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
    Button button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);



        bottomNavigationView=findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);


        recview=findViewById(R.id.recview);
        recview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));



        FirebaseRecyclerOptions<Annonce> options =
                new FirebaseRecyclerOptions.Builder<Annonce>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("annoncetun"), Annonce.class)
                        .build();




        adapter=new myadapter(options,getApplicationContext());
        recview.setAdapter(adapter);


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
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        Intent r = new Intent(getApplicationContext(),Register.class);

        switch (item.getItemId()) {

                case R.id.ajouter:
                    if(user==null){
                        startActivity(r);
                    }else {
                        startActivity(i);
                        break;
                    }

            //case R.id.quitte: finish();
        }

        Intent s = new Intent(getApplicationContext(),Mesannonces.class);


        switch (item.getItemId()) {
            case R.id.mesannonces:
                if(user==null){
                    startActivity(r);
                }else {
                    startActivity(s);
                    break;
                }



        }


        Intent j = new Intent(getApplicationContext(),Map.class);


        switch (item.getItemId()) {
            case R.id.map: startActivity(j);
                break;


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





