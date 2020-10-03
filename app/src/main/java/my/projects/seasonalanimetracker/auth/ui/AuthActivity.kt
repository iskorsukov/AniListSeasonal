package my.projects.seasonalanimetracker.auth.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.auth.viewmodel.AuthViewModel
import my.projects.seasonalanimetracker.auth.viewobject.AuthStatus
import my.projects.seasonalanimetracker.auth.viewmodel.IAuthViewModel
import my.projects.seasonalanimetracker.main.ui.MainActivity
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity: AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let { uri ->
            Timber.d(uri.fragment)
            viewModel.handleLoginComplete(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel.authStatusLD().observe(this, Observer { authVO ->
            Timber.d("auth status: ${authVO.authStatus().name}")
            if (authVO.authStatus() == AuthStatus.LOGGED_IN) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })
    }

    fun login(v: View) {
        val loginIntent = Intent(Intent.ACTION_VIEW).also {
            it.addCategory(Intent.CATEGORY_BROWSABLE)
            it.data = Uri.parse("https://anilist.co/api/v2/oauth/authorize?client_id=3152&response_type=token")
        }
        startActivity(loginIntent)
    }

    fun loginAsGuest(v: View) {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}