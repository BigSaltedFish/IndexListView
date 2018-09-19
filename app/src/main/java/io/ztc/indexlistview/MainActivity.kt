package io.ztc.indexlistview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import io.ztc.indexlistview.IndexListUtils.PinyinComparator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), IndexBaseRecHolder.IndexItemClickListener {

    override fun onItemClick(parent: ViewGroup?, itemView: View?, position: Int) {

    }

    var context: Context? = null
    var mDatas: ArrayList<SortModel>? = null
    var sortAdapter: UserAdapter? = null
    var indexScrollHolder: IndexScrollHolder? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        getDatas()

        sortAdapter = UserAdapter(list)
        sortAdapter!!.setItemClickListener(this)
        val stickyDelegate = object : IndexDivider.StickyDelegate() {
            override fun isCategoryFistItem(position: Int): Boolean {
                return sortAdapter!!.isCategory(position)
            }

            override fun getCategoryName(position: Int): String {
                return sortAdapter!!.getItem(position).sortLetters[0].toString()
            }

            override fun getFirstVisibleItemPosition(): Int {
                return indexScrollHolder!!.findFirstVisibleItemPosition()
            }
        }

        list.addItemDecoration(IndexDivider.newShapeDivider()
                .setStartSkipCount(0)
                .setMarginLeftDp(15)
                .setMarginRightDp(15)
                .setDelegate(stickyDelegate))
        indexScrollHolder = IndexScrollHolder.newInstance(list, object : IndexScrollHolder.SimpleDelegate() {
            override fun getCategoryHeight(): Int {
                return stickyDelegate.categoryHeight
            }
        })

        /**设置左侧触摸监听 */
        sideBar.setOnTouchLetterChangedListener { s ->
            val position = sortAdapter!!.getPositionForCategory(s[0].toInt())
            if (position != -1) {
                indexScrollHolder!!.smoothScrollToPosition(position)
            }
        }
        Collections.sort(mDatas, PinyinComparator())
        sortAdapter!!.data = mDatas
        list.adapter = sortAdapter
        list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun getDatas() {
        mDatas = ArrayList()
        mDatas!!.add(SortModel("赵"))
        mDatas!!.add(SortModel("钱"))
        mDatas!!.add(SortModel("孙"))
        mDatas!!.add(SortModel("李"))
        mDatas!!.add(SortModel("周"))
        mDatas!!.add(SortModel("吴"))
        mDatas!!.add(SortModel("郑"))
        mDatas!!.add(SortModel("王"))
        mDatas!!.add(SortModel("冯"))
        mDatas!!.add(SortModel("陈"))
        mDatas!!.add(SortModel("褚"))
        mDatas!!.add(SortModel("卫"))
        mDatas!!.add(SortModel("蒋"))
        mDatas!!.add(SortModel("沈"))
        mDatas!!.add(SortModel("韩"))
        mDatas!!.add(SortModel("杨"))
        mDatas!!.add(SortModel("朱"))
        mDatas!!.add(SortModel("秦"))
        mDatas!!.add(SortModel("尤"))
        mDatas!!.add(SortModel("许"))
        mDatas!!.add(SortModel("何"))
        mDatas!!.add(SortModel("吕"))
        mDatas!!.add(SortModel("施"))
        mDatas!!.add(SortModel("张"))
    }
}
