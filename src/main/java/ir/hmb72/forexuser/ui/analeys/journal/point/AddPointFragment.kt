package ir.hmb72.forexuser.ui.analeys.journal.point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import ir.hmb72.forexuser.databinding.FragmentAddPointBinding
import ir.hmb72.forexuser.utils.BUNDLE_ID_POINT
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.NEW
import ir.hmb72.forexuser.viewmodel.journal.point.PointViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class AddPointFragment : BottomSheetDialogFragment() {
    //Binding
    private lateinit var binding: FragmentAddPointBinding

    private val viewModel: PointViewModel by viewModels()

    @Inject
    lateinit var entity: PointEntity

    private var type = ""
    private var isEdit = false
    private var idData = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddPointBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get id data from bundle
        idData = arguments?.getInt(BUNDLE_ID_POINT) ?: 0
        if (idData > 0) {
            type = EDIT
            isEdit = true
        } else {
            isEdit = false
            type = NEW
        }

        //InitViews
        binding.apply {

            //setDate
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateEdtFrgPoint.text = dateFormat.format(calendar.timeInMillis)
            entity.date = dateFormat.format(calendar.timeInMillis)


            //update
            if (type == EDIT) {
                viewModel.getOnePoint(idData)
                viewModel.showOneData.observe(viewLifecycleOwner) {
                    eventEdtFrgPoint.setText(it.event)
                    noteEdtFrgPoint.setText(it.note)
                }
            }

            //save
            saveBtnFrgPoint.setOnClickListener {
                val event = eventEdtFrgPoint.text.toString()
                val note = noteEdtFrgPoint.text.toString()


                entity.id = idData
                entity.event = event
                entity.note = note

                if (event.isNotEmpty()) {
                    viewModel.save(isEdit, entity)
                    dismiss()
                }
            }


        }
    }


}