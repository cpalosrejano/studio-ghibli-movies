package io.kikiriki.sgmovie.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.ActivitySettingsBinding
import io.kikiriki.sgmovie.ui.BaseActivity
import io.kikiriki.sgmovie.utils.Constants
import java.util.Locale

@AndroidEntryPoint
class SettingsActivity : BaseActivity() {

    private val viewBinding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.lblPrivacyPolicy.setOnClickListener { openPrivacyPolicy() }
        viewBinding.lblTranslateApp.setOnClickListener { translateApp() }

        try {
            val versionName: String = packageManager.getPackageInfo(packageName, 0).versionName.orEmpty()
            viewBinding.appInfoTvVersion.text = resources.getString(R.string.settings_lbl_version, versionName)
        } catch (_:Exception) {
            viewBinding.appInfoTvVersion.visibility = View.GONE
        }

    }

    private fun openPrivacyPolicy() {
        val privacyPolicyUrl = Uri.parse(Constants.Legal.PRIVACY_POLICY_URL)
        val intent = Intent(Intent.ACTION_VIEW, privacyPolicyUrl)
        try { startActivity(intent) } catch (_ : Exception) {}
    }

    private fun translateApp() {
        val locale = Locale.getDefault().toLanguageTag()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.Contact.EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_lbl_translate_app_email_subject, locale))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_lbl_translate_app_email_body))
        }
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.settings_lbl_send_email)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.settings_lbl_error_no_email_client_found, Toast.LENGTH_SHORT).show()
        }
    }

}