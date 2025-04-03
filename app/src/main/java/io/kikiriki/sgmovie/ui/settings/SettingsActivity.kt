package io.kikiriki.sgmovie.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.ActivitySettingsBinding
import io.kikiriki.sgmovie.ui.BaseActivity
import io.kikiriki.sgmovie.utils.Constants
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity() {

    private val viewBinding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        setupView()
        setupObservers()
        viewModel.initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        viewModel.isPaypalEnabled.observe(this) {
            viewBinding.layoutPaypal.visibility = if (it) View.VISIBLE else View.GONE
            viewBinding.layoutPaypalDivider.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isContactEnabled.observe(this) {
            viewBinding.layoutContact.visibility = if (it) View.VISIBLE else View.GONE
            viewBinding.layoutContactDivider.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setupView() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.layoutPrivacyPolicy.setOnClickListener {
            viewModel.onClickPrivacyPolicy()
            openWebBrowser(Constants.Legal.PRIVACY_POLICY_URL)
        }
        viewBinding.layoutContact.setOnClickListener {
            viewModel.onClickContactApp()
            sendEmail()
        }

        viewBinding.layoutPaypal.setOnClickListener {
            viewModel.onClickPaypal()
            openWebBrowser(Constants.Contact.PAYPAL)
        }

        try {
            val versionName: String = packageManager.getPackageInfo(packageName, 0).versionName.orEmpty()
            viewBinding.appInfoTvVersion.text = resources.getString(R.string.settings_lbl_version, versionName)
        } catch (_:Exception) {
            viewBinding.appInfoTvVersion.visibility = View.GONE
        }

    }

    private fun openWebBrowser(url: String) {
        val privacyPolicyUrl = url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, privacyPolicyUrl)
        try { startActivity(intent) } catch (_ : Exception) {}
    }

    private fun sendEmail() {
        val locale = Locale.getDefault().toLanguageTag()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.Contact.EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_lbl_translate_app_email_subject, locale))
            putExtra(Intent.EXTRA_TEXT, "")
        }
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.settings_lbl_send_email)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.settings_lbl_error_no_email_client_found, Toast.LENGTH_SHORT).show()
        }
    }

}