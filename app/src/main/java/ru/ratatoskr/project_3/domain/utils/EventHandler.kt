package ru.ratatoskr.project_3.domain.utils

interface EventHandler<T> {
    fun obtainEvent(event: T)
}