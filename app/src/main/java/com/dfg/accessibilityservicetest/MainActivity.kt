package com.dfg.accessibilityservicetest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    lateinit var tvOpenAccessibility: TextView;
    lateinit var scAtuoLogin: SwitchCompat;
    val sp: SharedPreferences by lazy {
        application.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        tvOpenAccessibility = findViewById(R.id.tv_openAccessibility);
        scAtuoLogin = findViewById(R.id.scAutoLogin);
        scAtuoLogin.isChecked = isAutoLogin();

        tvOpenAccessibility.setOnClickListener {
            // 如果无障碍服务已开启，弹提示，否则跳转到啊设置页
            if (isAccessibilitySettingsOn(JumpAdAccessibilityService::class.java)) {
                Toast.makeText(this, "开了无服务", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                Toast.makeText(this, "跳转设置页面也开无服务", Toast.LENGTH_SHORT).show();
            }
        }

        scAtuoLogin.setOnCheckedChangeListener { _, isChecked ->
            setAutoLogin(isChecked)
            Log.d(this.javaClass.simpleName,"点击")
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAccessibilitySettingsOn(JumpAdAccessibilityService::class.java)) {
            tvOpenAccessibility.visibility = View.GONE
            scAtuoLogin.visibility = View.VISIBLE
        } else {
            tvOpenAccessibility.visibility = View.VISIBLE;
            scAtuoLogin.visibility = View.GONE
        }
    }

    private fun isAutoLogin() = sp.getBoolean("auto_login", false);
    private fun setAutoLogin(isCheck: Boolean) = sp.edit().putBoolean("auto_login", true);

}