package ir.hmb72.forexuser.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import ir.hmb72.forexuser.utils.POINT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePoint(entity: PointEntity)

    @Update
    suspend fun updatePoint(entity: PointEntity)

    @Delete
    suspend fun deletePoint(entity: PointEntity)

    @Query("SELECT * FROM $POINT_TABLE")
    fun getAllPoint(): Flow<List<PointEntity>>

    @Query("SELECT * FROM $POINT_TABLE WHERE id==:id")
    fun getOnePoint(id: Int): Flow<PointEntity>
}