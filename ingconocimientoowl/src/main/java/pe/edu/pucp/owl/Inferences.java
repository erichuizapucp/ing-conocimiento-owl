package pe.edu.pucp.owl;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import pe.edu.pucp.owl.api.DLQueryEngine;

import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Set;


public class Inferences {
    private final String SIMILAR_TO_UNIX = "_SimilarToUnix";
    private final String PRODUCTION_UNIX = "ProductionUnix";
    private final String CERTIFIED_UNIX = "_CertifiedUnixSystem";

    public void processSimilarToUnix() {
        try {
            showResults(getResults(SIMILAR_TO_UNIX));
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
    public void processProductionToUnix() {
        try {
            showResults(getResults(PRODUCTION_UNIX));
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public void processCertifiedUnix() {
        try {
            showResults(getResults(CERTIFIED_UNIX));
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private Set<OWLClass> getResults(String definedClass) throws OWLOntologyCreationException {
        DLQueryEngine engine = getEngine();
        return engine.getSubClasses(definedClass, false);
    }

    private void showResults(Set<OWLClass> result) {
        Iterator<OWLClass> it = result.stream().filter(c -> !c.isOWLNothing()).iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private DLQueryEngine getEngine() throws OWLOntologyCreationException {
        ClassLoader classLoader = getClass().getClassLoader();
        String ontologyPath = URLDecoder.decode(classLoader.getResource("ontology/unix-family.owl").getFile());

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(
                        new FileDocumentSource(new File(ontologyPath)));

        OWLReasoner reasoner = new Reasoner(ontology);
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();

        DLQueryEngine engine = new DLQueryEngine(reasoner, shortFormProvider);

        return engine;
    }
}
