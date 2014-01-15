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

package com.reseauprofessionel.lieuxfavoris;

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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class LieuxFavorisActivity extends DashboardListActivity {

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray tablieux = null;
	ArrayList<HashMap<String, String>> lieuxArrayList;//la liste finale des lieux
	
	//url des lieux favoris
	private static String url_authentification = "http://10.0.2.2/friendstracker/controller/lieuxfavoris.php";
	// noeud nom dans json
	//Textes complets 	idLieuxFavoris 	nom 	longitude 	latitude 	Membres_idMembres
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LIEUXFAVORIS = "lieuxfavoris";
	private static final String TAG_IDLIEUXFAVORIS = "idLieuxFavoris";
	private static final String TAG_NOM = "nom";
	private static final String TAG_LONGITUDE = "longitude";
	private static final String TAG_LATITUDE = "latitude";
	
	
	private String id = null; 
	
	
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_f4);
    setTitleFromActivityLabel (R.id.title_text);
    lieuxArrayList = new ArrayList<HashMap<String,String>>();
    
    new listeLieuxFavoris().execute();
}


public class listeLieuxFavoris extends AsyncTask<String, String, String> {	
	
	 // Before starting background thread Show Progress Dialog	 
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(LieuxFavorisActivity.this);
		pDialog.setMessage("Loading Data Please waite ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	
	@Override
	protected String doInBackground(String... args) {
		
		String id = "1"; // pour le test
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Membres_idMembres", id));
		
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
				tablieux = json.getJSONArray(TAG_LIEUXFAVORIS);
				
				for (int i = 0; i < tablieux.length(); i++) {
					
					JSONObject lieu = tablieux.getJSONObject(i);

					// Storing each json item in variable
					
					String id_lieu = lieu.getString(TAG_IDLIEUXFAVORIS);
					String nom = lieu.getString(TAG_NOM);
					String lng = lieu.getString(TAG_LONGITUDE);
					String lat = lieu.getString(TAG_LATITUDE);
					
					//trace(""+i);
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_IDLIEUXFAVORIS, id_lieu);
					map.put(TAG_NOM, nom);
					map.put(TAG_LONGITUDE, lng);
					map.put(TAG_LATITUDE, lat);
					// adding HashList to ArrayList
					lieuxArrayList.add(map);
					
					
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
										LieuxFavorisActivity.this, lieuxArrayList,
										R.layout.item_lieuxfavoris, new String[] { TAG_IDLIEUXFAVORIS,TAG_NOM,TAG_LONGITUDE,
												TAG_LATITUDE},
										new int[] { R.id.idlieux, R.id.nom,R.id.longitude,R.id.latitude });
								// updating liste view
								setListAdapter(adapter);
								}
							});
					}else{
						Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
					}
}
}
}
 // end class
