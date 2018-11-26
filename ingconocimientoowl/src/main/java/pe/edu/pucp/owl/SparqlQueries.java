package pe.edu.pucp.owl;

import org.apache.commons.io.IOUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.IOException;


public class SparqlQueries {
    private final String UNIX_FAMILY_NS = "http://www.pucp.edu.pe/ontologies/unix-family#";

    public void executeQuery(String queryName) {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager.get().readModel(model, "ontology/unix-family.owl");

        try {
            Query query = QueryFactory.create(getQuery(queryName));
            QueryExecution qexec = QueryExecutionFactory.create(query, model);

            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results, model);

            qexec.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public String getQuery(String queryName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(queryName));
    }
}
