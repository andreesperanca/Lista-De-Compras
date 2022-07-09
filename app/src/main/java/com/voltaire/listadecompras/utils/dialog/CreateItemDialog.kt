package com.voltaire.listadecompras.utils.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.databinding.DialogAddItemBinding
import com.voltaire.listadecompras.utils.Constants.Companion.CANCEL_MESSAGE
import com.voltaire.listadecompras.utils.Constants.Companion.INVALID_DATA_MESSAGE
import com.voltaire.listadecompras.utils.functions.toastCreator
import com.voltaire.listadecompras.utils.functions.validateEditText

class CreateItemDialog(
    private val context: Context,
    val idList : Long,
    var createItem: (newItem : Item) -> Unit,
) {

    fun show() {
        DialogAddItemBinding.inflate(LayoutInflater.from(context)).apply {
            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->

                    if (
                        validateEditText(this.nameItem) &&
                        validateEditText(this.amount) &&
                        validateEditText(this.price))
                    {
                        createItem(  Item(
                            0,
                            idList,
                            this.nameItem.text.toString(),
                            this.price.text.toString(),
                            this.amount.text.toString()
                        ))

                    } else {
                        toastCreator(context, INVALID_DATA_MESSAGE)
                    }
                }.setNegativeButton("Cancelar") { _, _ ->
                    toastCreator(context, CANCEL_MESSAGE)

                }
                .show()
        }
    }
}