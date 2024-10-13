package ir.hmb72.forexuser.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.FragmentLoginVerifyBinding
import ir.hmb72.forexuser.databinding.FragmentSplashBinding
import ir.hmb72.forexuser.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginVerifyFragment : Fragment() {
    //Binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    //Other
    private val viewModelLogin: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(4000)
                /*viewModelLogin.readData.asLiveData().observe(viewLifecycleOwner) {
                    findNavController().popBackStack(R.id.splashFragment,true)
                    if (it.username.isNotEmpty()){


                        findNavController().navigate(R.id.action_to_homeFragment)
                    }else{


                        findNavController().navigate(R.id.action_to_welcomeFragment)
                    }

                }*/

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}