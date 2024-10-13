package ir.hmb72.forexuser.ui.signals

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.hmb72.forexuser.data.model.network.analyze.ResponseSignal.Signal
import ir.hmb72.forexuser.databinding.ItemListSignalBinding
import ir.hmb72.forexuser.utils.base.BaseDiffUtils
import javax.inject.Inject

class AdapterAnalyze @Inject constructor() : RecyclerView.Adapter<AdapterAnalyze.ViewHolder>() {
    private lateinit var binding: ItemListSignalBinding
    private var items = emptyList<Signal>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAnalyze.ViewHolder {
        binding = ItemListSignalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterAnalyze.ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()


    inner class ViewHolder(private val binding: ItemListSignalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Signal) {
            binding.apply {
                //Symbol currency
                Glide.with(context)
                    .load(item.symbolImagePath)
                    .into(currencyImg)
                symbolCurrencyTxt.text = item.symbol
                nameCurrencyTxt.text = item.fullName
                enterPriceTxt.text = item.price
                profitTxt.text = item.profit
//                statusImg.setImageResource(R.drawable.img_low_lost_chart)

                root.setOnClickListener {
                    onItemClickListener?.let { it(item)}

                }


            }
        }
    }

    private var onItemClickListener: ((Signal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Signal) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<Signal>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffResult = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }


}