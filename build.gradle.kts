// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Подключение Hilt в проект
    alias(libs.plugins.hilt) apply false
    // Подключение Ksp в проект
    alias(libs.plugins.ksp) apply false
}