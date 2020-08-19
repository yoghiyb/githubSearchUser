package com.yoghi.githubuserapp2.services

import com.yoghi.githubuserapp2.models.Search
import com.yoghi.githubuserapp2.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    fun searchUser(@Query("q") q: String): Call<Search>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<User>>
}