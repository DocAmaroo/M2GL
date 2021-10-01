

MATCH (c:Commune {name:'MONTPELLIER'})
CREATE (gf:Personne {nom:"FRECHE",prenom:"GEORGES"}) <-[ap1:ADMINISTREE_PAR {date_debut:1997, date_fin:2004}]- (c),
(hm:Personne {nom:"MANDROUX",prenom:"HELENE"})<-[ap2:ADMINISTREE_PAR {date_debut:2004, date_fin:2014}]-(c), (ps:Personne {nom:"SAUREL",prenom:"PHILIPPE"})<-[ap3:ADMINISTREE_PAR {date_debut:2014, date_fin:2020}]-(c), (md:Personne {nom:"DELAFOSSE",prenom:"MICKAEL"})<-[ap4:ADMINISTREE_PAR {date_debut:2020, date_fin:2026}]-(c)
return *


CREATE INDEX ON :Resource(uri) 


WITH ' PREFIX sch: <http://schema.org/> 
CONSTRUCT{ ?item a sch:City; 
           sch:address ?inseeCode; 
           sch:name ?itemLabel ; 
           sch:geoTouches ?otherItem .
	   ?otherItem a sch:City;	
	   sch:name ?otheritemLabel ;
	   sch:address ?otherinseeCode . } 
WHERE { ?item wdt:P374 ?inseeCode .
	?item wdt:P47 ?otherItem .
	?otherItem wdt:P374 ?otherinseeCode .
        ?item rdfs:label ?itemLabel . 
          filter(lang(?itemLabel) = "fr")  . 
        ?otherItem rdfs:label ?otheritemLabel . 
          filter(lang(?otheritemLabel) = "fr")  .
	 FILTER regex(?inseeCode, "^34")  .
        } ' AS sparql CALL semantics.importRDF(
  "https://query.wikidata.org/sparql?query=" +  
      apoc.text.urlencode(sparql),"JSON-LD", 
    { headerParams: { Accept: "application/ld+json"} })
YIELD terminationStatus, triplesLoaded,namespaces,extraInfo
RETURN terminationStatus, triplesLoaded,namespaces,extraInfo
