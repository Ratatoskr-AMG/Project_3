package ru.ratatoskr.doheco.domain.utils

interface EventHandler<T> {
    fun obtainEvent(event: T)
}