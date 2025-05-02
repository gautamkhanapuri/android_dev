package com.example.testapplication

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.security.PrivateKey
import java.util.concurrent.Executor

class AuthHandler(
    private val activity: AppCompatActivity,
    private val onAuthSuccess: () -> Unit,
    private val fallBackToPin: () -> Unit
    ) {
    private var failCount = 0
    private val maxFail = 3
    private val correctPin = "1234" // Hardcoded for now

    fun authenticate() {
        val biometricManager = BiometricManager.from(activity)

        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            showBiometricPrompt()
        } else {
            fallBackToPin()
        }
    }

    private fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    failCount++
                    if (failCount >= maxFail) {
                        fallBackToPin()
                    } else {
                        Toast.makeText(activity, "Fingerprint not recognized", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity, "Auth error: $errString", Toast.LENGTH_SHORT).show()
                    fallBackToPin()

                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate to continue")
            .setNegativeButtonText("Use PIN")

        biometricPrompt.authenticate(promptInfo.build())
    }
}