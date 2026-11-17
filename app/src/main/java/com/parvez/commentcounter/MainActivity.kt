
package com.parvez.commentcounter

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
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
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "Facebook Clone Detector"
            textSize = 15f
            setPadding(0, 12, 0, 20)
        }
        root.addView(subtitle)

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
            setPadding(0, 10, 0, 20)
        }
        list.addView(count)

        if (apps.isEmpty()) {
            list.addView(TextView(this).apply {
                text = "No Facebook apps found."
                textSize = 16f
            })
        }

        apps.forEachIndexed { index, app ->
            val item = TextView(this).apply {
                text = "Facebook ${index + 1}\n" +
                    "Name: ${app.first}\n" +
                    "Package: ${app.second}"

                textSize = 15f
                setPadding(16, 16, 16, 16)
                setBackgroundColor(0xFFEFEFEF.toInt())
            }

            val params = LinearLayout.LayoutParams(
                -1, -2
            )
            params.bottomMargin = 12
            list.addView(item, params)
        }

        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val footer = TextView(this).apply {
            text = "Diagnostic Version 0.1"
            gravity = Gravity.CENTER
            textSize = 13f
        }
        root.addView(footer)

        setContentView(root)
    }
}
