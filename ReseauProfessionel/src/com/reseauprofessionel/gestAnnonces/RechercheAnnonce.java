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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.reseauprofessionel.authentification.AuthentificationActivity;
import com.reseauprofessionel.authentification.AuthentificationActivity.login;
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

public class RechercheAnnonce extends DashboardActivity { //DashboardListActivity

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray tabprofessions = null;
	JSONArray tabannonces = null;
	public static ArrayList<HashMap<String, String>> annoncesArrayList;
	ArrayList<HashMap<String, String>> professionsArrayList;//la liste finale des lieux
	private Spinner spinnerProf;
	private Spinner spinnerArrondissement;
	private Spinner spinnerEstProf;
	JSONParser jsonParser = new JSONParser();
	 
	
	//url des lieux favoris
	private static String url_authentification = "http://10.0.2.2/reseauprofessionnel/controller/get_professions.php";
	private static String url_recherche_annonce = "http://10.0.2.2/reseauprofessionnel/controller/rechercher_Annonce.php";
	// noeud nom dans json
	//Textes complets 	idLieuxFavoris 	nom 	longitude 	latitude 	Membres_idMembres
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ANNONCES = "annonces";
	private static final String TAG_IDANNONCE = "idAnnonce";
	private static final String TAG_TITREANNONCE = "titre_annonce";
	private static final String TAG_TEXTEANNONCE = "texte_annonce";
	
	private static final String TAG_PROFESSIONS = "professions";
	private static final String TAG_IDPROFESSION = "idProfession";
	private static final String TAG_ESTPROFESSIONNEL = "estProfessionnel";
	private static final String TAG_ARRONDIsSEMENT = "arrondissement";
	private static final String TAG_NOM = "nom";
	//private static final String TAG_IDUTILISATEUR = "idUtilisateur";
	//private static final String TAG_DESTINATION = "destination";
	
	private String id = null; 
		
	//EditText ETTitreAnnonce ;
	//EditText ETTextAnnonce ;
	Spinner SpinnerProf;
	Button RechercherButton;
	//RadioGroup groupRadioUser;
	
	public static String idProfession = "1" ; 
	public static String Profession = null;
	public static String arrondissement = "1er arrondissement";
	public static String estProfessionnel = "Oui";
	
	private String i = null ; 
	//private String idUtilisateur = "0";
	//private String destination = null;
	
	public static List<String> ListArrondissement = null;
	private static List<String> ListBoolestProfessionnel;
	
	
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_search);
    professionsArrayList = new ArrayList<HashMap<String,String>>();
    annoncesArrayList = new ArrayList<HashMap<String,String>>();
    spinnerProf = (Spinner) findViewById(R.id.spinnerProfR);
    spinnerArrondissement = (Spinner) findViewById(R.id.spinnerArrondissement);
    
    RechercherButton = (Button) findViewById(R.id.rechercherAnnonces);
    
    initialisation();
  
    RechercherButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Log.d("yowwwww", "yess");
			Intent i = new Intent(getApplicationContext(),ListeRechercheAnnonce.class);
			// Closing all previous activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			//RecupererLesChamps();
			//new RechercherAnnonce().execute(); 
		}
	}) ;
    
    if(Profile.estProfessionnel.equals("0")){
    	//groupRadioUser.setVisibility(View.INVISIBLE);
    	//destination = "1";
    }
    
    if(Profile.estProfessionnel.equals("Oui"))
    	estProfessionnel = "Non";
    else if (Profile.estProfessionnel.equals("Non"))
    		estProfessionnel = "Oui";	
    
    new listeProfessions().execute();
}

	public void initialisation(){
		
		ListArrondissement = new ArrayList<String>();
		ListArrondissement.add("1er arrondissement");
		ListArrondissement.add("2eme arrondissement");
		ListArrondissement.add("3eme arrondissement");
		ListArrondissement.add("4eme arrondissement");
		ListArrondissement.add("5eme arrondissement");
		ListArrondissement.add("6eme arrondissement");
		ListArrondissement.add("7eme arrondissement");
		ListArrondissement.add("8eme arrondissement");
		ListArrondissement.add("9eme arrondissement");
		ListArrondissement.add("10eme arrondissement");
		ListArrondissement.add("11eme arrondissement");
		ListArrondissement.add("12eme arrondissement");
		ListArrondissement.add("13eme arrondissement");
		ListArrondissement.add("14eme arrondissement");
		ListArrondissement.add("15eme arrondissement");
		ListArrondissement.add("16eme arrondissement");
		ListArrondissement.add("17eme arrondissement");
		ListArrondissement.add("18eme arrondissement");
		ListArrondissement.add("19eme arrondissement");
		ListArrondissement.add("20eme arrondissement");
	
		ArrayAdapter<String> dataAdapterArrondissement = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ListArrondissement);
		dataAdapterArrondissement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrondissement.setAdapter(dataAdapterArrondissement);
		spinnerArrondissement.setOnItemSelectedListener(new MonSpinner1ListenerArrondissement());
		
		ListBoolestProfessionnel = new ArrayList<String>();
		ListBoolestProfessionnel.add("Oui");
		ListBoolestProfessionnel.add("Non");
		
		ArrayAdapter<String> dataAdapterBoolEstProf = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ListBoolestProfessionnel);
		dataAdapterBoolEstProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
}

