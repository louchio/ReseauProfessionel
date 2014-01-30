package com.reseauprofessionel.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reseauprofessionel.friends.AllFriendsActivity;
import com.reseauprofessionel.gestAnnonces.GestAnnoncesActivity;
import com.reseauprofessionel.gestAnnonces.ListAllProfessions;
import com.reseauprofessionel.gestAnnonces.RechercheAnnonce;
import com.reseauprofessionel.lieuxfavoris.LieuxFavorisActivity;
import com.reseauprofessionel.profile.ProfileActivity;
import com.reseauprofessionel.request.RequestActivity;

/**
 * This is the base class for activities in the dashboard application.
 * It implements methods that are useful to all top level activities.
 * That includes: (1) stub methods for all the activity lifecycle methods;
 * (2) onClick methods for clicks on home, search, feature 1, feature 2, etc.
 * (3) a method for displaying a message to the screen via the Toast class.
 *
 */

public abstract class DashboardActivity extends Activity 
{

	protected static  String idUser= null;
	private static final String TAG_ID = "idUtilisateur";
/**
 * onCreate - called when the activity is first created.
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 */

@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_default);
}
    
/**
 * onDestroy
 * The final call you receive before your activity is destroyed. 
 * This can happen either because the activity is finishing (someone called finish() on it, 
 * or because the system is temporarily destroying this instance of the activity to save space. 
 * You can distinguish between these two scenarios with the isFinishing() method.
 *
 */

@Override
protected void onDestroy ()
{
   super.onDestroy ();
}

/**
 * onPause
 * Called when the system is about to start resuming a previous activity. 
 * This is typically used to commit unsaved changes to persistent data, stop animations 
 * and other things that may be consuming CPU, etc. 
 * Implementations of this method must be very quick because the next activity will not be resumed 
 * until this method returns.
 * Followed by either onResume() if the activity returns back to the front, 
 * or onStop() if it becomes invisible to the user.
 *
 */

@Override
protected void onPause ()
{
   super.onPause ();
}

/**
 * onRestart
 * Called after your activity has been stopped, prior to it being started again.
 * Always followed by onStart().
 *
 */

@Override
protected void onRestart ()
{
   super.onRestart ();
}

/**
 * onResume
 * Called when the activity will start interacting with the user. 
 * At this point your activity is at the top of the activity stack, with user input going to it.
 * Always followed by onPause().
 *
 */

@Override
protected void onResume ()
{
   super.onResume ();
}

/**
 * onStart
 * Called when the activity is becoming visible to the user.
 * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
 *
 */

@Override
protected void onStart ()
{
   super.onStart ();
}

/**
 * onStop
 * Called when the activity is no longer visible to the user
 * because another activity has been resumed and is covering this one. 
 * This may happen either because a new activity is being started, an existing one 
 * is being brought in front of this one, or this one is being destroyed.
 *
 * Followed by either onRestart() if this activity is coming back to interact with the user, 
 * or onDestroy() if this activity is going away.
 */

@Override
protected void onStop ()
{
   super.onStop ();
}

/**
 */
// Click Methods

/**
 * Handle the click on the home button.
 * 
 * @param v View
 * @return void
 */

public void onClickHome (View v)
{
    goHome (this);
}

/**
 * Handle the click on the search button.
 * 
 * @param v View
 * @return void
 */

public void onClickSearch (View v)
{
    startActivity (new Intent(getApplicationContext(), SearchActivity.class));
}

/**
 * Handle the click on the About button.
 * 
 * @param v View
 * @return void
 */

public void onClickAbout (View v)
{
    startActivity (new Intent(getApplicationContext(), AboutActivity.class));
}

/**
 * Handle the click of a Feature button.
 * 
 * @param v View
 * @return void
 */

public void onClickFeature (View v)
{
    int id = v.getId ();
    Intent i = null;
    
    switch (id) {
      case R.id.home_btn_feature1 :
           i = new Intent(getApplicationContext(), ProfileActivity.class);
           break;
      case R.id.home_btn_feature2 :
    	  i = new Intent(getApplicationContext(), RechercheAnnonce.class);
           break;
      case R.id.home_btn_feature3 :
    	   i = new Intent(getApplicationContext(), AllFriendsActivity.class);
           break;
      case R.id.home_btn_feature4 :
           //i= new Intent(getApplicationContext(), LieuxFavorisActivity.class);
    	   i= new Intent(getApplicationContext(), GestAnnoncesActivity.class);
           break;
      case R.id.home_btn_feature5 :
           i = new Intent(getApplicationContext(), ListAllProfessions.class);
           break;
      case R.id.home_btn_feature6 :
    	  i = new Intent(getApplicationContext(), RequestActivity.class);
           break;
      default: 
    	   break;
    }
    if(i != null) {
    	i.putExtra(TAG_ID, idUser);
    	startActivityForResult(i, 100);
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

/**
 */
// More Methods

/**
 * Go back to the home activity.
 * 
 * @param context Context
 * @return void
 */

public void goHome(Context context) 
{
    final Intent intent = new Intent(context, HomeActivity.class);
    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity (intent);
}

/**
 * Use the activity label to set the text in the activity's title text view.
 * The argument gives the name of the view.
 *
 * <p> This method is needed because we have a custom title bar rather than the default Android title bar.
 * See the theme definitons in styles.xml.
 * 
 * @param textViewId int
 * @return void
 */

public void setTitleFromActivityLabel (int textViewId)
{
    TextView tv = (TextView) findViewById (textViewId);
    if (tv != null) tv.setText (getTitle ());
} // end setTitleText

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_LONG).show ();
} // end toast

/**
 * Send a message to the debug log and display it using Toast.
 */
public void trace (String msg) 
{
    Log.d("Demo", msg);
    toast (msg);
}

public static String getIdUser(){
	return idUser;
}

} // end class
