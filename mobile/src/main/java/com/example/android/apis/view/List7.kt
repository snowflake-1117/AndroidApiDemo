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

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R


import android.app.ListActivity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.list_7.*

/**
 * A list view example where the data comes from a cursor.
 */
class List7 : ListActivity(), OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private val mPhone: TextView by lazy { phone }

    // Get a cursor with all numbers.
    // This query will only return contacts with phone numbers
    private val mCursor: Cursor by lazy {
        contentResolver.query(Phone.CONTENT_URI,
                PHONE_PROJECTION, Phone.NUMBER + " NOT NULL", null, null)
    }

    private val mAdapter: SimpleCursorAdapter by lazy {
        SimpleCursorAdapter(this,
                // Use a template that displays a text view
                android.R.layout.simple_list_item_1,
                // Give the cursor to the list adapter
                mCursor,
                // Map the DISPLAY_NAME column to...
                arrayOf(Phone.DISPLAY_NAME),
                // The "text1" view defined in the XML template
                intArrayOf(android.R.id.text1),
                0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_7)
        listView.onItemSelectedListener = this

        listAdapter = mAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
        if (position >= 0) {
            //Get current cursor
            val c = parent.getItemAtPosition(position) as Cursor
            val type = c.getInt(COLUMN_PHONE_TYPE)
            val phone = c.getString(COLUMN_PHONE_NUMBER)
            var label: String? = null
            //Custom type? Then get the custom label
            if (type == Phone.TYPE_CUSTOM) {
                label = c.getString(COLUMN_PHONE_LABEL)
            }
            //Get the readable string
            val numberType = Phone.getTypeLabel(resources, type, label) as String
            val text = numberType + ": " + phone
            mPhone.text = text
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(this@List7, ContactsContract.Contacts.CONTENT_URI, PHONE_PROJECTION, null, null, null);
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        mAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        mAdapter.swapCursor(null)
    }

    companion object {

        private val PHONE_PROJECTION = arrayOf(Phone._ID, Phone.TYPE, Phone.LABEL, Phone.NUMBER, Phone.DISPLAY_NAME)

        private val COLUMN_PHONE_TYPE = 1
        private val COLUMN_PHONE_LABEL = 2
        private val COLUMN_PHONE_NUMBER = 3
    }
}
