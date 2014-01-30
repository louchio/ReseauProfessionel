package com.reseauprofessionel.authentification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.gestAnnonces.GestAnnoncesActivity;
import com.reseauprofessionel.gestAnnonces.GestAnnoncesActivity.MonSpinner1Listener;
import com.reseauprofessionel.json.JSONParser;

@SuppressLint("CutPasteId")
public class Inscription extends DashboardActivity {
	
	
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONArray tablieux = null;
	JSONParser jsonParser = new JSONParser();
	JSONParser jParser = new JSONParser();
	// url to create new membere
	private static String url_nouveau_membre = "http://10.0.2.2/reseauprofessionnel/controller/Nouveau_Membre.php";
	private static String url_membre_existe = "http://10.0.2.2/reseauprofessionnel/controller/Membre_existe.php";

	// JSON Node names
	private static final String TAG_CIVILITE = "civilite";
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_ARR = "arr";
	private static final String TAG_TELEPHONE = "telephone";
	private static final String TAG_ISPROF = "isProf";
	private static final String TAG_IDPROFESSION = "idProfession";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	Spinner  ETCivilite;
	EditText ETNom ;
	EditText ETPrenom ;
	EditText ETEmail ;
	EditText ETPassword ;
	EditText ETPasswordC ;
	Spinner  ETArr ;
	EditText ETTelephone ;
	Spinner  ETIsProf;
	Spinner  ETSpinnerProf;
	TableRow ETchampMetiers;
	Button   SinscrireButton ;
	Button   AnnulerButton ;

	
	private String 	Civilite	= null ;
	private String 	Nom			= null ; 
	private String 	Prenom 		= null ; 
	private String 	Email 		= null ; 
	private String 	Password 	= null ; 
	private String 	PasswordC 	= null ;
	private String 	Arr		 	= null ;
	private String 	Telephone 	= null ;
	private String	IsProf	 	= null ;
	private String 	IdProfession = null;
	
	ArrayList<HashMap<String, String>> professionsArrayList;
	List<String> Prof;

	private static final String TAG_PROFESSIONS = "professions";
	private static final String TAG_NOMP = "nom";
	
	private static String url_authentification = "http://10.0.2.2/reseauprofessionnel/controller/get_professions.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		
		ETCivilite	= (Spinner) findViewById(R.id.ETgroupSexe) ;
		ETNom 		= (EditText) findViewById(R.id.ETNom) ;
		ETPrenom 	= (EditText) findViewById(R.id.ETPrenom) ;
		ETEmail 	= (EditText) findViewById(R.id.ETEmail) ;
		ETPassword 	= (EditText) findViewById(R.id.ETPassword) ;
		ETPasswordC = (EditText) findViewById(R.id.ETPasswordC) ;
		ETArr		= (Spinner) findViewById(R.id.ETgroupArrondissement) ;
		ETTelephone	= (EditText) findViewById(R.id.ETTelephone) ;
		ETIsProf	= (Spinner) findViewById(R.id.ETgroupIsProf) ;
		ETIsProf.setOnItemSelectedListener(new MonSpinnerEstProfListener());
		ETSpinnerProf = (Spinner) findViewById(R.id.ETspinnerMetier);
		ETchampMetiers = (TableRow) findViewById(R.id.ETChampMetiers);
		SinscrireButton = (Button) findViewById(R.id.SinscrireButton);
		AnnulerButton   = (Button) findViewById(R.id.AnnulerButton);
		
		// Remplir les listes déroulantes
		List<String> Civilite = new ArrayList<String>();
		Civilite.add("Monsieur");
		Civilite.add("Madame");
		ArrayAdapter<String> dataAdapterCivilite = new ArrayAdapter<String>
													(this,android.R.layout.simple_spinner_item, Civilite);
		dataAdapterCivilite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ETCivilite.setAdapter(dataAdapterCivilite);
		
		List<String> Arrondissement = new ArrayList<String>();
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
		ETArr.setAdapter(dataAdapterArrondissement);
		
