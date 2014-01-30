<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['idannonce']) && isset($_POST['titreAnnonce']) && isset($_POST['texteAnnonce']) && isset($_POST['idProfession']))
// && isset($_POST['password']) 
 {
	$idannonce = $_POST['idannonce'];
    $titreannonce = $_POST['titreAnnonce'];
    $texteannonce = $_POST['texteAnnonce'];
	$idprofession = $_POST['idProfession'];
	
//    $password = $_POST['password'];
	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		$result = mysql_query("UPDATE annonce SET titreAnnonce = '$titreannonce', textAnnonce = '$texteannonce', idProfession = '$idprofession' WHERE idannonce = '$idannonce';");

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