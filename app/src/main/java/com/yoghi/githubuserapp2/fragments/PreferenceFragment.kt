package com.yoghi.githubuserapp2.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.other.AlarmReceiver

class PreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var SWITCH_REMINDER: String

    private lateinit var CHANGE_LANG: String

    private lateinit var isSwitchReminderPreference: SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        alarmReceiver = AlarmReceiver()

        init()
        setSummaries()
    }

    private fun init() {
        SWITCH_REMINDER = resources.getString(R.string.key_reminder)
        CHANGE_LANG =  resources.getString(R.string.change_lang)
        isSwitchReminderPreference =
            findPreference<SwitchPreference>(SWITCH_REMINDER) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isSwitchReminderPreference.isChecked = sh.getBoolean(SWITCH_REMINDER, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == SWITCH_REMINDER) {
            isSwitchReminderPreference.isChecked =
                sharedPreferences.getBoolean(SWITCH_REMINDER, false)
            Log.d("CEKSWITCH", "OK SWITCH")
            if (isSwitchReminderPreference.isChecked) {
                alarmReceiver.setRepeatingAlarm(requireContext(), "09:00", "Cari user github")
            } else {
                alarmReceiver.cancelAlarm(requireContext())
            }
        }
    }
}