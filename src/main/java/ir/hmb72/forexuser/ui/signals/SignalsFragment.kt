package ir.hmb72.forexuser.ui.signals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.data.model.network.analyze.ResponseSignal
import ir.hmb72.forexuser.databinding.FragmentSignalsBinding
import ir.hmb72.forexuser.utils.base.BaseFragment
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.setupLoading
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.utils.showSnackBar
import ir.hmb72.forexuser.viewmodel.AnalyzeViewModel
import javax.inject.Inject


@AndroidEntryPoint
class SignalsFragment : BaseFragment() {
    //Binding
    private var _binding: FragmentSignalsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: AdapterAnalyze

    //Other
    private val viewmodel: AnalyzeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
        menuBtn.setOnClickListener { Toast.makeText(requireContext(), "تاریخچه به زودی اضافه میگردد ", Toast.LENGTH_SHORT).show() }

            loadData()
        }
    }

    private fun initListSignal(data: List<ResponseSignal.Signal>) {
        adapter.setData(data)
        binding.listSignals.setupRecyclerView(LinearLayoutManager(requireContext()), adapter)
        adapter.setOnItemClickListener {
            findNavController().navigate(SignalsFragmentDirections.actionSignalsFragmentToDetailSignalFragment4(it))
        }
    }

    private fun loadData() {
        if (isNetworkAvailable) {
            binding.apply {
                viewmodel.getSignal("get_list")
                viewmodel.observerAnalyze.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is NetworkRequest.Loading -> {
                            loadingListSignal.setupLoading(true, listSignals)

                        }

                        is NetworkRequest.Success -> {
                            loadingListSignal.setupLoading(false, listSignals)
                            response.data?.let { data ->
                                initListSignal(data.listSignal!!)
                            }

                        }

                        is NetworkRequest.Error -> {
                            loadingListSignal.setupLoading(false, listSignals)
                            root.showSnackBar(response.error.toString())

                        }

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