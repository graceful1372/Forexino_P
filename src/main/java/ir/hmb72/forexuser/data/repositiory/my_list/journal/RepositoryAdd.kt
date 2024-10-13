package ir.hmb72.forexuser.data.repositiory.my_list.journal

import ir.hmb72.forexuser.data.database.NoteDao
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import javax.inject.Inject

class RepositoryAdd @Inject constructor(private val dao: NoteDao) {

    fun getDetail(number:Int) = dao.getDetail(number)

    suspend fun saveNote(entity: NoteEntity) = dao.saveNote(entity)

    suspend fun update(entity: NoteEntity) {
        dao.updateNote(entity)
    }

    fun getLastNumberTransaction()=dao.getLastNoTransaction()

    fun isItemExist(number:Int) = dao.isItemExist(number)

}