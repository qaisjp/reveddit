package com.qaisjp.reveddit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.qaisjp.reveddit.databinding.ActivityMainBinding
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            // If a shared piece of text
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSharedTextIntent(intent)
                } else {
                    throw RuntimeException("Unexpected intent ${intent.type}")
                }
            }
            else -> {
                inflate();
            }
        }
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

    private fun inflate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}