package com.dfg.accessibilityservicetest

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.text.TextUtils

/*
* Create by KUTAI1 on 2023/7/28
*/
fun Context.isAccessibilitySettingsOn(clazz: Class<out AccessibilityService?>): Boolean {
    var accessibilityEnabled = false    // 判断设备的无障碍功能是否可用
    try {
        accessibilityEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED) == 1
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }
    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
    if (accessibilityEnabled) {
        // 获取启用的无障碍服务
        val settingValue: String? = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        if (settingValue != null) {
            // 遍历判断是否包含我们的服务
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals(
                        "${packageName}/${clazz.canonicalName}",
                        ignoreCase = true
                    )
                ) return true

            }
        }
    }
    return false
}