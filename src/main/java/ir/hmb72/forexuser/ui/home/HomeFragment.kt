package ir.hmb72.forexuser.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.data.model.network.home.ResponseCoinGecko
import ir.hmb72.forexuser.data.model.network.home.ResponseCoinGecko.ResponseCoinGeckoItem
import ir.hmb72.forexuser.data.model.network.home.ResponseNews.ResponseNewsItem
import ir.hmb72.forexuser.databinding.FragmentHomeBinding
import ir.hmb72.forexuser.utils.base.BaseFragment
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.setupLoading
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.viewmodel.HomeViewModel
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    //Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapterNews: AdapterNews

    @Inject
    lateinit var adapterCoin: AdapterCoin


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Show more news
            showMoreNews.setOnClickListener {
                val uri = Uri.parse("https://www.forexfactory.com/calendar")

                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

        }
        loadNews()
        loadCoins()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadNews() {
        binding.apply {
            if (isNetworkAvailable) {
                viewModel.newsList.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is NetworkRequest.Loading -> {
                            listNewsLoading.setupLoading(true, listNews)
                        }

                        is NetworkRequest.Success -> {
                            listNewsLoading.setupLoading(false, listNews)

                            response.data?.let { data ->
                                initListNews(data)
                            }
                        }

                        is NetworkRequest.Error -> {
                            listNewsLoading.setupLoading(false, listNews)
                        }
                    }

                }
            }


        }
    }
    private fun loadCoins() {
        binding.apply {
            if (isNetworkAvailable) {
                viewModel.getListCoin("usd")
                viewModel.observerCoin.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is NetworkRequest.Loading -> {
                            listCoinsLoading.setupLoading(true, listCoin)
                        }

                        is NetworkRequest.Success -> {
                            listCoinsLoading.setupLoading(false, listCoin)

                            response.data?.let { data ->
                                initListCoins(data)
                            }
                        }

                        is NetworkRequest.Error -> {
                            listCoinsLoading.setupLoading(false, listCoin)
                        }
                    }

                }
            }


        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initListNews(data: List<ResponseNewsItem>) {
        adapterNews.setData(data)
        binding.listNews.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), adapterNews)

    }
    private fun initListCoins(data: List<ResponseCoinGeckoItem>) {
        adapterCoin.setData(data)
        binding.listCoin.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), adapterCoin)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}