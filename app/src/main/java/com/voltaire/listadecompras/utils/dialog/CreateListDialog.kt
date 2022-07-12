package com.voltaire.listadecompras.utils.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.databinding.DialogAddListBinding
import com.voltaire.listadecompras.utils.Constants.Companion.CANCEL_MESSAGE
import com.voltaire.listadecompras.utils.Constants.Companion.INVALID_DATA_MESSAGE
import com.voltaire.listadecompras.utils.functions.toastCreator

class CreateListDialog() {

    fun show(context : Context, createList: (newList: MarketList) -> Unit = {})
    {
        DialogAddListBinding
            .inflate(LayoutInflater.from(context)).apply {

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val nameNewList = this.txtDialogAdd.text
                        if (nameNewList != null && nameNewList.isNotBlank() && nameNewList.isNotEmpty()) {
                            val newMarketList = MarketList(0, nameNewList.toString())
                            createList(newMarketList)
                        } else {
                            toastCreator(context, INVALID_DATA_MESSAGE)
                        }
                    }
                    .setNegativeButton("Cancelar") { _, _ ->
                        toastCreator(context, message = CANCEL_MESSAGE)
                    }
                    .show()
            }
    }
}