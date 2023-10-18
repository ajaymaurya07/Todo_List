package com.example.todomylist.repository

import androidx.lifecycle.LiveData
import com.example.todomylist.db.Todo
import com.example.todomylist.db.TodoDao

class Repository(var todoDao: TodoDao) {

    suspend fun insetmethod(todo: Todo):Long{
        return todoDao.insertData(todo)
    }

    var getalldata:LiveData<List<Todo>> =todoDao.getdata()

    suspend fun deleteItem(todo: Todo):Int{
        return todoDao.delete(todo)
    }

    suspend fun updateitem(todo: Todo):Int{
        return todoDao.update(todo)
    }



}