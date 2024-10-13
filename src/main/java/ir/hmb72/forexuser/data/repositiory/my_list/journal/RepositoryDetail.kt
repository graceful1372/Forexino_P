package ir.hmb72.forexuser.data.repositiory.my_list.journal

import ir.hmb72.forexuser.data.database.NoteDao
import javax.inject.Inject

class RepositoryDetail @Inject constructor(private val  dao:NoteDao) {

    fun getDetail(number:Int) = dao.getDetail(number)

    suspend fun updateImage(uri:String , number: Int)  = dao.updateImage(uri , number)


}