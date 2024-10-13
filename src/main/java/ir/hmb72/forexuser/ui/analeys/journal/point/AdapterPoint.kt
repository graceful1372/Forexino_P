package ir.hmb72.forexuser.ui.analeys.journal.point

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import ir.hmb72.forexuser.databinding.ItemListPointBinding
import ir.hmb72.forexuser.utils.DELETE
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.MOVE
import javax.inject.Inject


class AdapterPoint @Inject constructor() : RecyclerView.Adapter<AdapterPoint.ViewHolder>() {

    private lateinit var binding: ItemListPointBinding
    private var items = emptyList<PointEntity>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemListPointBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

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
        fun bind(item: PointEntity) {

            binding.apply {
                titlePointTxt.text = item.event
                datePointTxt.text = item.date
                contentPointTxt.text = item.note
                //Click and show Menu
                     root.setOnClickListener {
                         val popupMenu = PopupMenu(context , it  )
     //                    val popupMenu = PopupMenu(context , it ,Gravity.NO_GRAVITY , 0 , R.style.PopupMenuStyle )
                         popupMenu.menuInflater.inflate(R.menu.menu_journal_recycler , popupMenu.menu)

               /*          //change color background menu
                           popupMenu.menu.setGroupDividerEnabled(true)

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
                            R.id.edit_item ->{
                                clickOnItem?.let {
                                    it(item , EDIT)
                                }
                            }
                            R.id.delete_item ->{
                                clickOnItem?.let {
                                    it(item , DELETE)
                                }
                            }
                            R.id.detail_item ->{
                                clickOnItem?.let {
                                    it(item , MOVE)
                                }
                            }

                        }
                        return@setOnMenuItemClickListener true
                    }


                }


            }


        }
    }


    fun setData(data: List<PointEntity>) {
        val noteDiffUtils = NotesDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(noteDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(
        private val oldItem: List<PointEntity>,
        private val newItem: List<PointEntity>
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


    private var clickOnItem: ((PointEntity, String) -> Unit)? = null

    //click
    fun clickItem(listener: (PointEntity, String) -> Unit) {

        clickOnItem = listener
    }


}

