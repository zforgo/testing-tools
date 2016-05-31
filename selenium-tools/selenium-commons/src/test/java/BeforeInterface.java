import org.junit.Before;

public interface BeforeInterface {

	@Before
	default void init() {
		System.out.println("Bele");
	}
}
