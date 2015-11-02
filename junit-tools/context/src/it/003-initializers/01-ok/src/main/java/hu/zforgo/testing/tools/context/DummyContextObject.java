package hu.zforgo.testing.tools.context;

public class DummyContextObject {

	private boolean enabled;
	private static DummyContextObject instance;

	private DummyContextObject(boolean enabled) {
		this.enabled = enabled;
	}

	public static DummyContextObject create(boolean enabled) {
		if (instance == null) {
			instance = new DummyContextObject(enabled);
		}
		System.out.println("instance created: " + instance.isEnabled());
		return instance;
	}

	public static DummyContextObject getInstance() {
		if (instance == null) {
			System.out.println("instance is null: ");
			instance = new DummyContextObject(false);
		}
		return instance;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
