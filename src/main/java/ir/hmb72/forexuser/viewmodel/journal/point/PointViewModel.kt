package ir.hmb72.forexuser.viewmodel.journal.point

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import ir.hmb72.forexuser.data.repositiory.my_list.journal.point.PointRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(private val rep: PointRepository) : ViewModel() {

    fun save(isEdit: Boolean, entity: PointEntity) = viewModelScope.launch {
        if (isEdit) {
            rep.update(entity)
        } else {
            rep.save(entity)
        }
    }

    fun delete(entity: PointEntity) = viewModelScope.launch {
        rep.delete(entity)
    }

    val showAllData = MutableLiveData<List<PointEntity>>()
    fun getAllPoint() = viewModelScope.launch {
        rep.getAllPoint().collect {
            showAllData.postValue(it)
        }
    }

    // used to fragment DetailPoint
    val showOneData = MutableLiveData<PointEntity>()
    fun getOnePoint(id: Int) = viewModelScope.launch {
        rep.getOnePoint(id).collect{
            showOneData.postValue(it)
        }
    }

}
