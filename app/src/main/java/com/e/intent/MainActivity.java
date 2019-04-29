package com.e.intent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imgview;
    private Button btncapture;
    private EditText etnumber;
    private Button btndial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etnumber=findViewById(R.id.etnumber);
        btndial=findViewById(R.id.btndial);
        btndial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dial();
            }
        });

        checkPermission();
        imgview=findViewById(R.id.imgview);
        btncapture=findViewById(R.id.btncapture);

        btncapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCamera();
            }
        });

    }
private void Dial(){
    Uri u=Uri.parse("tel:"+etnumber.getText().toString());
    Intent intent=new Intent(Intent.ACTION_DIAL,u);
    startActivity(intent);
}

private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
}

private void loadCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,1);
        }


}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if (requestCode==1 && resultCode==RESULT_OK){
          Bundle extras=data.getExtras();
          Bitmap imageBitmap=(Bitmap) extras.get("data");
          imgview.setImageBitmap(imageBitmap);
      }
    }
}
