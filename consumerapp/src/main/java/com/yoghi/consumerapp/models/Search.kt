package com.yoghi.consumerapp.models

data class Search(
    var totalCount: Int = 0,
    var incompleteResults: Boolean = false,
    var items: List<User> = emptyList()
)