package com.yoghi.consumerapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoghi.consumerapp.models.User
import com.yoghi.consumerapp.services.ServiceBuilder
import com.yoghi.consumerapp.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()
    val listFollowings = MutableLiveData<ArrayList<User>>()
    val userDetail = MutableLiveData<User>()

    private val userService = ServiceBuilder.buildService(UserService::class.java)

    fun setDetail(username: String) {
        val detailUser = User()

        val requestCall = userService.getDetailUser(username)

        requestCall.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("DETAIL_USER", t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    detailUser.id = responseBody.id
                    detailUser.name = responseBody.name
                    detailUser.login = responseBody.login
                    detailUser.company = responseBody.company
                    detailUser.location = responseBody.location
                    detailUser.avatar_url = responseBody.avatar_url
                    detailUser.public_repos = responseBody.public_repos
                    detailUser.followers = responseBody.followers
                    detailUser.following = responseBody.following
                    detailUser.type = responseBody.type

                    userDetail.postValue(detailUser)
                }else{
                    Log.d("DETAIL_USER", response.body().toString())
                }
            }

        })
    }

    fun setFollowers(username: String){
        val listItems = ArrayList<User>()

        val requestCall = userService.getFollowers(username)

        requestCall.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("DETAIL", t.message.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    Log.d("DETAIL", responseBody.toString())
                    for(i in responseBody.indices){
                        val item = responseBody[i]
                        val user = User()
                        user.login = item.login
                        user.type = item.type
                        user.avatar_url = item.avatar_url

                        listItems.add(user)
                    }

                    listFollowers.postValue(listItems)
                }else{
                    Log.d("DETAIL", response.body().toString())
                }
            }

        })
    }

    fun setFollowings(username: String){
        val listItems = ArrayList<User>()

        val requestCall = userService.getFollowings(username)

        requestCall.enqueue(object: Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("DETAIL", t.message.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    val responseBody = response.body()

                    if (responseBody != null) {
                        for(i in responseBody.indices){
                            val item = responseBody[i]
                            val user = User()
                            user.login = item.login
                            user.type = item.type
                            user.avatar_url = item.avatar_url

                            listItems.add(user)
                        }
                    }

                    listFollowings.postValue(listItems)
                }else{
                    Log.d("DETAIL", response.body().toString())
                }
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }

    fun getFollowing(): LiveData<ArrayList<User>>{
        return listFollowings
    }

    fun getDetail(): LiveData<User>{
        return userDetail
    }
}