package com.yoghi.githubuserapp2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.adapters.FollowingAdapter
import com.yoghi.githubuserapp2.models.User
import com.yoghi.githubuserapp2.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {

    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null){
            username = arguments!!.getString("username").toString()
            Log.d("USERNAME_FRAGMENT", username)
        }

        followingAdapter = FollowingAdapter()
        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = followingAdapter

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        showLoading(true)
        detailViewModel.setFollowings(username)
        detailViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followings ->
            if(followings != null){
                followingAdapter.setFollowing(followings)
                showLoading(false)
            }
            if(followings == emptyList<User>()){
                val msg = this.resources.getString(R.string.following_message)
                tv_following_message.text = msg
                tv_following_message.visibility = View.VISIBLE
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tv_following_message.visibility = View.GONE
            progressBar_following.visibility = View.VISIBLE
        } else {
            progressBar_following.visibility = View.GONE
        }
    }

}
