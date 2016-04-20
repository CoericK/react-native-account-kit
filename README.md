# react-native-account-kit
React Native Account Kit for Android and iOS (soon)

## Installation

```bash
npm install react-native-account-kit --save
```
### Add it to your android project

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

### Configure native projects
#### [Android](https://github.com/CoericK/react-native-account-kit/tree/master/android/)
#### [iOS](https://github.com/CoericK/react-native-account-kit/tree/master/android/)
