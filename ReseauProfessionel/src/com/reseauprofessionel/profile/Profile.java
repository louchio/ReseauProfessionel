package com.reseauprofessionel.profile;

public class Profile {
	public static String nom = "";
	public static String prenom = "";
	public static String email = "";
	
	
	public static void creer_profile(String n, String p, String e){
		nom 	= n;
		prenom 	= p;
		email = e;
	}
	
	public static void maj_profile(String n, String p){
		nom 	= n;
		prenom 	= p;
	}
}
