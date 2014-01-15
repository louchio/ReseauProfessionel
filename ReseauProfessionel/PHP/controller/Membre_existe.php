<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['email']) ) {

	$email = $_POST['email'];

	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();
	
	 $req = "SELECT * FROM membres where email = '$email' ;" ;
	 $res = mysql_query($req) ;
	 $nb  = mysql_num_rows($res) ;
	  
	
	if($nb > 0) {
			$response["success"] = 0 ;
			$response["message"] = "Ce email est deja utilise .";
			
			// echoing JSON response
			echo json_encode($response);
	} else {
		$response["success"] = 1 ;
		$response["message"] = "Ce email est valide ";

		// echoing JSON response
		echo json_encode($response);
	} 
}
?>