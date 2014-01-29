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

public class SelectAnnonce extends DashboardActivity { //DashboardListActivity

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray tablieux = null;
	ArrayList<HashMap<String, String>> professionsArrayList;//la liste finale des lieux
	private Spinner spinnerProf;
	JSONParser jsonParser = new JSONParser();
	 
	
	//url des lieux favoris
	private static String url_authentification = "http://10.0.2.2/reseauprofessionnel/controller/get_professions.php";
	private static String url_modifier_annonce = "http://10.0.2.2/reseauprofessionnel/controller/MAJ_annonce.php";
	private static String url_supprimer_annonce = "http://10.0.2.2/reseauprofessionnel/controller/Delete_annonce.php";
	// noeud nom dans json
	//Textes complets 	idLieuxFavoris 	nom 	longitude 	latitude 	Membres_idMembres
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PROFESSIONS = "professions";
	private static final String TAG_IDPROFESSION = "idProfession";
	private static final String TAG_NOM = "nom";
	private static final String TAG_TITREANNONCE = "titreAnnonce";
	private static final String TAG_TexteAnnonce = "texteAnnonce";
	private static final String TAG_IDANNONCE = "idannonce";
	private static final String TAG_DESTINATION = "destination";
	
	private String id = null; 
		
	EditText ETTitreAnnonce ;
	EditText ETTextAnnonce ;
	Spinner SpinnerProf;
	Button ModofierButton;
	RadioGroup groupRadioUser;
	
	private String TitreAnnonce = null ; 
	private String TexteAnnonce = null ; 
	private String idUtilisateur = "0";
	private String idProfession = null;
	private String destination = null;
	
	public static String idannonce = null;
	public static String idannonceSuprim = null;
	
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_selectannonce);
    //setTitleFromActivityLabel (R.id.title_text);
    professionsArrayList = new ArrayList<HashMap<String,String>>();
    
    spinnerProf = (Spinner) findViewById(R.id.spinnerMetierS);
    ModofierButton = (Button) findViewById(R.id.ModifierButtonS);
    groupRadioUser = (RadioGroup) findViewById(R.id.groupTypeUserS);
    
    initialisation();
    
    ModofierButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Log.d("yowwwww", "yess");
			RecupererLesChamps();
			new ModificationAnnonce().execute(); 
		}
	}) ;
    
    if(Profile.estProfessionnel.equals("0")){
    	groupRadioUser.setVisibility(View.INVISIBLE);
    	destination = "1";
    }
    new listeProfessions().execute();
}

public void initialisation(){
	ETTitreAnnonce		= (EditText) findViewById(R.id.titre_AnnonceS) ;
	ETTitreAnnonce.setText (ListAllAnnoceParProf.titreannonce);
	ETTextAnnonce 	= (EditText) findViewById(R.id.texteAnnonceS) ;
	ETTextAnnonce.setText(ListAllAnnoceParProf.texteannonce);
}

private void RecupererLesChamps() {
	TitreAnnonce 		= ETTitreAnnonce.getText().toString(); 
	TexteAnnonce 		= ETTextAnnonce.getText().toString() ;
	idannonce = ListAllAnnoceParProf.idannonce;
	
	Log.i("saaaaaaaaaww", "titre annonce :"+TitreAnnonce+",  Texte Annonce : "+TexteAnnonce+" , Idannonce = "+idannonce);
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

//Définition de la classe NouvelleAnnonce pour ajoute une Annonce

class ModificationAnnonce extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(SelectAnnonce.this);
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
		
		params.add(new BasicNameValuePair(TAG_TITREANNONCE, TitreAnnonce));
		params.add(new BasicNameValuePair(TAG_TexteAnnonce, TexteAnnonce));
		params.add(new BasicNameValuePair(TAG_IDANNONCE,idannonce));
		params.add(new BasicNameValuePair(TAG_IDPROFESSION, idProfession));
		Log.i("saaaaaaaaaww", "titre annonce :"+TitreAnnonce+",  Texte Annonce : "+TexteAnnonce+" , Idannonce = "+idannonce+" idprofession = "+idProfession);
		// ##############" destiniation ###################################
		//params.add(new BasicNameValuePair(TAG_DESTINATION, destination));
		
		

		// getting JSON Object
		// Note that create membre url accepts POST method
		JSONObject json = jsonParser.makeHttpRequest(url_modifier_annonce,"POST", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully created member
				Intent i = new Intent(getApplicationContext(), ListAllAnnoceParProf.class);
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

class SupprimerAnnonce extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(SelectAnnonce.this);
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
		params.add(new BasicNameValuePair(TAG_IDANNONCE,idannonceSuprim));
		Log.i("SUPRESSIONNNN","  Idannonce Supprimer = "+idannonce);
		// ##############" destiniation ###################################
		//params.add(new BasicNameValuePair(TAG_DESTINATION, destination));
		
	
		// getting JSON Object
		// Note that create membre url accepts POST method
		JSONObject json = jsonParser.makeHttpRequest(url_supprimer_annonce,"POST", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully created member
				Intent i = new Intent(getApplicationContext(), ListAllAnnoceParProf.class);
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

//Définition une classe interne qui represente la tache asynchrone à exéciter
public class listeProfessions extends AsyncTask<String, String, String> {	
	
	 // La méthode  onPreExecute appelée avant le traitement
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(SelectAnnonce.this);
		pDialog.setMessage("Loading Data Please waite ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	
	//Une AsyncTask doit impérativement implémenter la méthode doInBackground. C’est elle qui 
	//réalisera le traitement de manière asynchrone dans un Thread séparé.
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
				// Aucun lieux favoris trouvé
				// retourner a l activité HomeActivité
				
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
										SelectAnnonce.this, 
										
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

	public void onClickDelete (View v)
	{
	   // super.onClickDelete(v);	
	    idannonceSuprim =idannonce; 
	    new SupprimerAnnonce().execute();
	}
}
 // end class
