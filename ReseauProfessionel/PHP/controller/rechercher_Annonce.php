<?php

$reponse = array(); // json reponse
if(isset($_GET['idProfession']) && isset($_GET['estProfessionnel']) && isset($_GET['arrondissement'])) { 
	require_once "connexion.php";
	$db  = new connexion();
	$IdProfession = $_GET['idProfession'];
	$estProfessionnel = $_GET['estProfessionnel'];
	$arrondissment = $_GET['arrondissement'];
	
	$req = "SELECT * FROM annonce, membres where annonce.idUtilisateur = membres.idUtilisateur and (annonce.idProfession='".$IdProfession."' and membres.arrondissement = '".$arrondissment."' and membres.estProfessionnel='".$estProfessionnel."');";
	$result = mysql_query($req);
	
	if(mysql_num_rows($result) > 0) {
		
		$reponse['annonces'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$annonce = array();
			$annonce['idAnnonce'] = $row['idannonce'];
			$annonce['titre_annonce'] = $row['titreAnnonce'];
			$annonce['texte_annonce'] = $row['textAnnonce'];
			
			array_push($reponse['annonces'], $annonce);
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