<?php

$reponse = array(); // json reponse
if(isset($_GET['Membres_idMembres'])) {
	require_once "connexion.php";
	$db  = new connexion();
	$req = "SELECT * FROM profession;";
	
	$result = mysql_query($req);
	
	if(mysql_num_rows($result) > 0) {
		
		$reponse['professions'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$profession = array();

			$profession['idProfession'] = $row['idprofession'];
			$profession['nom'] = $row['nom'];
			
			array_push($reponse['professions'], $profession);
		}
		
		$reponse['success'] = 1 ;
		
		// envoi du resultat de type json
		echo json_encode($reponse);
	}
	else {
		// no lieu found 
		$reponse['success'] = 0;
		$reponse['message'] = "Aucun lieux favoris";
		
		// echo no lieu JSON
		echo json_encode($reponse);
	}
}
else {
	$reponse['success'] = 0;
	$reponse['message'] = "Id_membres introuvable";
		
	// echo no Parametre JSON
	echo json_encode($reponse);
}
?>