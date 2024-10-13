package ir.hmb72.forexuser.data.model.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.hmb72.forexuser.utils.POINT_TABLE

@Entity(tableName = POINT_TABLE)
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var note: String = "",
    var event: String = "",
    var date: String = "",
)
