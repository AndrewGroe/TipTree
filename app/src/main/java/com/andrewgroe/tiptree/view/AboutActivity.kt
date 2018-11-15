package com.andrewgroe.tiptree.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import com.andrewgroe.tiptree.BuildConfig
import com.andrewgroe.tiptree.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAboutPage()
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

    private fun createAboutPage() {
        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_launcher_foreground)
                .addItem(Element().setTitle("Version " + BuildConfig.VERSION_NAME))
                .addGroup("Connect with the developer")
                .addGitHub("AndrewGroe/TipTree", "Check out this project on Github")
                .addGroup("Credits:")
                .addItem(Element().setTitle(Html.fromHtml(getString(R.string.about_credits)).toString()))
                .setDescription(getString(R.string.about_page_description))
                .create()

        setContentView(aboutPage)
    }
}
