package pe.edu.pucp.owl;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

import java.util.Iterator;

public class Validation {
    public void ValidateOwlModel() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager.get().readModel(model, "ontology/unix-family.owl");

        Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        System.out.println("Iniciando validación del modelo OWL");

        ValidityReport validity = infModel.validate();
        if (validity.isValid()) {
            System.out.println("El modelo es correcto y no presenta inconsistencias");
        }
        else {
            System.out.println("Se encontraron los siguientes conflictos");
            for (Iterator i = validity.getReports(); i.hasNext(); ) {
                ValidityReport.Report report = (ValidityReport.Report)i.next();
                System.out.println("    - " + report);
            }
        }

        System.out.println("Fin de la validación");
    }
}
