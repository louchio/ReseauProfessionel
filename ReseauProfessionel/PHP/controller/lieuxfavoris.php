<?php

$reponse = array(); // json reponse
if(isset($_GET['Membres_idMembres'])) {
	require_once "connexion.php";
	$db  = new connexion();
	$req = "SELECT * FROM lieuxfavoris WHERE Membres_idMembres='".$_GET['Membres_idMembres']."'";
	
	$result = mysql_query($req);
	
	if(mysql_num_rows($result) > 0) {
		
		$reponse['lieuxfavoris'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$lieu = array();

			$lieu['idLieuxFavoris'] = $row['idLieuxFavoris'];
			$lieu['nom'] = $row['nom'];
			$lieu['longitude'] =  $row['longitude'];
			$lieu['latitude'] = $row['latitude'];
			
			array_push($reponse['lieuxfavoris'], $lieu);
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