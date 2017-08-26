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

import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.AbsListView
import kotlinx.android.synthetic.main.list_8.*

import java.util.ArrayList


/**
 * A list view that demonstrates the use of setEmptyView. This example alos uses
 * a custom layout file that adds some extra buttons to the screen.
 */
class List8 : ListActivity() {

    internal val mAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use a custom layout file
        setContentView(R.layout.list_8)

        // Tell the list view which view to display when the list is empty
        listView.emptyView = empty

        // Set up our adapter
        listAdapter = mAdapter

        // Wire up the clear button to remove all photos
        clear.setOnClickListener { mAdapter.clearPhotos() }

        // Wire up the add button to add a new photo
        add.setOnClickListener { mAdapter.addPhotos() }
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    inner class PhotoAdapter(private val mContext: Context) : BaseAdapter() {

        private val mPhotoPool = arrayOf(R.drawable.sample_thumb_0, R.drawable.sample_thumb_1, R.drawable.sample_thumb_2, R.drawable.sample_thumb_3, R.drawable.sample_thumb_4, R.drawable.sample_thumb_5, R.drawable.sample_thumb_6, R.drawable.sample_thumb_7)

        private val mPhotos = ArrayList<Int>()

        override fun getCount(): Int {
            return mPhotos.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View =
                // Make an ImageView to show a photo
                ImageView(mContext).apply {
                    setImageResource(mPhotos[position])
                    adjustViewBounds = true
                    layoutParams = AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT)
                    // Give it a nice background
                    setBackgroundResource(R.drawable.picture_frame)
                }

        fun clearPhotos() {
            mPhotos.clear()
            notifyDataSetChanged()
        }

        fun addPhotos() {
            val whichPhoto = Math.round(Math.random() * (mPhotoPool.size - 1)).toInt()
            val newPhoto = mPhotoPool[whichPhoto]
            mPhotos.add(newPhoto)
            notifyDataSetChanged()
        }

    }
}
