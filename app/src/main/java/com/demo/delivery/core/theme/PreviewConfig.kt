package com.demo.delivery.core.theme

import android.content.res.Configuration


/**
 * Ui mode для предварительного просмотра в темной теме
 */
const val PREVIEW_UI_MODE_DARK =
    Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
/**
 * Ui mode для предварительного просмотра в светлой теме
 */
const val PREVIEW_UI_MODE_LIGHT =
    Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
/**
 * Спецификация устройства для превью с нужной высотой и шириной
 */
const val PREVIEW_DEVICE = "spec:width=430dp,height=932dp,dpi=440"
