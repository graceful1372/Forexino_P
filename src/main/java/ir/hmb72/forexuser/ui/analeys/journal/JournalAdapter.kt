package ir.hmb72.forexuser.ui.analeys.journal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.databinding.ItemListJournalBinding
import ir.hmb72.forexuser.utils.DELETE
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.MOVE
import javax.inject.Inject


class JournalAdapter @Inject constructor() : RecyclerView.Adapter<JournalAdapter.ViewHolder>(){

    private lateinit var binding: ItemListJournalBinding
    private var items = emptyList<NoteEntity>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemListJournalBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
//        holder.setIsRecyclable(false)


    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int): Int {

        return position

    }


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(item: NoteEntity) {

            binding.apply {
                numberTxt.text = item.noTransaction.toString()
                symbolTxt.text =item.symbol.uppercase().chunked(3).joinToString("/")
                val resName = "img_" + item.symbol.lowercase()
                val resId = context.resources.getIdentifier(resName, "drawable", context.packageName)
                symbolImg.setImageResource(resId)


                typeTxt.text =  item.type


                if (item.value == 0.0 ){

                }else{
                    valueTxt.text = item.value.toString()

                }
                if(item.result >0){
                    resultTxt.setTextColor(ContextCompat.getColor(context, R.color.green))
                    resultTxt.text = item.result.toString()

                }else if (item.result == 0.0 ){

                } else {
                    resultTxt.setTextColor(ContextCompat.getColor(context,R.color.Light_Crimson))
                    resultTxt.text = item.result.toString()

                }
                when {
                    item.result <= -10 -> {
                        imgMiniChart.setBackgroundResource(R.drawable.img_high_lost_chart) }
                    item.result > -10 && item.result < 0 -> {
                        imgMiniChart.setBackgroundResource(R.drawable.img_low_lost_chart)
                    }
                    item.result > 0 && item.result < 10 -> {
                        imgMiniChart.setBackgroundResource(R.drawable.img_low_up_chart)
                    }
                    item.result >= 10 -> {
                        imgMiniChart.setBackgroundResource(R.drawable.img_high_up_chart)
                    }


                }


                //Click and show Menu
                root.setOnClickListener {
                    val popupMenu = PopupMenu(context , it  )
//                    val popupMenu = PopupMenu(context , it ,Gravity.NO_GRAVITY , 0 , R.style.PopupMenuStyle )
                    popupMenu.menuInflater.inflate(R.menu.menu_journal_recycler , popupMenu.menu)

                    //change color background menu
                  /*  popupMenu.menu.setGroupDividerEnabled(true)
                    for (i in 0 until popupMenu.menu.size()) {
                        val menuItem = popupMenu.menu.getItem(i)
                        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        menuItem.actionView = null
                        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
                        val view = LayoutInflater.from(context).inflate(R.layout.popup_menu_item, null)
                        val titleView = view.findViewById<TextView>(android.R.id.title)
                        titleView.text = menuItem.title
                        menuItem.title = null
                        menuItem.actionView = view
                    }
*/
                    popupMenu.show()

                    //Select
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId){
                            R.id.edit_item -> {
                                clickOnItem?.let {
                                    it(item , EDIT)
                                }
                            }
                            R.id.delete_item -> {
                                clickOnItem?.let {
                                    it(item , DELETE)
                                }
                            }
                            R.id.detail_item -> {
                                clickOnItem?.let {
                                    it(item , MOVE)
                                }
                            }

                        }
                        return@setOnMenuItemClickListener true
                    }


                }

                //Click on delete

            }



        }
    }


    fun setData(data: List<NoteEntity>) {
        val noteDiffUtils = NotesDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(noteDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(
        private val oldItem: List<NoteEntity>,
        private val newItem: List<NoteEntity>
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


    private var clickOnItem: ((NoteEntity, String) -> Unit)? = null

    //click
    fun clickItem(listener: (NoteEntity, String ) -> Unit) {

        clickOnItem = listener
    }


}

