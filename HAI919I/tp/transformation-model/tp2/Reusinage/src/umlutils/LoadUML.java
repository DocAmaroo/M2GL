package umlutils;


//import org.eclipse.emf.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
//import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;

public class LoadUML {

	public static void main(String[] args) {

			
		
		//Instruction récupérant le modèle sous forme d'arbre à partir de la classe racine "Model"
		Model umlP = chargerModele("model/model.uml");
		
		
		String nomModele=  umlP.getName();
		
		System.out.println("Ton modèle se nomme: \""+nomModele+"\"");
		
		switchPackage(umlP,"Package1","Package2","Enseignant");
		
		//Modifier le nom du modèle UML
		umlP.setName("NewModelName");
		System.out.println("\""+nomModele+"\" Changé ! Le modèle se nomme: \""+umlP.getName()+"\"");
		
		//Sauvegarder le modèle avec son nouveau nom
		sauverModele("model/newmodel.uml", umlP);
		
	}
	
	
	//Changer de package
	public static void switchPackage(Model uml, String p1, String p2, String classToChange) {
		PackageableElement package1 = uml.getPackagedElement(p1);
		Package package2;
		boolean flag = false;
		PackageableElement packagedElement = uml.getPackagedElement(p2);
		
		if(packagedElement instanceof Package) {
			package2 = (Package) packagedElement;
		} else {
			System.err.println("-switchPackage- erreur lors de la saisie de package");
			return;
		}
		
		EList<Element> p1List = package1.getOwnedElements();
		for(int i = 0; i < p1List.size(); i++) {
			Element e = p1List.get(i);
			if(e instanceof Class) {
				Class obj = (Class) e;
				if(obj.getName().equals(classToChange)) {
					obj.setPackage(package2);
					flag = true;
				}
			}
		}
		if(flag) {
			System.out.println("La classe "+ classToChange + " à migrer du package " + p1 + " au package " + p2);
		} else {
			System.out.println("Aucune migration entre la classe " + classToChange + " et les packages " + p1 + " et " + p2);
		}
	}
	
	public static void sauverModele(String uri, EObject root) {
		   Resource resource = null;
		   try {
		      URI uriUri = URI.createURI(uri);
		      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		      resource = (new ResourceSetImpl()).createResource(uriUri);
		      resource.getContents().add(root);
		      resource.save(null);
		   } catch (Exception e) {
		      System.err.println("ERREUR sauvegarde du modèle : "+e);
		      e.printStackTrace();
		   }
		}

		public static Model chargerModele(String uri) {
		   Resource resource = null;
		   EPackage pack=UMLPackage.eINSTANCE;
		   try {
		      URI uriUri = URI.createURI(uri);
		      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new XMIResourceFactoryImpl());
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
		   return (Model) resource.getContents().get(0);
		}

}
