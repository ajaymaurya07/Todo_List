package com.example.todomylist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todotable")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("rowId")
    var id:Int,

    @ColumnInfo("title")
    var title:String,

    @ColumnInfo("discription")
    var discription:String
)
