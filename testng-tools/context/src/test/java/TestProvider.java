import hu.zforgo.testing.generator.annotations.Provider;

public class TestProvider {

	@Provider
	public static void generate() {

	}

	@Provider
	public static void generate(String input) {

	}

	public static void nongenerate(){}

	public static void nongenerate(Integer input){}

	@Provider
	public void nonstatic() {

	}

	@Provider
	public void nonstatic(String input){}

	public void nonstaticnonprovider(){}
	public void nonstaticnonprovider(String input){}
}
