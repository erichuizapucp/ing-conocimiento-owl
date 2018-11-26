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

    public void processSimilarToUnix() {
        try {
            DLQueryEngine engine = getEngine();
            Set<OWLClass> result = engine.getSubClasses(SIMILAR_TO_UNIX, false);

            Iterator<OWLClass> it = result.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        }
        catch (Exception e) {
            System.err.println(e);
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
