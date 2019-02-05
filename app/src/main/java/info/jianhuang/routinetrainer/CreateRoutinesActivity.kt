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
import info.jianhuang.routinetrainer.db.RoutineDbTable
import kotlinx.android.synthetic.main.activity_create_routines.*
import kotlinx.android.synthetic.main.single_card.*
import java.io.IOException

class CreateRoutinesActivity : AppCompatActivity() {

    private val TAG = CreateRoutinesActivity::class.java.simpleName

    private val IMAGE_REQUEST = 1

    private var imageUri: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_routines)
    }

    fun storeRoutine(v: View){
        if(et_title.isBlank() || et_desc.isBlank()){
            displayErrorMessage("Your routine needs a title and description")
            Log.d(TAG, "Title or description missing")
            return
        }else if(imageUri == null){
            Log.d(TAG, "image missing")
            displayErrorMessage("Image is missing")
            return
        }

        val title = et_title.text.toString()
        val description = et_desc.text.toString()
        val routine = Routine(title, description, imageUri)

        val id = RoutineDbTable(this).store(routine)

        if (id == -1L){
            displayErrorMessage("Routine not stored...")
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

            val bitmap = data.data

            bitmap?.let {
                this.imageUri = bitmap
                iv_image.setImageURI(bitmap)
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
