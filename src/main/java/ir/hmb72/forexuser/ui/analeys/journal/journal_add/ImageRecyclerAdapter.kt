package ir.hmb72.forexuser.ui.analeys.journal.journal_add

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.hmb72.forexuser.databinding.ItemListImageAddFragmentBinding
import javax.inject.Inject


class ImageRecyclerAdapter @Inject constructor() :
    RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: ItemListImageAddFragmentBinding
    private var items = emptyList<String>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemListImageAddFragmentBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int): Int {

        return position

    }


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: String) {

            binding.apply {


                imgName.text = "hello"
                Glide.with(context)
                    .load(item)
                    .override(40, 40)
                    .centerCrop()
                    .into(binding.img)


                //Click
                root.setOnClickListener {
                    clickOnItem?.let {
                        it(item)
                    }
                }


            }


        }
    }

    class NotesDiffUtils(
        private val oldItem: List<String>,
        private val newItem: List<String>
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

    fun setData(data: List<String>) {
        val noteDiffUtils = NotesDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(noteDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }

    private var clickOnItem: ((String) -> Unit)? = null

    //click
    fun clickItem(listener: (String) -> Unit) {

        clickOnItem = listener
    }


}

