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

import com.example.android.apis.R

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.CursorAdapter
import android.widget.FilterQueryProvider
import android.widget.Filterable
import android.widget.TextView

class AutoComplete4 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autocomplete_4)

        val content = contentResolver
        val cursor = content.query(Contacts.CONTENT_URI,
                CONTACT_PROJECTION, null, null, null)

        val adapter = ContactListAdapter(this, cursor)

        val textView = findViewById<View>(R.id.edit) as AutoCompleteTextView
        textView.setAdapter(adapter)
    }

    // XXX compiler bug in javac 1.5.0_07-164, we need to implement Filterable
    // to make compilation work
    class ContactListAdapter(context: Context, c: Cursor) : CursorAdapter(context, c), Filterable {
        private val mContent: ContentResolver
        init {
            mContent = context.contentResolver
        }

        override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false) as TextView
            view.text = cursor.getString(COLUMN_DISPLAY_NAME)
            return view
        }

        override fun bindView(view: View, context: Context, cursor: Cursor) {
            (view as TextView).text = cursor.getString(COLUMN_DISPLAY_NAME)
        }

        override fun convertToString(cursor: Cursor): String {
            return cursor.getString(COLUMN_DISPLAY_NAME)
        }

        override fun runQueryOnBackgroundThread(constraint: CharSequence): Cursor {
            val filter = filterQueryProvider
            if (filter != null) {
                return filter.runQuery(constraint)
            }

            val uri = Uri.withAppendedPath(
                    Contacts.CONTENT_FILTER_URI,
                    Uri.encode(constraint.toString()))
            return mContent.query(uri, CONTACT_PROJECTION, null, null, null)
        }
    }

    companion object {

        val CONTACT_PROJECTION = arrayOf(Contacts._ID, Contacts.DISPLAY_NAME)

        private val COLUMN_DISPLAY_NAME = 1
    }
}