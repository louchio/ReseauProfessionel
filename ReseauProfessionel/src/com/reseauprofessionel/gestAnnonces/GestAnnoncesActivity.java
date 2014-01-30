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

package com.reseauprofessionel.gestAnnonces;

import java.util.ArrayList;




import android.view.View;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.reseauprofessionel.authentification.AuthentificationActivity;
import com.reseauprofessionel.authentification.Inscription;
import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.DashboardListActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;
import com.reseauprofessionel.profile.Profile;

/**
 * @author Anas KHABALI
 * This is the activity for the favorites places in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class GestAnnoncesActivity extends DashboardActivity { //DashboardListActivity

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray tablieux = null;
	ArrayList<HashMap<String, String>> professionsArrayList;//la liste finale des lieux
	private Spinner spinnerProf;
	JSONParser jsonParser = new JSONParser();
	 
	
	//url des lieux favoris
	private static String url_authentification = "http://10.0.2.2/reseauprofessionnel/controller/get_professions.php";
	private static String url_nouvelle_annonce = "http://10.0.2.2/reseauprofessionnel/controller/Nouvelle_Annonce.php";
	// noeud nom dans json
	//Textes complets 	idLieuxFavoris 	nom 	longitude 	latitude 	Membres_idMembres
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PROFESSIONS = "professions";
	private static final String TAG_IDPROFESSION = "idProfession";
	private static final String TAG_NOM = "nom";
	private static final String TAG_TITREANNONCE = "titreAnnonce";
	private static final String TAG_TexteAnnonce = "texteAnnonce";
	private static final String TAG_IDUTILISATEUR = "idUtilisateur";
	private static final String TAG_DESTINATION = "destination";
	
	private String id = null; 
		
	EditText ETTitreAnnonce ;
	EditText ETTextAnnonce ;
	TextView TMetier;
	Button PublierButton;
	RadioGroup groupRadioUser;
	
	private String TitreAnnonce = null ; 
	private String TexteAnnonce = null ; 
	private String idUtilisateur = "0";
	private String idProfession = "1";
	private String destination = "0";
	
	
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_ajtannonce);
    //setTitleFromActivityLabel (R.id.title_text);
    professionsArrayList = new ArrayList<HashMap<String,String>>();
    
    spinnerProf = (Spinner) findViewById(R.id.spinnerMetier);
    TMetier = (TextView) findViewById(R.id.CMetier);
    PublierButton = (Button) findViewById(R.id.PublierButton);
    groupRadioUser = (RadioGroup) findViewById(R.id.groupTypeUser);
    ETTitreAnnonce		= (EditText) findViewById(R.id.titre_Annonce) ;
	ETTextAnnonce 	= (EditText) findViewById(R.id.texteAnnonce) ;
   // initialisation();
    
    PublierButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Log.d("yowwwww", "yess");
			RecupererLesChamps();
			new NouvelleAnnonce().execute(); 
		}
	}) ;
    
    groupRadioUser.setVisibility(View.INVISIBLE);
    
    if(Profile.estProfessionnel.equals("Oui")){
		System.out.println("Est un professionel");
		spinnerProf.setVisibility(View.INVISIBLE);
		TMetier.setVisibility(View.INVISIBLE);
	}
    else if (Profile.estProfessionnel.equals("Non")){
    	System.out.println("n'Est pas un professionel");
    	spinnerProf.setVisibility(View.VISIBLE);	
    	TMetier.setVisibility(View.VISIBLE);
    }

    new listeProfessions().execute();
}

/*public void initialisation(){
	ETTitreAnnonce		= (EditText) findViewById(R.id.titre_Annonce) ;
	ETTextAnnonce 	= (EditText) findViewById(R.id.texteAnnonce) ;
}*/

private void RecupererLesChamps() {
	TitreAnnonce 		= ETTitreAnnonce.getText().toString(); 
	TexteAnnonce 		= ETTextAnnonce.getText().toString() ;
	idUtilisateur = GestAnnoncesActivity.idUser;
	
	//idProfession = 	spinnerProf.getSelectedItem().
	
	//System.out.println("titre annonce :"+TitreAnnonce+",  Texte Annonce : "+TexteAnnonce+" , IdUser = "+idUtilisateur);
}

