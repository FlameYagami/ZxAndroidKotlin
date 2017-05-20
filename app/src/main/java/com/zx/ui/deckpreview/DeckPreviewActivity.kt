package com.zx.ui.deckpreview

import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.bean.DeckPreviewBean
import com.zx.game.utils.DeckUtils
import com.zx.ui.base.BaseActivity
import com.zx.ui.base.BaseRecyclerViewListener
import com.zx.ui.deckeditor.DeckEditorActivity
import com.zx.uitls.FileUtils
import com.zx.uitls.IntentUtils
import com.zx.uitls.JsonUtils
import com.zx.view.dialog.DialogEditText
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class DeckPreviewActivity : BaseActivity(), BaseRecyclerViewListener.OnItemClickListener, BaseRecyclerViewListener.OnItemLongClickListener {
    @BindView(R.id.view_content)
    internal var viewContent: CoordinatorLayout? = null
    @BindView(R.id.rv_deckpreview)
    internal var rvDeckPreview: RecyclerView? = null
    @BindString(R.string.add_succeed)
    internal var addSucceed: String? = null
    @BindString(R.string.update_succeed)
    internal var updateSucceed: String? = null
    @BindString(R.string.delete_succeed)
    internal var deleteSucceed: String? = null
    @BindString(R.string.deck_name_exits)
    internal var deckNameExits: String? = null

    internal var mDeckPreviewAdapter: DeckPreviewAdapter = null!!

    override val layoutId: Int
        get() = R.layout.activity_deck_preview

    override fun initViewAndData() {
        ButterKnife.bind(this)
        val linearLayoutManager = LinearLayoutManager(this)
        mDeckPreviewAdapter = DeckPreviewAdapter(this)
        rvDeckPreview?.layoutManager = linearLayoutManager
        rvDeckPreview?.adapter = mDeckPreviewAdapter

        mDeckPreviewAdapter.setOnItemClickListener(this)
        mDeckPreviewAdapter.setOnItemLongClickListener(this)
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Observable.just<List<DeckPreviewBean>>(DeckUtils.deckPreviewList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { deckPreviewBeanList -> mDeckPreviewAdapter.updateData(deckPreviewBeanList) }
    }

    /**
     * 卡组添加
     */
    @OnClick(R.id.fab_add)
    fun onAdd_Click() {
        DialogEditText(this, getString(R.string.deck_name), "", getString(R.string.deck_name_hint), object : DialogEditText.OnButtonClick {
            override fun getText(dialog: DialogEditText, content: String) {
                val checkDeckName = DeckUtils.checkDeckName(content)
                if (!checkDeckName) {
                    FileUtils.writeFile(JsonUtils.serializer(ArrayList<String>()), DeckUtils.getDeckPath(content))
                    mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                    dialog.dismiss()
                    showSnackBar(viewContent!!, addSucceed.toString())
                } else {
                    showSnackBar(viewContent!!, deckNameExits.toString())
                }
            }
        }).show()
    }

    override fun onItemClick(view: View, data: List<*>, position: Int) {
        DeckEditorActivity.deckPreviewBean = data[position] as DeckPreviewBean
        IntentUtils.gotoActivity(this, DeckEditorActivity::class.java)
    }

    override fun onItemLongClick(view: View, data: List<*>, position: Int) {
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
        DialogEditText(this, getString(R.string.deck_name), deckName, getString(R.string.deck_name_hint), object : DialogEditText.OnButtonClick {
            override fun getText(dialog: DialogEditText, content: String) {
                val checkDeckName = deckName == content || !DeckUtils.checkDeckName(content)
                if (checkDeckName) {
                    FileUtils.renameFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                    mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                    dialog.dismiss()
                    showSnackBar(viewContent!!, updateSucceed.toString())
                } else {
                    showSnackBar(viewContent!!, deckNameExits.toString())
                }
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
        DialogEditText(this, getString(R.string.deck_name), deckName, getString(R.string.deck_name_hint), object : DialogEditText.OnButtonClick {
            override fun getText(dialog: DialogEditText, content: String) {
                val checkDeckName = DeckUtils.checkDeckName(content)
                if (!checkDeckName) {
                    FileUtils.copyFile(DeckUtils.getDeckPath(deckName), DeckUtils.getDeckPath(content))
                    mDeckPreviewAdapter.updateData(DeckUtils.deckPreviewList)
                    dialog.dismiss()
                    showSnackBar(viewContent!!, getString(R.string.copy_succeed))
                } else {
                    showSnackBar(viewContent!!, deckNameExits.toString())
                }
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
        showSnackBar(viewContent!!, deleteSucceed!!)
    }

    companion object {

        private val TAG = DeckPreviewActivity::class.java.simpleName
    }
}
