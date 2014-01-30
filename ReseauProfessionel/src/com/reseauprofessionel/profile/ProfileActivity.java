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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
	private static final String TAG_IDM = "idUtilisateur";
	private static final String TAG_ARR = "arr";
	private static final String TAG_TELEPHONE = "telephone";
	
	private static final String TAG_SUCCESS = "success";
	// private static final String TAG_MESSAGE = "message";

	TextView pNom ;
	TextView pPrenom ;
	TextView pEmail ;
	Spinner pArrondissement ;
	EditText pTelephone ;
	List<String> Arrondissement;
	Button   editerButton ;
	Button   annulerButton ;

	private String Telephone	= null ;
	private String Arr 		= null ; 
  

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
		
		pNom 		= (TextView) findViewById(R.id.profileNom) ;
		pPrenom 	= (TextView) findViewById(R.id.profilePrenom) ;
		pEmail 		= (TextView) findViewById(R.id.profileEmail) ;
		pArrondissement	  = (Spinner) findViewById(R.id.profileArrondissement) ;
		pTelephone	  	  = (EditText) findViewById(R.id.profileTelephone) ;

		editerButton  = (Button) findViewById(R.id.Button_EditerProfile);
		annulerButton = (Button) findViewById(R.id.Button_AnnulerEditerProfile);
		
		Arrondissement = new ArrayList<String>();
		Arrondissement.add("1er arrondissement");
		Arrondissement.add("2eme arrondissement");
		Arrondissement.add("3eme arrondissement");
		Arrondissement.add("4eme arrondissement");
		Arrondissement.add("5eme arrondissement");
		Arrondissement.add("6eme arrondissement");
		Arrondissement.add("7eme arrondissement");
		Arrondissement.add("8eme arrondissement");
		Arrondissement.add("9eme arrondissement");
		Arrondissement.add("10eme arrondissement");
		Arrondissement.add("11eme arrondissement");
		Arrondissement.add("12eme arrondissement");
		Arrondissement.add("13eme arrondissement");
		Arrondissement.add("14eme arrondissement");
		Arrondissement.add("15eme arrondissement");
		Arrondissement.add("16eme arrondissement");
		Arrondissement.add("17eme arrondissement");
		Arrondissement.add("18eme arrondissement");
		Arrondissement.add("19eme arrondissement");
		Arrondissement.add("20eme arrondissement");
		
		ArrayAdapter<String> dataAdapterArrondissement = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Arrondissement);
		dataAdapterArrondissement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pArrondissement.setAdapter(dataAdapterArrondissement);
		
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
		//pArrondissement.set(Profile.adresse);
	
		pNom.setText(Profile.nom);
		pPrenom.setText(Profile.prenom);
		pEmail.setText(Profile.email);
		pTelephone.setText(Profile.numTel);
		pArrondissement.setSelection(getPosition(Arrondissement,Profile.adresse));
	}
	
	public int getPosition(List<String> list,String arrondisselent){
		
		for(int i=0;i<list.size();i++){
			if(arrondisselent.equals(list.get(i)))
				return i;
		}
		return 0;
	}

	private void RecupererLesChamps() {
		Telephone = pTelephone.getText().toString();
		Arr 	  = pArrondissement.getSelectedItem().toString();
		System.out.println("aaaaaaaaaaaaaaaaaaaarrrrrrr"+Arr); 
	}

	boolean  SaisieIsValidated() {
		if(Telephone.equals("")) 
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
			params.add(new BasicNameValuePair(TAG_ARR, Arr));
			params.add(new BasicNameValuePair(TAG_TELEPHONE, Telephone));

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
					Profile.maj_profile(Telephone, Arr);
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

