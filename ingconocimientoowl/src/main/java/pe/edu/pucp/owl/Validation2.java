package pe.edu.pucp.owl;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;

public class Validation2 {
    public void validateOntology() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String ontologyPath = URLDecoder.decode(classLoader.getResource("ontology/unix-family.owl").getFile());

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager
                    .loadOntologyFromOntologyDocument(
                            new FileDocumentSource(new File(ontologyPath)));

            OWLReasoner reasoner = new Reasoner(ontology);
            if (isValid(reasoner)) {
                System.out.println("La ontología es consistente");
            }
            else {
                System.out.println("La ontología es incosistente");

                Iterator<OWLClass> unsatisfiedClass = reasoner.getUnsatisfiableClasses().iterator();
                while (unsatisfiedClass.hasNext()) {
                    System.out.println(unsatisfiedClass.next());
                }
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private boolean isValid(OWLReasoner reasoner) {
        boolean isConsistent = reasoner.isConsistent();
        boolean hasUnsatisfiedClasses =
                reasoner.getUnsatisfiableClasses().getEntities().stream().filter(c -> !c.isOWLNothing()).count() > 0;

        return isConsistent && !hasUnsatisfiedClasses;
    }
}
