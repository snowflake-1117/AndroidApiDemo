/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.view

import android.Manifest
import com.example.android.apis.R

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.autocomplete_4.*

class AutoComplete4 : Activity() {
    val contactPermission: ContactPermission = ContactPermission()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autocomplete_4)

        contactPermission.checkPermission(this, this, edit, contentResolver)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    contactPermission.autoCompleteContacts(this, edit, contentResolver)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showRequestAgainDialog()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    /**
     * When user denied READ_CONTACTS permission than show this dialog for persuasion.
     */
    fun showRequestAgainDialog(): Unit {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.autocomplete_4_showRequestAgainDialog)
        builder.setPositiveButton(R.string.autocomplete_4_setPositiveButton) {
            _, _ ->
            try {
                val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + packageName))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                val intent: Intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                startActivity(intent)
            }
        }
        builder.setNegativeButton(R.string.autocomplete_4_setNegativeButton) {
            _, _ ->
            finish()
        }
        builder.create()
        builder.show()
    }

    // XXX compiler bug in javac 1.5.0_07-164, we need to implement Filterable
    // to make compilation work
    class ContactListAdapter(context: Context, c: Cursor, flags: Int) : CursorAdapter(context, c, flags), Filterable {
        private var mContent: ContentResolver = context.contentResolver

        override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false) as TextView
            view.text = cursor.getString(COLUMN_DISPLAY_NAME)
            return view
        }

        override fun bindView(view: View, context: Context?, cursor: Cursor) {
            (view as TextView).text = cursor.getString(COLUMN_DISPLAY_NAME)
        }

        override fun convertToString(cursor: Cursor): String {
            return cursor.getString(COLUMN_DISPLAY_NAME)
        }

        override fun runQueryOnBackgroundThread(constraint: CharSequence?): Cursor {
            val filter: FilterQueryProvider? = filterQueryProvider
            if (filter != null) {
                return if (constraint != null) filter.runQuery(constraint) else filter.runQuery("")
            }

            val uri = Uri.withAppendedPath(
                    ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(constraint.toString()))
            return mContent.query(uri, CONTACT_PROJECTION, null, null, null)
        }
    }

    companion object {
        val CONTACT_PROJECTION = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)
        private val COLUMN_DISPLAY_NAME = 1
        val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
    }
}