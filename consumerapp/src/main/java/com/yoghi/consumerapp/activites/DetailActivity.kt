package com.yoghi.consumerapp.activites

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yoghi.consumerapp.R
import com.yoghi.consumerapp.adapters.TabPagerAdapter
import com.yoghi.consumerapp.db.DatabaseContract
import com.yoghi.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.yoghi.consumerapp.helper.MappingHelper
import com.yoghi.consumerapp.models.User
import com.yoghi.consumerapp.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_fav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var user: User

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    var isFav= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra(EXTRA_USER)!!

        val tabPagerAdapter = TabPagerAdapter(this, supportFragmentManager, username)
        view_pager.adapter = tabPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

        setStatusFavorite(false)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.setDetail(username)

        detailViewModel.getDetail().observe(this, Observer { detail ->
            if(detail != null){
                user = detail
                tv_detail_name.text = detail.name
                tv_detail_username.text = detail.login
                tv_total_repo?.text = detail.public_repos.toString()
                tv_total_follower?.text = detail.followers.toString()
                tv_total_following?.text = detail.following.toString()
                tv_company?.text = detail.company
                tv_location?.text = detail.location

                Glide.with(this)
                    .load(detail.avatar_url)
                    .into(circleImageView)

                checkIsFav(detail.id)
            }
        })


        btn_fav?.setOnClickListener {
            val values = ContentValues()
            values.put(DatabaseContract.UserColumns._ID, user.id)
            values.put(DatabaseContract.UserColumns.LOGIN, user.login)
            values.put(DatabaseContract.UserColumns.TYPE, user.type)
            values.put(DatabaseContract.UserColumns.AVATAR_URL, user.avatar_url)

            if(isFav){
                val uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user.id)
                contentResolver.delete(uriWithId, null, null)
                setStatusFavorite(false)
                Toast.makeText(this, "Berhasil dihapus dari daftar favorit", Toast.LENGTH_SHORT).show()
            }else{
                contentResolver.insert(CONTENT_URI, values)
                setStatusFavorite(true)
                Toast.makeText(this, "Berhasil disimpan sebagai user favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIsFav(id: Int) {
        GlobalScope.launch{
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredUsers.await()
            if (users.size > 0) {
                users.forEach {user ->
                    if(user.id == id){
                        setStatusFavorite(true)
                    }else{
                        setStatusFavorite(false)
                    }
                }
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean){
        if (statusFavorite){
            btn_fav?.setImageResource(R.drawable.ic_baseline_favorite_24)
            isFav = true
        }else{
            btn_fav?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            isFav = false
        }
    }
}
