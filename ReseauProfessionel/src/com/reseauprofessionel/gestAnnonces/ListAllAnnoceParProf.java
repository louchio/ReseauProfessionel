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
import java.util.HashMap;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.reseauprofessionel.authentification.AuthentificationActivity;
import com.reseauprofessionel.board.DashboardListActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;

/**
 * @author Anas KHABALI
 * This is the activity for the favorites places in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class ListAllAnnoceParProf extends DashboardListActivity {

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray tabannonces = null;
	ArrayList<HashMap<String, String>> annoncesArrayList;//la liste finale des lieux
	
	//url des lieux favoris
	private static String url_get_annonces = "http://10.0.2.2/reseauprofessionnel/controller/get_annoncesProf.php";
	// noeud nom dans json
	//Textes complets 	idLieuxFavoris 	nom 	longitude 	latitude 	Membres_idMembres
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ANNONCES = "annonces";
	private static final String TAG_IDANNONCE = "idAnnonce";
	private static final String TAG_TITREANNONCE = "titre_annonce";
	private static final String TAG_TEXTEANNONCE = "texte_annonce";
	//private static final String TAG_NBRANNONCES = "nbrAnnonce";
	public static String idannonce = null;
	public static String titreannonce = null;
	public static String texteannonce = null;
	 
	
	private String id = null; 
	
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_listeannonceprof);
    TextView tv = (TextView) findViewById (R.id.title_text);
    if (tv != null) tv.setText ("Annonces "+ListAllProfessions.nomSelectProfession);
    annoncesArrayList = new ArrayList<HashMap<String,String>>();
    
    new listeProfessions().execute();
}

//Définition une classe interne qui represente la tache asynchrone à exéciter
public class listeProfessions extends AsyncTask<String, String, String> {	
	
	 // La méthode  onPreExecute appelée avant le traitement
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(ListAllAnnoceParProf.this);
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
		params.add(new BasicNameValuePair("idUser", AuthentificationActivity.getIdUser()));
		params.add(new BasicNameValuePair("idProfession", ListAllProfessions.idSelectProfession));
		
		Log.d("parametre ###########################", " idUser= "+AuthentificationActivity.getIdUser()+"  idprofession = "+ListAllProfessions.idSelectProfession+" isMenmbre = "+id );
		
		// getting JSON Object
		// Note that authentifier url accepts POST method
		JSONObject json = jParser.makeHttpRequest( url_get_annonces,"GET", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// si les membres a des lieux favoris!!
				tabannonces = json.getJSONArray(TAG_ANNONCES);
				
				for (int i = 0; i < tabannonces.length(); i++) {
					
					JSONObject annonce = tabannonces.getJSONObject(i);

					// Storing each json item in variable
					
					String id_annonce = annonce.getString(TAG_IDANNONCE);
					String titre_annonce = annonce.getString(TAG_TITREANNONCE);
					String texte_annonce = annonce.getString(TAG_TEXTEANNONCE);
					
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_IDANNONCE, id_annonce);
					map.put(TAG_TITREANNONCE, titre_annonce);
					map.put(TAG_TEXTEANNONCE, texte_annonce);
					// adding HashList to ArrayList
					annoncesArrayList.add(map);
					
					
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
								
								
								/**
								 * Updating parsed JSON data into ListView
								 * */
								ListAdapter adapter = new SimpleAdapter(
										ListAllAnnoceParProf.this, annoncesArrayList,
										R.layout.item_annonceprof, new String[] {TAG_TITREANNONCE,TAG_TEXTEANNONCE},//TAG_NBRANNONCES
										new int[] { R.id.LTitreAnnonce,R.id.LTexteAnnonce});//R.id.LNombreAnnonce
								// updating liste view
								setListAdapter(adapter);
								}
							});
					}else{
						Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
					}
}
}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		 HashMap<String, String> map = new HashMap<String, String>();
		 map=annoncesArrayList.get(position);
		 
		 // C'est mieu d'encapsuler ces données dans une classe "Annonce"
		  idannonce = map.get(TAG_IDANNONCE);
		  titreannonce = map.get(TAG_TITREANNONCE);
		  texteannonce = map.get(TAG_TEXTEANNONCE);
		  
		  
		  Intent i = new Intent(getApplicationContext(),SelectAnnonce.class);
		  // Closing all previous activities
		  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		  startActivity(i);
	}
	
	public void onClickRetourne(View v){
		Intent i = new Intent(getApplicationContext(),ListAllProfessions.class);
		// Closing all previous activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
 // end class
