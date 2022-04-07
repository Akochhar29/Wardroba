package com.example.wardroba.fragments

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentHomeBinding
import com.example.wardroba.databinding.FragmentRecommendationsBinding
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class Recommendations : Fragment() {
    private var _binding: FragmentRecommendationsBinding? = null
    private val binding get() = _binding!!
    private var imageCapture:ImageCapture? = null
    private val REQUEST_PERMISSION_CODE = 171
    private val REQUIRED_PERMISSIONS_Legacy = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val REQUIRED_PERMISSIONS =  arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendationsBinding.inflate(inflater, container, false)
        startCamera()
        binding.btnScan.setOnClickListener {
            takePhoto()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()){
            //good to go further
            this.startCamera()
        }else{
            if(Build.VERSION.SDK_INT >= 31){
                ActivityCompat.requestPermissions(this.requireActivity(), REQUIRED_PERMISSIONS, REQUEST_PERMISSION_CODE)
            }else{
                ActivityCompat.requestPermissions(this.requireActivity(), REQUIRED_PERMISSIONS_Legacy, REQUEST_PERMISSION_CODE)
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnScan.setOnClickListener {
            takePhoto()
        }

    }
    private fun startCamera(){
        imageCapture = ImageCapture.Builder().build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()
            this.bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this.requireActivity()))
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this.requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    private fun bindPreview(cameraProvider: ProcessCameraProvider){
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.previewViewContainer.surfaceProvider)
        }

        val cameraSelector  = CameraSelector.DEFAULT_BACK_CAMERA

        try{
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

        }catch (ex: Exception){
            Log.e("ABC", "Use case binding failed.", ex)
        }
    }
    private fun takePhoto(){

        if (this.imageCapture != null){
            Toast.makeText(this.requireContext() , "Picture Taken Successfully" , Toast.LENGTH_SHORT).show()
            val outputDirectory = getOutputDirectory()
            val filename = "Wardroba_" + SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.CANADA).format(System.currentTimeMillis()) + ".jpeg"
            val pictureFile = File(outputDirectory, filename)
            val outputOptions  = ImageCapture.OutputFileOptions.Builder(pictureFile).build()
            Log.e("output option " ,outputOptions.toString() )



            imageCapture!!.takePicture(outputOptions,
                ContextCompat.getMainExecutor(this.requireActivity()),
                object : ImageCapture.OnImageSavedCallback{
                    override fun onError(exception: ImageCaptureException) {
                        Log.e("ABC", "Image saving failed ${exception.message}", exception)
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val pictureURI = Uri.fromFile(pictureFile)
                        Log.e("ABC", "Image successfully saved at ${pictureURI}")
                        //this@Recommendations.requireActivity().imgProfilePic.setImageURI(pictureURI)

                        //SAVE THE USER IMAGE IN THE DB
//                        currentUser.profileImg = pictureURI.toString()
//                        viewModel.updateGuest2(currentUser)

                        //get dominant colour
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context!!.getContentResolver(), pictureURI)
                        val clothingColour = getMainColour(bitmap)

                        this@Recommendations.saveToExternalStorage(pictureURI)
                        val action = RecommendationsDirections.actionRecommendationsToColourRecommendations()
                        findNavController().navigate(action)
                    }


                }
            )

        }else{
            Log.e("ABC", "ImageCapture use case is unavailable. Cannot Click Pictures.")
            return
        }
    }
    private fun saveToExternalStorage(pictureURI: Uri){
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            val resolver: ContentResolver = this@Recommendations.requireActivity().applicationContext.contentResolver
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pictureURI.lastPathSegment)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Wardroba")

            val uri  = resolver.insert(contentUri, contentValues)

            if (uri == null){
                throw IOException("Failed to create media on external storage.")
            }else{
                Log.e("ABC", "Media successfully saved to external storage.")
            }
        }else{
            Log.e("ABC", "Unable to access external storage.")
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = this.requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else this.requireActivity().filesDir
    }

    private fun getMainColour(bitmap: Bitmap): Int {
        var colour = R.color.black
        Palette.from(bitmap).generate() { palette ->
            colour =
                palette?.getDominantColor(ContextCompat.getColor(requireContext(), R.color.black))!!
        }
        return colour
    }
}