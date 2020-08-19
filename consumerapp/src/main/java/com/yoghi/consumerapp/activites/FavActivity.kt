package com.yoghi.consumerapp.activites

import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yoghi.consumerapp.R
import com.yoghi.consumerapp.adapters.FavUserAdapter
import com.yoghi.consumerapp.db.DatabaseContract
import com.yoghi.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.yoghi.consumerapp.helper.MappingHelper
import com.yoghi.consumerapp.models.User
import kotlinx.android.synthetic.main.activity_fav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavActivity : AppCompatActivity() {

    private lateinit var favAdapter: FavUserAdapter
    private lateinit var uriWithId: Uri

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

        favAdapter = FavUserAdapter(this)
        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.adapter = favAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadSavedUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadSavedUserAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            list?.apply {
                favAdapter.listFavUser = list
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = favAdapter.listFavUser[position]
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user.id)

                contentResolver.delete(uriWithId, null, null)
                val view = findViewById<View>(R.id.activity_fav)
                Snackbar.make(view, "Berhasil menghapus user", Snackbar.LENGTH_LONG).apply {
                    setAction("Batalkan") {
                        val values = ContentValues()
                        values.put(DatabaseContract.UserColumns._ID, user.id)
                        values.put(DatabaseContract.UserColumns.LOGIN, user.login)
                        values.put(DatabaseContract.UserColumns.TYPE, user.type)
                        values.put(DatabaseContract.UserColumns.AVATAR_URL, user.avatar_url)
                        contentResolver.insert(CONTENT_URI, values)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_fav)
        }
    }

    private fun loadSavedUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                Log.d("CEKCURSOR", cursor.toString())
                MappingHelper.mapCursorToArrayList(cursor)
            }
            showLoading(false)
            val users = deferredUsers.await()
            Log.d("CEKUSER", users.toString())
            if (users.size > 0) {
                favAdapter.listFavUser = users
            } else {
                favAdapter.listFavUser = ArrayList()
                tv_fav_message.text = "Tidak ada data saat ini"
            }

        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            fav_progressBar.visibility = View.VISIBLE
            tv_fav_message.visibility = View.GONE
        } else {
            fav_progressBar.visibility = View.GONE
        }
    }
}