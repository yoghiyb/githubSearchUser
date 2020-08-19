package com.yoghi.githubuserapp2.adapters

import android.app.Activity
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

class FavUserAdapter(private val activity: Activity) :
    RecyclerView.Adapter<FavUserAdapter.FavUserViewHolder>() {

    var listFavUser = ArrayList<User>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)
            notifyDataSetChanged()
        }

    fun addItem(user: User) {
        this.listFavUser.add(user)
        notifyItemInserted(this.listFavUser.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFavUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavUser.size)
    }

    class FavUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_users, parent, false)
        return FavUserViewHolder(view)
    }

    override fun getItemCount(): Int = listFavUser.size

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }
}