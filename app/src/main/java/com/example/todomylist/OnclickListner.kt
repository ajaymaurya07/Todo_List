package com.example.todomylist

import com.example.todomylist.db.Todo

interface OnclickListner {
    fun onitemclick(todo: Todo)
    fun onitemupdate(todo: Todo)

}