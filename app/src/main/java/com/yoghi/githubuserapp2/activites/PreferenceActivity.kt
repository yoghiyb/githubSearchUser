package com.yoghi.githubuserapp2.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.fragments.PreferenceFragment

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, PreferenceFragment()).commit()
    }
}