package dev.skaba.soma.app

import android.app.Application
import dev.skaba.soma.app.di.AppContainer

class SomaApplication : Application() {

  lateinit var container: AppContainer

  override fun onCreate() {
    super.onCreate()
    container = AppContainer(this)
  }
}