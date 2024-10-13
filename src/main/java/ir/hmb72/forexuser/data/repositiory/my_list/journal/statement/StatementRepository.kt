package ir.hmb72.forexuser.data.repositiory.my_list.journal.statement

import ir.hmb72.forexuser.data.database.NoteDao
import javax.inject.Inject

class StatementRepository @Inject constructor(private val dao:NoteDao) {

    fun searchDate(long:String) = dao.searchDate(long)

    fun searchNotesBetweenDates(one:String,two:String) = dao.searchNotesBetweenDates(one , two)

    fun getXyWithDate(one:String,two:String) = dao.getXyWithDate(one , two)

    fun getXyDaily(date:String) = dao.getXyDaily(date)
}