package ir.hmb72.forexuser.ui.analeys.journal.journal_detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.databinding.ItemListImageBinding
import javax.inject.Inject

class AdapterImageList @Inject constructor() : RecyclerView.Adapter<AdapterImageList.ViewHolder>() {

    private lateinit var binding: ItemListImageBinding
    private var listImage = emptyList<String>()

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ImageDiffutils(private val oldItem: List<String>, private val newItem: List<String>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }

    fun setData(data:List<String>){

        val instanceDiffutils = ImageDiffutils(listImage , data)
        val resultCalculate = DiffUtil.calculateDiff(instanceDiffutils)
        listImage = data
        resultCalculate.dispatchUpdatesTo(this)



    }


}