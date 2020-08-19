package com.yoghi.githubuserapp2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.models.User
import kotlinx.android.synthetic.main.list_users.view.*

class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private val followerData = ArrayList<User>()

    fun setFollower(items: ArrayList<User>){
        followerData.clear()
        followerData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_users, parent, false)
        return FollowerViewHolder(view)
    }

    override fun getItemCount(): Int = followerData.size

    override fun onBindViewHolder(followerViewHolder: FollowerViewHolder, position: Int) {
        followerViewHolder.bind(followerData[position])
    }

    class FollowerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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