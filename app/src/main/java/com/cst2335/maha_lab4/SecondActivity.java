package com.cst2335.maha_lab4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        profileImage=findViewById(R.id.profileImage);

        EditText phoneNumber=findViewById(R.id.et_phone);



        //--------------------------get email address from Previous-------------------------

        Intent fromPrevious=getIntent();
        String emailAddress=fromPrevious.getStringExtra("EmailAddress");

        Log.i("email", "onCreate_second activity: "+emailAddress);





        //-------------------------------- Load Image from Storage ---------------------


        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists())
        {
            Log.i("image", "onCreate: true" );
            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(theImage);

        }



        //------------------------------------  SharedPreferences Save Email   -----------------------------------


        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LoginName", emailAddress);
        editor.apply();



        //-------------------------------------------- Call  ------------------------------------



        Button call_btn=findViewById(R.id.call_button);

        call_btn.setOnClickListener(c->{

            Intent call=new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
            startActivity(call);


        });



        //-------------------------------------  Take Picture with camera   -------------------------------------
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        ActivityResultLauncher<Intent>cameraResult=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {



                        if (result.getResultCode() == Activity.RESULT_OK){

                            Intent data=result.getData();
                            Bitmap thumbnail=data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;
                            try {
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch (IOException e)
                            { e.printStackTrace();
                            }






                        }

                    }
                });




        Button take_Picture=findViewById(R.id.pic_button);



        take_Picture.setOnClickListener(c->{





            cameraResult.launch(cameraIntent);





        });

//---------------------------------------------------------------------






    }
}