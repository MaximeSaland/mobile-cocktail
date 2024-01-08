package com.enseirb.cocktail.ui.fragmentInterface

// Interface used for handling back button with fragments
interface IOnBackPressed {
    // Return true if the fragment does not need to use the default onBackPressed, false otherwise
    fun onBackPressed(): Boolean

}