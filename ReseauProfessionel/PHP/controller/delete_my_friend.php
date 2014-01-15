<?php

/*
 * Following code will delete a friend from table
 * A friend is identified by friend id (idMembers)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['idMembres']) && isset($_POST['MonId'])) {
    $fid   = $_POST['idMembres'];
	$my_id = $_POST['MonId'] ;

    // include db connect class
	require_once 'connexion.php';

	// connecting to db
	$conexion = new connexion();

    // mysql update row with matched fid
    $result = mysql_query("DELETE  FROM amis where (idAmis='".$my_id."' and Membres_idMembres = '".$fid."') 
							or (idAmis='".$fid."' and Membres_idMembres = '".$my_id."'); ");
    
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Friend successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no friend found
        $response["success"] = 0;
        $response["message"] = "No Friend found";

        // echo no users JSON
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