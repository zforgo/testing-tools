package hu.zforgo.junit.tools.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleObjectTest extends SimpleConfigurationTest {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleObjectTest.class);

	public void stringTest() {
		LOG.info("Starting String checks...");

	}

	public void stringArrayTest() {
		LOG.info("Starting String array checks...");

	}
	/*
	String checks
		valid string
		missing string
		multi line string (preserve new lines)
		utf-8
		latin1
		leading / trailing spaces and new lines

	String array
		default separator
		given separator
		escaped separator char (must be preserved)
		multi line string
	 */
}
