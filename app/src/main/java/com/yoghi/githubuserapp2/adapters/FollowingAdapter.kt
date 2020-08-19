package com.yoghi.githubuserapp2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.models.User
import kotlinx.android.synthetic.main.list_users.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val followingData = ArrayList<User>()

    fun setFollowing(items: ArrayList<User>){
        followingData.clear()
        followingData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_users, parent, false)
        return FollowingViewHolder(view)
    }

    override fun getItemCount(): Int = followingData.size

    override fun onBindViewHolder(followingViewHolder: FollowingViewHolder, position: Int) {
        followingViewHolder.bind(followingData[position])
    }

    class FollowingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView){
                tv_user_name.text = user.login
                tv_type.text = user.type

                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(circleAvatar)
            }
        }

    }
}