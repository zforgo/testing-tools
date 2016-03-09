package hu.zforgo.junit.tools;

import hu.zforgo.junit.tools.context.GroupRule;
import hu.zforgo.junit.tools.context.annotations.AlwaysRun;
import hu.zforgo.junit.tools.context.annotations.Groups;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;

public class GroupRuleTest {

	@Rule
	public GroupRule groupRule = new GroupRule();

	@Rule
	public LoggerRule loggerRule = new LoggerRule();

	@Test
	@AlwaysRun
	public void alwaysRun() {
		Assert.assertTrue(1 == 1L);
	}

	@Test
	@Groups({"nevergroup"})
	public void neverRun() {
		Assert.assertTrue("Never gonna happen", false);
	}

	@Test(expected = AssumptionViolatedException.class)
	@Groups({"nevergroup"})
	public void neverRunExcepted() {
		Assume.assumeTrue(false);
		Assert.assertTrue("Never gonna happen", false);
	}

	@Test
	@Groups(" foo")
	public void leadingSpace() {
		Assert.assertTrue(1 == 1L);
	}

	@Test
	@Groups("foo ")
	public void trailingSpace() {
		Assert.assertTrue(1 == 1L);
	}

	@Test
	@Groups({"raz"," foo"})
	public void arrayLeading() {
		Assert.assertTrue(1 == 1L);
	}

	@Test
	@Groups({"raz","foo"})
	public void multiValue() {
		Assert.assertTrue(1 == 1L);
	}

	@Test
	@Groups({"raz","haz"})
	public void multiExclusion() {
		Assert.assertTrue("Never gonna happen", false);
	}




}
