package com.yoghi.consumerapp.activites

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoghi.consumerapp.R
import com.yoghi.consumerapp.adapters.UserAdapter
import com.yoghi.consumerapp.models.User
import com.yoghi.consumerapp.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Consumer App"

        // set adapter to main recycleView
        userAdapter = UserAdapter()
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = userAdapter

        // mainViewModel
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.getUsers().observe(this, Observer { users ->
            if (users != null) {
                userAdapter.setData(users)
                showLoading(false)
            }
            if(users == emptyList<User>()){
                val msg = this.resources.getString(R.string.not_found)
                tv_main_message.text = msg
                tv_main_message.visibility = View.VISIBLE
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        // set SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_placeholder)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                showLoading(true)
                // call setUsers func in mainViewModel
                mainViewModel.setUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_change_lang -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.fav -> {
                val intent = Intent(this, FavActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val preferenceIntent = Intent(this, PreferenceActivity::class.java)
                startActivity(preferenceIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            tv_main_message.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
