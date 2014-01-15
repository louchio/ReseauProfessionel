<?php

class connexion {
 
	/*
	 * Constructeur de la class connexion
	 */
	function __construct(){
 		$this->connect();
 	}
 	
 	function connect(){
 		require_once '../db_config/db_config.php';
 		
 		$con = mysql_connect(DB_SERVER,DB_USER,DB_PASSWORD) or die (mysql_error());
 		$db = mysql_select_db(DB_DATABASE,$con) or die(mysql_error());
 		
 		return $con;
 	}
 	
 	function  __destruct(){
 		mysql_close();
 	}
}
