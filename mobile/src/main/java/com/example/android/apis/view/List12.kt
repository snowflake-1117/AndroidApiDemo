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
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnKeyListener
import android.widget.ArrayAdapter
import android.widget.EditText

import com.example.android.apis.R
import kotlinx.android.synthetic.main.list_12.*

import java.util.ArrayList

/**
 * Demonstrates the using a list view in transcript mode
 *
 */
class List12 : ListActivity(), OnClickListener, OnKeyListener {

    private val mUserText: EditText by lazy { userText }

    private val mAdapter: ArrayAdapter<String>
            by lazy { ArrayAdapter(this, android.R.layout.simple_list_item_1, mStrings) }

    private val mStrings = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_12)

        listAdapter = mAdapter

        mUserText.setOnClickListener(this)
        mUserText.setOnKeyListener(this)
    }

    override fun onClick(v: View) {
        sendText()
    }

    private fun sendText() {
        val text = mUserText.text.toString()
        mAdapter.add(text)
        mUserText.text = null
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                    sendText()
                    return true
                }
            }
        }
        return false
    }
}
