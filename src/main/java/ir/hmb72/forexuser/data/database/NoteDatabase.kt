package ir.hmb72.forexuser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.model.local.room.PointEntity

@Database(entities = [NoteEntity::class , PointEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun pointDao(): PointDao
}