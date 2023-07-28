package com.dfg.accessibilityservicetest

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/*
* Create by KUTAI1 on 2023/7/28
*/
class JumpAdAccessibilityService : AccessibilityService() {
    val TAG = javaClass.simpleName
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d(TAG, "onAccessibilityEvent：$event")
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.className == "com.tencent.mm.plugin.webwx.ui.ExtDeviceWXLoginUI") {
                Log.d(TAG,"jinlaile weixin");
                //获取登录并点击
                val noteList =
                    event.source?.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/lni")
                if(!noteList.isNullOrEmpty()){
                    noteList[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG,"jinlaile noteList");
                }
            }
        }
    }

     /*override fun onServiceConnected() {
         super.onServiceConnected()
         val serviceInfo = AccessibilityServiceInfo().apply {
             eventTypes = AccessibilityEvent.TYPES_ALL_MASK
             feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
             flags = AccessibilityServiceInfo.DEFAULT
             packageNames = arrayOf("com.tencent.mm") //监听的应用包名，支持多个
             notificationTimeout = 10
         }
         setServiceInfo(serviceInfo)
     }*/

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
    }
}
