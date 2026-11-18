
package com.parvez.commentcounter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 36, 32, 24)
        }

        val title = TextView(this).apply {
            text = "Parvez Comment Counter"
            textSize = 25f
            typeface = Typeface.DEFAULT_BOLD
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "Automatic Facebook Comment Tracker"
            textSize = 15f
            setPadding(0, 12, 0, 20)
        }
        root.addView(subtitle)

        val status = TextView(this).apply {
            text = "পরীক্ষামূলক সংস্করণ: Accessibility চালু করুন।"
            textSize = 16f
            setPadding(0, 12, 0, 16)
        }
        root.addView(status)

        val accessibilityButton = Button(this).apply {
            text = "Enable Accessibility Service"
            setOnClickListener {
                startActivity(
                    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                )
            }
        }
        root.addView(accessibilityButton)

        val scroll = ScrollView(this)
        val list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        scroll.addView(list)

        val apps = packageManager
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .mapNotNull { app ->
                val name = packageManager
                    .getApplicationLabel(app).toString()

                if (name.contains("Facebook", true)) {
                    Pair(name, app.packageName)
                } else null
            }
            .distinctBy { it.second }
            .sortedBy { it.second }

        val count = TextView(this).apply {
            text = "Facebook Apps Found: ${apps.size}"
            textSize = 19f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 20, 0, 16)
        }
        list.addView(count)

        apps.forEachIndexed { index, app ->
            val item = TextView(this).apply {
                text = "Facebook ${index + 1}\n" +
                    "Name: ${app.first}\n" +
                    "Package: ${app.second}"
                textSize = 14f
                setPadding(16, 16, 16, 16)
                setBackgroundColor(0xFFEFEFEF.toInt())
            }

            val params = LinearLayout.LayoutParams(-1, -2)
            params.bottomMargin = 12
            list.addView(item, params)
        }

        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val footer = TextView(this).apply {
            text = "Diagnostic Version 0.2"
            gravity = Gravity.CENTER
            textSize = 13f
            setPadding(0, 16, 0, 0)
        }
        root.addView(footer)

        setContentView(root)
    }
}
