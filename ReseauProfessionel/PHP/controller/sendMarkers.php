<?php 
/**
 * sendMarkers scripte php 
 * ce scripte re�oit et insert les coordonn�es dans la base de donn�e
 * @author Anas KHABALI
 * @version 0.1
 * 
 * @var id : id du Membres qui a envoy�e des coordonn�e
 * 		latitude Double
 * 		longitude Double
 *@return   JSONArray un tableau qui contient le resultat du scripte 
 *
 *@since 2012
 */

if(isset($_POST['id']) && isset($_POST['latitude']) && isset($_POST['longitude']) && isset($_POST["vitesse"])
		&& isset($_POST['date']))
{
	/* Si tout les parametres sont fournie
	 * Traitement 
	 */
	
	$reponse = Array(); // Tableau retourner a la fin de se scripte
	
	require_once 'connexion.php';
	
	$bd = new connexion();
	
	$requete = "INSERT INTO Markers VALUES(NULL,".$_POST['longitude'].",".$_POST['latitude'].",".$_POST['vitesse'].",".$_POST['date'].",".$_POST['id'].")";
	$resultat = mysql_query($requete);
	
	if(! $resultat) {
		$reponse["success"] = "0";
		$reponse["message"] = "Erreur serveur introuvable";
		echo json_encode($reponse);
	}
	else {
		$reponse['success'] = "1";
		echo json_encode($reponse);
	}
}
 
?>