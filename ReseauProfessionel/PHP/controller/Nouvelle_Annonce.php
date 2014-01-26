<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['titreAnnonce']) && isset($_POST['texteAnnonce']) && isset($_POST['idUtilisateur'])) {

	$titreAnnonce = $_POST['titreAnnonce'];
    $texteAnnonce = $_POST['texteAnnonce'];
    $idUtilisateur = $_POST['idUtilisateur'];
	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		$result = mysql_query("INSERT INTO annonce(titreAnnonce,textAnnonce,idUtilisateur) VALUES('$titreAnnonce', '$texteAnnonce', $idUtilisateur) ;");

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