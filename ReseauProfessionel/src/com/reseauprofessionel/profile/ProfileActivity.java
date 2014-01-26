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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reseauprofessionel.authentification.AuthentificationActivity;
import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;

/**
 * This is the activity for feature 1 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */


public class ProfileActivity extends DashboardActivity 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	// url to create new membere
	private static String url_MAJ_Profile_Membre = "http://10.0.2.2/reseauprofessionnel/controller/MAJ_Profile_Membre.php";
	//private static String url_membre_existe = "http://10.0.2.2/reseauprofessionnel/controller/Membre_existe.php";

	// JSON Node names
	private static final String TAG_IDM = "idMembres";
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	// private static final String TAG_EMAIL = "email";
	//private static final String TAG_PASSWORD = "password";
	private static final String TAG_SUCCESS = "success";
	// private static final String TAG_MESSAGE = "message";

	EditText pNom ;
	EditText pPrenom ;
	TextView pEmail ;

	Button   editerButton ;
	Button   annulerButton ;

	private String Nom 			= null ; 
	private String Prenom 		= null ; 
	private String Email 		= null ; 
	//private String Password 	= null ; 
	//private String PasswordC 	= null ; 

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

		pNom 		  = (EditText) findViewById(R.id.profileNom) ;
		pPrenom 	  = (EditText) findViewById(R.id.profilePrenom) ;
		pEmail 		  = (TextView) findViewById(R.id.profileEmail) ;

		editerButton  = (Button) findViewById(R.id.Button_EditerProfile);
		annulerButton = (Button) findViewById(R.id.Button_AnnulerEditerProfile);

		ReInitialiser();

		editerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RecupererLesChamps();
				if (SaisieIsValidated()){
					new MAJMembre().execute(); 
				}
			}
		}) ;

		annulerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ReInitialiser();
			}
		});

	}
	private void ReInitialiser() {
		pNom.setText(Profile.nom);
		pPrenom.setText(Profile.prenom);
		pEmail.setText(Profile.email);
	}

	private void RecupererLesChamps() {
		Nom 		= pNom.getText().toString(); 
		Prenom 		= pPrenom.getText().toString() ;

		// Email 		= pEmail.getText().toString();
		//Password 	= pPassword.getText().toString();
		//PasswordC 	= pPasswordC.getText().toString();
	}

	boolean  SaisieIsValidated() {
		if(Nom.equals("") || Prenom.equals("")) 
			//	|| Email.equals("")	|| Password.equals("") || PasswordC.equals("") ) 
		{
			toast("Veuillez remplire tout les champs !");
			return false ;
		} 
		return true ;
	}

	/**
	 * @desc Background Async Task to Create new membre
	 * */
	class MAJMembre extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProfileActivity.this);
			pDialog.setMessage("Chargement ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creation du membre
		 * */
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_IDM, AuthentificationActivity.idUser));
			params.add(new BasicNameValuePair(TAG_NOM, Nom));
			params.add(new BasicNameValuePair(TAG_PRENOM, Prenom));

			//params.add(new BasicNameValuePair(TAG_EMAIL, Email));
			//	params.add(new BasicNameValuePair(TAG_PASSWORD, Password));

			// getting JSON Object
			// Note that create membre url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_MAJ_Profile_Membre,"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// successfully updated member
					//toast(json.getString(TAG_MESSAGE));
					Profile.maj_profile (Nom, Prenom);
					Intent i = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}

} // end class

