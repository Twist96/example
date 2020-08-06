package com.gasco.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gasco.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileEditActivity     extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int READ_STORAGE_PERMISSION = 2;
    private static final int WRITE_STORAGE_PERMISSION = 3;
    private static final String TAG = "ProfileEditActivity";

    private String newImage;

    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_edit);

        profileImageView = findViewById(R.id.profile_image);
    }

    public void SelectImage(View view) {
        //check for permission

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
            return;
        }
        pickImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_STORAGE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImage();
        }
    }

    private void pickImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 500);
        intent.putExtra("aspectY", 750);
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 487);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Your Avi"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null){

                Uri imageUri = data.getData();
                profileImageView.setImageURI(imageUri);

                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    newImage = encodeImage(selectedImage);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "onActivityResult: " + e.getLocalizedMessage());
                }

            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,20,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public void DismissActivity(View view) {
        finish();
    }
}
