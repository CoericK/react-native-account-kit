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