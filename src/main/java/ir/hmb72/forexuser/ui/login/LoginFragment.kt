package ir.hmb72.forexuser.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.network.login.BodyLogin
import ir.hmb72.forexuser.data.model.network.login.BodyRegister
import ir.hmb72.forexuser.databinding.FragmentHomeBinding
import ir.hmb72.forexuser.databinding.FragmentLoginBinding
import ir.hmb72.forexuser.utils.base.BaseFragment
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.showSnackBar
import ir.hmb72.forexuser.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    //Binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var registerBody: BodyRegister

    @Inject
    lateinit var loginBody: BodyLogin

    //Other
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Lottie
            loginAnim.setFailureListener { t->
                Log.e("lottie", "onViewCreated: $t", )
            }
            loginAnim.setAnimation(R.raw.login)
            loginAnim.playAnimation()
            sendBtn.setOnClickListener {

                if (isNetworkAvailable) {
                    if (phoneEdt.text.toString().isNotEmpty()) {
                        //Register user then I must edit this code . use sms
//                        body.phone = phoneEdt.text.toString()
//                        body.name = "12132"
//                        body.family = "2134"

                        loginBody.phone = phoneEdt.text.toString()

                        viewModel.login(loginBody)
                    }
                }
            }

        }
        observeLoginState()
    }


    private fun observeLoginState() {
        binding.apply {
            viewModel.loginData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {}
                    is NetworkRequest.Success -> {
                        response.data?.let { data ->
                            //save in data store
//                            viewModel.saveUserData(data.name.toString())

//                            viewModel.readData.asLiveData().observe(viewLifecycleOwner) {
//                                Toast.makeText(requireContext(), "data store : ${it.username}", Toast.LENGTH_SHORT)
//                                    .show()
//                            }

                            //Create delay for save data store
                            viewLifecycleOwner.lifecycleScope.launch {
                                delay(1000)
                                //Navigate to Home Fragment
                                findNavController().popBackStack(R.id.loginFragment, true)
                                findNavController().navigate(R.id.action_to_homeFragment)
                            }


                        }
                    }

                    is NetworkRequest.Error -> {
                        binding.root.showSnackBar(response.error!!)
                    }
                }


            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}