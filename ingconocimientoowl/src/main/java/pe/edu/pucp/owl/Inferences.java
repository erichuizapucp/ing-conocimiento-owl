package pe.edu.pucp.owl;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.OWLFBRuleReasoner;
import org.apache.jena.reasoner.rulesys.OWLFBRuleReasonerFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.List;

public class Inferences {
    private final String UNIX_FAMILY_NS = "http://www.pucp.edu.pe/ontologies/unix-family#";
    private final String SIMILAR_TO_UNIX_URI = UNIX_FAMILY_NS + "_SimilarToUnix";

    private Model model;
    private OntModelSpec spec;
    private OntModel owlModel;

    public void processInference(String className) {
        model = FileManager.get().loadModel("ontology/unix-family.owl");
        spec = new OntModelSpec(OntModelSpec.OWL_MEM_RULE_INF);

        try {
            owlModel = ModelFactory.createOntologyModel(spec, model);

            if (className.equals("similar-to-unix")) {
                SimilarToUnixInference();
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private void SimilarToUnixInference() {
        OntClass similarToUnixClass = owlModel.getOntClass(SIMILAR_TO_UNIX_URI);
        ExtendedIterator<OntClass> similarToUnix  = similarToUnixClass.listSubClasses();

        while (similarToUnix.hasNext()) {
            OntClass instance = similarToUnix.next();
            System.out.println(instance.getURI());
        }
    }
}
