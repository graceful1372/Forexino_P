package ir.hmb72.forexuser.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val listTime = listOf(
        Item("2024-09-16T01:00:00-04:00"),
        Item("2024-09-16T02:00:00-04:00"),
        Item("2024-09-16T03:00:00-04:00"),
        Item("2024-09-16T04:00:00-04:00"),
        Item("2024-09-16T05:00:00-04:00"),
        Item("2024-09-16T06:00:00-04:00")
    )

//    val itemTime = listTime[0].clockDate
    val formatter = DateTimeFormatter.ISO_DATE_TIME
//    val itemConverted = ZonedDateTime.parse(itemTime, formatter)

    val currentDateTime = ZonedDateTime.now()
    println("Current time : $currentDateTime")

   // convert
    val currentTimeUTC = currentDateTime.withZoneSameInstant(ZoneOffset.UTC)
    println("Current time UTC : $currentTimeUTC")




//    println("dateT1Utc = $currentTimeUTC")

//    for (item in listTime){
//        val itemConverted1 = ZonedDateTime.parse(item.clockDate, formatter)
//        val date2 = itemConverted1.withZoneSameInstant(ZoneOffset.UTC)
//        println("date2 = $date2")
//        when {
//            date2.isBefore(currentTimeUTC) -> println("زمان مشخص شده در گذشته است.")
//            date2.isAfter(currentTimeUTC) -> println("زمان مشخص شده در آینده است.")
//            else -> println("زمان مشخص شده برابر با زمان حال است.")
//        }
//    }
    /*---------------------*/
    var sortedList = mutableListOf<Item>()
    val afterList = mutableListOf<Item>()
    val beforeList = mutableListOf<Item>()

    for (item in listTime) {
        //Convert to standard format
        val itemConverted = ZonedDateTime.parse(item.clockDate, formatter)
        println("item in for loop = $itemConverted")
        //Convert to UTC format
        val itemClockUTC = itemConverted.withZoneSameInstant(ZoneOffset.UTC)
        println("             UTC = $itemClockUTC")

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
    println("afterList : $afterList")
    println("beforeList : $beforeList")
    println("sortedList : $sortedList")


}

/*fun call() {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val dateTime1String = "2024-09-14T07:23:00-04:00"
    val dateTime2String = "2024-09-14T10:34:39.111399900+03:30[Asia/Tehran]"

    val dateTime1 = ZonedDateTime.parse(dateTime1String, formatter)
    val dateTime2 = ZonedDateTime.parse(dateTime2String, formatter)

    // تبدیل هر دو تاریخ و زمان به UTC (جهت اطمینان)
    val dateTime1InUTC = dateTime1.withZoneSameInstant(ZoneOffset.UTC)
    val dateTime2InUTC = dateTime2.withZoneSameInstant(ZoneOffset.UTC)

    // محاسبه اختلاف زمانی
    val duration = Duration.between(dateTime1InUTC, dateTime2InUTC)
    val hours = duration.toHours()
//        val minutes = duration.toMinutesPart()
//        val seconds = duration.toSecondsPart()

    println("اختلاف زمانی: $hours ساعت  ")
}*/

data class Sample(val name: String, val date: String)
data class Item(val clockDate: String)