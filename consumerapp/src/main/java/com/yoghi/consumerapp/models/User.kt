package com.yoghi.consumerapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var name: String? = null,
    var company: String? = null,
    var blog: String? = null,
    var location: String? = null,
    var email: String? = null,
    var bio: String? = null,
    var public_repos: Int = 0,
    var public_gists: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var type: String? = null
): Parcelable

