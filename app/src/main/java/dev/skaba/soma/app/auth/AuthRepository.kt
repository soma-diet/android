package dev.skaba.soma.app.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {
  private val auth: FirebaseAuth = FirebaseAuth.getInstance()

  val currentUser get() = auth.currentUser

  suspend fun signInAnonymously(): Boolean {
    return try {
      if (currentUser != null) return true

      auth.signInAnonymously().await()
      true
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }

  suspend fun getAuthToken(): String? {
    return try {
      val user = currentUser ?: return null
      val result = user.getIdToken(true).await()
      result.token
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }
}