package net.paissad.tools.soapui;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

import lombok.Getter;

@Getter
public class MultiTestRunnerOptions {

	@Option(name = "-h", aliases = { "--help" }, help = true, usage = "Shows the help")
	private boolean			help;

	@Option(name = "--trp", required = true, metaVar = "</path/testrunner.sh>", usage = "Path to the SoapUI testrunner script")
	private String			testRunnerPath;

	@Option(name = "--settings", metaVar = "</path/soapui-settings.xml>", required = false, usage = "SoapUI settings file to use for all executions of the testrunner script")
	private String			settingsFile;

	@Option(name = "--out", required = true, metaVar = "<dir>", usage = "Directory where to store the results of the tests executions")
	private String			outputFolder;

	@Option(name = "--in", required = true, metaVar = "<dir>", usage = "Directory containing all *-soapui-project.xml tests projects to run")
	private String			projectsDir;

	@Option(name = "--log-level", required = false, metaVar = "[level]", usage = "Sets the log level. Possible values are OFF|ERROR|WARN|INFO|DEBUG|TRACE. Default value is 'INFO'")
	private String			logLevel;

	@Argument
	private List<String>	arguments	= new ArrayList<>();

	/**
	 * @param args : arguments / options.
	 * @return The command line parser.
	 * @throws CmdLineException If an error occurs while parsing the options.
	 */
	public CmdLineParser parseOptions(final String... args) throws CmdLineException {

		final CmdLineParser parser = new CmdLineParser(this);

		ParserProperties.defaults().withUsageWidth(150);

		try {
			parser.parseArgument(args);

		} catch (CmdLineException e) {
			System.err.println();
			System.err.println("======> " + e.getMessage());
			System.err.println();
			printUsage(parser);
			throw e;
		}

		return parser;
	}

	/**
	 * Prints the usage.
	 * 
	 * @param parser : the options & arguments parser
	 */
	public static void printUsage(final CmdLineParser parser) {
		System.out.println("soapui-multi-testrunner [options...] arguments...");
		parser.printUsage(System.out);
		System.out.println();

		System.out.println("  Example: soapui-multi-testrunner " + parser.printExample(filter -> !filter.option.help()));
	}
}
