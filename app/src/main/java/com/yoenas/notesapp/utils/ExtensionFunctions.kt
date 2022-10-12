package com.yoenas.notesapp.utils

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yoenas.notesapp.R
import com.yoenas.notesapp.presentation.MainActivity

object ExtensionFunctions {

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun Toolbar.setupActionBar(fragment: Fragment, destinationId: Int?) {

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        this.apply {
            setupWithNavController(navController, appBarConfiguration)
            (fragment.requireActivity() as MainActivity).setSupportActionBar(this)
            (fragment.requireActivity() as MainActivity).setupActionBarWithNavController(
                navController,
                appBarConfiguration
            )
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    destinationId -> {
                        this.setNavigationIcon(R.drawable.ic_left_arrow)
                    }
                }
            }
        }
    }
}