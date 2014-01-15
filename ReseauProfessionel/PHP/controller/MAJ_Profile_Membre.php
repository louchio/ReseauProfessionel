<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['nom']) && isset($_POST['prenom']))
// && isset($_POST['password']) 
 {
    $id = $_POST['idMembres'];
    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
	
//    $password = $_POST['password'];
	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		$result = mysql_query("UPDATE membres SET nom = '$nom', prenom = '$prenom' WHERE idMembres = '$id';");

		// check if row inserted or not
		if ($result) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Membre successfully updated.";

			// echoing JSON response
			echo json_encode($response);
		} else {
			// failed to update row
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