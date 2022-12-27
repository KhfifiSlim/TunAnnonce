package com.dev_mobile.annoncetun;

import static java.lang.String.valueOf;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class TouteAnnonce extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener myDateSetListener;

    ImageView image,call;
    TextView date,nom,modele,prix,img,emailuser,cat;
    Button submit;
    DatabaseReference reference;
    String id,favo;
    Uri filepath;
    Uri uriimg,uriimg2;
    Bitmap bitmap;
    TextView desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toute_annonce);


        call=findViewById(R.id.call);
        emailuser = findViewById(R.id.usemail);
        uriimg= Uri.parse(getIntent().getStringExtra("image"));
        uriimg2= Uri.parse(getIntent().getStringExtra("image"));


        id =getIntent().getStringExtra("id");

        reference= FirebaseDatabase.getInstance().getReference("annoncetun");

        nom=findViewById(R.id.nom);
        nom.setText(getIntent().getStringExtra("marque"));
        desc=findViewById(R.id.desc);
        desc.setText(getIntent().getStringExtra("desc"));
        cat=findViewById(R.id.cat);
        cat.setText(getIntent().getStringExtra("cat"));
    //    num=findViewById(R.id.num);
      //  num.setText(getIntent().getStringExtra("numtel"));
        modele=findViewById(R.id.modele);
        modele.setText(getIntent().getStringExtra("modele"));

        prix=findViewById(R.id.prix);
        prix.setText(valueOf(getIntent().getDoubleExtra("prix",0.0)));

        date=findViewById(R.id.dater);
        date.setText(getIntent().getStringExtra("date"));

        emailuser.setText(getIntent().getStringExtra("username"));

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



        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String ch ="tel:";
                String numtel = ch.concat(getIntent().getStringExtra("numtel"));
                intent.setData(Uri.parse(numtel));
                startActivity(intent);

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