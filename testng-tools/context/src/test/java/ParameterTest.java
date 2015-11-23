import hu.zforgo.testing.generator.annotations.Provided;
import hu.zforgo.testing.generator.annotations.Provided.Param;
import javaslang.Tuple;
import javaslang.collection.Stream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class ParameterTest {

	@Test
	public void tupleTest() {
		String[] ize = new String[]{"valami", "masvalami"};
		String[] ize2 = new String[]{"valami2", "masvalami2"};
		String[][] faszom = new String[][]{
				new String[]{"valami", "masvalami"},
				new String[]{"valami2", "masvalami2"}
		};
		boolean foound = Stream.of(faszom).flatMap(Stream::of).findFirst(s-> s.startsWith("mas2")).isDefined();
//		.forEach(System.out::println);
		System.out.println("-------------");
		Stream.of(faszom).map(Tuple::of).map(t -> t._1).forEach(System.out::println);
		boolean f = Stream.of(faszom).findFirst(t -> Arrays.asList(t).contains("val")).isDefined();
//		.forEach(System.out::println);
		Stream.of("valami", "masvalami").zip(Stream.of(1L, 2L))
				.forEach(System.out::println);
	}

	private Object hehe(Object a) {
		return a;
	}

	//	@Test(dataProviderClass = DataGenerator.class, dataProvider = "generate")
	@Test
	public void test(@Provided(params = {@Param(name = "file", value= "valid_user.json")}) CharSequence string, List<Integer> numbers) {
		System.out.println("String is: " + string);
	}

	@DataProvider
	public Object[][] genData() {
		return null;
//		return new Object[][]{{"five", Collections.singletonList(3)}};
	}

}
/*
package com.javacodegeeks.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InstanceDataProviderExample {
	@Test(dataProvider="getData")
	public void instanceDbProvider(int p1, String p2) {
		System.out.println("Instance DataProvider Example: Data(" + p1 + ", " + p2 + ")");
	}

	@DataProvider
	public Object[][] getData() {
		return new Object[][]{{5, "five"}, {6, "six"}};
	}
}
 */