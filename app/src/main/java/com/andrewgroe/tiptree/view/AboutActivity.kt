package com.andrewgroe.tiptree.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.andrewgroe.tiptree.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun openAuthorURL(view: View) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://roundicons.com/")
        startActivity(openURL)

    }

    fun openIconURL(view: View) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.flaticon.com/")
        startActivity(openURL)
    }
}
