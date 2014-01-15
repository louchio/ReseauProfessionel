/* 
 * Copyright (C) 2012
 * Package com.reseauprofesionnel.servicegps
 * Created By KHABALI ANAS
 * 25/05/2012  21:33
 */

package com.reseauprofessionel.servicegps;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.reseauprofessionel.json.JSONParser;
/**
 * @author Anas KHABALI
 * @category Service
 * This is the GPSSERVICE  Service
 * it's a service that provide and manage GPS Functionality 
 */
public class GPSService extends Service {
	
	
	private static String id = null;

	private double latitude;
	private double longitude;
	private double vitesse;
	private long date;
	
	private String locationContext;
	private LocationManager locationManager;
	
	private JSONParser jsonParser = new JSONParser();
	
	private static final String url_sendMarkers = "http://10.0.2.2/reseauprofesionnel/controller/sendMarkers.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "messgae";
	
	
	private LocationListener onLocationChaged = new LocationListener() {
	
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			/**
			 * Envoie des coordonnées aprés chaque changement des coordonnées
			 * cette methode appele la methode envoie des coordonees()
			 */
			findCoordonnees();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){

		locationContext = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(locationContext);
		if(locationManager != null) {
			findCoordonnees();
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 100, onLocationChaged);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100, onLocationChaged);
			
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId){
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(onLocationChaged);
	}

	private void findCoordonnees() {
		/**
		 * sendCoordonnees() methode
		 * cette methode envoie les coordonnees recuperer de l'apareil vers la base de données
		 * @return void
		 */
		
		List<String> providers = locationManager.getProviders(true); // liste des fournisseurs de coordonnée disponible
		
		for(String provider : providers) {	
			//Recuperation des coordonnées
			
			Location location = locationManager.getLastKnownLocation(provider);
			if(location != null && id != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				vitesse = location.getSpeed();
				date = location.getTime();
				
				new SendCoordonnees().execute();
			}
			else {
			}
		}
		/*
		 * Envoie des coordonnées vers la tables Markers dans la base de données
		 */
	}
	
	 class SendCoordonnees extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			
			// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						
						params.add(new BasicNameValuePair("id", ""+id));
						params.add(new BasicNameValuePair("latitude", ""+latitude));
						params.add(new BasicNameValuePair("longitude", ""+longitude));
						params.add(new BasicNameValuePair("vitesse", ""+vitesse));
						params.add(new BasicNameValuePair("date", ""+date));
						// getting JSON Object
						// Note that create membre url accepts POST method
						JSONObject json = jsonParser.makeHttpRequest(url_sendMarkers,"POST", params);
						
						// check log cat fro response
						Log.d("Resultat service", json.toString());

			return null;
		}

	}
	 
	 public static String getId() {
			return id;
		}

		public static void setId(String id) {
			GPSService.id = id;
		}

}
