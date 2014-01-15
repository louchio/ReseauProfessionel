<?php

$reponse = array(); // json reponse

if(isset($_POST['id'])) {
	require_once "connexion.php";
	$db  = new connexion();
	$req = "select * from membres where idMembres = '".$_POST['id']."'";
	
	$result = mysql_query($req) or die("Erreur dans la requete fichier getProfile.php ");
	
	if(mysql_num_rows($result) == 1) {
		
		$reponse['user'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$user = array();
			
			$user['idMembres'] = $row['idMembres'];
			$user['email'] = $row['email'];
			$user['password'] = $row['password'];
			$user['nom'] = $row['nom'];
			$user['prenom'] = $row['prenom'];
			
			array_push($reponse['user'], $user);
		}
		
		$reponse['success'] = 1 ;
		
		// envoi du resultat de type json
		echo json_encode($reponse);
	}
	else {
		// no user found
		$reponse['success'] = 0;
		$reponse['message'] = "Error id introuvable";
		
		// echo no user JSON
		echo json_encode($reponse);
	}
}
else {
	$reponse['success'] = 0;
	$reponse['message'] = "Pas de Parametres";
		
	// echo no Parametre JSON
	echo json_encode($reponse);
}
?>