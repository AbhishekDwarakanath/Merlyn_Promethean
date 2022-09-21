package com.merlyn.merlynprom.ui.main.utlis

import android.content.Context
import android.content.Intent
import android.os.Build
import android.content.BroadcastReceiver
import com.merlyn.merlynprom.ui.main.model.MerlynEndlessService
import com.merlyn.merlynprom.ui.main.model.ServiceState
import com.merlyn.merlynprom.ui.main.model.getServiceState


class StartMerlynSerivceReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && getServiceState(context) == ServiceState.STARTED) {
            Intent(context, MerlynEndlessService::class.java).also {
                it.action = Actions.START.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    log("Starting the service in >=26 Mode from a BroadcastReceiver")
                    context.startForegroundService(it)
                    return
                }
                log("Starting the service in < 26 Mode from a BroadcastReceiver")
                context.startService(it)
            }
        }
    }
}