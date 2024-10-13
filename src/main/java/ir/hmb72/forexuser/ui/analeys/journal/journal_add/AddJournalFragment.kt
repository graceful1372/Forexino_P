package ir.hmb72.forexuser.ui.analeys.journal.journal_add

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.R.layout.dialog_symbol
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.databinding.FragmentAddJournalBinding
import ir.hmb72.forexuser.utils.BUNDLE_ID
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.NEW
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.viewmodel.journal.AddJournalViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class AddJournalFragment : BottomSheetDialogFragment() {

    //Binding
    private var _binding: FragmentAddJournalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var entity: NoteEntity

    @Inject
    lateinit var imageAdapter: ImageRecyclerAdapter

    @Inject
    lateinit var symbolAdapter: SymbolAdapter

    private val viewModel: AddJournalViewModel by viewModels()

    //Other
    private var idItem = 0
    private var typeData = ""
    private var isEdit = false

    //Spinner
    private val symbolList: MutableList<SymbolModel> = mutableListOf()
    private val typeList: MutableList<String> = mutableListOf()
    private var symbol = ""
    private var type = ""

    //Camera
    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String = ""
    private lateinit var photoFile: File
    private var uriString: String = ""
    private val imageListUri = mutableListOf<String>()
    private val imagePaths = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddJournalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        idItem = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        if (idItem > 0) {
            typeData = EDIT
            isEdit = true
        } else {
            isEdit = false
            typeData = NEW
        }
        //InitViews
        binding.apply {

            takePicture()
            checkType() //check type is edit or save
            checkResultNumber() //check number is positive or negative
            setSymbol() //Set symbol currency
            saveData()
        }
    }

    private fun takePicture() {
        var nameImage = ""
        //Get name for image
        viewModel.getNumberTransaction()
        viewModel.numberTransaction.observe(viewLifecycleOwner) {
            nameImage = it.toString()
        }
        binding.takePhotoBtn.setOnClickListener {
            //This intent open camera
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //create file to store image .
            photoFile = createImageFile(nameImage)
            //get uri and pass to start Activity
            val uri = FileProvider.getUriForFile(
                requireContext(),
//   BuildConfig.APPLICATION_ID + ".provider",
                "ir.hmb72.forexuser.provider", photoFile
            )
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            //This line passing  to val result code in fun onActivityResult .
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
        }

    }

    private fun saveData() {
        binding.apply {
            //Show number transaction
            viewModel.getNumberTransaction()
            viewModel.numberTransaction.observe(viewLifecycleOwner) {

                //Check type for show suitable Number Transaction
                if (!isEdit) {
                    val a = it + 1
                    numberTransaction.setText(a.toString())
                }


            }
            saveBtn.setOnClickListener {

                //Get data from edit text
                val numberTransaction = numberTransaction.text.toString()
                val value = valueEdt.text.toString()
                val result = resultEdt.text.toString()
                val description = descriptionEdt.text.toString()
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                //Set data to entity
                if (result.isNotEmpty()) entity.result =
                    result.toDouble() else entity.result = 0.0
                if (value.isNotEmpty()) entity.value =
                    value.toDouble() else entity.value = 0.0

                entity.id = idItem
                entity.noTransaction = numberTransaction.toInt()
                entity.description = description
                entity.symbol = symbol
                entity.type = type
                entity.uri = uriString
                entity.dateTime = dateFormat.format(calendar.timeInMillis)


                //Save in view model
                viewModel.saveNote(isEdit, entity)
                dismiss()

            }


        }
    }

    private fun setSymbol() {
        binding.apply {
            symbolTxt.setOnClickListener {

                val inflater = LayoutInflater.from(context)
                val customView = inflater.inflate(dialog_symbol, null)

                //Init Element
                val editText = customView.findViewById<EditText>(R.id.searchEdt)
                val rec = customView.findViewById<RecyclerView>(R.id.recycler_dialog)
                var name = ""
                var img = 0

                //Search
                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        symbolAdapter.clearData()
                        symbolAdapter.setData(listSymbol(s.toString()))
                    }

                })

                symbolAdapter.setData(listSymbol())
                rec.setupRecyclerView(LinearLayoutManager(context), symbolAdapter)
                symbolAdapter.clickItem {
                    img = it.img
                    name = it.name
                    symbol = it.name
                }

                val builder = AlertDialog.Builder(context)
                builder.setView(customView)
                builder.setPositiveButton("OK") { _, _ ->
                    symbolTxt.text = name.uppercase().chunked(3).joinToString("/")
                    imgSymbol.setImageResource(img)

                }
                builder.setNegativeButton("Cancel") { dialog, which ->

                }
                val dialog = builder.create()
                dialog.show()


            }

        }

    }

    private fun checkResultNumber() {
        //Change color and background Result Edit Text
        binding.apply {
            resultEdt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val input = s.toString().toDoubleOrNull()

                    if (input != null) {
                        if (input >= 0) {
//                            resultEdt.setBackgroundResource(R.drawable.bg_positive_edt)
                            resultEdt.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.green
                                )
                            )
                        } else {
//                            resultEdt.setBackgroundResource(R.drawable.bg_negative_edt)
                            resultEdt.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.red
                                )
                            )

                        }
                    } else {
                        resultEdt.setBackgroundResource(R.drawable.bg_lay_choose_symbol)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

    }

    private fun checkType() {
        binding.apply {
            if (typeData == EDIT) {
                viewModel.getDetail(idItem)
                viewModel.dataEdit.observe(viewLifecycleOwner) { editData ->
                    editData.let {
                        numberTransaction.setText(it.noTransaction.toString())
                        valueEdt.setText(it.value.toString())
                        resultEdt.setText(it.result.toString())
                        descriptionEdt.setText(it.description)
                        uriString = it.uri.toString()
                        symbolTxt.text = it.symbol


                    }

                }
            }
        }

    }

    private fun createImageFile(name: String): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
        val storageDir: File? =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${name}_${timeStamp}_", ".jpg", storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                val uri = FileProvider.getUriForFile(
                    requireContext(), "ir.hmb72.forexuser.provider", photoFile
                )


                uriString = uri.toString()
