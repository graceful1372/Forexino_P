package ir.hmb72.forexuser.utils.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.utils.network.NetworkChecker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment:Fragment()   {

    @Inject
    lateinit var networkChecker: NetworkChecker

    var isNetworkAvailable = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Check network
        lifecycleScope.launch {
            networkChecker.checkNetwork().collect(){
                isNetworkAvailable = it 
                if (!it){
                    Toast.makeText(requireContext(), R.string.checkYourNetwork, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}