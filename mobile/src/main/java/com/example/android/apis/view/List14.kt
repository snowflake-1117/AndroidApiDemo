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
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ImageView
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.example.android.apis.R

/**
 * Demonstrates how to write an efficient list adapter. The adapter used in this example binds
 * to an ImageView and to a TextView for each row in the list.
 *
 * To work efficiently the adapter implemented here uses two techniques:
 * - It reuses the convertView passed to getView() to avoid inflating View when it is not necessary
 * - It uses the ViewHolder pattern to avoid calling findViewById() when it is not necessary
 *
 * The ViewHolder pattern consists in storing a data structure in the tag of the view returned by
 * getView(). This data structures contains references to the views we want to bind data to, thus
 * avoiding calls to findViewById() every time getView() is invoked.
 */
class List14 : ListActivity() {

    private class EfficientAdapter(context: Context) : BaseAdapter() {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        private val mInflater: LayoutInflater = LayoutInflater.from(context)
        // Icons bound to the rows.
        private val mIcon1: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon48x48_1)
        private val mIcon2: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon48x48_2)

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter.getCount
         */
        override fun getCount(): Int {
            return DATA.size
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
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter.getView
         */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            // A ViewHolder keeps references to children views to avoid unnecessary calls
            // to findViewById() on each row.
            val holder: ViewHolder

            // When convertView is not null, we can reuse it directly, there is no need
            // to re-inflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_icon_text, parent, false)

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = ViewHolder()
                holder.text = convertView.findViewById<View>(R.id.text) as TextView
                holder.icon = convertView.findViewById<View>(R.id.icon) as ImageView

                convertView.tag = holder
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = convertView.tag as ViewHolder
            }

            // Bind the data efficiently with the holder.
            holder.text.text = DATA[position]
            holder.icon.setImageBitmap(if ((position and 1) == 1) mIcon1 else mIcon2)

            return convertView!!
        }

        internal class ViewHolder {
            lateinit var text: TextView
            lateinit var icon: ImageView
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = EfficientAdapter(this)
    }

    companion object {
        private val DATA = Cheeses.sCheeseStrings
    }
}
