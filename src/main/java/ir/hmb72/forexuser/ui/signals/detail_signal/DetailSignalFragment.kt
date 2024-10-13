package ir.hmb72.forexuser.ui.signals.detail_signal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.databinding.FragmentDetailSignalBinding
import ir.hmb72.forexuser.utils.base.BaseFragment

@AndroidEntryPoint
class DetailSignalFragment : BaseFragment() {
    //Binding
    private var _binding: FragmentDetailSignalBinding? = null
    private val binding get() = _binding!!

    //Other
    private val args by navArgs<DetailSignalFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (isNetworkAvailable){
                //Load image
                Glide.with(requireContext())
                    .load(args.detailSignalData.imagePath)
                    .into(signalImg)
               //Load data
                nameCurrencyTxt.text = args.detailSignalData.symbol
                spotTradeTxt.text = args.detailSignalData.spotTrade
                priceEnterTxt.text = args.detailSignalData.price
                tp1Txt.text = args.detailSignalData.tp1
                tp2Txt.text = args.detailSignalData.tp2
                stopLossTxt.text = args.detailSignalData.stopLoss

                //Back
                backBtn.setOnClickListener{
                    findNavController().navigateUp()
                }


            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}