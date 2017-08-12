/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.app.ActionBar
import android.app.ActionBar.Tab
import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.SeekBar
import android.widget.ShareActionProvider
import android.widget.TextView
import android.widget.Toast
import android.widget.SearchView.OnQueryTextListener

import com.example.android.apis.R

/**
 * This activity demonstrates how to use system UI flags to implement
 * a content browser style of UI (such as a book reader).
 */
class ContentBrowserActivity : Activity(), OnQueryTextListener, ActionBar.TabListener {

    /**
     * Implementation of a view for displaying immersive content, using system UI
     * flags to transition in and out of modes where the user is focused on that
     * content.
     */
    //BEGIN_INCLUDE(content)
    class Content(context: Context, attrs: AttributeSet) : ScrollView(context, attrs), View.OnSystemUiVisibilityChangeListener, View.OnClickListener {
        internal var mText: TextView
        internal var mTitleView: TextView
        internal var mSeekView: SeekBar
        internal var mNavVisible: Boolean = false
        internal var mBaseSystemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        internal var mLastSystemUiVis: Int = 0

        internal var mNavHider: Runnable = Runnable { setNavVisibility(false) }

        init {

            mText = TextView(context)
            mText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            mText.text = context.getString(R.string.alert_dialog_two_buttons2ultra_msg)
            mText.isClickable = false
            mText.setOnClickListener(this)
            mText.setTextIsSelectable(true)
            addView(mText, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            setOnSystemUiVisibilityChangeListener(this)
        }

        fun init(title: TextView, seek: SeekBar) {
            // This called by the containing activity to supply the surrounding
            // state of the content browser that it will interact with.
            mTitleView = title
            mSeekView = seek
            setNavVisibility(true)
        }

        override fun onSystemUiVisibilityChange(visibility: Int) {
            // Detect when we go out of low-profile mode, to also go out
            // of full screen.  We only do this when the low profile mode
            // is changing from its last state, and turning off.
            val diff = mLastSystemUiVis xor visibility
            mLastSystemUiVis = visibility
            if (diff and View.SYSTEM_UI_FLAG_LOW_PROFILE != 0 && visibility and View.SYSTEM_UI_FLAG_LOW_PROFILE == 0) {
                setNavVisibility(true)
            }
        }

        override fun onWindowVisibilityChanged(visibility: Int) {
            super.onWindowVisibilityChanged(visibility)

            // When we become visible, we show our navigation elements briefly
            // before hiding them.
            setNavVisibility(true)
            handler.postDelayed(mNavHider, 2000)
        }

        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            super.onScrollChanged(l, t, oldl, oldt)

            // When the user scrolls, we hide navigation elements.
            setNavVisibility(false)
        }

        override fun onClick(v: View) {
            // When the user clicks, we toggle the visibility of navigation elements.
            val curVis = systemUiVisibility
            setNavVisibility(curVis and View.SYSTEM_UI_FLAG_LOW_PROFILE != 0)
        }

        internal fun setBaseSystemUiVisibility(visibility: Int) {
            mBaseSystemUiVisibility = visibility
        }

        internal fun setNavVisibility(visible: Boolean) {
            var newVis = mBaseSystemUiVisibility
            if (!visible) {
                newVis = newVis or (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
            val changed = newVis == systemUiVisibility

            // Unschedule any pending event to hide navigation if we are
            // changing the visibility, or making the UI visible.
            if (changed || visible) {
                val h = handler
                h?.removeCallbacks(mNavHider)
            }

            // Set the new desired visibility.
            systemUiVisibility = newVis
            mTitleView.visibility = if (visible) View.VISIBLE else View.INVISIBLE
            mSeekView.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        }
    }
    //END_INCLUDE(content)

    internal var mContent: Content

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)

        setContentView(R.layout.content_browser)
        mContent = findViewById<View>(R.id.content) as Content
        mContent.init(findViewById<View>(R.id.title) as TextView,
                findViewById<View>(R.id.seekbar) as SeekBar)

        val bar = actionBar
        bar!!.addTab(bar.newTab().setText("Tab 1").setTabListener(this))
        bar.addTab(bar.newTab().setText("Tab 2").setTabListener(this))
        bar.addTab(bar.newTab().setText("Tab 3").setTabListener(this))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.content_actions, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)

        // Set file with share history to the provider and set the share intent.
        val actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar)
        val actionProvider = actionItem.actionProvider as ShareActionProvider
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME)
        // Note that you can set/change the intent any time,
        // say when the user has selected an image.
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        val uri = Uri.fromFile(getFileStreamPath("shared.png"))
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        actionProvider.setShareIntent(shareIntent)
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * This method is declared in the menu.
     */
    fun onSort(item: MenuItem) {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_tabs -> {
                actionBar!!.navigationMode = ActionBar.NAVIGATION_MODE_TABS
                item.isChecked = true
                return true
            }
            R.id.hide_tabs -> {
                actionBar!!.navigationMode = ActionBar.NAVIGATION_MODE_STANDARD
                item.isChecked = true
                return true
            }
            R.id.stable_layout -> {
                item.isChecked = !item.isChecked
                mContent.setBaseSystemUiVisibility(if (item.isChecked)
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                else
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                return true
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Toast.makeText(this, "Searching for: $query...", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onTabSelected(tab: Tab, ft: FragmentTransaction) {}

    override fun onTabUnselected(tab: Tab, ft: FragmentTransaction) {}

    override fun onTabReselected(tab: Tab, ft: FragmentTransaction) {}
}
