/*
 * Copyright (C) 2009 The Android Open Source Project
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
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class Animation3 : Activity(), AdapterView.OnItemSelectedListener {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_3)

        val s = findViewById<View>(R.id.spinner) as Spinner
        ArrayAdapter(this,
                android.R.layout.simple_spinner_item, INTERPOLATORS).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            s.adapter = it
            s.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
        val target = findViewById<View>(R.id.target)
        val targetParent = target.parent as View

        val a = TranslateAnimation(0.0f,
                (targetParent.width - target.width - targetParent.paddingLeft -
                        targetParent.paddingRight).toFloat(), 0.0f, 0.0f).apply {
            duration = 1000
            startOffset = 300
            repeatMode = Animation.RESTART
            repeatCount = Animation.INFINITE
        }

        when (position) {
            0 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.accelerate_interpolator)
            1 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.decelerate_interpolator)
            2 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.accelerate_decelerate_interpolator)
            3 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.anticipate_interpolator)
            4 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.overshoot_interpolator)
            5 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.anticipate_overshoot_interpolator)
            6 -> a.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.anim.bounce_interpolator)
        }

        target.startAnimation(a)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    companion object {
        private val INTERPOLATORS = arrayOf("Accelerate", "Decelerate", "Accelerate/Decelerate", "Anticipate", "Overshoot", "Anticipate/Overshoot", "Bounce")
    }
}