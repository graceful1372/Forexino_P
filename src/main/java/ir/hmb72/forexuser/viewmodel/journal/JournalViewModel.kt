package ir.hmb72.forexuser.viewmodel.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import ir.hmb72.forexuser.data.model.local.chart.MyChartData
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.repositiory.my_list.journal.JournalRepository
import ir.hmb72.forexuser.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(private val repository: JournalRepository) : ViewModel() {

    val noteData = MutableLiveData<DataStatus<List<NoteEntity>>>()

    fun allNote() = viewModelScope.launch {
        repository.getAllNote()

            .collect {
                noteData.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }

    fun deleteAll(entity: NoteEntity) = viewModelScope.launch {
        repository.delete(entity)
    }

    private val _chartData = MutableLiveData<List<MyChartData>>()
    val chartData: LiveData<List<MyChartData>> get() = _chartData
    fun getXY() = viewModelScope.launch {
        repository.getXY().collect() {

            _chartData.postValue(it)
        }
    }

}


