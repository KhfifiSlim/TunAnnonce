package com.dev_mobile.annoncetun;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<Annonce,myadapter.myviewholder>
{
    Context context;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String ch="fav";
    String ch2="nonfav";
    String email,username,fav,favtest;

    Integer index;
    public myadapter(@NonNull FirebaseRecyclerOptions<Annonce> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull Annonce annonce)
    {

            if (user != null) {
            email = user.getEmail();

            for (int i = 0; i < email.length(); i++) {
                index = email.indexOf("@");
            }
            username = email.substring(0, index);

        favtest = annonce.getFavoris();
        if(favtest.equals("nonfav")){
            holder.fav.setColorFilter(context.getResources().getColor(R.color.white));
        }else if(favtest.equals(ch.concat(username))){
            holder.fav.setColorFilter(context.getResources().getColor(R.color.red));
        }}

        holder.nom.setText(annonce.getMarque());
        holder.localisation.setText(annonce.getLocalisation());
       // holder.cat.setText(annonce.getCategorie());
        holder.prix.setText(annonce.getPrix().toString());
        holder.miseencir.setText(annonce.getMiseEnCirculation());



        Glide.with(holder.img.getContext()).load(annonce.getPimage()).into(holder.img);




        holder.img.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                Intent k = new Intent(context, TouteAnnonce.class);
                k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                k.putExtra("id", annonce.getId());
                k.putExtra("marque", annonce.getMarque());
                k.putExtra("modele", annonce.getLocalisation());
                k.putExtra("prix", annonce.getPrix());
                k.putExtra("username",annonce.getUser());
                k.putExtra("desc",annonce.getDesc());
                k.putExtra("cat",annonce.getCategorie());
                k.putExtra("numtel",annonce.getNumtel());
                k.putExtra("date", annonce.getMiseEnCirculation());
                k.putExtra("image", annonce.getPimage());

                context.startActivity(k);

                                           }
                                       }
        );


        holder.fav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (user != null) {
                    email = user.getEmail();

                    for( int i = 0; i<email.length(); i++) {
                        index = email.indexOf("@");
                    }
                    username = email.substring(0,index);
                    if (holder.fav.getColorFilter() != null) {
                        holder.fav.setColorFilter(context.getResources().getColor(R.color.white));
                        holder.fav.setColorFilter(null);
                        fav=ch2;
                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("annoncetun");
                        Annonce a = new Annonce(annonce.getId(),annonce.getMarque(),annonce.getLocalisation(), annonce.getPrix(),annonce.getMiseEnCirculation(),annonce.getPimage(),annonce.getUser(),annonce.getDesc(),annonce.getNumtel(),annonce.getCategorie(),fav);
                        root.child(annonce.getId()).setValue(a);
                    } else if (holder.fav.getColorFilter() == null) {
                        holder.fav.setColorFilter(context.getResources().getColor(R.color.red));
                        //   Intent f = new Intent(context,Listefavoris.class);
                        //  context.startActivity(f);
                        fav=ch.concat(username);
                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("annoncetun");
                        Annonce a = new Annonce(annonce.getId(),annonce.getMarque(),annonce.getLocalisation(), annonce.getPrix(),annonce.getMiseEnCirculation(),annonce.getPimage(),annonce.getUser(),annonce.getDesc(),annonce.getNumtel(),annonce.getCategorie(),fav);
                        root.child(annonce.getId()).setValue(a);


                    }

                }else{
                    Toast.makeText(context,"Vous n'avez pas de compte !",Toast.LENGTH_LONG).show();
                }



            }
        }
        );


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);

    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView calendar;
        ImageView img,fav;
        ImageView edit,delete,lire;
        TextView nom,prix,miseencir,localisation,date,cat;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
           // user=(TextView)itemView.findViewById(R.id.user);
            img=(ImageView)itemView.findViewById(R.id.img1);
            fav=(ImageView)itemView.findViewById(R.id.fav);
            nom=(TextView)itemView.findViewById(R.id.nom);
            localisation=(TextView)itemView.findViewById(R.id.loc);
        //    cat=(TextView)itemView.findViewById(R.id.cat);
            prix=(TextView)itemView.findViewById(R.id.prix);
            miseencir=(TextView)itemView.findViewById(R.id.miseencir);

            //edit=(ImageView)itemView.findViewById(R.id.editicon);
            //delete=(ImageView)itemView.findViewById(R.id.deleteicon);
            calendar=(ImageView)itemView.findViewById(R.id.calendar);
            date = (TextView)itemView.findViewById(R.id.dater);
           // lire=(ImageView)itemView.findViewById(R.id.lireicon);



        }
    }
}