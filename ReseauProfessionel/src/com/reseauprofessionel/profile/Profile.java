package com.reseauprofessionel.profile;

public class Profile {
	
	
	public static String nom = "";
	public static String prenom = "";
	public static String numTel = "";
	public static String adresse = "";
	public static String email = "";
	public static String estProfessionnel ;
	public static String login = "";
	public static String password = "";
	public static String idProfession ;
	
	
	public static void creer_profile(String nom1, String prenom1, String numTel1,String adresse1, String email1, String estProfessionnel1,String login1, String password1,String idProfession1){
		nom 	= nom1;
		prenom 	= prenom1;
		numTel = numTel1;
		adresse = adresse1;
		email = email1;
		estProfessionnel = estProfessionnel1;
		login = login1;
		password = password1;
		idProfession = idProfession1;
	}
	
	public static void maj_profile(String n, String p){
		nom 	= n;
		prenom 	= p;
	}
}
