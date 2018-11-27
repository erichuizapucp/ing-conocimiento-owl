package pe.edu.pucp.owl;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        try {
            Options options = new Options();

            options = buildValidateOntologyOptions(options);
            options = buildValidateOntologyOptions2(options);
            options = buildInferencyOptions(options);
            options = buildSparqlOptions(options);

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("v")) {
                Validation validation = new Validation();
                validation.ValidateOwlModel();
            }

            if (cmd.hasOption("v2")) {
                Validation2 validation = new Validation2();
                validation.validateOntology();
            }

            if (cmd.hasOption("i")) {
                Inferences inferences = new Inferences();
                if (cmd.hasOption("class")) {
                    String className = cmd.getOptionValue("class");

                    if (className.equals("similar-to-unix")) {
                        inferences.processSimilarToUnix();
                    }
                    if (className.equals("production-unix")) {
                        inferences.processProductionToUnix();
                    }
                    if (className.equals("certified-unix")) {
                        inferences.processCertifiedUnix();
                    }
                }
            }

            if (cmd.hasOption("s")) {
                SparqlQueries sparqlQueries = new SparqlQueries();
                if (cmd.hasOption("query")) {
                    String query = cmd.getOptionValue("query");
                    sparqlQueries.executeQuery(query);
                }
            }
        }
        catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static Options buildValidateOntologyOptions(Options options) {
        options.addOption("v", "validate", false, "Validates ontology");
        return options;
    }

    public static Options buildValidateOntologyOptions2(Options options) {
        options.addOption("v2", "validate", false, "Validates ontology");
        return options;
    }

    public static Options buildInferencyOptions(Options options) {
        options.addOption("i", "inference", false, "Performs inferences for defined classes");
        options.addOption(Option.builder()
                .longOpt("class")
                .desc("Class")
                .hasArg()
                .argName("CLASS")
                .build());
        return options;
    }

    public static Options buildSparqlOptions(Options options) {
        options.addOption("s", "search", false, "Performs searches using Sparql");
        options.addOption(Option.builder()
                .longOpt("query")
                .desc("Query")
                .hasArg()
                .argName("QUERY")
                .build());
        return options;
    }
}