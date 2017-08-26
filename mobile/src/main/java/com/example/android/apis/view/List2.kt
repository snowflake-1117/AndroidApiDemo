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

import android.app.ListActivity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.provider.ContactsContract.Contacts
import android.os.Bundle
import android.widget.SimpleCursorAdapter

/**
 * A list view example where the
 * data comes from a cursor.
 */
class List2 : ListActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    // Get a cursor with all people
    private val mCursor: Cursor by lazy {
        contentResolver.query(Contacts.CONTENT_URI,
                CONTACT_PROJECTION, null, null, null)
    }

    private val mAdapter: SimpleCursorAdapter by lazy {
        SimpleCursorAdapter(this@List2,
                // Use a template that displays a text view
                android.R.layout.simple_list_item_1,
                // Give the cursor to the list adatper
                mCursor,
                // Map the NAME column in the people database to...
                arrayOf(Contacts.DISPLAY_NAME),
                // The "text1" view defined in the XML template
                intArrayOf(android.R.id.text1),
                0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = mAdapter

        loaderManager.initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(this@List2, Contacts.CONTENT_URI, CONTACT_PROJECTION, null, null, null);
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        mAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        mAdapter.swapCursor(null)
    }

    companion object {
        private val CONTACT_PROJECTION = arrayOf(Contacts._ID, Contacts.DISPLAY_NAME)
    }
}