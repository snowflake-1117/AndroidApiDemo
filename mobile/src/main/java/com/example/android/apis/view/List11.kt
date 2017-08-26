/*
 * Copyright (C) 2008 The Android Open Source Project
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
import android.widget.ArrayAdapter
import android.widget.ListView

/**
 * This example shows how to use choice mode on a list. This list is
 * in CHOICE_MODE_MULTIPLE mode, which means the items behave like
 * checkboxes.
 */
class List11 : ListActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, GENRES)

        listView.apply {
            itemsCanFocus = false
            choiceMode = ListView.CHOICE_MODE_MULTIPLE
        }
    }

    companion object {
        private val GENRES = arrayOf("Action", "Adventure", "Animation", "Children", "Comedy",
                "Documentary", "Drama", "Foreign", "History", "Independent", "Romance", "Sci-Fi",
                "Television", "Thriller")
    }
}
