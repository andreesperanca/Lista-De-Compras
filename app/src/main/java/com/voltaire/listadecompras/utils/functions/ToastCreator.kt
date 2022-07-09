package com.voltaire.listadecompras.utils.functions

import android.content.Context
import android.widget.Toast

fun toastCreator(context: Context, message : String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}