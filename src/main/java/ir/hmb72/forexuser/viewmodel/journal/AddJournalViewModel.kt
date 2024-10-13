package ir.hmb72.forexuser.viewmodel.journal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.repositiory.my_list.journal.RepositoryAdd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddJournalViewModel @Inject constructor(
    private val repository: RepositoryAdd,
) : ViewModel() {


    fun saveNote(isEdit: Boolean, entity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        if (isEdit) {
            repository.update(entity)
        } else {
            repository.saveNote(entity)
        }


    }

    val dataEdit = MutableLiveData<NoteEntity>()
    fun getDetail(number: Int) = viewModelScope.launch {
        repository.getDetail(number).collect {
            dataEdit.postValue(it)
        }


    }

    val typeData = MutableLiveData<MutableList<String>>()
    fun loadTypeData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf("SUPPLY & DEMAND", "CLASSIC")
        typeData.postValue(data)

    }

    //Show number transaction
    private var _numberTransaction =MutableLiveData<Int>()
    val numberTransaction: LiveData<Int> = _numberTransaction
//    fun getNumberTransaction()=viewModelScope.launch {
//        val number= repository.getLastNumberTransaction()
//        Log.e("Number", "getNumberTransaction: $number ", )
//        _numberTransaction.postValue(10)
//    }

    fun getNumberTransaction()=viewModelScope.launch {
        repository.getLastNumberTransaction().observeForever { number ->
        _numberTransaction.postValue(number?:0)
            Log.e("Number", "getNumberTransaction: $number ")
        }
    }

}


