<?php

/*
 * Following code will create a new membre row
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['civilite']) && isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['email']) 
	&& isset($_POST['password']) && isset($_POST['telephone']) && isset($_POST['arr'])
	&& isset($_POST['isProf']) && isset($_POST['idProfession'])
	) {
	
	$civilite = $_POST['civilite'];
    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
	$email = $_POST['email'];
    $password = $_POST['password'];
	$passwordHashe = sha1($password);
	$arr = $_POST['arr'];
	$telephone = $_POST['telephone'];
	
	$isProf = $_POST['isProf'];
	/*
	if ($_POST['isProf'] == "Oui") $isProf = 1;
	else $isProf = 0;
	*/
	$idProfession = $_POST['idProfession'];	
	
		// include db connect class
		require_once 'connexion.php';

		// connecting to db
		$conexion = new connexion();

     	// mysql inserting a new row
		//$result = mysql_query("INSERT INTO membres(email, password, nom, prenom) VALUES('$email', '$password', '$nom', '$prenom') ;");
		$req =  "INSERT INTO membres(civilite, nom, prenom, numTel, email, password, arrondissement, estProfessionnel, idProfession) VALUES('$civilite', '$nom', '$prenom', '$telephone', '$email', '$passwordHashe', '$arr', '$isProf', '$idProfession') ;";
		$result = mysql_query($req);
			
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
			$response["message"] = $result;
			//$response["message"] = "Oops! An error occurred.";
			
			// echoing JSON response
			echo json_encode($req);
		}		
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>