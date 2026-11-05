package com.ps.devoptoggle

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Space
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var permView: TextView
    private lateinit var stateView: TextView

    private lateinit var btn_toggle: Button
    private lateinit var cmdView: TextView

    private var on=false

    private val grantCmd by lazy {
        "adb shell pm grant $packageName android.permission.WRITE_SECURE_SETTINGS"
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Content (yeh center hoga)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(20), dp(32), dp(20), dp(20))
        }

        content.addView(TextView(this).apply {
            text = getString(R.string.title)
            textSize = 24f
            gravity = Gravity.CENTER
            setTypeface(typeface, Typeface.BOLD)
        })
        content.addView(TextView(this).apply {
            text = getString(R.string.subtitle)
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, dp(4), 0, dp(20))
        })

        permView = TextView(this).apply {
            textSize = 15f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, dp(8))
        }
        stateView = TextView(this).apply {
            textSize = 18f
            gravity = Gravity.CENTER
            setTypeface(typeface, Typeface.BOLD)
            setPadding(0, 0, 0, dp(16))
        }
        content.addView(permView)
        content.addView(stateView)

        btn_toggle = Button(this).apply {
            setOnClickListener {if(on) apply(false) else apply(true)}
        }

        content.addView(btn_toggle)


        content.addView(TextView(this).apply {
            text = getString(R.string.cmd_label)
            gravity = Gravity.CENTER
            setPadding(0, dp(24), 0, dp(6))
        })
        cmdView = TextView(this).apply {
            text = grantCmd
            typeface = Typeface.MONOSPACE
            textSize = 13f
            setTextIsSelectable(true)
            setBackgroundColor(0x11000000)
            setPadding(dp(12), dp(12), dp(12), dp(12))
        }
        content.addView(cmdView)
        content.addView(Button(this).apply {
            text = getString(R.string.btn_copy)
            setOnClickListener {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setPrimaryClip(ClipData.newPlainText("adb", grantCmd))
                toast(R.string.copied)
            }
        })

// 2) ScrollView: content chhota ho to center, bada ho to scroll
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(
                content,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

// 3) Footer (hamesha neeche)
        val footer = TextView(this).apply {
            text = "Made with ♥ by Pushpraj Singh  ↗"
            textSize = 13f
            gravity = Gravity.CENTER
            setTextColor(Color.parseColor("#3F51B5"))
            setPadding(0, dp(16), 0, dp(16))
            isClickable = true
            isFocusable = true
            setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/YOUR_USERNAME")))
            }
        }

// 4) Root: upar scroll (weight 1), neeche footer
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(scroll, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
            addView(footer, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT))
        }

        setContentView(root)

    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun hasPermission() =
        checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") ==
            PackageManager.PERMISSION_GRANTED

    private fun refresh() {
        val ok = hasPermission()
        permView.text = getString(if (ok) R.string.perm_ok else R.string.perm_missing)
        permView.setTextColor(if (ok) 0xFF2E7D32.toInt() else 0xFFC62828.toInt())
         on = Settings.Global.getInt(
            contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) == 1
        stateView.text = getString(if (on) R.string.state_on else R.string.state_off)
        btn_toggle.text = getString(if(on) R.string.btn_off else R.string.btn_on)
    }

    private fun apply(enable: Boolean) {
        if (!hasPermission()) {
            toast(R.string.failed)
            refresh()
            return
        }
        try {
            val v = if (enable) 1 else 0
            Settings.Global.putInt(contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, v)
            Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, v)
            Settings.Global.putInt(contentResolver, "adb_wifi_enabled", v)

            toast(R.string.done)
        } catch (e: SecurityException) {
            toast(R.string.failed)
        }
        refresh()
    }

    private fun toast(res: Int) = Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
}
