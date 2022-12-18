package net.doheco.domain.utils

import android.widget.Toast

interface EventHandler<T> {
    fun obtainEvent(event: T)
}

interface EventHandlerToast<T,Toast> {
    fun obtainEvent(event: T, toast:Toast)
}