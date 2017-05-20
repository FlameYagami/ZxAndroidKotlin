package com.zx.view.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R

/**
 * Created by 八神火焰 on 2017/2/9.
 */

class DialogVersusPersonal(context: Context, private val onButtonClick: DialogVersusPersonal.OnButtonClick) : AlertDialog(context) {

    interface OnButtonClick {
        fun getButtonType(dialog: DialogVersusPersonal, type: ButtonType)
    }

    enum class ButtonType {
        JoinRoom,
        CreateRoom
    }

    init {
        val view = View.inflate(context, R.layout.dialog_versus_psersonal, null)
        ButterKnife.bind(this, view)
        setView(view)
        setTitle(context.getString(R.string.versus_personal))
    }

    @OnClick(R.id.btn_join_room, R.id.btn_create_room)
    fun onButton_Click(view: View) {
        when (view.id) {
            R.id.btn_join_room -> onButtonClick.getButtonType(this, ButtonType.JoinRoom)
            R.id.btn_create_room -> onButtonClick.getButtonType(this, ButtonType.CreateRoom)
        }
    }
}