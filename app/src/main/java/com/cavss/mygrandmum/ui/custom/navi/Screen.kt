package com.cavss.mygrandmum.ui.custom.navi

import androidx.annotation.StringRes
import com.cavss.mygrandmum.R

sealed class Screen(val type: String, @StringRes val title: Int, val emoji : String) {
    object CallBook : Screen("callBook", R.string.nav_title_callbook, "📞")
    object Watcher : Screen("watcher", R.string.nav_title_watcher, "👁️")
    object CardGame : Screen("cardGame", R.string.nav_title_cardgame, "🧠")
}
