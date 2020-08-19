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
import com.yoghi.githubuserapp2.adapters.FollowerAdapter
import com.yoghi.githubuserapp2.models.User
import com.yoghi.githubuserapp2.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

/**
 * A simple [Fragment] subclass.
 */
class FollowerFragment : Fragment() {

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null){
            username = arguments!!.getString("username").toString()
            Log.d("USERNAME_FRAGMENT", username)
        }

        followerAdapter = FollowerAdapter()
        rv_detail.layoutManager = LinearLayoutManager(activity)
        rv_detail.adapter = followerAdapter

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        showLoading(true)
        detailViewModel.setFollowers(username)
        detailViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followers ->
            if (followers != null) {
                followerAdapter.setFollower(followers)
                showLoading(false)
            }
            if(followers == emptyList<User>()){
                val msg = this.resources.getString(R.string.follower_message)
                tv_follower_message.text = msg
                tv_follower_message.visibility = View.VISIBLE
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tv_follower_message.visibility = View.GONE
            progressBar_follower.visibility = View.VISIBLE
        } else {
            progressBar_follower.visibility = View.GONE
        }
    }

}
