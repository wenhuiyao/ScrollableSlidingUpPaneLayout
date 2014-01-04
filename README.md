ScrollableSlidingUpPaneLayout
=========

A SlidingPaneLayout variant. Instead of sliding left and right, it slides up and down. And work with ListView, GridView, and ScrollView**.

#### Demo app:

* https://dl.dropboxusercontent.com/u/24027452/ScrollableSlidingUpPaneLayoutDemo.apk

#### Main Features

* Allow sliding pane clamping at certain position
* Allow scrollable view taking over touch event when sliding up layout reach top edge. Scrollable views are ListView, GridView, and ScrollView.


#### Demo

![Demo Image 1](https://dl.dropboxusercontent.com/u/24027452/Screenshot_2013-10-20-20-55-51.png)  ![Demo Image 2](https://dl.dropboxusercontent.com/u/24027452/Screenshot_2013-10-20-20-56-01.png)

#### Usage

	<com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPanelLayout
		xmlns:android="http://schemas.android.com/apk/res/android"
    	xmlns:tools="http://schemas.android.com/tools"
    	xmlns:scrollablePane="http://schemas.android.com/apk/res-auto"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"
    	scrollablePane:draw_view_under_panel="true"
    	scrollablePane:expand_initially="false"
    	scrollablePane:is_sliding_enable="true"
    	scrollablePane:middle_clamp_bound="220dp"
    	scrollablePane:panel_height="@dimen/actionBarSize"
    	tools:context=".MainActivity" >

    	<FrameLayout
        	android:layout_width="match_parent"
        	android:layout_height="match_parent"
        	android:layout_gravity="center">

        	<ProgressBar android:layout_width="wrap_content"
            	android:layout_height="wrap_content"
            	android:layout_gravity="center"
            />
        
        	<ImageView
            	android:id="@+id/fullSizeImage"
            	android:layout_width="match_parent"
            	android:layout_height="match_parent"
            	android:layout_gravity="center"
            	android:adjustViewBounds="true"
            	android:scaleType="fitCenter" />
    	</FrameLayout>

    	<LinearLayout
        	android:layout_width="match_parent"
        	android:layout_height="match_parent"
        	android:orientation="vertical" >

  			...
    	</LinearLayout>

	</com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPanelLayout>
	

** For ScrollView, please use com.wenhui.lib.scrollableslidinguppanelayout.SlideableScrollView instead.

#### Credit

SlidingUpPaneLayout by [Umano](https://github.com/umano/AndroidSlidingUpPanel).

License
--------

    Copyright 2013 Wenhui Yao.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
