package ir.hmb72.forexuser.data.repositiory.my_list.journal.point

import ir.hmb72.forexuser.data.database.PointDao
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import javax.inject.Inject



class PointRepository @Inject constructor(private val dao: PointDao) {
    suspend fun save (entity: PointEntity) = dao.savePoint(entity)
    suspend fun update (entity: PointEntity) = dao.updatePoint(entity)
    suspend fun delete (entity: PointEntity) = dao.deletePoint(entity)
    fun getAllPoint() = dao.getAllPoint()
    fun getOnePoint(id:Int) = dao.getOnePoint(id)

}