package com.yoghi.githubuserapp2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.activites.DetailActivity
import com.yoghi.githubuserapp2.models.User
import kotlinx.android.synthetic.main.list_users.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userData = ArrayList<User>()

    fun setData(items: ArrayList<User>){
        userData.clear()
        userData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_users, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = userData.size

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(userData[position])
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
//                Log.d("AVATAR", user.avatar.toString());
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(circleAvatar)

                tv_user_name.text = user.login
                tv_type.text = user.type

                itemView.setOnClickListener {
                    val detailIntent = Intent(itemView.context, DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_USER, user.login)
                    itemView.context.startActivity(detailIntent)
                }
            }
        }
    }
}