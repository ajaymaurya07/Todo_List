package com.example.todomylist.eventbus

class Event<out T>(var message: @UnsafeVariance T) {
    fun gethandle():T{
        return message
    }
}