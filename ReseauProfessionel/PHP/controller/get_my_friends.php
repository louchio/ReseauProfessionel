<?php
/*
 * Following code will list all the friends of a member
 */

// array for JSON response
$response = array();

/**
* Si le parametre a été bien passé !
*/

if(isset($_POST['idMembres'])) {

	$id = $_POST['idMembres'] ;
	// include db connect class
	require_once 'connexion.php';

	// connecting to db
	$conexion = new connexion();

	// get all friends from `members` and `amis`  tables
	 $result = 	mysql_query("SELECT * FROM `membres` m, amis a 
							where (a.idAmis = '".$id."'  AND m.idMembres = a.Membres_idMembres )
							or    (a.Membres_idMembres = '".$id."'  AND m.idMembres = a.idAmis  )  ;") 
				or die(mysql_error()); 
				

	// check for empty result
	if (mysql_num_rows($result) > 0) {
		// looping through all results
		// friends node
		$response["friends"] = array();
		
		while ($row = mysql_fetch_array($result)) {
			// temp friend array
			$friend = array();
			$friend["idMembres"] = $row["idMembres"];
			$friend["email"] = $row["email"];
			$friend["password"] = $row["password"];
			$friend["nom"] = $row["nom"];
			$friend["prenom"] = $row["prenom"];


			// push single friend into final response array
			array_push($response["friends"], $friend);
		}
		// success
		$response["success"] = 1;
		
	} else {
		// no friends found
		$response["success"] = 0;
		$response["message"] = "No friends found";
	}
} else{
	$reponse['success'] = 0;
	$reponse['message'] = "Pas de Parametres";
}

		// echo JSON object 
		echo json_encode($response);

?>
