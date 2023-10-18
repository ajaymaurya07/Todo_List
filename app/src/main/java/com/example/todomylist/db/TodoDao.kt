package com.example.todomylist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Insert
    suspend fun insertData(todo: Todo):Long

    @Query("SELECT * FROM todotable")
    fun getdata():LiveData<List<Todo>>

    @Delete
    suspend fun delete(todo: Todo):Int

    @Update
    suspend fun update(todo: Todo):Int

}
//@Dao
//interface SubscriberDao {
//    @Insert
//    suspend fun insertSubscriber(subscriber: Subscriber):Long

//    @Query("SELECT * FROM My_subscriber ")
//    fun getAlldata() : LiveData<List<Subscriber>>
//
//    @Update
//    suspend fun updateData(subscriber: Subscriber):Int
//
//    @Delete
//    suspend fun deleteSingleData(subscriber: Subscriber):Int
//
//    @Query("delete from My_subscriber")
//    suspend fun deleteAlldata():Int

//}