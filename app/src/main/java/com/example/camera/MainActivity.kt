package com.example.camera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_cptr.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,123)
        }

        btn_galeri.setOnClickListener{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permission=arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permission,PERMISSION_CODE);
                }
                else {
                    pickImageFromGallery();
                }


            }
            else{pickImageFromGallery();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123)
        {
            var bmp = data?.extras?.get("data") as? Bitmap
            imageView2.setImageBitmap(bmp)



        }
        if(resultCode==Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE ){
            imageView2.setImageURI(data?.data)
        }


}
    private fun pickImageFromGallery(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }

    companion object{
        private val IMAGE_PICK_CODE=1000;
        private val PERMISSION_CODE=1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){pickImageFromGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
    }




}
