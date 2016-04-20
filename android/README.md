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