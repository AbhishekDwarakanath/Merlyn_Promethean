package com.merlyn.merlynprom.ui.main

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import com.merlyn.merlynprom.R
import com.merlyn.merlynprom.ui.main.model.MerlynEndlessService
import com.merlyn.merlynprom.ui.main.model.ServiceState
import com.merlyn.merlynprom.ui.main.model.getServiceState
import com.merlyn.merlynprom.ui.main.utlis.Actions
import com.merlyn.merlynprom.ui.main.utlis.log
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Abhishek ", "Fragment Started")
        viewOfLayout = inflater!!.inflate(R.layout.fragment_main, container, false)
        viewOfLayout.txt_service_status.text = "Merlyn Endless Service"
        return viewOfLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Abhishek ","Fragment Started onActivityCreated")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewOfLayout.btn_start.let {
            it.setOnClickListener {
                log("START THE FOREGROUND SERVICE ON DEMAND")
                actionOnService(Actions.START)
            }
        }

        viewOfLayout.btn_stop.let {
            it.setOnClickListener {
                log("STOP THE FOREGROUND SERVICE ON DEMAND")
                actionOnService(Actions.STOP)
            }
        }
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(requireActivity()) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(requireActivity(), MerlynEndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >=26 Mode")
                requireActivity().startForegroundService(it)
                return
            }
            log("Starting the service in < 26 Mode")
            requireActivity().startService(it)
        }
    }
}