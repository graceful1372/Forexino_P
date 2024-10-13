package ir.hmb72.forexuser.ui.welcome

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.data.model.local.welcome.PageItem
import ir.hmb72.forexuser.databinding.ItemPageBinding


class ViewPagerAdapter(private val items: List<PageItem>) : RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {
    private lateinit var binding: ItemPageBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.PagerVH {
        binding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return PagerVH(binding)
    }

    override fun getItemCount(): Int = items.size
    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.bind(items[position])
    inner class PagerVH(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PageItem) {
            binding.apply {

                title.text = ContextCompat.getString(context, item.title)
                content.text = ContextCompat.getString(context, item.content)
                item.image?.let { image.setBackgroundResource(it) }
                item.waveImg?.let { waveImg.setBackgroundResource(it) }
                fabGonext.isVisible = adapterPosition == 3
                fabGonext.setOnClickListener {
                    onClickListener?.invoke()
                }
            }
        }
    }
    private var onClickListener :(()->Unit)? = null
    fun setOnClickListener(listener : ()->Unit){
        onClickListener = listener
    }

}

