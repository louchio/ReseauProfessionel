package com.reseauprofessionel.authentification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reseauprofessionel.board.DashboardActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;
import com.reseauprofessionel.profile.Profile;

public class AuthentificationActivity extends DashboardActivity {

	
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	EditText inputEmail;
	EditText inputPassword;
	TextView compte ;

	Button seconnecterBtn;
	Button annulerBtn;

	// url pour s'authetifier 
	private static String url_authentification = "http://10.0.2.2/reseauprofessionnel/controller/authentification.php";

	// noeud nom dans json
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_USER = "user";
	private static final String TAG_IDUser = "idUtilisateur";

	private class BtnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.seconnecter :
				new login().execute();
				break;
			case R.id.annuler :
				inputEmail.setText("");
				inputPassword.setText("");
				break;
			case R.id.sinscrire_lien :
				startActivity(new Intent(getApplicationContext(), Inscription.class));
			}
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentification);

		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);

		inputEmail.setText("c");
		inputPassword.setText("c");

		seconnecterBtn = (Button) findViewById(R.id.seconnecter);
		annulerBtn = (Button) findViewById(R.id.annuler);
		compte = (TextView) findViewById(R.id.sinscrire_lien);

		BtnClickListener listener = new BtnClickListener();

		seconnecterBtn.setOnClickListener(listener);
		annulerBtn.setOnClickListener(listener);
		compte.setOnClickListener(listener);

	}

	public class login extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AuthentificationActivity.this);
			pDialog.setMessage("Connexion..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}


		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 * essayer de se connecter
		 */
		@Override
		protected String doInBackground(String... args) {

			String email = inputEmail.getText().toString();
			String password = null;
			try {
				password = sha1(inputPassword.getText().toString());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));

			// getting JSON Object
			// Note that authentifier url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_authentification,"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully connected
					JSONArray userArray = json.getJSONArray(TAG_USER);
					JSONObject user = userArray.getJSONObject(0);
					
					//creer_profile(String nom1, String prenom1, String numTel1,String adresse1, String email1, int estProfessionnel1,String login1, String password1,int idProfession1)

					Profile.creer_profile((String) user.get("nom"),
										(String) user.get("prenom"),
										(String) user.get("numTel"),
										(String) user.get("adresse"),
										(String) user.get("email"),
										(String) user.get("estProfessionnel"),
										(String) user.get("password"),
										(String) user.get("idProfession"));

					Intent i = new Intent(getApplicationContext(), HomeActivity.class);
					AuthentificationActivity.idUser = user.getString(TAG_IDUser);
					pDialog.dismiss();
					startActivity(i);
					//i.putExtra(TAG_IDM, user.getString(TAG_IDM));
					//startActivityForResult(i, 100);
					// closing this screen
					finish();
				} else {
					// Login ou password incorrect 
					pDialog.dismiss();
					return "error";
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String res){
			if(res == "error"){
				Toast.makeText(getApplicationContext(), "Connexion echou�e", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user connected
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}
	
	static String sha1(String input) throws NoSuchAlgorithmException {
	    MessageDigest mDigest = MessageDigest.getInstance("SHA1");
	    byte[] result = mDigest.digest(input.getBytes());
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < result.length; i++) {
	        sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    return sb.toString();
	 }
	/*
	@Override
	protected void onStop() {

	}
	 */
}
