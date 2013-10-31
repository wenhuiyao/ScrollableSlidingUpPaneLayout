/*
 * Copyright 2013 
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

package com.wenhui.lib.scrollableslidinguppanelayout;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.wenhui.lib.scrollableslidinguppanelayout.viewDelegate.AbsListViewDelegate;
import com.wenhui.lib.scrollableslidinguppanelayout.viewDelegate.ScrollYDelegate;

class InstanceCreationUtils {

    static ScrollableSlidingUpPaneLayoutHelper.ViewDelegate getBuiltInViewDelegate(final View view) {
    	
    	if( view instanceof SlideableScrollView ){
    		return (SlideableScrollView)view;
    	}
    	
    	if( view instanceof AbsListView ){
    		return new AbsListViewDelegate();
    	}
    	
        // Default is the ScrollYDelegate
        return new ScrollYDelegate();
    }

}
