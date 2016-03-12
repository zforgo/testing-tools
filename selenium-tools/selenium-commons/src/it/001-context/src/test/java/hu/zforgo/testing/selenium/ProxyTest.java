package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.ClassUtil;
import hu.zforgo.testing.configuration.Configuration;
import javaslang.Tuple2;
import org.junit.Test;
import org.openqa.selenium.Proxy;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

	@Test
	public void proxyTest() throws NoSuchFieldException {
		Map<DriverSetup, Tuple2<Proxy, Configuration>> config = ClassUtil.fieldValue(SeleniumContext.class, "driverConfig");
		{
			Proxy p = config.get(DriverSetup.CHROME)._1;
			assertThat(p).isNull();
		}
		{
			Proxy p = config.get(DriverSetup.FIREFOX)._1;

			assertThat(p.isAutodetect()).isTrue();
			assertThat(p.getFtpProxy()).isNullOrEmpty();
			assertThat(p.getHttpProxy()).isNullOrEmpty();
			assertThat(p.getProxyType()).isEqualTo(Proxy.ProxyType.AUTODETECT);
		}
		{
			Proxy p = config.get(DriverSetup.HTMLUNIT)._1;
			assertThat(p.isAutodetect()).isFalse();
			assertThat(p.getFtpProxy()).isEqualTo("localhost:9321");
			assertThat(p.getHttpProxy()).isNullOrEmpty();
			assertThat(p.getProxyType()).isEqualTo(Proxy.ProxyType.MANUAL);
		}
	}
}
