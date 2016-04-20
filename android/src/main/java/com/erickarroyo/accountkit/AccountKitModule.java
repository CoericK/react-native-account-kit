package com.erickarroyo.accountkit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.HashMap;
import java.util.Map;

public class AccountKitModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    public static final String REACT_CLASS = "AccountKitManager";
    public static int APP_REQUEST_CODE = 99;

    // Constants Exposed

    protected ReactApplicationContext reactContext;

    private Promise mPickerPromise;

    public AccountKitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        this.reactContext.addActivityEventListener(this);
        AccountKit.initialize(this.reactContext.getApplicationContext());


    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    /*
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        return constants;
    }
    */


    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }


    @ReactMethod
    public void loginWithPhone(final String response_type) {
        final Intent intent = new Intent(this.reactContext.getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, (response_type.equals("TOKEN") ? AccountKitActivity.ResponseType.TOKEN : AccountKitActivity.ResponseType.CODE));
        // ... perform additional configuration ...
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        this.reactContext.startActivityForResult(intent, APP_REQUEST_CODE, new Bundle());
    }


    @ReactMethod
    public void getCurrentAccessToken(Callback callback) {
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        WritableMap map = Arguments.createMap();

        if (accessToken != null) {
            map.putString("account_id", accessToken.getAccountId());
            map.putString("token", accessToken.getToken());
            map.putString("token_refresh_interval", Long.toString(accessToken.getTokenRefreshIntervalSeconds()));
            map.putString("last_refresh", Long.toString(accessToken.getLastRefresh().getTime()));
            callback.invoke(null, map);
        } else {
            callback.invoke("E_NO_ACCESS_TOKEN", "Access Token Doesnt exists");
        }


    }

    @ReactMethod
    public void getCurrentAccount(final Callback callback) {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                WritableMap map = Arguments.createMap();
                // Get Account Kit ID
                String accountKitId = account.getId();
                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
                // Get email
                String email = account.getEmail();

                map.putString("id", accountKitId);
                map.putString("phone_number", phoneNumberString);
                map.putString("email", email);

                callback.invoke(null, map);
            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
                callback.invoke("E_NO_ACCOUNT", "Account doesnt exists");
            }
        });
    }

    @ReactMethod
    public void logOut() {
        AccountKit.logOut();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("Request Code", "" + requestCode);
        //Log.d("Result Code", "" + resultCode);
        //Log.d("Intent", data.toString());


        //this.reactContext.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request


            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());


            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";

                //sendEvent(reactContext, "AccountKitLoginCancelled", params);


            } else {


                final AccessToken accessToken = loginResult.getAccessToken();
                final String authorizationCode = loginResult.getAuthorizationCode();
                final long tokenRefreshIntervalInSeconds = loginResult.getTokenRefreshIntervalInSeconds();
                final String finalAuthorizationState = loginResult.getFinalAuthorizationState();

                WritableMap map = Arguments.createMap();
                if (accessToken != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    map.putString("account_id", accessToken.getAccountId());
                    map.putString("token", accessToken.getToken());
                    map.putString("token_refresh_interval", Long.toString(accessToken.getTokenRefreshIntervalSeconds()));
                    map.putString("last_refresh", Long.toString(accessToken.getLastRefresh().getTime()));


                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));

                    map.putString("auth_code", authorizationCode.substring(0, 10));
                    map.putString("auth_state", finalAuthorizationState);


                }


                sendEvent(reactContext, "AccountKitLogged", map);
                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                //goToMyLoggedInActivity();
            }

            // Surface the result to your user in an appropriate way.
            //Toast.makeText(this.reactContext, toastMessage, Toast.LENGTH_LONG).show();


        }


    }

}
