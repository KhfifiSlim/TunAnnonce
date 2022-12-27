package com.dev_mobile.annoncetun;

import static java.lang.String.valueOf;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Maannonce extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener myDateSetListener;

    ImageView image,edit,delete,testimg;
    TextView date,nom,modele,prix,img,emailuser,cat;
    Double p2;
    DatabaseReference reference;
    String idann,titre,loc,p,decr,numero,dateaj,test,catg,favo;
    Uri filepath;
    Uri uriimg,uriimg2;
    Bitmap bitmap;
    TextView desc;
   Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maannonce);

        delete=(ImageView)findViewById(R.id.delete);
        edit=(ImageView)findViewById(R.id.edit);
        testimg=(ImageView)findViewById(R.id.img);
        test = getIntent().getStringExtra("image");
        uriimg= Uri.parse(getIntent().getStringExtra("image"));
        uriimg2= Uri.parse(getIntent().getStringExtra("image"));

        catg =getIntent().getStringExtra("cat");
        favo = getIntent().getStringExtra("favo");
        idann =getIntent().getStringExtra("idannonce");
        titre= getIntent().getStringExtra("marque");
        loc=getIntent().getStringExtra("modele");
        p=valueOf(getIntent().getDoubleExtra("prix",0.0));
        p2=getIntent().getDoubleExtra("prix",0.0);
        decr=getIntent().getStringExtra("desc");
        numero=getIntent().getStringExtra("numtel");
        dateaj=getIntent().getStringExtra("date");

        reference= FirebaseDatabase.getInstance().getReference("annoncetun");

        nom=findViewById(R.id.nom);
        nom.setText(titre);
        desc=findViewById(R.id.desc);
        desc.setText(decr);
        cat=findViewById(R.id.cat);
        cat.setText(getIntent().getStringExtra("cat"));
        //    num=findViewById(R.id.num);
        //  num.setText(getIntent().getStringExtra("numtel"));
        modele=findViewById(R.id.modele);
        modele.setText(loc);

        prix=findViewById(R.id.prix);
        prix.setText(p);

        date=findViewById(R.id.dater);
        date.setText(dateaj);

        //emailuser.setText(getIntent().getStringExtra("username"));

        image=findViewById(R.id.img);
        Picasso.get().load(getIntent().getStringExtra("image")).into(image);


        date = findViewById(R.id.dater);


        myDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date2 = dayOfMonth + "/" + month + "/" + year;
                date.setText(date2);
            }
        };



        edit.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {


                Intent j = new Intent(getApplicationContext(), ModiferAnnonce.class);
                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                j.putExtra("idann", idann);
                j.putExtra("titre", titre);
                j.putExtra("loc", loc);
                j.putExtra("prix",p2);
                j.putExtra("desc", decr);
                j.putExtra("numtel", numero);
                j.putExtra("date", dateaj);
                j.putExtra("image",test);
                j.putExtra("cat",catg);
                j.putExtra("favo",favo);
                startActivity(j);

                                           }
                                       }
        );

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(testimg.getContext());
                builder.setTitle("Supprimer annonce");
                builder.setMessage("Etes-vous sûr de supprimer cette annonce ?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("annoncetun");
                        root.child(idann).removeValue();

                        Toast.makeText(getApplicationContext(),"Annonce supprimée",Toast.LENGTH_SHORT).show();
                        Intent j = new Intent(getApplicationContext(),Mesannonces.class);
                        startActivity(j);
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}