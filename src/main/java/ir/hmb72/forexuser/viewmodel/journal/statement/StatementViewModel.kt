package ir.hmb72.forexuser.viewmodel.journal.statement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.local.chart.MyChartData
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.repositiory.my_list.journal.statement.StatementRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatementViewModel @Inject constructor(private val repository: StatementRepository):ViewModel() {

    val resultSearch = MutableLiveData<List<NoteEntity>>()
    fun searchDate(long: String) = viewModelScope.launch {
        repository.searchDate(long).collect{
            resultSearch.postValue(it)
        }
    }

    val resultSearchTwoDate = MutableLiveData<List<NoteEntity>>()
    fun searchBetweenDate(one:String , two:String) = viewModelScope.launch {
        repository.searchNotesBetweenDates(one , two).collect{
            resultSearchTwoDate.postValue(it)
        }
    }

    val xyWithDate = MutableLiveData<List<MyChartData>>()
    fun getXyWithRangeDate(one:String, two:String) = viewModelScope.launch {
        repository.getXyWithDate(one , two)
            .map { listChart->

            listChart.foldIndexed(listOf<MyChartData>()){ index, acc, curr ->
                    val previousY = acc.lastOrNull()?.y ?: 0f
                    val newY = previousY + curr.y
                    acc + MyChartData(index.toFloat() + 1f , newY)

                }

            }
            .collect{
                xyWithDate.postValue(it)
            }
    }

    val xyDaily = MutableLiveData<List<MyChartData>>()
    fun getXyDaily(date:String) = viewModelScope.launch {
        repository.getXyDaily(date)
            .map {list->
                list.fold(listOf<MyChartData>()){ acc, curr ->
                    val perviousy = acc.lastOrNull()?.y ?:0f
                    val newY = perviousy + curr.y
                    acc + MyChartData(curr.x , newY)

                }
            }
            .collect{
                xyDaily.postValue(it)
            }
    }


}