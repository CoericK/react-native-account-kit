'use strict';
import React from 'react-native';

var Q = require('q');

const {DeviceEventEmitter,  NativeModules } = React;

const {AccountKitManager} = NativeModules;

export default class AccountKit {

    constructor() {
        this.RESPONSE_TYPE_TOKEN = 'TOKEN';
        this.RESPONSE_TYPE_CODE = 'CODE';
    }


    loginWithPhone(response_type = 'TOKEN') {
        if (response_type === this.RESPONSE_TYPE_TOKEN || response_type === this.RESPONSE_TYPE_CODE) {
            AccountKitManager.loginWithPhone(response_type);
        } else {
            console.log('Warning: Please select a valid response type');
        }
    }


    getCurrentAccessToken() {
        var d = Q.defer();
        AccountKitManager.getCurrentAccessToken((e, access_token)=> {
            if (e) {
                d.reject(e);
            } else {
                d.resolve(access_token);
            }
        });

        return d.promise;
    }

    getCurrentAccount() {
        var d = Q.defer();
        AccountKitManager.getCurrentAccount((e, current_account)=> {
            if (e) {
                d.reject(e);
            } else {
                d.resolve(current_account);
            }
        });

        return d.promise;
    }

    logOut() {
        AccountKitManager.logOut();
    }


}