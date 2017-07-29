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
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ViewFlipper
import kotlinx.android.synthetic.main.animation_2.*


class Animation2 : Activity(), AdapterView.OnItemSelectedListener {

    private val mStrings = arrayOf("Push up", "Push left", "Cross fade", "Hyperspace")

    lateinit private var mFlipper: ViewFlipper

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_2)

        mFlipper = this.flipper
        mFlipper.startFlipping()

        ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mStrings).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.run { adapter = it }
            spinner.run { onItemSelectedListener = this@Animation2 }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
        when (position) {

            0 -> {
                mFlipper.inAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.push_up_in)
                mFlipper.outAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.push_up_out)
            }
            1 -> {
                mFlipper.inAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.push_left_in)
                mFlipper.outAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.push_left_out)
            }
            2 -> {
                mFlipper.inAnimation = AnimationUtils.loadAnimation(this,
                        android.R.anim.fade_in)
                mFlipper.outAnimation = AnimationUtils.loadAnimation(this,
                        android.R.anim.fade_out)
            }
            else -> {
                mFlipper.inAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.hyperspace_in)
                mFlipper.outAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.hyperspace_out)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}
}
