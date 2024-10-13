package ir.hmb72.forexuser.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hmb72.forexuser.data.model.network.home.ResponseNews.ResponseNewsItem
import ir.hmb72.forexuser.databinding.ItemListNewsBinding
import ir.hmb72.forexuser.utils.base.BaseDiffUtils
import javax.inject.Inject
import android.util.Log
import androidx.annotation.RequiresApi
import ir.hmb72.forexuser.R
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AdapterNews @Inject constructor() : RecyclerView.Adapter<AdapterNews.ViewHolder>() {
    private lateinit var binding: ItemListNewsBinding
    private var items = emptyList<ResponseNewsItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.ViewHolder {
        binding = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterNews.ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()


    inner class ViewHolder(private val binding: ItemListNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ResponseNewsItem) {
            binding.apply {
                titleTxt.text = item.title
                //split date
                item.date?.let { dateString ->
                    val (date, time) = dateString.split("T")
                    val dateCheck = date.substring(0, 10) // Extract date portion
                    dateTxt.text = dateCheck
                }
                clockTxt.text = item.date?.let { timeToZoneAsiaTehran(it)}

                countryTxt.text = item.country
                //Check impact
                when (item.impact?.lowercase()) {
                    "low" -> impactImg.setImageResource(R.drawable.bg_impact_low)
                    "high" -> impactImg.setImageResource(R.drawable.bg_impact_hight)
                    "medium" -> impactImg.setImageResource(R.drawable.bg_impact_medium)
                    else -> {

                    }
                }



            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setData(data: List<ResponseNewsItem>) {
        val checkedData = filterListData(data)

        val adapterDiffUtils = BaseDiffUtils(items, checkedData)
        val diffResult = DiffUtil.calculateDiff(adapterDiffUtils)
        items = checkedData
        diffResult.dispatchUpdatesTo(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterListData(data:List<ResponseNewsItem>):List<ResponseNewsItem>{

        val todaysItem = data.filter{item->
            item.date?.let{dateString->
                val (dateSplit,_) = dateString.split("T")
                val date = LocalDate.now()
                dateSplit == date.toString()

            }?: false
        }



        //Setting the news that has passed since the current hour

        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val currentDateTime = ZonedDateTime.now()

        // convert
        val currentTimeUTC = currentDateTime.withZoneSameInstant(ZoneOffset.UTC)


        var sortedList: MutableList<ResponseNewsItem>
        val afterList = mutableListOf<ResponseNewsItem>()
        val beforeList = mutableListOf<ResponseNewsItem>()

        for (item in todaysItem) {
            //Convert to standard format
            val itemConverted = ZonedDateTime.parse(item.date, formatter)
            //Convert to UTC format
            val itemClockUTC = itemConverted.withZoneSameInstant(ZoneOffset.UTC)
            when {
                itemClockUTC.isBefore(currentTimeUTC) -> {
                    beforeList.add(item)
                }

                itemClockUTC.isAfter(currentTimeUTC) -> {
                    afterList.add(item)
                }

            }
        }
        sortedList = (afterList + beforeList).toMutableList()



        return sortedList

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeToZoneAsiaTehran(dateString:String):String{
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // convert to Standard format
        val zonedDateTime = ZonedDateTime.parse(dateString , formatter) //convert to Zone date format
        val tehranZone = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Tehran")) //Convert to asia tehran zone
        val dateStringTehran = tehranZone.format(formatter)//convert to String
        val (date, time) = dateStringTehran.split("T") //Split String
        return time.substring(0,5)//Chose 5 character of Time => 12:00

    }


}