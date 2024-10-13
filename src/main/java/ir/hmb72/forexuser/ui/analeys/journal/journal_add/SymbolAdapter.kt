package ir.hmb72.forexuser.ui.analeys.journal.journal_add

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.SpinnerItemBinding
import javax.inject.Inject

class SymbolAdapter @Inject constructor() : RecyclerView.Adapter<SymbolAdapter.ViewHolder>() {

    private lateinit var binding: SpinnerItemBinding
    private var items = emptyList<SymbolModel>()
    private lateinit var context: Context
    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = SpinnerItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(false)


    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int): Int {

        return position

    }


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(item: SymbolModel) {

            binding.apply {
                imageView.setImageResource(item.img)
                textView.text = item.name.uppercase().chunked(3).joinToString ("/")
               


                //Click and show Menu
                root.setOnClickListener {
                    selectedItem = adapterPosition
                    notifyDataSetChanged()
                    clickOnItem?.let {
                        it(item)
                    }

                }


                //Change color
                if (selectedItem == adapterPosition) {
                    root.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    root.setBackgroundResource(R.drawable.bg_dialog_edt)
                }


            }


        }
    }


    fun setData(data: List<SymbolModel>) {
        val noteDiffUtils = NotesDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(noteDiffUtils)
        items = data


        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(
        private val oldItem: List<SymbolModel>,
        private val newItem: List<SymbolModel>
    ) : DiffUtil.Callback() {
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


    private var clickOnItem: ((SymbolModel) -> Unit)? = null

    //click
    fun clickItem(listener: (SymbolModel) -> Unit) {

        clickOnItem = listener
    }

    //Clear data when search
    fun clearData() {
        items = emptyList()
        notifyDataSetChanged()

    }

}


