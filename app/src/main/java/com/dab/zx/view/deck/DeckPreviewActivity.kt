package com.dab.zx.view.deck

import android.databinding.ViewDataBinding
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.dab.zx.R
import com.dab.zx.bean.DeckPreviewBean
import com.dab.zx.config.MyApp
import com.dab.zx.game.utils.DeckUtils
import com.dab.zx.uc.dialog.DialogEditText
import com.dab.zx.uitls.FileUtils
import com.dab.zx.uitls.JsonUtils
import com.dab.zx.view.base.BaseBindingActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_deck_preview.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class DeckPreviewActivity : BaseBindingActivity() {

    internal lateinit var mDeckPreviewAdapter: DeckPreviewAdapter

    companion object {
        private val TAG = DeckPreviewActivity::class.java.simpleName
        internal var mAddSucceed: String = MyApp.context.getString(R.string.add_succeed)
        internal var mUpdateSucceed: String = MyApp.context.getString(R.string.update_succeed)
        internal var mDeleteSucceed: String = MyApp.context.getString(R.string.delete_succeed)
        internal var mDeckNameExits: String = MyApp.context.getString(R.string.deck_name_exits)
    }

    override val layoutId: Int
        get() = R.layout.activity_deck_preview

    override fun initViewAndData(mViewDataBinding: ViewDataBinding) {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        mDeckPreviewAdapter = DeckPreviewAdapter(this)
        rvDeckPreview.layoutManager = LinearLayoutManager(this)
        rvDeckPreview.adapter = mDeckPreviewAdapter
        mDeckPreviewAdapter.setOnItemClickListener { _: View, _: List<*>, _: Int -> this::onItemClick }
        mDeckPreviewAdapter.setOnItemLongClickListener { _: View, _: List<*>, _: Int -> this::onItemLongClick }
        fabAdd.onClick { onAdd_Click() }
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
        DialogEditText(this, getString(R.string.deck_pre_name), "", getString(R.string.deck_pre_name_hint), { dialog, content ->
            val checkDeckName = DeckUtils.checkDeckName(content)
            if (!checkDeckName) {
                FileUtils.writeFile(JsonUtils.serializer(ArrayList<String>()), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(mAddSucceed.toString())
            } else {
                showSnackBar(mDeckNameExits.toString())
            }
        }).show()
    }

    fun onItemClick(data: List<*>, position: Int) {
        DeckEditorActivity.deckPreviewBean = data[position] as DeckPreviewBean
        startActivity<DeckEditorActivity>()
    }

    fun onItemLongClick(data: List<*>, position: Int) {
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
        DialogEditText(this, getString(R.string.deck_pre_name), deckName, getString(R.string.deck_pre_name_hint), { dialog, content ->
            val checkDeckName = deckName == content || !DeckUtils.checkDeckName(content)
            if (checkDeckName) {
                FileUtils.renameFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(mUpdateSucceed)
            } else {
                showSnackBar(mDeckNameExits)
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
        DialogEditText(this, getString(R.string.deck_pre_name), deckName, getString(R.string.deck_pre_name_hint), { dialog, content ->
            val checkDeckName = DeckUtils.checkDeckName(content)
            if (!checkDeckName) {
                FileUtils.copyFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                dialog.dismiss()
                showSnackBar(getString(R.string.copy_succeed))
            } else {
                showSnackBar(mDeckNameExits)
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
        showSnackBar(mDeleteSucceed)
    }
}