/*private void RecupererLesChamps() {
	
	TitreAnnonce 		= ETTitreAnnonce.getText().toString(); 
	TexteAnnonce 		= ETTextAnnonce.getText().toString() ;
	idUtilisateur = GestAnnoncesActivity.idUser;
	
	//idProfession = 	spinnerProf.getSelectedItem().
	
	//System.out.println("titre annonce :"+TitreAnnonce+",  Texte Annonce : "+TexteAnnonce+" , IdUser = "+idUtilisateur);
}*/

public class MonSpinner1ListenerProfession implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
			
			Log.i("Evenement spinner", "click Profession");
			HashMap<String, String> map = new HashMap<String, String>();
			map=professionsArrayList.get(arg2);
			idProfession = map.get(TAG_IDPROFESSION);
			Profession = map.get(TAG_NOM);
			Log.i("mmmmmmmmmmmmmmmmmmmm", "idProf = "+idProfession+"  Nom = "+Profession);
		//Toast.makeText(getApplicationContext(), "Selected : " + nomStrings[arg2],Toast.LENGTH_SHORT).show();
	}
	public void onNothingSelected(AdapterView<?> arg0) {
	}
  }

public class MonSpinner1ListenerArrondissement implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		Log.i("Evenement spinner", "click arrondoseement");
		arrondissement = ListArrondissement.get(arg2);
		}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	//Toast.makeText(getApplicationContext(), "Selected : " + nomStrings[arg2],Toast.LENGTH_SHORT).show();
	}


//Définition de la classe NouvelleAnnonce pour ajoute une Annonce

class RechercherAnnonce extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(RechercheAnnonce.this);
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
		params.add(new BasicNameValuePair(TAG_IDPROFESSION, idProfession));
		params.add(new BasicNameValuePair(TAG_ESTPROFESSIONNEL, estProfessionnel));
		params.add(new BasicNameValuePair(TAG_ARRONDIsSEMENT, arrondissement));
		Log.d("Create Response", "idprofession= "+idProfession+",  estProfessionnel = "+ estProfessionnel+" ,arrondissement="+arrondissement);
		// getting JSON Object
		// Note that create membre url accepts POST methods
		JSONObject json = jsonParser.makeHttpRequest(url_recherche_annonce,"GET", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());
		
		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// si les membres a des lieux favoris!!
				tabannonces = json.getJSONArray(TAG_ANNONCES);
				Log.i("table annonce taille = ", ""+tabannonces.length())	;
				for (int i = 0; i < tabannonces.length(); i++) {
					
					JSONObject annonce = tabannonces.getJSONObject(i);

					// Storing each json item in variable
					
					String id_annonce = annonce.getString(TAG_IDANNONCE);
					String titre_annonce = annonce.getString(TAG_TITREANNONCE);
					String texte_annonce = annonce.getString(TAG_TEXTEANNONCE);
					
					//trace(""+i);
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					/// adding each child node to HashMap key => value
					map.put(TAG_IDANNONCE, id_annonce);
					map.put(TAG_TITREANNONCE, titre_annonce);
					map.put(TAG_TEXTEANNONCE, texte_annonce);
					// adding HashList to ArrayList
					annoncesArrayList.add(map);				
				}
				return TAG_SUCCESS;
			}
			else {
				// Aucun lieux favoris trouvé
				// retourner a l activité HomeActivité
				
				Intent i = new Intent(getApplicationContext(),RechercheAnnonce.class);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} 
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
	

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String res) {
		// dismiss the dialog once done
		pDialog.dismiss();
		if(res == TAG_SUCCESS && res != null) {
			runOnUiThread(new Runnable() {
				public void run() {
					
					
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							new ListeRechercheAnnonce(), annoncesArrayList,
							R.layout.item_annonceprof, new String[] {TAG_TITREANNONCE,TAG_TEXTEANNONCE},//TAG_NBRANNONCES
							new int[] { R.id.LTitreAnnonce,R.id.LTexteAnnonce});//R.id.LNombreAnnonce
					// updating liste view
					new ListeRechercheAnnonce().setListAdapter(adapter);
					}
				});
		}else{
			Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
		}
	}
}

//Définition une classe interne qui represente la tache asynchrone à exéciter
public class listeProfessions extends AsyncTask<String, String, String> {	
	
	 // La méthode  onPreExecute appelée avant le traitement
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(RechercheAnnonce.this);
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
				tabprofessions = json.getJSONArray(TAG_PROFESSIONS);
				
				for (int i = 0; i < tabprofessions.length(); i++) {
					
					JSONObject profession = tabprofessions.getJSONObject(i);

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
										 RechercheAnnonce.this, 
										
										professionsArrayList,
										
										R.layout.item_profession, new String[] {TAG_NOM},
										new int[] { R.id.nom_profession});
								// updating liste view
								spinnerProf.setAdapter(adapter);
								spinnerProf.setOnItemSelectedListener(new MonSpinner1ListenerProfession());
				
								}
							});
					}else{
						Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
					}
}
}
}
 // end class
