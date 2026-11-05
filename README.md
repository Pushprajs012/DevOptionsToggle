# Dev Options Switch

Ek chhota open-source Android app jo **Developer Options ko bina restart ke ON/OFF** karta hai
(aur chahe to USB debugging bhi band karta hai). Kuch phones (jaise Lava) UI se toggle karne par
restart maangte hain; yeh app `Settings.Global` ko direct likh kar wahi kaam kar deta hai.

## Yeh kaise kaam karta hai
App `WRITE_SECURE_SETTINGS` permission use karta hai. Yeh permission normal user ko UI se nahi
milti, **ek baar ADB se grant karni padti hai**. Uske baad hamesha kaam karti hai
(restart par bhi), jab tak app uninstall na ho.

## Use karne ka tarika (user ke liye)
1. Phone mein Developer Options + USB Debugging ON karo (ek baar).
2. App install karo aur kholo. App ke andar apna exact command dikhega, "Copy command" daba sakte ho.
3. PC se phone connect karke chalao:
   `adb shell pm grant com.devoptoggle.app android.permission.WRITE_SECURE_SETTINGS`
4. App dobara kholo. "Permission granted" dikhega.
5. "Developer Options OFF" dabao. Ho gaya. Dobara ON karne ke liye "ON" button.

### Bina PC ke (Android 11+)
1. Shizuku install karo, Settings > Developer options > Wireless debugging se Shizuku start karo.
2. Shizuku ya kisi terminal app (jise Shizuku access ho) se wahi `pm grant` command chalao.

## Build karna (developer ke liye)
1. Android Studio (Koala ya naya) mein `DevOptionsToggle` folder open karo. Gradle sync apne aap hoga.
2. Test: Run button ya `Build > Build APK(s)`.
3. Play Store ke liye: `Build > Generate Signed Bundle / APK > Android App Bundle (.aab)`,
   apni keystore banao (kho mat dena, backup rakho).
4. `applicationId` (com.devoptoggle.app) ko apne unique id se badlo (app/build.gradle.kts aur
   `namespace`, aur package folder) agar aap khud publish kar rahe ho.
5. `targetSdk` ko Play ki current requirement tak rakho.

## Dusre developers ke liye
Is project mein koi dependency nahi hai. Core logic `MainActivity.apply()` mein 2 line ka hai:
```kotlin
Settings.Global.putInt(contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0)
Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, 0)
```
Bas manifest mein `WRITE_SECURE_SETTINGS` declare karo aur ADB se grant karo.

## Limitations
- Permission ADB ke bina nahi milti. Yeh Android ka security design hai, workaround nahi hai.
- Kuch OEM ROMs (MIUI, ColorOS etc.) extra restrictions laga sakti hain.
- Developer Options menu ka entry kuch phones par tab bhi dikh sakta hai; setting value 0 ho jaati hai.

License: MIT
