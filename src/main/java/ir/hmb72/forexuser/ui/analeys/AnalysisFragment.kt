package ir.hmb72.forexuser.ui.analeys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.FragmentAnalysisBinding
import ir.hmb72.forexuser.utils.base.BaseFragment

@AndroidEntryPoint
class AnalysisFragment : BaseFragment() {
    //Binding
    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    //Other
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            journalTxt.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_journalFragment)
            }
            resumeTxt.setOnClickListener {}
            psychologyTxt.setOnClickListener {}
            riskTxt.setOnClickListener {}
            logoutTxt.setOnClickListener {
                //Logout
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}