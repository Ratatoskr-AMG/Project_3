package ru.ratatoskr.project_3.domain.extensions

import androidx.lifecycle.MutableLiveData

// Set default value for any type of MutableLiveData
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

// Set new value for any tye of MutableLiveData
fun <T> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }

// List to ArrayList
fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}