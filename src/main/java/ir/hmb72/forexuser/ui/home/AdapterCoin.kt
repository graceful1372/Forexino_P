package ir.hmb72.forexuser.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.network.home.ResponseCoinGecko.ResponseCoinGeckoItem
import ir.hmb72.forexuser.databinding.ItemListCoinBinding
import ir.hmb72.forexuser.utils.base.BaseDiffUtils
import javax.inject.Inject

class AdapterCoin @Inject constructor():RecyclerView.Adapter<AdapterCoin.ViewHolder>() {
    private var items = emptyList<ResponseCoinGeckoItem>()
    private lateinit var binding: ItemListCoinBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListCoinBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

    }
    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemListCoinBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : ResponseCoinGeckoItem){
            binding.apply {

                Glide.with(context)
                    .load(item.image)
                    .into(logoImg)

                titleTxt.text = item.id?.uppercase()
                ratioTxt.text = item.priceChangePercentage24h.toString().substring(0,4)
                priceTxt.text  = "$${item.currentPrice.toString()}"

                if(item.priceChangePercentage24h.toString().substring(0,1) == "-"){
                    upDownImg.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.ic_arrow_down))

                }else{
                    upDownImg.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.ic_arrow_top))



                }



            }
        }
    }

    fun setData(data: List<ResponseCoinGeckoItem>) {
        val adapterDiffUtil = BaseDiffUtils(items, data)
        val diffUtilResult = DiffUtil.calculateDiff(adapterDiffUtil)
        items = data
        diffUtilResult.dispatchUpdatesTo(this)
    }
}