package com.example.todomylist.viewmodel

import android.app.Dialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomylist.db.Todo
import com.example.todomylist.eventbus.Event
import com.example.todomylist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(var repository: Repository):ViewModel() {

    var cancelbtn:MutableLiveData<String> = MutableLiveData()
    var savebtn:MutableLiveData<String> = MutableLiveData()
    var title:MutableLiveData<String> = MutableLiveData()
    var discription:MutableLiveData<String> = MutableLiveData()
    var message:MutableLiveData<Event<String>> = MutableLiveData()
    var data:LiveData<List<Todo>> = repository.getalldata
    init {
        cancelbtn.value="Cancel"
        savebtn.value="Save"
    }

    fun savebtn(){
        if(title.value.isNullOrEmpty() && discription.value.isNullOrEmpty()){
            message.value=Event("all feild mendatory...")
        }
        else{
            viewModelScope.launch(Dispatchers.IO) {
                var rowid=repository.insetmethod(Todo(0,title.value!!,discription.value!!))
                withContext(Dispatchers.Main){
                    if (rowid>0){
                        message.value=Event("successfully inserted...")
                        temp.dismiss()
                        title.value=""
                        discription.value=""
                    }else{
                        message.value=Event("insertion failed..")
                    }
                }
            }
        }

    }



    lateinit var temp:Dialog
    fun dialog(dialog: Dialog){
         temp=dialog

    }

    fun adapterclickeditem(todo: Todo){

            viewModelScope.launch(Dispatchers.IO) {

                var rowid=repository.deleteItem(Todo(todo.id,todo.title,todo.discription))
                withContext(Dispatchers.Main){
                    if (rowid>0){
                        message.value=Event("successfully deleted...")
                        temp.dismiss()
                    }else{
                        message.value=Event("deletion failed..")
                    }
                }
            }
    }

    fun itemupdate(todo:Todo){
        viewModelScope.launch(Dispatchers.IO) {

            var rowid=repository.updateitem(Todo(todo.id,title.value!!,discription.value!!))
            withContext(Dispatchers.Main){
                if (rowid>0){
                    message.value=Event("successfully updateed...")
                    temp.dismiss()
                }else{
                    message.value=Event("updation failed..")
                }
            }
        }

    }

}