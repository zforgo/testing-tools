package hu.zforgo.testing.context;

import hu.zforgo.testing.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class FileMarkerInitializer implements ContextInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(FileMarkerInitializer.class);

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		LOG.info(getClass().getName() + " has been successfully inited");
	}

	@Override
	public void shutdown() throws Exception {
		LOG.info(getClass().getName() + " is shutting down");
		Files.write(Paths.get("shutdown.txt").toAbsolutePath(), Collections.singleton("OK"));
	}
}
