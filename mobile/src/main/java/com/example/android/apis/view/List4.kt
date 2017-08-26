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

import com.example.android.apis.Shakespeare

import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView


/**
 * A list view example where the data comes from a custom ListAdapter
 */
class List4 : ListActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use our own list adapter
        listAdapter = SpeechListAdapter(this)
    }


    /**
     * A sample ListAdapter that presents content from arrays of speeches and
     * text.
     *
     */
    private inner class SpeechListAdapter(
            /**
             * Remember our context so we can use it when constructing views.
             */
            private val mContext: Context) : BaseAdapter() {

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter.getCount
         */
        override fun getCount(): Int {
            return Shakespeare.TITLES.size
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter.getItem
         */
        override fun getItem(position: Int): Any {
            return position
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter.getItemId
         */
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        /**
         * Make a SpeechView to hold each row.
         *
         * @see android.widget.ListAdapter.getView
         */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val sv: SpeechView
            if (convertView == null) {
                sv = SpeechView(mContext, Shakespeare.TITLES[position],
                        Shakespeare.DIALOGUE[position])
            } else {
                sv = convertView as SpeechView
                sv.setTitle(Shakespeare.TITLES[position])
                sv.setDialogue(Shakespeare.DIALOGUE[position])
            }

            return sv
        }
    }

    /**
     * We will use a SpeechView to display each speech. It's just a LinearLayout
     * with two text fields.
     *
     */
    private inner class SpeechView(context: Context, title: String, words: String) : LinearLayout(context) {
        private val mTitle: TextView
        private val mDialogue: TextView

        init {
            this.orientation = LinearLayout.VERTICAL

            // Here we build the child views in code. They could also have
            // been specified in an XML file.

            mTitle = TextView(context)
            mTitle.text = title
            addView(mTitle, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))

            mDialogue = TextView(context)
            mDialogue.text = words
            addView(mDialogue, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }

        /**
         * Convenience method to set the title of a SpeechView
         */
        fun setTitle(title: String) {
            mTitle.text = title
        }

        /**
         * Convenience method to set the dialogue of a SpeechView
         */
        fun setDialogue(words: String) {
            mDialogue.text = words
        }
    }
}
