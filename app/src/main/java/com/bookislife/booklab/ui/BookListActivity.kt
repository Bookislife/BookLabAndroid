package com.bookislife.booklab.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.bookislife.booklab.R
import com.bookislife.booklab.model.Book
import com.bookislife.booklab.service.BookApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var progress: ProgressBar

    private var disposable: Disposable? = null

    // 网络请求服务
    private val bookApiService by lazy {
        BookApiService.create()
    }

    // 初始化时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        // 初始化UI组件
        initUI()
    }

    fun initUI() {
        // 获得进度条对象
        progress = findViewById(R.id.progress)

        // 设置标题栏
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationIcon(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)

        // 设置RecyclerView
        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        bookAdapter = BookAdapter(listOf(Book(1, "xx", "xx")))
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = bookAdapter

        // 请求数据
        loadData()
    }

    // 请求数据
    fun loadData() {
        // 开始请求时显示进度条
        progress.visibility = View.VISIBLE
        disposable = bookApiService.list()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // 结束请求后隐藏进度条
                progress.visibility = View.GONE
                // 重新设置Adapter的数据
                bookAdapter.items = it.data
                // 刷新Adapter，触发UI重绘
                bookAdapter.notifyDataSetChanged()
            }, { error ->
                progress.visibility = View.GONE
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    // 设置标题栏上的菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 设置菜单的业务逻辑
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh -> loadData()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}