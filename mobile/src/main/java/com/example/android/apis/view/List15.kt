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

import com.example.android.apis.R

import android.app.ListActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

/**
 * This demo illustrates the use of CHOICE_MODE_MULTIPLE_MODAL, a.k.a. selection mode on ListView.
 */
class List15 : ListActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listView.apply {
            choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
            setMultiChoiceModeListener(ModeCallback())
        }

        listAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_checked, mStrings)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBar.subtitle = "Long press to start selection"
    }

    private inner class ModeCallback : AbsListView.MultiChoiceModeListener {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.list_select_menu, menu)
            mode.title = "Select Items"
            setSubtitle(mode)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.share -> {
                    Toast.makeText(this@List15, "Shared " + listView.checkedItemCount +
                            " items", Toast.LENGTH_SHORT).show()
                    mode.finish()
                }
                else -> Toast.makeText(this@List15, "Clicked " + item.title,
                        Toast.LENGTH_SHORT).show()
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {}

        override fun onItemCheckedStateChanged(mode: ActionMode,
                                               position: Int, id: Long, checked: Boolean) {
            setSubtitle(mode)
        }

        private fun setSubtitle(mode: ActionMode) {
            val checkedCount = listView.checkedItemCount
            when (checkedCount) {
                0 -> mode.subtitle = null
                1 -> mode.subtitle = "One item selected"
                else -> mode.subtitle = "" + checkedCount + " items selected"
            }
        }
    }

    private val mStrings = Cheeses.sCheeseStrings
}
