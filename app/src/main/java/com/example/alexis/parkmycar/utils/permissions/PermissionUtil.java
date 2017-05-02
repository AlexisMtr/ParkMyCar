package com.example.alexis.parkmycar.utils.permissions;



import android.Manifest;

import android.app.Activity;

import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;

import android.widget.Toast;





public abstract class PermissionUtil {

    public static boolean checkPermissions(final Activity activity, final int idCall, final String... permissions) {

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED) {
                return requestCallPermission(activity, idCall, permission);
            }
        }
        return true;
    }


    public static boolean requestCallPermission(final Activity activity, final int idCall, final String... permissions) {

        boolean permissionDone = true;

        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, permissions, idCall);
                permissionDone = false;
            }
        }
        Toast.makeText(activity, "Permission activée", Toast.LENGTH_LONG).show();
        return permissionDone;
    }
}