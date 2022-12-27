package com.dev_mobile.annoncetun;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter2 extends FirebaseRecyclerAdapter<Annonce,myadapter2.myviewholder>
{
    Context context;

    public myadapter2(@NonNull FirebaseRecyclerOptions<Annonce> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull Annonce annonce)
    {



        holder.nom.setText(annonce.getMarque());
        holder.localisation.setText(annonce.getLocalisation());
        // holder.cat.setText(annonce.getCategorie());
        holder.prix.setText(annonce.getPrix().toString());
        holder.miseencir.setText(annonce.getMiseEnCirculation());

        Glide.with(holder.img.getContext()).load(annonce.getPimage()).into(holder.img);


/*
        holder.edit.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {


                Intent j = new Intent(context, ModiferAnnonce.class);
                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                j.putExtra("id", annonce.getId());
                j.putExtra("marque", annonce.getMarque());
               j.putExtra("modele", annonce.getModele());
                j.putExtra("prix", annonce.getPrix());
                j.putExtra("date", annonce.getMiseEnCirculation());
                j.putExtra("image", annonce.getPimage());

                context.startActivity(j);

            }
        }
        );

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Supprimer annonce");
                builder.setMessage("Etes-vous sûr de supprimer cette annonce ?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("annoncetun")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
*/

        holder.img.setOnClickListener(new View.OnClickListener() {


                                           @Override
                                           public void onClick(View view) {


                                               Intent k = new Intent(context, Maannonce.class);
                                               k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                               k.putExtra("idannonce", annonce.getId());
                                               k.putExtra("marque", annonce.getMarque());
                                               k.putExtra("modele", annonce.getLocalisation());
                                               k.putExtra("prix", annonce.getPrix());
                                               k.putExtra("username",annonce.getUser());
                                               k.putExtra("desc",annonce.getDesc());
                                               k.putExtra("cat",annonce.getCategorie());
                                               k.putExtra("numtel",annonce.getNumtel());
                                               k.putExtra("date", annonce.getMiseEnCirculation());
                                               k.putExtra("image", annonce.getPimage());
                                               k.putExtra("favo", annonce.getFavoris());
                                               context.startActivity(k);

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
        ImageView img;
        ImageView edit,delete,lire;
        TextView nom,prix,miseencir,localisation,date,cat;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            // user=(TextView)itemView.findViewById(R.id.user);
            img=(ImageView)itemView.findViewById(R.id.img1);
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