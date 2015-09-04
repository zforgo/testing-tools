package hu.zforgo.junit.tools.configuration;

public class HierarchicalConfigurationTest/* extends AbstractConfigurationTest*/ {

//	@Override
//	protected Configuration loadConfiguration() throws IOException {
//		if (c == null) {
//			URL root = Thread.currentThread().getContextClassLoader().getResource("");
//			assert root != null : "Couldn't find root url";
//			Path lastPath;
//			try {
//				lastPath = Paths.get(root.toURI()).resolve("configuration").resolve("levels");
//			} catch (URISyntaxException e) {
//				throw new IOException("Root folder is invalid", e);
//			}
//			List<Path> paths = new ArrayList<>();
//			for (String a : new String[]{"first","second","third"}) {
//				lastPath = lastPath.resolve(a);
//				paths.add(lastPath);
//			}
//			return ConfigurationFactory.load("hierarchical.properties", paths);
//		}
//		throw new IllegalStateException("Configuration must be loaded only once.");
//	}
}
