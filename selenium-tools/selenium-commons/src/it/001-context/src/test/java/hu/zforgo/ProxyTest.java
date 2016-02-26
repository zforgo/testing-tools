package hu.zforgo;

import hu.zforgo.common.util.ClassUtil;
import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import org.junit.Test;
import org.openqa.selenium.Proxy;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

	@Test
	public void proxyTest() throws NoSuchFieldException {
		Map<DriverSetup, Proxy> proxies = ClassUtil.fieldValue(SeleniumContext.class, "configuredProxies");
		{
			Proxy p = proxies.get(DriverSetup.CHROME);
			assertThat(p).isNull();
		}
		{
			Proxy p = proxies.get(DriverSetup.FIREFOX);

			assertThat(p.isAutodetect()).isTrue();
			assertThat(p.getFtpProxy()).isNullOrEmpty();
			assertThat(p.getHttpProxy()).isNullOrEmpty();
			assertThat(p.getProxyType()).isEqualTo(Proxy.ProxyType.AUTODETECT);
		}
//		{
//			Proxy p = proxies.get(DriverSetup.IE);
//
//			assertThat(p.isAutodetect()).isFalse();
//			assertThat(p.getFtpProxy()).isEqualTo("localhost:9321");
//			assertThat(p.getHttpProxy()).isNullOrEmpty();
//			assertThat(p.getProxyType()).isEqualTo(Proxy.ProxyType.MANUAL);
//		}
	}
}
