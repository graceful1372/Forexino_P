package ir.hmb72.forexuser.ui.analeys.journal.point.point_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.databinding.FragmentDetailPointBinding
import ir.hmb72.forexuser.viewmodel.journal.point.PointViewModel

@AndroidEntryPoint
class DetailPointFragment : Fragment() {
    //Binding
    private var _binding: FragmentDetailPointBinding? = null
    private val binding get() =_binding!!

    private val viewModel: PointViewModel by viewModels()

    //Other
    private val args: DetailPointFragmentArgs by navArgs()
    var idData:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPointBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPoint = args.id
        //InitViews
        binding.apply {
            //Back
            backBtn.setOnClickListener { findNavController().navigateUp() }
            //Load data
            viewModel.getOnePoint(idPoint)
            viewModel.showOneData.observe(viewLifecycleOwner){
                dateTxt.text = it.date
                titleTxt.text = it.event
                descriptionTxt.text = it.note
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}