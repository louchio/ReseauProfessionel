package com.reseauprofessionel.friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.reseauprofessionel.board.DashboardListActivity;
import com.reseauprofessionel.board.HomeActivity;
import com.reseauprofessionel.board.R;
import com.reseauprofessionel.json.JSONParser;

/**
 * 
 * @author Adil El-yamouni
 * 
 * this class provide and manage friends list
 *
 */
public class AllFriendsActivity extends DashboardListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	String fid ;
	String Monid;

	ArrayList<HashMap<String, String>> friendsList;
	HashMap<String, String> amisInfo = new HashMap<String, String>(); 

	// url to get all friends list
	private static String url_my_friends = "http://10.0.2.2/reseauprofessionnel/controller/get_my_friends.php";
	private static String url_delete_my_friends = "http://10.0.2.2/reseauprofessionnel/controller/delete_my_friend.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_FRIENDS = "friends";
	private static final String TAG_FID = "idMembres";
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	private static final String TAG_EMAIL= "email";

	// friends JSONArray
	JSONArray friends = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_friends);
		setTitleFromActivityLabel (R.id.title_text);
		
		Intent i = getIntent();
		Monid = i.getStringExtra(TAG_FID);
		
		// Hashmap for ListView
		friendsList = new ArrayList<HashMap<String, String>>();
		
		// Loading friends in Background Thread
		new LoadAllFriends().execute();

		// Get listview
		ListView lv = getListView();
		registerForContextMenu(lv);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    // info.position ids[info.position]
	    amisInfo = friendsList.get(info.position);
	    menu.setHeaderTitle(amisInfo.get(TAG_NOM)+" "+amisInfo.get(TAG_PRENOM));
	    
	    String[] menuItems = {"Détails","Localiser","Voir l'historique","Supprimer","Retour"};
	    
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i,info.position, menuItems[i]);
	    }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  int menuItemIndex = item.getItemId();
	  int fid = item.getOrder() ;
	  switch (menuItemIndex) {
		case 0:
				// On recupere les données coresopondant a l'item (ami)  selectionné ... 
				amisInfo = friendsList.get(fid);
				showDialogDetails(amisInfo);
			 break;
		case 1:
			 Toast.makeText(getApplicationContext(), "Localiser, for friend's id :"+fid, Toast.LENGTH_SHORT).show();
			break;
		case 2:
			 Toast.makeText(getApplicationContext(), "Historique, for friend's id :"+fid, Toast.LENGTH_SHORT).show();
			break;
		case 3:
			 //Toast.makeText(getApplicationContext(), "Supprimer, for friend's id :"+fid, Toast.LENGTH_SHORT).show();
			 amisInfo = friendsList.get(fid);
			 DeleteFriend(amisInfo.get(TAG_FID));
			break;
		case 4:
			// Toast.makeText(getApplicationContext(), "Retour", Toast.LENGTH_SHORT).show();
			break;
	  }  return true;
	}

	private void DeleteFriend(String _fid) {
		fid = _fid ;
		new DeleteFriend().execute() ;
	}

	private void showDialogDetails(HashMap<String, String> amisInfo) {
		//set up dialog
        final Dialog dialog = new Dialog(AllFriendsActivity.this);
        dialog.setContentView(R.layout.friend_details_dialogs);
        dialog.setTitle("Détails");
        dialog.setCancelable(true);
        //there are a lot of settings, for dialog, check them all out!

        //set up text
        TextView LabelNom = (TextView) dialog.findViewById(R.id.nom);
        LabelNom.setText("  "+amisInfo.get(TAG_NOM));
        
        TextView LabelPrenom = (TextView) dialog.findViewById(R.id.prenom);
        LabelPrenom.setText("  "+amisInfo.get(TAG_PRENOM));
        
        TextView LabelEmail = (TextView) dialog.findViewById(R.id.email);
        LabelEmail.setText("  "+amisInfo.get(TAG_EMAIL));

        //set up button
        Button button = (Button) dialog.findViewById(R.id.Retour);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //now that the dialog is set up, it's time to show it    
        dialog.show();
	}

	/**
	 * Background Async Task to Load all friends by making HTTP Request
	 */
	class LoadAllFriends extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllFriendsActivity.this);
			pDialog.setMessage("Chargement ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All friends from url
		 */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// As test, we take the "2" value.
			params.add(new BasicNameValuePair("idMembres",""+Monid));
			
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_my_friends, "POST", params);
			
			// Check your log cat for JSON reponse
			Log.d("My friends : ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {   // friends found
					// Getting Array of Friends
					friends = json.getJSONArray(TAG_FRIENDS);

						// looping through All friends
						for (int i = 0; i < friends.length(); i++) {
							JSONObject c = friends.getJSONObject(i);
	
							// Storing each json item in variable
							String fid		 = c.getString(TAG_FID);
					 		String nom	 	 = c.getString(TAG_NOM);
					 		String prenom 	 = c.getString(TAG_PRENOM);
					 		String email  	 = c.getString(TAG_EMAIL);
	
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
	
						   // adding each child node to HashMap key => value
					        map.put(TAG_FID, fid);
					        map.put(TAG_NOM, nom);
					        map.put(TAG_PRENOM,prenom);
					        map.put(TAG_EMAIL, email);
	
							// adding HashList to ArrayList
						    friendsList.add(map);
						    
						    
						}
						return TAG_SUCCESS;
					}else{
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

		/**
		 * After completing background task Dismiss the progress dialog
		 */
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			if(file_url == TAG_SUCCESS && file_url != null) {
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 */
						ListAdapter adapter = new SimpleAdapter(
								AllFriendsActivity.this, friendsList,
								R.layout.friendslist_item, new String[] { TAG_FID,TAG_NOM,TAG_PRENOM},
													new int[] { R.id.fid, R.id.nom, R.id.prenom});
						// updating listview
						setListAdapter(adapter);
					}
				});
			}else {
				Toast.makeText(getBaseContext(), "Aucun Amis Trouvé", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * @description Background Async Task to Delete friend
	 * */
	class DeleteFriend extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllFriendsActivity.this);
			pDialog.setMessage("Deleting friend ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting product
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("idMembres",""+fid));
				params.add(new BasicNameValuePair("MonId",""+Monid));
				
				// getting product details by making HTTP request
				JSONObject json = jParser.makeHttpRequest(url_delete_my_friends, "POST", params);

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Deleted friend : ", json.toString());
				}else{
					Log.d("friend is not deleted  : ", json.toString());
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
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			Intent in = getIntent() ;
			startActivity(in);
					
		}

	}
}
