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
import com.example.android.apis.R

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener
import android.widget.Chronometer
import kotlinx.android.synthetic.main.chronometer.*

class ChronometerDemo : Activity() {
    lateinit internal var mChronometer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.chronometer)

        mChronometer = chronometer

        // Watch for button clicks.
        start.setOnClickListener(mStartListener)

        stop.setOnClickListener(mStopListener)

        reset.setOnClickListener(mResetListener)

        set_format.setOnClickListener(mSetFormatListener)

        clear_format.setOnClickListener(mClearFormatListener)
    }

    internal var mStartListener: View.OnClickListener
            = OnClickListener { mChronometer.start() }

    internal var mStopListener: View.OnClickListener
            = OnClickListener { mChronometer.stop() }

    internal var mResetListener: View.OnClickListener
            = OnClickListener { mChronometer.base = SystemClock.elapsedRealtime() }

    internal var mSetFormatListener: View.OnClickListener
            = OnClickListener { mChronometer.format = "Formatted time (%s)" }

    internal var mClearFormatListener: View.OnClickListener
            = OnClickListener { mChronometer.format = null }
}
