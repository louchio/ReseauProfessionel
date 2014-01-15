<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['email']) && isset($_POST['password']) ) {

	$email = $_POST['email'];
    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
    $password = $_POST['password'];
	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		$result = mysql_query("INSERT INTO membres(email, password, nom, prenom) VALUES('$email', '$password', '$nom', '$prenom') ;");

		// check if row inserted or not
		if ($result) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Membre successfully created.";

			// echoing JSON response
			echo json_encode($response);
		} else {
			// failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";
			
			// echoing JSON response
			echo json_encode($response);
		}		
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>