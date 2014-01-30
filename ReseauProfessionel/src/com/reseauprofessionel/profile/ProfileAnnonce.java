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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.reseauprofessionel.authentification.AuthentificationActivity;
import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.gestAnnonces.ListeRechercheAnnonce;
import com.reseauprofessionel.gestAnnonces.RechercheAnnonce;
import com.reseauprofessionel.json.JSONParser;
import com.reseauprofessionel.profile.*;

/**
 * This is the activity for feature 1 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */


public class ProfileAnnonce extends DashboardActivity 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	JSONArray Profiles = null;
	// url to create new membere
	//private static String url_MAJ_Profile_Membre = "http://10.0.2.2/reseauprofessionnel/controller/MAJ_Profile_Membre.php";
	private static String url_retoune_profile = "http://10.0.2.2/reseauprofessionnel/controller/Retoune_Profile.php";

	// JSON Node names
	private static final String TAG_Profile = "profile";
	private static final String TAG_IDANNONCE = "idAnnonce";
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	private static final String TAG_NUMTEL = "numTel";
	private static final String TAG_ARRONDISSEMENT = "arrondissement";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_SUCCESS = "success";

	TextView TArrondissement ;
	TextView TTelephone ;
	TextView TEmail;
	TextView TNon;
	TextView TPrenom;
	TextView TProfession ;
	TextView TP2rofession;


	private String Nom 			= null ; 
	private String Prenom 		= null ; 
	private String Email 		= null ; 
	private String Telephone	= null ;
	private String Arrondissement	= null ;
	private String Profession	= null ;
	
	
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
		setContentView (R.layout.profil);

		TArrondissement	  = (TextView) findViewById(R.id.PArr) ;
		TTelephone	  	  = (TextView) findViewById(R.id.PTelephone) ;
		TProfession		  	  = (TextView) findViewById(R.id.PProf) ;
		TP2rofession		  	  = (TextView) findViewById(R.id.P2Prof) ;
		TProfession.setVisibility(0);
		TNon	  = (TextView) findViewById(R.id.PNom) ;
		TPrenom  	  = (TextView) findViewById(R.id.PPrenom) ;
		TEmail	  	  = (TextView) findViewById(R.id.PEmail) ;
		
		if(Profile.estProfessionnel.equals("Oui")){
			System.out.println("Est un professionel");
			TProfession.setVisibility(View.INVISIBLE);
			TP2rofession.setVisibility(View.INVISIBLE);
		}
	    else if (Profile.estProfessionnel.equals("Non")){
	    	System.out.println("n'Est pas un professionel");
	    	TProfession.setVisibility(View.VISIBLE);	
	    	TP2rofession.setVisibility(View.VISIBLE);
	    }

		test();
	}
	
	
	protected void test(){
		// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair(TAG_IDANNONCE, ListeRechercheAnnonce.idannonce));
					Log.i("Profilllleeee  idanonnce= ", ""+ListeRechercheAnnonce.idannonce);

					// getting JSON Object
					// Note that create membre url accepts POST method
					JSONObject json = jsonParser.makeHttpRequest(url_retoune_profile,"GET", params);

					// check log cat fro response
					Log.d("Create Response", json.toString());

					// check for success tag
					try {
						int success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							Profiles = json.getJSONArray(TAG_Profile);
							
							if(Profiles.length()>0){
							
								JSONObject profile = Profiles.getJSONObject(0);
							
								TNon.setText(profile.getString(TAG_NOM));
								TPrenom.setText(profile.getString(TAG_PRENOM));
								TEmail.setText(profile.getString(TAG_EMAIL));
								TTelephone.setText(profile.getString(TAG_NUMTEL));
								TArrondissement.setText(profile.getString(TAG_ARRONDISSEMENT));
								Log.i(" Teste     Nom = ", RechercheAnnonce.Profession);
								if(RechercheAnnonce.Profession != null){
									
									//TProfession.setVisibility(1);
									TProfession.setText(RechercheAnnonce.Profession);
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

	}

	/**
	 * @desc Background Async Task to Create new membre
	 * */
	class RecupererDonnePofile extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProfileAnnonce.this);
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
			params.add(new BasicNameValuePair(TAG_IDANNONCE, ListeRechercheAnnonce.idannonce));


			// getting JSON Object
			// Note that create membre url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_retoune_profile,"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					
					// successfully updated member
					//toast(json.getString(TAG_MESSAGE));
		
					//Intent i = new Intent(getApplicationContext(), HomeActivity.class);
					//startActivity(i);
					//finish();
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
	
	public void onClickRetourne(View v){
		Intent i = new Intent(getApplicationContext(),ListeRechercheAnnonce.class);
		// Closing all previous activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

} // end class

