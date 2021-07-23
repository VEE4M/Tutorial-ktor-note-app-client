package com.androiddevs.ktornoteapp.other

/**
 *Veli-Matti Tikkanen, 25.6.2021
 */
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
    private set

    fun getContentIfNotHandled() = if(hasBeenHandled){
        null
    }else{
        hasBeenHandled = true
        content
    }

    fun peekContent() = content
}