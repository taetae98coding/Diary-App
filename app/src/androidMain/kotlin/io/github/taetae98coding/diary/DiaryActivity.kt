package io.github.taetae98coding.diary

import android.Manifest
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.KeyboardShortcutInfo
import android.view.Menu
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.startup.AppInitializer
import io.github.taetae98coding.diary.initializer.GoogleCredentialInitializer
import io.github.taetae98coding.diary.initializer.LoggerInitializer
import kotlinx.coroutines.launch

internal class DiaryActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        callback = { /* do nothing.*/ },
    )

    private var isInitializerReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initInitializer()
        initPermission()
        initCompose()
        initSplash()
    }

    private fun initInitializer() {
        lifecycleScope.launch {
            with(AppInitializer.getInstance(this@DiaryActivity)) {
                initializeComponent(GoogleCredentialInitializer::class.java)
                initializeComponent(LoggerInitializer::class.java)
            }

            isInitializerReady = true
        }
    }

    private fun initCompose() {
        setContent {
            App()
        }
    }

    private fun initSplash() {
        val content = findViewById<View>(android.R.id.content)
        val listener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (isInitializerReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        }

        content.viewTreeObserver.addOnPreDrawListener(listener)
    }

    private fun initPermission() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
        )
    }

    override fun onProvideKeyboardShortcuts(
        data: MutableList<KeyboardShortcutGroup>?,
        menu: Menu?,
        deviceId: Int,
    ) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId)

        val navigation = KeyboardShortcutGroup(
            "탐색",
            listOf(
                KeyboardShortcutInfo("메모", KeyEvent.KEYCODE_1, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("태그", KeyEvent.KEYCODE_2, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("캘린더", KeyEvent.KEYCODE_3, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("버디 그룹", KeyEvent.KEYCODE_4, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("더보기", KeyEvent.KEYCODE_5, KeyEvent.META_CTRL_ON),
            ),
        )

        val actions = KeyboardShortcutGroup(
            "동작",
            listOf(
                KeyboardShortcutInfo("추가", KeyEvent.KEYCODE_A, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("실행", KeyEvent.KEYCODE_ENTER, KeyEvent.META_CTRL_ON),
            ),
        )

        val movement = KeyboardShortcutGroup(
            "캘린더",
            listOf(
                KeyboardShortcutInfo("이전 달", KeyEvent.KEYCODE_DPAD_LEFT, 0),
                KeyboardShortcutInfo("다음 달", KeyEvent.KEYCODE_DPAD_RIGHT, 0),
            ),
        )

        data?.add(navigation)
        data?.add(actions)
        data?.add(movement)
    }
}
