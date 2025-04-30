package com.demo.delivery

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Стандартная настройка для Hilt. Также нужно добавить этот клас в Manifest файл через поле name.
 */
@HiltAndroidApp
class App : Application() {
}