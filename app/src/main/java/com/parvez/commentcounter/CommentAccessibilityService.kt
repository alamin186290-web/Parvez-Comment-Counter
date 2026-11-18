
package com.parvez.commentcounter

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.util.Log

class CommentAccessibilityService : AccessibilityService() {

    private var activePackage: String? = null
    private var commentCount = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName?.toString() ?: return

        if (packageName == activePackage) return

        activePackage = packageName

        val isFacebook = try {
            val label = packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(packageName, 0)
            ).toString()

            label.contains("facebook", ignoreCase = true)
        } catch (e: Exception) {
            false
        }

        if (isFacebook) {
            commentCount = 0
            Log.d("ParvezCounter", "Facebook opened: $packageName")
        } else {
            commentCount = 0
            Log.d("ParvezCounter", "Facebook closed")
        }
    }

    override fun onInterrupt() {
        Log.d("ParvezCounter", "Accessibility interrupted")
    }
}
