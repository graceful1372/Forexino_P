package ir.hmb72.forexuser.data.repositiory.my_list.journal

import ir.hmb72.forexuser.data.database.NoteDao
import ir.hmb72.forexuser.data.model.local.chart.MyChartData
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JournalRepository @Inject constructor(private val dao: NoteDao) {

    suspend fun delete(entity: NoteEntity) = dao.deleteNote(entity)
    fun getAllNote() = dao.getAllData()
    fun getXY(): Flow<List<MyChartData>> {
        return dao.getXY().map { value ->
            /*If the user wants to delete or edit her/his data, we must first sort the
            list in order so that the chart does not get confused*/
            val sortedList = value.sortedBy { it.x }
            sortedList.fold(listOf<MyChartData>()) { acc, curr -> // accumulator , current value
                val previousY = acc.lastOrNull()?.y ?: 0f
                val newY = previousY + curr.y
                acc + MyChartData(curr.x, newY)
            }
        }
    }


}
