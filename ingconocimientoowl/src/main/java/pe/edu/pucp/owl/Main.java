package pe.edu.pucp.owl;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        try {
            Options options = new Options();

            options = buildValidateOntologyOptions(options);
            options = buildInferencyOptions(options);
            options = buildSparqlOptions(options);

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("v")) {
                Validation validation = new Validation();
                validation.ValidateOwlModel();
            }

            if (cmd.hasOption("i")) {
                Inferences inferences = new Inferences();
                if (cmd.hasOption("class")) {
                    String className = cmd.getOptionValue("class");
                    inferences.processInference(className);
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
        return options;
    }
}