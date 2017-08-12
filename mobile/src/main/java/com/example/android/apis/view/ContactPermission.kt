package com.example.android.apis.view

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.*

class ContactPermission {
    fun checkPermission(context: Context, activity: Activity, edit: AutoCompleteTextView, contentResolver: ContentResolver) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        AutoComplete4.MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        AutoComplete4.MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            autoCompleteContacts(context, edit, contentResolver)
        }
    }

    fun autoCompleteContacts(context: Context, edit: AutoCompleteTextView, contentResolver: ContentResolver) {
        val content = contentResolver
        val cursor = content.query(ContactsContract.Contacts.CONTENT_URI,
                AutoComplete4.CONTACT_PROJECTION, null, null, null)
        val adapter = AutoComplete4.ContactListAdapter(context, cursor, 0)
        edit.setAdapter(adapter)
    }
}