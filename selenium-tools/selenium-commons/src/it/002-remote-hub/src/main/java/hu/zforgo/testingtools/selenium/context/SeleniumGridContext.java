package hu.zforgo.testingtools.selenium.context;

import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.GridHubConfiguration;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.server.SeleniumServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class SeleniumGridContext implements ContextInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(SeleniumGridContext.class);

	private static volatile Hub hub;
	private static volatile SelfRegisteringRemote node;

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		try {
			URL remoteHub = new URL(contextConfig.getString("selenium.hub"));

			LOG.info("Starting remote Selenium HUB on: " + remoteHub);
			GridHubConfiguration c = GridHubConfiguration.build(new String[]{});
			c.setHost(remoteHub.getHost());
			c.setPort(remoteHub.getPort());
			Hub h = new Hub(c);
			h.start();
			hub = h;

			LOG.info("Starting remote Selenium Grid Node");
			RegistrationRequest rc = RegistrationRequest.build("-role", "hub", "-host", "localhost", "-hub", hub.getRegistrationURL().toString());

			SelfRegisteringRemote remote = new SelfRegisteringRemote(rc);
			remote.setRemoteServer(new SeleniumServer(rc.getConfiguration()));
			remote.startRemoteServer();
			LOG.info("Selenium Grid node is up and ready to register to the hub");
			remote.startRegistrationProcess();
			node = remote;

			LOG.info("Selenium Grid launched successfully. Hub is on: " + h.getUrl());
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
			if (hub != null) {
				hub.stop();
			}
		} catch (Exception e) {
			LOG.error("An error occured during shutting down Selenium Grid", e);
		}
	}
}
