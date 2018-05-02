package com.example.sophie.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 1; //request code for photo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void close(View view) {
        finish();
    }

    public void save(View view) {
        finish();    //back to MainActivity
    }

    public void setalarm(View view) {
        Intent i = new Intent(getApplicationContext(), Main3Activity.class);
        startActivity(i);
        finish();
    }

    public void addimage(View view) {
        Intent photo =new Intent(Intent.ACTION_GET_CONTENT);
        photo.setType("image/jpeg");
        photo.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(photo,"Complete action using"),RC_PHOTO_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_PHOTO_PICKER && resultCode==RESULT_OK)
        {
            //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //imgView.setImageBitmap(thumbnail);
        }



    }
}
