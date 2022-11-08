package net.doheco.domain.utils

interface EventHandler<T> {
    fun obtainEvent(event: T)
}