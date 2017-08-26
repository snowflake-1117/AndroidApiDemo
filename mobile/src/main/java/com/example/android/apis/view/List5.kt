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
import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * A list view example with separators.
 */
class List5 : ListActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = MyListAdapter(this)
    }

    private inner class MyListAdapter(private val mContext: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return mStrings.size
        }

        override fun areAllItemsEnabled(): Boolean {
            return false
        }

        override fun isEnabled(position: Int): Boolean {
            return !mStrings[position].startsWith("-")
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val tv: TextView
            if (convertView == null) {
                tv = LayoutInflater.from(mContext).inflate(
                        android.R.layout.simple_expandable_list_item_1, parent, false) as TextView
            } else {
                tv = convertView as TextView
            }
            tv.text = mStrings[position]
            return tv
        }
    }

    private val mStrings = arrayOf("----------", "----------", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "----------", "Abondance", "----------", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "----------", "Airedale", "Aisy Cendre", "----------", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese", "Ami du Chambertin", "----------", "----------", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "----------", "----------")
}