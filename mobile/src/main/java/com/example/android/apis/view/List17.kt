/*
 * Copyright (C) 2010 The Android Open Source Project
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
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView


/**
 * A list view where the last item the user clicked is placed in
 * the "activated" state, causing its background to highlight.
 */
class List17 : ListActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the built-in layout for showing a list item with a single
        // line of text whose background is changes when activated.
        listAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_activated_1, mStrings)
        listView.isTextFilterEnabled = true

        // Tell the list view to show one checked/activated item at a time.
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        // Start with first item activated.
        // Make the newly clicked item the currently selected one.
        listView.setItemChecked(0, true)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        // Make the newly clicked item the currently selected one.
        listView.setItemChecked(position, true)
    }

    private val mStrings = Cheeses.sCheeseStrings
}
