package info.jianhuang.routinetrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_create_routines.*
import java.io.IOException

class CreateRoutinesActivity : AppCompatActivity() {

    private val TAG = CreateRoutinesActivity::class.java.simpleName

    private val IMAGE_REQUEST = 1

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_routines)
    }

    fun storeRoutine(v: View){
        if(et_title.isBlank() || et_desc.isBlank()){
            displayErrorMessage("Your routine needs a title and description")
            Log.d(TAG, "Title or description missing")
        }else if(imageBitmap == null){
            Log.d(TAG, "image missing")
            displayErrorMessage("Image is missing")
            return
        }
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text= message
        tv_error.visibility = View.VISIBLE
    }

    private fun EditText.isBlank() = this.text.toString().isBlank()

    fun chooseImage(v: View){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        //finds best app to open an image
        val chooser = Intent.createChooser(intent, "Choose image for routine")

        startActivityForResult(chooser, IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose image")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null){

            val bitmap = tryReadBitmap(data.data)

            bitmap?.let {
                iv_image.setImageBitmap(bitmap)
                Log.d(TAG, "Read bitmap and update view")
            }

        }
    }

    private fun tryReadBitmap(data: Uri): Bitmap? {
       return try{
           MediaStore.Images.Media.getBitmap(contentResolver, data)
       } catch(e: IOException){
           e.printStackTrace()
           null
       }
    }


}
