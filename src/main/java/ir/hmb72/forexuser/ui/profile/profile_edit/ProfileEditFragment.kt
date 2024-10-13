package ir.hmb72.forexuser.ui.profile.profile_edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.FragmentProfileBinding
import ir.hmb72.forexuser.databinding.FragmentProfileEditBinding


class ProfileEditFragment : Fragment() {

    //Binding
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {

        _binding = FragmentProfileEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitView
        binding.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}