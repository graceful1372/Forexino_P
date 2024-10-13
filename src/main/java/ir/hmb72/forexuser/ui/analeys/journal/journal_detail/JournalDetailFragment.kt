package ir.hmb72.forexuser.ui.analeys.journal.journal_detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.databinding.FragmentJournalDetailBinding
import ir.hmb72.forexuser.viewmodel.journal.AddJournalViewModel
import ir.hmb72.forexuser.viewmodel.journal.DetailViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class JournalDetailFragment : Fragment() {
    //Binding
    private var _binding: FragmentJournalDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val viewModelAdd: AddJournalViewModel by viewModels()

    @Inject
    lateinit var entity: NoteEntity

    private val args: JournalDetailFragmentArgs by navArgs()
    private var number =0

    @Inject
    lateinit var adapterImageList: AdapterImageList

    //Image
    private lateinit var uri: Uri
    private lateinit var photoFile: File
    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String = ""
    private var uriString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentJournalDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        number = args.id
        //InitViews
        binding.apply {

            //Get data
            viewModel.getDetail(number)
            viewModel.detail.observe(viewLifecycleOwner) {
                descriptionDetail.text = it.description
                showTypeTxt.text = it.type
                showTimeTxt.text = it.dateTime

                if (it.uri!!.isNotEmpty()) {
                    uri = Uri.parse(it.uri)
                    imageViewDetail.setImageURI(uri)
                } else {
                    imageViewDetail.setBackgroundResource(R.drawable.bg_lay_choose_symbol)
                }


            }
            //Go to zoom fragment and zoom image view
            imageViewDetail.setOnClickListener {
//                val direction =
//                    JournalDetailFragmentDirections.actionDetailFragmentToZoomImageViewFragment(number)
//                findNavController().navigate(direction)
            }

            cardImage.setOnClickListener {
                val contentResolver = requireContext().contentResolver
                val deleted = contentResolver.delete(uri, null, null)
                if (deleted > 0) {
//                    Toast.makeText(requireContext(), "Deleted image", Toast.LENGTH_SHORT).show()
                    imageViewDetail.setImageResource(R.drawable.bg_lay_choose_symbol)
                } else {
//                    Toast.makeText(requireContext(), "Failed to delete image", Toast.LENGTH_SHORT).show()
                }
                /*val imagePath = uri.path
                Log.d("DeleteImage", "imagePath: $imagePath")
                val imageFile = imagePath?.let { it1 -> File(it1) }

                if (imageFile!!.exists() && imageFile.isFile) {
                    val isdelete = imageFile.delete()

                    if (isdelete){
                        Toast.makeText(requireContext() , "Delete image " ,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext() , "dont delete" ,Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(requireContext() , "can't read file  " ,Toast.LENGTH_SHORT).show()
                }
*/
            }

            //Add image
            addImage.setOnClickListener {
                takePicture(number.toString())

            }


            /*
                        viewModel.getDetail(number.toInt())
                        viewModel.detail.observe(viewLifecycleOwner) {
                            adapterImageList.setData(it)


                        }



                        imgRecyclerView.setupRecyclerView(
                            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                            adapterImageList

                        )*/


        }
    }

    //Picture
    private fun takePicture(name: String) {
        //This intent open camera
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //create file to store image .
        photoFile = createImageFile(name)
        //get uri and pass to start Activity
        val uri = FileProvider.getUriForFile(
            requireContext(),
//            BuildConfig.APPLICATION_ID + ".provider",
            "com.graceful1372.meysam.provider",
            photoFile
        )
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        //This line passing  to val result code in fun onActivityResult .
        startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun createImageFile(name: String): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${name}_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {


                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    "ir.hmb72.forexuser.provider",
                    photoFile
                )


                uriString = uri.toString()

                binding.imageViewDetail.setImageURI(uri)

                viewModel.updateImage(uriString, number.toInt())


            }
        } else {

            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}
