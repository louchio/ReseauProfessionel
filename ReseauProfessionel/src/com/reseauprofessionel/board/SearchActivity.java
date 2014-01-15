package com.reseauprofessionel.board;

import android.os.Bundle;

/**
 * This is the Search activity in the dashboard application.
 * c'est l'activity permettant la recherche des amis.
 * et permettant l'envoie des demande de suivie;
 *
 */

public class SearchActivity extends DashboardActivity 
{

/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_search);
    setTitleFromActivityLabel (R.id.title_text);
}
    
} // end class
