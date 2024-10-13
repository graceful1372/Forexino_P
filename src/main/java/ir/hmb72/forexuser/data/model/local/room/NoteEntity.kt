package ir.hmb72.forexuser.data.model.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.hmb72.forexuser.utils.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var noTransaction:Int = 0,
    var result: Double = 0.0,
    var value: Double = 0.0,
    var symbol: String = "",
    var type: String = "",
    var description: String? ="",
    var uri:String?="",
    var dateTime:String = ""


    )
