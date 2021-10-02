package com.qaisjp.reveddit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.RuntimeException

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

    private val USE_CUSTOM_TABS = false;
    private fun handleSharedTextIntent(intent: Intent) {
        val url = intent.getStringExtra(Intent.EXTRA_TEXT)!!

        // lazy way to convert reddit.com to reveddit.com.
        // this will edit "reddit.com" outside of the host, but for
        // post links that doesn't matter—only the IDs matter.
        //
        // note: avoiding url.startWith because of `np.reddit.com` links.
        val replacedUrl = url.replace("reddit.com", "reveddit.com")

        if (USE_CUSTOM_TABS) {
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(replacedUrl))
            startActivity(browserIntent)
        }
    }
}