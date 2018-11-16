package com.andrewgroe.tiptree.view

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.andrewgroe.tiptree.BuildConfig
import com.andrewgroe.tiptree.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import android.support.v7.app.AppCompatDelegate
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_MASK


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simulateDayNight(/* DAY */ 1)
        createAboutPage()
    }

    private fun createAboutPage() {
        val sliderIntent = Intent(Intent.ACTION_VIEW)
        sliderIntent.data = Uri.parse("https://github.com/Ramotion/fluid-slider")

        val pickerIntent = Intent(Intent.ACTION_VIEW)
        pickerIntent.data = Uri.parse("https://github.com/ashik94vc/ElegantNumberButton")

        val iconIntent = Intent(Intent.ACTION_VIEW)
        iconIntent.data = Uri.parse("https://roundicons.com/")


        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_launcher_foreground)
                .addItem(Element().setTitle("Version " + BuildConfig.VERSION_NAME))
                .addGroup("Connect with the developer")
                .addGitHub("AndrewGroe/TipTree", "Check out this project on Github")
                .addGroup("Credits:")
                .addItem(Element().setTitle(getString(R.string.credits_slider)).setIntent(sliderIntent))
                .addItem(Element().setTitle(getString(R.string.credits_picker)).setIntent(pickerIntent))
                .addItem(Element().setTitle(getString(R.string.credits_icon)).setIntent(iconIntent))
                .setDescription(getString(R.string.about_page_description))
                .create()

        setContentView(aboutPage)
    }

    private fun simulateDayNight(currentSetting: Int) {
        val DAY = 0
        val NIGHT = 1
        val FOLLOW_SYSTEM = 3

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO)
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES)
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
