<?php

$reponse = array(); // json reponse
if(isset($_GET['Membres_idMembres']) && isset($_GET['idUser'])) { 
	require_once "connexion.php";
	$db  = new connexion();
	$req1 = "SELECT * FROM profession;";
	$UserId = $_GET['idUser'];
	
	$result1 = mysql_query($req1);
	
	if(mysql_num_rows($result1) > 0) {
		
		$reponse['professionsUser'] = array(); 
		while($row = mysql_fetch_array($result1)) {
			
			$profession = array();
			$idprofession = $row['idprofession'];
			$profession['idProfession'] = $idprofession;
			$profession['nom'] = $row['nom'];
			
			$req2 = "SELECT * FROM annonce where (idUtilisateur='".$UserId."' and idProfession = '".$idprofession."');";
			$result2 = mysql_query($req2);
			$profession['nbrAnnonce'] = mysql_num_rows($result2);;
			
			array_push($reponse['professionsUser'], $profession);
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