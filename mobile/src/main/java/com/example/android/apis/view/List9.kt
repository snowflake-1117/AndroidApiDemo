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
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.TextView


/**
 * Another variation of the list of cheeses. In this case, we use
 * [ AbsListView#setOnItemScrollListener(AbsListView.OnItemScrollListener)][AbsListView.setOnScrollListener] to display the
 * first letter of the visible range of cheeses.
 */
class List9 : ListActivity(), AbsListView.OnScrollListener {

    private inner class RemoveWindow : Runnable {
        override fun run() {
            removeWindow()
        }
    }

    private val mRemoveWindow = RemoveWindow()
    private val mWindowManager: WindowManager
            by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val inflate: LayoutInflater
            by lazy { getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val mDialogText: TextView
            by lazy { inflate.inflate(R.layout.list_position, null) as TextView }
    private var mHandler = Handler()
    private var mShowing: Boolean = false
    private var mReady: Boolean = false
    private var mPrevLetter = Character.MIN_VALUE

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
        listAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, mStrings)

        listView.setOnScrollListener(this)

        mDialogText.visibility = View.INVISIBLE

        mHandler.post {
            mReady = true
            val lp = WindowManager.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT)
            mWindowManager.addView(mDialogText, lp)
        }
    }

    override fun onResume() {
        super.onResume()
        mReady = true
    }


    override fun onPause() {
        super.onPause()
        removeWindow()
        mReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager.removeView(mDialogText)
        mReady = false
    }


    override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                          visibleItemCount: Int, totalItemCount: Int) {
        if (mReady) {
            val firstLetter = mStrings[firstVisibleItem][0]

            if (!mShowing && firstLetter != mPrevLetter) {

                mShowing = true
                mDialogText.visibility = View.VISIBLE
            }
            mDialogText.text = firstLetter.toString()
            mHandler.removeCallbacks(mRemoveWindow)
            mHandler.postDelayed(mRemoveWindow, 3000)
            mPrevLetter = firstLetter
        }
    }


    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}


    private fun removeWindow() {
        if (mShowing) {
            mShowing = false
            mDialogText.visibility = View.INVISIBLE
        }
    }

    private val mStrings = Cheeses.sCheeseStrings
}
