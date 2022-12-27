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
import android.widget.EditText;
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

public class ModiferAnnonce extends AppCompatActivity {
    Annonce annonce;
    DatePickerDialog.OnDateSetListener myDateSetListener;
    ImageView calendar;
    CircleImageView image;
    TextView date,nom,localisation,img,desc,num;
    Button submit,browse;
    DatabaseReference reference;
    String id;
    Uri filepath;
    Uri uriimg,uriimg2;
    Bitmap bitmap;
    String p,test,catg,fav;
    EditText prix;
    String email,username,favo;

    Integer index;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifer_annonce);
        test = getIntent().getStringExtra("image");
        uriimg= Uri.parse(test);
        uriimg2= Uri.parse(getIntent().getStringExtra("image"));
        catg = getIntent().getStringExtra("cat");
        favo = getIntent().getStringExtra("favo");
        browse=(Button)findViewById(R.id.browse);
       // fav=annonce.getFavoris();
        email = user.getEmail();

        for( int i = 0; i<email.length(); i++) {
            index = email.indexOf("@");
        }
        username = email.substring(0,index);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(ModiferAnnonce.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });



        num=findViewById(R.id.numero);
        id =getIntent().getStringExtra("idann");

        reference= FirebaseDatabase.getInstance().getReference("annoncetun");
        desc=findViewById(R.id.desc);

        num.setText(getIntent().getStringExtra("numtel"));
        nom=findViewById(R.id.nom);
        nom.setText(getIntent().getStringExtra("titre"));

        desc.setText(getIntent().getStringExtra("desc"));
        localisation=findViewById(R.id.modele);
        localisation.setText(getIntent().getStringExtra("loc"));

        prix=findViewById(R.id.prix);
        p=valueOf(getIntent().getDoubleExtra("prix",0.0));
        prix.setText(p);

        date=findViewById(R.id.dater);
        date.setText(getIntent().getStringExtra("date"));

      //  img=findViewById(R.id.uimgurl);
       // img.setText(getIntent().getStringExtra("image"));

        image=findViewById(R.id.img);
        Picasso.get().load(getIntent().getStringExtra("image")).into(image);

        calendar=(ImageView)findViewById(R.id.calendar);
        date = findViewById(R.id.dater);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(
                        ModiferAnnonce.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        myDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }});
        myDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date2 = dayOfMonth + "/" + month + "/" + year;
                date.setText(date2);
            }
        };

        submit=findViewById(R.id.usubmit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                uploadtofirebase();
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
    private void uploadtofirebase()

    {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Téléchargeur d'image");
        dialog.show();


        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));



        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri){

                                dialog.dismiss();
                                uriimg2=uri;
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("annoncetun");
                                Annonce q=new Annonce(id,nom.getText().toString(),localisation.getText().toString(), Double.valueOf(prix.getText().toString()),date.getText().toString(),uriimg2.toString(),username,desc.getText().toString(),num.getText().toString(),catg,favo);
                                reference.child(id).setValue(q);
                                Toast.makeText(getApplicationContext(),"Votre annonce à été modifié avec succès",Toast.LENGTH_LONG).show();
                                Intent j = new Intent(getApplicationContext(),Mesannonces.class);
                                startActivity(j);


                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Téléchargement :"+(int)percent+" %");
                    }
                });


    }


}