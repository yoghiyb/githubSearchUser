package com.yoghi.githubuserapp2.helper

import android.database.Cursor
import android.util.Log
import com.yoghi.githubuserapp2.db.DatabaseContract
import com.yoghi.githubuserapp2.models.User

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        userCursor?.apply {
            while (moveToNext()){
                val user = User()
                user.id =getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                user.login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                user.type = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE))
                user.avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                usersList.add(user)
            }
        }
        return usersList
    }
}