package com.yoghi.githubuserapp2.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoghi.githubuserapp2.models.Search
import com.yoghi.githubuserapp2.models.User
import com.yoghi.githubuserapp2.services.ServiceBuilder
import com.yoghi.githubuserapp2.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUsers(username: String) {
        val listItems = ArrayList<User>()

        val userService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = userService.searchUser(username)

        requestCall.enqueue(object : Callback<Search> {
            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d("VIEWMODEL", t.toString())
            }

            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val items = responseBody?.items
                    Log.d("VIEWMODEL", responseBody?.totalCount.toString())

                    if (items != null) {
                        for (i in items.indices) {
                            val item = items[i]
                            val user = User()
                            user.id = item.id
                            user.login = item.login
                            user.avatar_url = item.avatar_url
                            user.url = item.url
                            user.type = item.type

                            listItems.add(user)
                        }
                    }

                    listUsers.postValue(listItems)
                } else {
                    Log.d("VIEWMODEL", response.code().toString())
                }
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

}