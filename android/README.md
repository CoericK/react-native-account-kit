* In `android/settings.gradle`

```gradle
...
include ':react-native-account-kit'
project(':react-native-account-kit').projectDir = new File(settingsDir, '../node_modules/react-native-account-kit/android')
```

* In `android/app/build.gradle`

```gradle
...
dependencies {
    ...
    compile project(':react-native-account-kit')
}
```

* Register Module (in MainActivity.java)

```java
import com.erickarroyo.accountkit.AccountKitPackage;;  // <--- import

public class MainActivity extends .... {
  
  ......

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new AccountKitPackage() // <------ add this line to your MainActivity class
        );
    }
  }

  ......
}
```

* In `manifest/AndroidManifest.xml`

```xml
...
    <manifest ...>
        ...
            <uses-permission android:name="android.permission.RECEIVE_SMS" />
            <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        ...
        
        <application ...>
            ...
            
            <meta-data
                android:name="com.facebook.sdk.ApplicationId"
                android:value="@string/app_id" />
            <meta-data
                android:name="com.facebook.accountkit.ClientToken"
                android:value="@string/ACCOUNT_KIT_CLIENT_TOKEN" />
            <meta-data
                android:name="com.facebook.accountkit.ApplicationName"
                android:value="@string/app_name" />
                
                
             <activity
                        android:name="com.facebook.accountkit.ui.AccountKitActivity"
                        android:theme="@style/AppLoginTheme"
                        tools:replace="android:theme" />
            ...
        </application>
    ...
```


* Create/Add a custom theme `values/themes.xml` to see more themes check the [Facebook Demo](https://github.com/fbsamples/account-kit-samples-for-android/blob/master/AccountKitSample/res/values/themes.xml)

```xml
...
<?xml version="1.0" encoding="utf-8"?>

...
<resources>

    ...
    <style name="AppLoginTheme" parent="Theme.AccountKit" />

    <style name="AppLoginTheme.Salmon" parent="Theme.AccountKit">
        <item name="com_accountkit_primary_color">@android:color/white</item>
        <item name="com_accountkit_primary_text_color">#565a5c</item>
        <item name="com_accountkit_secondary_color">#ffe5e5</item>
        <item name="com_accountkit_secondary_text_color">
            ?attr/com_accountkit_primary_text_color
        </item>
        <item name="com_accountkit_status_bar_color">@android:color/black</item>

        <item name="com_accountkit_button_background_color">#ff5a5f</item>
        <item name="com_accountkit_button_text_color">@android:color/white</item>
        <item name="com_accountkit_icon_color">#ff5a5f</item>
        <item name="com_accountkit_toolbar_elevation">8dp</item>
    </style>

    <style name="AppLoginTheme.Yellow" parent="Theme.AccountKit.Outline">
        <item name="com_accountkit_primary_color">#f4bf56</item>
        <item name="com_accountkit_primary_text_color">@android:color/white</item>
        <item name="com_accountkit_secondary_text_color">#44566b</item>
        <item name="com_accountkit_status_bar_color">#ed9d00</item>

        <item name="com_accountkit_input_accent_color">?attr/com_accountkit_primary_color</item>
        <item name="com_accountkit_input_border_color">?attr/com_accountkit_primary_color</item>
    </style>
    ...
</resources>

```