/*
 * Copyright (C) 2011 Wglxy.com
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

package com.reseauprofessionel.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.R;

/**
 * This is the activity for feature 1 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */


public class ProfileActivity extends DashboardActivity 
{

	EditText PNom ;
	EditText PPrenom ;
	EditText PEmail ;

	Button   editerButton ;

	/**
	 * onCreate
	 *
	 * Called when the activity is first created. 
	 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
	 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 *
	 * @param savedInstanceState Bundle
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_profile);

		PNom 		= (EditText) findViewById(R.id.profileNom) ;
		PPrenom 	= (EditText) findViewById(R.id.profilePrenom) ;
		PEmail 		= (EditText) findViewById(R.id.profileEmail) ;

		editerButton = (Button) findViewById(R.id.Button_EditerProfile);

		PNom.setText(Profile.nom);
		PPrenom.setText(Profile.prenom);
		PEmail.setText(Profile.email);

		//setTitleFromActivityLabel (R.id.title_text);
	}

} // end class

