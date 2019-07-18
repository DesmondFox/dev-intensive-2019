package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val view = window.decorView.rootView
    val visibleRect = Rect()
    view.getWindowVisibleDisplayFrame(visibleRect)


    // Костыль! Обычно тесты проходят без навбара, а в телефонах он есть часто.
    // А значит введем некую переменную высоты некоего навбара
    val floatingNavBarHeight = 200
    val fullRect = Rect()
    view.getLocalVisibleRect(fullRect)
    fullRect.also {
        it.top = visibleRect.top
        it.bottom -= floatingNavBarHeight
    }
    return visibleRect.height() < fullRect.height()
}

fun Activity.isKeyboardClosed(): Boolean = !isKeyboardOpen()