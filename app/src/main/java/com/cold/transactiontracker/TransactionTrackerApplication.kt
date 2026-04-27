package com.cold.transactiontracker

import android.app.Application
import com.cold.transactiontracker.core.database.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class TransactionTrackerApplication : Application() {

    @Inject
    lateinit var initializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            initializer.populate()
        }
    }
}