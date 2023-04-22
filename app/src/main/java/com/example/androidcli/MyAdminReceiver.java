package com.example.androidcli;

//import static android.os.UserManager.DISALLOW_CHANGE_WIFI_STATE;

import static android.os.UserManager.DISALLOW_BLUETOOTH;
import static android.os.UserManager.DISALLOW_CONFIG_BLUETOOTH;
import static android.os.UserManager.DISALLOW_CONFIG_WIFI;
import static android.os.UserManager.DISALLOW_UNINSTALL_APPS;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyAdminReceiver extends DeviceAdminReceiver {

void showToast(Context context, CharSequence msg) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
}

@Override
public void onPasswordFailed(Context context, Intent intent) {
    showToast(context, "Sample Device Admin: pw failed");
    Log.d("Hello", "onPasswordFailed");
    DevicePolicyManager mgr = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    int no = mgr.getCurrentFailedPasswordAttempts();


    if (no >= 3) {
        showToast(context, "3 failure");
        mgr.resetPassword("111111", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        //mgr.lockNow();
    }
}

@Override
public void onEnabled(Context context, Intent intent)
{
    showToast(context, "Sample Device Admin: enabled");
}


@Override
public CharSequence onDisableRequested(Context context, Intent intent)
{
    return "This is an optional message to warn the user about disabling.";
}

@Override
public void onDisabled(Context context, Intent intent) {
    showToast(context, "Sample Device Admin: disabled");
}

@Override
public void onPasswordChanged(Context context, Intent intent) {
    showToast(context, "Sample Device Admin: pw changed");
}



@Override
public void onPasswordSucceeded(Context context, Intent intent) {
    showToast(context, "Sample Device Admin: pw succeeded");
    }
}
