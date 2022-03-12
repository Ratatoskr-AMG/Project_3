package ru.ratatoskr.project_3.domain.base

interface EventHandler<T> {
    fun obtainEvent(event: T)
}