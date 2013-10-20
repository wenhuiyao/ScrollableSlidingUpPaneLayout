/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wenhui.lib.scrollableslidinguppanelayout.viewDelegate;

import android.view.View;
import android.widget.AbsListView;

import com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPanelAttacher;

/**
 * FIXME
 */
public class AbsListViewDelegate
        extends ScrollableSlidingUpPanelAttacher.ViewDelegate {

    public static final Class SUPPORTED_VIEW_CLASS = AbsListView.class;

    @Override
    public boolean isReadyForPull(View view, final float x, final float y) {
        boolean ready = false;

        // First we check whether we're scrolled to the top
        AbsListView absListView = (AbsListView) view;
        if (absListView.getCount() == 0) {
            ready = true;
        } else if (absListView.getFirstVisiblePosition() == 0) {
            final View firstVisibleChild = absListView.getChildAt(0);
            ready = firstVisibleChild != null && firstVisibleChild.getTop() >= 0;
        }

        return ready;
    }

}
