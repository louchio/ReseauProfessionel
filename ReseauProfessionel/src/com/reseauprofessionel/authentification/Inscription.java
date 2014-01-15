package com.reseauprofessionel.authentification;

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

import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;

public class Inscription extends DashboardActivity {
	
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	
	// url to create new membere
	private static String url_nouveau_membre = "http://10.0.2.2/reseauprofessionnel/controller/Nouveau_Membre.php";
	private static String url_membre_existe = "http://10.0.2.2/reseauprofessionnel/controller/Membre_existe.php";

	// JSON Node names
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	EditText ETNom ;
	EditText ETPrenom ;
	EditText ETEmail ;
	EditText ETPassword ;
	EditText ETPasswordC ;
	Button   SinscrireButton ;
	Button   AnnulerButton ;
	
	private String Nom 			= null ; 
	private String Prenom 		= null ; 
	private String Email 		= null ; 
	private String Password 	= null ; 
	private String PasswordC 	= null ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		
		ETNom 		= (EditText) findViewById(R.id.ETNom) ;
		ETPrenom 	= (EditText) findViewById(R.id.ETPrenom) ;
		ETEmail 	= (EditText) findViewById(R.id.ETEmail) ;
		ETPassword 	= (EditText) findViewById(R.id.ETPassword) ;
		ETPasswordC = (EditText) findViewById(R.id.ETPasswordC) ;
		
		SinscrireButton = (Button) findViewById(R.id.SinscrireButton);
		AnnulerButton   = (Button) findViewById(R.id.AnnulerButton);
		
		SinscrireButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RecupererLesChamps();
				if (SaisieIsValidated()){
					new NouveauMembre().execute(); 
				}
			}
		}) ;
		
		AnnulerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Initialiser();
			}
		});
		
	}
	private void Initialiser() {
		ETNom.setText(""); ETPrenom.setText("");ETEmail.setText("");
		ETPassword.setText("");ETPasswordC.setText("");
	}

	private void RecupererLesChamps() {
		Nom 		= ETNom.getText().toString(); 
		Prenom 		= ETPrenom.getText().toString() ;
		Email 		= ETEmail.getText().toString();
		Password 	= ETPassword.getText().toString();
		PasswordC 	= ETPasswordC.getText().toString();
	}
	
	boolean  SaisieIsValidated() {
		if(Nom.equals("") || Prenom.equals("")|| Email.equals("") || Password.equals("") || PasswordC.equals("") ) {
			toast("Veuillez remplire tout les champs !");
			return false ;
		} else{
			if(!Password.equals(PasswordC)) {
				toast("Les mots de passes ne sont pas identiques !");
				return false ;
			}else {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair(TAG_EMAIL, Email));
		
					// getting JSON Object
					// Note that create membre url accepts POST method
					JSONObject json = jsonParser.makeHttpRequest(url_membre_existe,"POST", params);
					
					// check log cat fro response
					Log.d("Create Response", json.toString());
		
					try {
						// check for success tag
						int success;
						success = json.getInt(TAG_SUCCESS);
						if(success == 0) {
							toast(json.getString(TAG_MESSAGE));
							return false ;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
				
		}
		return true ;
	}
	
	/**
	 * @desc Background Async Task to Create new membre
	 * */
	class NouveauMembre extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Inscription.this);
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
			params.add(new BasicNameValuePair(TAG_NOM, Nom));
			params.add(new BasicNameValuePair(TAG_PRENOM, Prenom));
			params.add(new BasicNameValuePair(TAG_EMAIL, Email));
			params.add(new BasicNameValuePair(TAG_PASSWORD, Password));

			// getting JSON Object
			// Note that create membre url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_nouveau_membre,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// successfully created member
					Intent i = new Intent(getApplicationContext(), AuthentificationActivity.class);
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
}