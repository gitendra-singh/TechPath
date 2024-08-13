package com.example.techpath.navigation

sealed class Routes (val routes: String) {

     data object Home : Routes("home")
     data object Display: Routes("display")

}