package com.zx.view.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import com.zx.R

/**
 * Created by 八神火焰 on 2017/2/9.
 */

class DialogVersusPersonal(context: Context, private val onButtonClick: (dialog: DialogVersusPersonal, type: ButtonType) -> Unit) : AlertDialog(context) {

    var OnButtonClick: ((dialog: DialogVersusPersonal, type: ButtonType) -> Unit)? = null

    enum class ButtonType {
        JoinRoom,
        CreateRoom
    }

    init {
        val view = View.inflate(context, R.layout.dialog_versus_psersonal, null)
        OnButtonClick = onButtonClick
        setView(view)
        setTitle(context.getString(R.string.versus_personal))
//        btn_join_room.onClick(context,) { _ -> onButton_Click() }
//        btn_create_room.onClick { _ -> onButton_Click() }
    }

    fun onButton_Click(view: View) {
        when (view.id) {
            R.id.btn_join_room -> onButtonClick.invoke(this, ButtonType.JoinRoom)
            R.id.btn_create_room -> onButtonClick.invoke(this, ButtonType.CreateRoom)
        }
    }
}
