package ir.hmb72.forexuser.viewmodel.journal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.repositiory.my_list.journal.RepositoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val reposiotry: RepositoryDetail) : ViewModel() {

     val detail = MutableLiveData<NoteEntity>()
    fun getDetail(number: Int) = viewModelScope.launch {
        reposiotry.getDetail(number).collect {
            detail.postValue(it)
        }


    }

    val imageList = MutableLiveData<List<String>>()
  /*  fun gerImage(id:Int) = viewModelScope.launch {
        reposiotry.getImage(id).collect{
         imageList.postValue(it)
        }
    }*/

    fun updateImage(uri:String , number: Int) = viewModelScope.launch(Dispatchers.IO) {
        reposiotry.updateImage(uri , number)
    }
}