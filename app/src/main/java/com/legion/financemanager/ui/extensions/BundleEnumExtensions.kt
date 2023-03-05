package com.legion.financemanager.ui.extensions

import android.os.Bundle

fun Bundle.putEnum(key: String, enum: Enum<*>){
    putInt(key, enum.ordinal)
}

inline fun <reified T: Enum<T>> Bundle.getEnum(key: String): T {
    val ordinal = getInt(key)
    return enumValues<T>()[ordinal]
}