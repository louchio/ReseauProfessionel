<?php

$reponse = array(); // json reponse

if(isset($_POST['id'])) {
	require_once "connexion.php";
	$db  = new connexion();
	$req = "select * from membres where idUtilisateur = '".$_POST['id']."'";
	
	$result = mysql_query($req) or die("Erreur dans la requete fichier getProfile.php ");
	
	if(mysql_num_rows($result) == 1) {
		
		$reponse['user'] = array(); 
		while($row = mysql_fetch_array($result)) {
			
			$user = array();
			
			$user['idUtilisateur'] = $row['idUtilisateur'];
			$user['nom'] = $row['nom'];
			$user['prenom'] = $row['prenom'];
			$user['numTel'] = $row['numTel'];
			$user['adresse'] = $row['adresse'];
			$user['email'] = $row['email'];
			$user['estProfessionnel'] = $row['estProfessionnel'];
			$user['login'] = $row['login'];
			$user['password'] = $row['password'];
			$user['idProfession'] = $row['idProfession'];
			
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