public class MonSpinner1Listener implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		HashMap<String, String> map = new HashMap<String, String>();
		map=professionsArrayList.get(arg2);
		idProfession = map.get(TAG_IDPROFESSION);
		
		Log.i("mmmmmmmmmmmmmmmmmmmm", "idProf = "+idProfession);
		//Toast.makeText(getApplicationContext(), "Selected : " + nomStrings[arg2],Toast.LENGTH_SHORT).show();
	}
	public void onNothingSelected(AdapterView<?> arg0) {
	}
  }

//D�finition de la classe NouvelleAnnonce pour ajoute une Annonce

class NouvelleAnnonce extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(GestAnnoncesActivity.this);
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
		/*params.add(new BasicNameValuePair(TAG_TITREANNONCE, TitreAnnonce));
		params.add(new BasicNameValuePair(TAG_TexteAnnonce, TexteAnnonce));
		params.add(new BasicNameValuePair(TAG_IDUTILISATEUR, idUtilisateur));*/
		
		if(Profile.estProfessionnel.equals("Oui")){
			idProfession = Profile.idProfession;
			System.out.println("Id TESSSSSSSSt = "+idProfession);
		}
		params.add(new BasicNameValuePair(TAG_TITREANNONCE, TitreAnnonce));
		params.add(new BasicNameValuePair(TAG_TexteAnnonce, TexteAnnonce));
		params.add(new BasicNameValuePair(TAG_IDUTILISATEUR, GestAnnoncesActivity.idUser));
		params.add(new BasicNameValuePair(TAG_IDPROFESSION, idProfession));
		params.add(new BasicNameValuePair(TAG_DESTINATION, destination));
		
		

		// getting JSON Object
		// Note that create membre url accepts POST method
		JSONObject json = jsonParser.makeHttpRequest(url_nouvelle_annonce,"POST", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully created member
				Intent i = new Intent(getApplicationContext(), GestAnnoncesActivity.class);
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

//D�finition une classe interne qui represente la tache asynchrone � ex�citer
public class listeProfessions extends AsyncTask<String, String, String> {	
	
	 // La m�thode  onPreExecute appel�e avant le traitement
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(GestAnnoncesActivity.this);
		pDialog.setMessage("Loading Data Please waite ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	
	//Une AsyncTask doit imp�rativement impl�menter la m�thode doInBackground. C�est elle qui 
	//r�alisera le traitement de mani�re asynchrone dans un Thread s�par�.
	@Override
	protected String doInBackground(String... args) {
		
		String id = "1"; // pour le test
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Membres_idMembres", id));
		
		Log.d("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY= 11111  ", "1");
		// getting JSON Object
		// Note that authentifier url accepts POST method
		JSONObject json = jParser.makeHttpRequest(url_authentification,"GET", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// si les membres a des lieux favoris!!
				tablieux = json.getJSONArray(TAG_PROFESSIONS);
				
				for (int i = 0; i < tablieux.length(); i++) {
					
					JSONObject profession = tablieux.getJSONObject(i);

					// Storing each json item in variable
					
					String id_profession = profession.getString(TAG_IDPROFESSION);
					String nom = profession.getString(TAG_NOM);
					
					//trace(""+i);
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_IDPROFESSION, id_profession);
					map.put(TAG_NOM, nom);
					// adding HashList to ArrayList
					professionsArrayList.add(map);
					
					
				}
				return TAG_SUCCESS;
				
			} else {
				// Aucun lieux favoris trouv�
				// retourner a l activit� HomeActivit�
				
				Intent i = new Intent(getApplicationContext(),HomeActivity.class);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} 
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Apres le trailement
	@Override
	protected void onPostExecute(String res){
		// dismiss the dialog after getting all products
					pDialog.dismiss();
						// updating UI from Background Thread
					if(res == TAG_SUCCESS && res != null) {
						runOnUiThread(new Runnable() {
							public void run() {
								
								
								
								SpinnerAdapter adapter = new SimpleAdapter(
										GestAnnoncesActivity.this, 
										
										professionsArrayList,
										
										R.layout.item_profession, new String[] {TAG_NOM},
										new int[] { R.id.nom_profession});
								// updating liste view
								spinnerProf.setAdapter(adapter);
								spinnerProf.setOnItemSelectedListener(new MonSpinner1Listener());
				
								}
							});
					}else{
						Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
					}
}
}
}
 // end class
