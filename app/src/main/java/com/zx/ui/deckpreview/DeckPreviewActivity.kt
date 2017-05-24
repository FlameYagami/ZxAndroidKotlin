package com.zx.ui.deckpreview

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zx.R
import com.zx.bean.DeckPreviewBean
import com.zx.game.utils.DeckUtils
import com.zx.ui.base.BaseActivity
import com.zx.ui.deckeditor.DeckEditorActivity
import com.zx.uitls.FileUtils
import com.zx.uitls.IntentUtils
import com.zx.uitls.JsonUtils
import com.zx.view.dialog.DialogEditText
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_deck_preview.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class DeckPreviewActivity : BaseActivity() {
    internal var mDeckPreviewAdapter: DeckPreviewAdapter = null!!

    companion object {
        private val TAG = DeckPreviewActivity::class.java.simpleName
        internal var mAddSucceed: String? = null
        internal var mUpdateSucceed: String? = null
        internal var mDeleteSucceed: String? = null
        internal var mDeckNameExits: String? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_deck_preview

    override fun initViewAndData() {
        mAddSucceed = resources.getString(R.string.add_succeed)
        mUpdateSucceed = resources.getString(R.string.update_succeed)
        mDeleteSucceed = resources.getString(R.string.delete_succeed)
        mDeckNameExits = resources.getString(R.string.deck_name_exits)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        val linearLayoutManager = LinearLayoutManager(this)
        mDeckPreviewAdapter = DeckPreviewAdapter(this)
        rv_deckpreview.layoutManager = linearLayoutManager
        rv_deckpreview.adapter = mDeckPreviewAdapter

        mDeckPreviewAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::onItemClick }
        mDeckPreviewAdapter.setOnItemLongClickListener { _: View, _: List<*>, _: Int -> this::onItemLongClick }
        fab_add.onClick { onAdd_Click() }
    }

    override fun onResume() {
        super.onResume()
        Observable.just(DeckUtils.deckPreviewList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { deckPreviewBeanList -> mDeckPreviewAdapter.updateData(deckPreviewBeanList) }
    }

    /**
     * 卡组添加
     */
    fun onAdd_Click() {
        DialogEditText(this, getString(R.string.deck_name), "", getString(R.string.deck_name_hint), { dialog, content ->
            val checkDeckName = DeckUtils.checkDeckName(content)
            if (!checkDeckName) {
                FileUtils.writeFile(JsonUtils.serializer(ArrayList<String>()), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(view_content, mAddSucceed.toString())
            } else {
                showSnackBar(view_content, mDeckNameExits.toString())
            }
        }).show()
    }

    fun onItemClick(view: View, data: List<*>, position: Int) {
        DeckEditorActivity.deckPreviewBean = data[position] as DeckPreviewBean
        IntentUtils.gotoActivity(this, DeckEditorActivity::class.java)
    }

    fun onItemLongClick(view: View, data: List<*>, position: Int) {
        AlertDialog.Builder(this).setItems(arrayOf("重命名", "卡组另存", "卡组删除")) { dialog, which ->
            dialog.dismiss()
            if (which == 0) {
                renameDeck(data, position)
            } else if (which == 1) {
                copyDeck(data, position)
            } else {
                deleteDeck(data, position)
            }
        }.show()
    }

    /**
     * 重命名卡组

     * @param data     数据源
     * *
     * @param position 数据位置
     */
    private fun renameDeck(data: List<*>, position: Int) {
        val deckName = (data[position] as DeckPreviewBean).deckName
        DialogEditText(this, getString(R.string.deck_name), deckName, getString(R.string.deck_name_hint), { dialog, content ->
            val checkDeckName = deckName == content || !DeckUtils.checkDeckName(content)
            if (checkDeckName) {
                FileUtils.renameFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(view_content, mUpdateSucceed.toString())
            } else {
                showSnackBar(view_content, mDeckNameExits.toString())
            }
        }).show()
    }

    /**
     * 复制卡组

     * @param data     数据源
     * *
     * @param position 数据位置
     */
    private fun copyDeck(data: List<*>, position: Int) {
        val deckName = (data[position] as DeckPreviewBean).deckName
        DialogEditText(this, getString(R.string.deck_name), deckName, getString(R.string.deck_name_hint), { dialog, content ->
            val checkDeckName = DeckUtils.checkDeckName(content)
            if (!checkDeckName) {
                FileUtils.copyFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(view_content, getString(R.string.copy_succeed))
            } else {
                showSnackBar(view_content, mDeckNameExits.toString())
            }
        }).show()
    }

    /**
     * 删除卡组

     * @param data     数据源
     * *
     * @param position 数据位置
     */
    private fun deleteDeck(data: List<*>, position: Int) {
        val deckName = (data[position] as DeckPreviewBean).deckName
        FileUtils.deleteFile(DeckUtils.getDeckPath(deckName))
        mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
        showSnackBar(view_content, mDeleteSucceed!!)
    }
}
