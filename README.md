# react-native-account-kit
React Native Account Kit for Android and iOS (soon)

## Installation

```bash
npm install react-native-account-kit subscribable --save
```

### Configure native projects
#### [Android](https://github.com/CoericK/react-native-account-kit/tree/master/android/)
#### [iOS](https://github.com/CoericK/react-native-account-kit/tree/master/android/)

### How to use it
```javascript
...

import React, {
    DeviceEventEmitter
} from 'react-native';

import AccountKit from 'react-native-account-kit';
var Subscribable = require('subscribable');

var AK = new AccountKit();
...
class MyProject extends Component {
    ...
    
    constructor(props, context) {
        super(props, context);


        AK.getCurrentAccessToken()
            .then(function (at) {
                console.log(at);
            })
            .fail(function (e) {
                console.log(e);
            });

        AK.getCurrentAccount()
            .then(function (account_info) {
                console.log(account_info);
            })
            .fail(function (e) {
                console.log(e);
            });
    }
       
    mixins = [Subscribable.Mixin];
    ...
}
```

* To call the Login/Register View

```javascript
    AK.loginWithPhone(); // Expects as Response Type a TOKEN by Default
    //AK.loginWithPhone(AK.RESPONSE_TYPE_CODE); // Set the response type as TOKEN if you need it
    
    //Now we need to set the listener for the successful login
    
    componentWillMount() {
        DeviceEventEmitter.addListener('AccountKitLogged', function (e:Event) {
            console.log(e); // Will have the token/code information
        });
    }

}
```