		professionsArrayList = new ArrayList<HashMap<String,String>>();
		Prof = new ArrayList<String>();
		Prof.add("Non");
		Prof.add("Oui");
		ArrayAdapter<String> dataAdapterProf = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Prof);
		dataAdapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ETIsProf.setAdapter(dataAdapterProf);
	
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
		
		new listeProfessions().execute();
		
	}
	
	public class MonSpinnerEstProfListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			if((Prof.get(arg2).equals("Oui")))
					ETchampMetiers.setVisibility(View.VISIBLE);
			else
				ETchampMetiers.setVisibility(View.INVISIBLE);
			
			//Toast.makeText(getApplicationContext(), "Selected : " + nomStrings[arg2],Toast.LENGTH_SHORT).show();
		}
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	  }

	public class MonSpinnerMetierListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String, String> map = new HashMap<String, String>();
			map=professionsArrayList.get(arg2);
			IdProfession = map.get(TAG_IDPROFESSION);
			
			Log.i("mmmmmmmmmmmmmmmmmmmm", "idProf = "+IdProfession);
			//Toast.makeText(getApplicationContext(), "Selected : " + nomStrings[arg2],Toast.LENGTH_SHORT).show();
		}
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	  }

	
	public class listeProfessions extends AsyncTask<String, String, String> {	
		
		 // La méthode  onPreExecute appelée avant le traitement
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(Inscription.this);
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
						String nom = profession.getString(TAG_NOMP);
						
						//trace(""+i);
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_IDPROFESSION, id_profession);
						map.put(TAG_NOMP, nom);
						// adding HashList to ArrayList
						professionsArrayList.add(map);
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa ="+professionsArrayList.size());
						
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
											Inscription.this, 
											
											professionsArrayList,
											
											R.layout.item_profession_insc, new String[] {TAG_NOMP},
											new int[] { R.id.nom_professionInsc});
									// updating liste view
									ETSpinnerProf.setAdapter(adapter);
									ETSpinnerProf.setOnItemSelectedListener(new MonSpinnerMetierListener());
					
									}
								});
						}else{
							Toast.makeText(getBaseContext(), "Aucun Lieux Favoris", Toast.LENGTH_LONG).show();
						}
	}
	}
	private void Initialiser() {
		ETNom.setText("")		;
		ETPrenom.setText("")	;
		ETEmail.setText("")		;
		ETPassword.setText("")	;
		ETPasswordC.setText("")	;
		ETTelephone.setText("")	;
	}

	private void RecupererLesChamps() {
		// Recuperer le bouton du civilite selectionné
		Civilite = ETCivilite.getSelectedItem().toString();
		Nom 		= ETNom.getText().toString();
		Prenom 		= ETPrenom.getText().toString();
		Email 		= ETEmail.getText().toString();
		Password 	= ETPassword.getText().toString();
		PasswordC 	= ETPasswordC.getText().toString();
		Arr			= ETArr.getSelectedItem().toString();
		Telephone 	= ETTelephone.getText().toString();
		IsProf 		= ETIsProf.getSelectedItem().toString();
	}
	
	boolean  SaisieIsValidated() {
		if(Nom.equals("") || Prenom.equals("")|| Email.equals("") || Password.equals("") 
			|| PasswordC.equals("") || Telephone.equals("")) {
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
			params.add(new BasicNameValuePair(TAG_CIVILITE, Civilite));
			params.add(new BasicNameValuePair(TAG_NOM, Nom));
			params.add(new BasicNameValuePair(TAG_PRENOM, Prenom));
			params.add(new BasicNameValuePair(TAG_EMAIL, Email));
			params.add(new BasicNameValuePair(TAG_PASSWORD, Password));
			params.add(new BasicNameValuePair(TAG_ARR, Arr));
			params.add(new BasicNameValuePair(TAG_TELEPHONE, Telephone));
			params.add(new BasicNameValuePair(TAG_ISPROF, IsProf));
			params.add(new BasicNameValuePair(TAG_IDPROFESSION, IdProfession));

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
	
	public void onClickRetourne(View v){
		Intent i = new Intent(getApplicationContext(),AuthentificationActivity.class);
		// Closing all previous activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
