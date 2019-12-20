package com.tushar.newsmvi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tushar.newsmvi.R
import com.tushar.newsmvi.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMainFragment()
    }

    private fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment"
            )
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            // Handle loading
            showProgressBar(dataState.loading)

            // Handle Message
            dataState.message?.let { event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }

}
