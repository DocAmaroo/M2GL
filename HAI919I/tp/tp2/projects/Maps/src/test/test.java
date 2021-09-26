package test;

import maps.MapsPackage;
import maps.PublicSpace;
import maps.Road;
import maps.map;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//Je charge l'instance map.xmi du méta-modèle maps.ecore
		Resource resource = chargerModele("model/map.xmi", MapsPackage.eINSTANCE);
		if (resource == null) System.err.println(" Erreur de chargement du modèle");
		
		
		//Instruction récupérant le modèle sous forme d'arbre à partir de la classe racine "map"
		map maMap = (map) resource.getContents().get(0);
		List<Road> roads = maMap.getRoad();
		
		// Question 1 
		System.out.println("# --- Nom des rues (Street)");
		roads.stream()
			.filter(r -> r.getClass().getSimpleName().equals("StreetImpl"))
			.map(r -> r.getName())
			.forEach(System.out::println);
		
		// Question 2
		System.out.println("\n\n# --- Nom des rues piétonnes (Pedestrian) > 1000mètres");
		roads.stream()
			.filter(p -> p.getClass().getSimpleName().equals("PedestrianImpl"))
			.filter(p -> p.getLength() > 1000)
			.map(r -> r.getName())
			.forEach(System.out::println);
		
		// Question 3
		System.out.println("\n\n# --- Nom des rues adjentes à une rue donnée");
		String roadName = "Avenue Guillaume Pellicier";
		Road road = null;
		
		for (Road r : roads)
			if (r.getName().equals(roadName))
				road = r;
		
		if (road == null)
			System.out.println("[-] Désolé, aucune rue ne correspond à votre recherche: '" + roadName + "'");
		else {
			System.out.println("[+] Une rue correspond à votre recherche: '" + roadName + "'");
			System.out.println("Liste des rues adjenctes:");
			road.getMeet().stream()
				.map(r -> r.getName())
				.forEach(r -> System.out.println("  - " + r));
		}
	
		// Question 4
		System.out.println("\n\n# --- Nom des rues en bordure d'une place (Square)");
		List<PublicSpace> publicSpaces = maMap.getSpaces();
		String placeName = "MaxiSquare";
		PublicSpace publicSpace = null;
		
		for (PublicSpace ps : publicSpaces)
			if (ps.getName().equals(placeName))
				publicSpace = ps;
		
		if (publicSpace == null)
			System.out.println("[-] Désolé, aucune place ne correspond à votre recherche: '" + placeName + "'");
		else {
			System.out.println("[+] Une place correspond à votre recherche: '" + placeName + "'");
			System.out.println("Liste des routes qui l'entoure:");
			publicSpace.getBorderedBy().stream()
				.map(r -> r.getName())
				.forEach(r -> System.out.println("  - " + r));
		}
	}
	
	
	public static Resource chargerModele(String uri, EPackage pack) {
		   Resource resource = null;
		   try {
		      URI uriUri = URI.createURI(uri);
		      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		      resource = (new ResourceSetImpl()).createResource(uriUri);
		      XMLResource.XMLMap xmlMap = new XMLMapImpl();
		      xmlMap.setNoNamespacePackage(pack);
		      java.util.Map options = new java.util.HashMap();
		      options.put(XMLResource.OPTION_XML_MAP, xmlMap);
		      resource.load(options);
		   }
		   catch(Exception e) {
		      System.err.println("ERREUR chargement du modèle : "+e);
		      e.printStackTrace();
		   }
		   return resource;
		}

}
