package com.qaisjp.reveddit

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

class Share : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If a shared piece of text
        if (intent.action == Intent.ACTION_SEND) {
            if ("text/plain" == intent.type) {
                handleSharedTextIntent(intent)
                return
            }
        }

        throw RuntimeException("Unexpected intent ${intent.type}")
    }

    private val USE_CUSTOM_TABS = true;
    private fun handleSharedTextIntent(intent: Intent) {
        val url = intent.getStringExtra(Intent.EXTRA_TEXT)!!

        // lazy way to convert reddit.com to reveddit.com.
        // this will edit "reddit.com" outside of the host, but for
        // post links that doesn't matter—only the IDs matter.
        //
        // note: avoiding url.startWith because of `np.reddit.com` links.
        val replacedUrl = url.replace("reddit.com", "reveddit.com")
        val uri = Uri.parse(replacedUrl)

        if (USE_CUSTOM_TABS) {
            val builder = CustomTabsIntent.Builder()

            // Make the address bar colour match reveddit's header
            val colorInt: Int = Color.parseColor("#c70300")
            builder.setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(colorInt)
                    .build()
            )

            builder.build().launchUrl(this, uri)
            finish()
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(browserIntent)
            finish()
        }
    }
}