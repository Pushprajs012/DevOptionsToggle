# Play Store Listing (copy-paste)

## App name (max 30)
Dev Options Switch

## Short description (max 80)
Turn Developer Options ON/OFF instantly with one tap. No restart needed.

## Full description (max 4000)
Dev Options Switch lets you turn Developer Options on or off with a single tap, without restarting your phone.

Some phones ask for a restart or hide the toggle when you try to switch Developer Options off from Settings. This app does it directly and instantly.

FEATURES
- One-tap Developer Options ON / OFF
- Optionally turn off USB debugging at the same time
- Shows current Developer Options state
- Built-in copy button for the one-time setup command
- English and Hindi
- No ads, no internet access, no data collection
- Tiny app, no extra libraries

HOW TO USE
1. Enable Developer Options and USB Debugging once from Settings.
2. Install and open the app. It shows a one-time command with your app id.
3. Connect the phone to a computer and run:
   adb shell pm grant com.devoptoggle.app android.permission.WRITE_SECURE_SETTINGS
   (Android 11+ users can also do this without a PC using Shizuku with Wireless debugging.)
4. Reopen the app. When it shows "Permission granted", tap "Developer Options OFF".

WHY IS A ONE-TIME ADB STEP NEEDED?
Android protects system settings. Changing them requires the WRITE_SECURE_SETTINGS permission, which can only be granted by the user through ADB. This is a deliberate Android security rule. The permission stays until you uninstall the app.

PRIVACY
The app does not collect, store or share any data and has no internet permission. It only changes the Developer Options setting on your device when you tap a button.

## Category
Tools

## Tags / keywords
developer options, usb debugging, adb, settings, toggle, developer mode

## Content rating
Everyone (answer questionnaire: no violence, no user content, no ads)

## Data safety form
- Data collected: None
- Data shared: None
- Encryption/deletion: Not applicable

## "Permissions declaration" / reviewer note (paste if asked)
The app declares android.permission.WRITE_SECURE_SETTINGS. This permission is not granted at install; the user grants it explicitly through ADB. The app uses it solely to write Settings.Global.development_settings_enabled and adb_enabled when the user taps the on-screen buttons. No data is read, collected or transmitted. The app has no INTERNET permission.

## Screenshots needed
At least 2 phone screenshots (take: permission missing screen, permission granted screen).
Feature graphic 1024x500, icon 512x512 (export from Android Studio: Image Asset).
