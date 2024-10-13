package ir.hmb72.forexuser.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import ir.hmb72.forexuser.data.model.local.chart.MyChartData
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.utils.NOTE_TABLE


import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(entity: NoteEntity)

    @Delete
    suspend fun deleteNote(entity: NoteEntity)

    @Update
    suspend fun updateNote(entity: NoteEntity)


    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noTransaction ASC")
    fun getAllData(): Flow<MutableList<NoteEntity>>


    //Journal Fragment
    @Query("SELECT noTransaction as x , result as y FROM $NOTE_TABLE")
    fun getXY(): Flow<List<MyChartData>>

    @Query("SELECT noTransaction FROM $NOTE_TABLE ORDER BY noTransaction DESC LIMIT 1")
    fun getLastNoTransaction(): LiveData<Int>


    @Query("SELECT * FROM $NOTE_TABLE WHERE id == :number ")
    fun getDetail(number: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE noTransaction == :number ")
    fun isItemExist(number: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id == :number ")
    fun getDataWithId(number: Int): Flow<NoteEntity>

    @Query("UPDATE $NOTE_TABLE SET uri = :uri WHERE noTransaction = :number")
    fun updateImage(uri: String, number: Int)

    //Calendar fragment
    @Query("SELECT * FROM $NOTE_TABLE WHERE dateTime LIKE '%' || :number || '%' ")
    fun searchDate(number: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE dateTime BETWEEN :startDateString AND :endDateString ORDER BY dateTime ASC")
    fun searchNotesBetweenDates(
        startDateString: String,
        endDateString: String
    ): Flow<List<NoteEntity>>

    @Query("SELECT noTransaction as x , result as y FROM $NOTE_TABLE WHERE dateTime BETWEEN :startDateString AND :endDateString ORDER BY dateTime ASC")
    fun getXyWithDate(startDateString: String, endDateString: String): Flow<List<MyChartData>>

    @Query("SELECT noTransaction as x , result as y FROM $NOTE_TABLE WHERE dateTime LIKE '%' || :date || '%' ")
    fun getXyDaily(date: String): Flow<List<MyChartData>>

}