//                imageListUri.add(uriString)
//                imagePaths.add(uriString)
                binding.takePhotoBtn.setImageURI(uri)
                //من میتونم اخرین تصویر را انیجا ثبت کنم
//                imageAdapter.setData(imagePaths)

            }
        } else {

            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun listSymbol(searchQuery: String? = null): MutableList<SymbolModel> {
        val symbol: MutableList<SymbolModel> = mutableListOf()
        symbol.add(SymbolModel(R.drawable.img_audcad, "AUDCAD"))
        symbol.add(SymbolModel(R.drawable.img_audchf, "AUDCHF"))
        symbol.add(SymbolModel(R.drawable.img_audjpy, "AUDJPY"))
        symbol.add(SymbolModel(R.drawable.img_audnzd, "AUDNZD"))
        symbol.add(SymbolModel(R.drawable.img_audusd, "AUDUSD"))

        symbol.add(SymbolModel(R.drawable.img_cadchf, "CADCHF"))
        symbol.add(SymbolModel(R.drawable.img_cadjpy, "CADJPY"))
        symbol.add(SymbolModel(R.drawable.img_chfjpy, "CHFJPY"))

        symbol.add(SymbolModel(R.drawable.img_euraud, "EURAUD"))
        symbol.add(SymbolModel(R.drawable.img_eurcad, "EURCAD"))
        symbol.add(SymbolModel(R.drawable.img_eurchf, "EURCHF"))
        symbol.add(SymbolModel(R.drawable.img_eurgbp, "EURGBP"))
        symbol.add(SymbolModel(R.drawable.img_eurjpy, "EURJPY"))
        symbol.add(SymbolModel(R.drawable.img_eurnzd, "EURNZD"))
        symbol.add(SymbolModel(R.drawable.img_eurusd, "EURUSD"))

        symbol.add(SymbolModel(R.drawable.img_gbpaud, "GBPAUD"))
        symbol.add(SymbolModel(R.drawable.img_gbpcad, "GBPCAD"))
        symbol.add(SymbolModel(R.drawable.img_gbpchf, "GBPCHF"))
        symbol.add(SymbolModel(R.drawable.img_gbpjpy, "GBPJPY"))
        symbol.add(SymbolModel(R.drawable.img_gbpnzd, "GBPNZD"))
        symbol.add(SymbolModel(R.drawable.img_gbpusd, "GBPUSD"))

        symbol.add(SymbolModel(R.drawable.img_nzdcad, "NZDCAD"))
        symbol.add(SymbolModel(R.drawable.img_nzdchf, "NZDCHF"))
        symbol.add(SymbolModel(R.drawable.img_nzdjpy, "NZDJPY"))
        symbol.add(SymbolModel(R.drawable.img_nzdusd, "NZDUSD"))
        symbol.add(SymbolModel(R.drawable.img_usdcad, "USDCAD"))
        symbol.add(SymbolModel(R.drawable.img_usdchf, "USDCHF"))
        symbol.add(SymbolModel(R.drawable.img_usdjpy, "USDJPY"))


        /*
AUDCAD - Australian Dollar/Canadian Dollar
AUDCHF - Australian Dollar/Swiss Franc
AUDJPY - Australian Dollar/Japanese Yen
AUDNZD - Australian Dollar/New Zealand Dollar
AUDUSD - Australian Dollar/United States Dollar

CADCHF - Canadian Dollar/Swiss Franc
CADJPY - Canadian Dollar/Japanese Yen
CHFJPY - Swiss Franc/Japanese Yen

EURAUD - Euro/Australian Dollar *
EURCAD - Euro/Canadian Dollar
EURCHF - Euro/Swiss Franc*
EURGBP - Euro/British Pound Sterling*
EURJPY - Euro/Japanese Yen*
EURNZD - Euro/New Zealand Dollar
EURUSD - Euro/United States Dollar*

GBPAUD - British Pound Sterling/Australian Dollar
GBPCAD - British Pound Sterling/Canadian Dollar
GBPCHF - British Pound Sterling/Swiss Franc
GBPJPY - British Pound Sterling/Japanese Yen
GBPNZD - British Pound Sterling/New Zealand Dollar
GBPUSD - British Pound Sterling/United States Dollar

NZDCAD - New Zealand Dollar/Canadian Dollar
NZDCHF - New Zealand Dollar/Swiss Franc
NZDJPY - New Zealand Dollar/Japanese Yen
NZDUSD - New Zealand Dollar/United States Dollar
USDCAD - United States Dollar/Canadian Dollar
USDCHF - United States Dollar/Swiss Franc
USDJPY - United States Dollar/Japanese Yen*/


        if (searchQuery != null && searchQuery.isNotBlank()) {
            return symbol.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }.toMutableList()
        }
        return symbol


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    //Picture

}
