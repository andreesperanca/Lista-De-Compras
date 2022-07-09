package com.voltaire.listadecompras.utils.functions

import android.widget.EditText

fun validateEditText(editText  : EditText) : Boolean{
    val text = editText.text
    return text != null && text.isNotBlank() && text.isNotEmpty()
}