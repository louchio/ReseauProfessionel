<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['idUtilisateur']) && isset($_POST['telephone']) && isset($_POST['arr'])) {

    $id = $_POST['idUtilisateur'];
    $arr = $_POST['arr'];
	$telephone = $_POST['telephone'];
	/*
	if ($_POST['isProf'] == "Oui") $isProf = 1;
	else $isProf = 0;
	*/
		
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		$req = "UPDATE membres SET arrondissement = '$arr', numTel= '$telephone' WHERE idUtilisateur = '$id';" ;
		$result = mysql_query($req);

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
			//$response["message"] = $req;
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