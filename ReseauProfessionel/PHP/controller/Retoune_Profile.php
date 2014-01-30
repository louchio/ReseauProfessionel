<?php

$reponse = array(); // json reponse
if(isset($_GET['idAnnonce'])) { 
	require_once "connexion.php";
	$db  = new connexion();
	$idAnnonce = $_GET['idAnnonce'];
	
	$req = "SELECT * FROM membres, annonce where  membres.idUtilisateur = annonce.idUtilisateur 
					and (annonce.idannonce='".$idAnnonce."');";
	
	$result = mysql_query($req);
	
	if(mysql_num_rows($result) > 0) {
		
		$reponse['profile'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$user = array();

			$user['nom'] = $row['nom'];
			$user['prenom'] = $row['prenom'];
			$user['numTel'] = $row['numTel'];
			$user['arrondissement'] = $row['arrondissement'];
			$user['email'] = $row['email'];
			
			array_push($reponse['profile'], $user);
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