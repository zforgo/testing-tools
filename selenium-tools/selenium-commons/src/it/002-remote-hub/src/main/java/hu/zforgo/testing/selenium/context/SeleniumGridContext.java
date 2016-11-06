package hu.zforgo.testing.selenium.context;

import hu.zforgo.testing.configuration.Configuration;
import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.internal.utils.configuration.StandaloneConfiguration;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.server.SeleniumServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class SeleniumGridContext implements ContextInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(SeleniumGridContext.class);

	private static volatile SeleniumServer server;
	private static volatile SelfRegisteringRemote node;

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		try {
			URL remoteHub = new URL(contextConfig.getString("selenium.hub"));

			LOG.info("Starting remote Selenium HUB on: " + remoteHub);
			GridHubConfiguration c = new GridHubConfiguration();
			c.host = remoteHub.getHost();
			c.port = remoteHub.getPort();

			SeleniumServer s = new SeleniumServer(c);
			server = s;

			LOG.info("Starting remote Selenium Grid Node");
			GridNodeConfiguration nodeConfiguration = GridNodeConfiguration.loadFromJSON(getClass().getResource("/nodeconfig.json").getFile());

			RegistrationRequest rc = RegistrationRequest.build(nodeConfiguration, "TestNode");

			SelfRegisteringRemote remote = new SelfRegisteringRemote(rc);
			LOG.info("Selenium Grid node is up and ready to register to the hub");
			remote.setRemoteServer(s);
			remote.startRemoteServer();
			remote.startRegistrationProcess();
			node = remote;

			LOG.info("Selenium Grid launched successfully. Hub is on: " + remoteHub.toString());
		} catch (Exception e) {
			LOG.error("An error occured during starting Selenium Grid", e);
			throw new ContextInitializationFailure(e);
		}

	}

	@Override
	public void shutdown() throws Exception {
		try {
			if (node != null) {
				node.stopRemoteServer();
			}
		} catch (Exception e) {
			LOG.error("An error occured during shutting down Selenium Grid Node", e);
		}
		try {
			if (server != null) {
				server.stop();
			}
		} catch (Exception e) {
			LOG.error("An error occured during shutting down Selenium Grid", e);
		}
	}
}
