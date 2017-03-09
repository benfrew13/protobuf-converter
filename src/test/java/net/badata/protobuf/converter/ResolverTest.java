package net.badata.protobuf.converter;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.badata.protobuf.converter.domain.ResolverDomain;
import net.badata.protobuf.converter.proto.ResolverProto;

public class ResolverTest {

	private static final String DOMAIN_STRING = "a,b,c,d,e,f";
	private static final List<String> PROTO_STRINGS = Arrays.asList("u", "v", "w", "x", "y", "z");
	
	private ResolverDomain.Test testDomain;
	private ResolverProto.ResolverTest testProtobuf;

	@Before
	public void setUp() {
		createTestDomain();
		createTestProtobuf();
	}

	private void createTestDomain() {
		testDomain = new ResolverDomain.Test();
		testDomain.setCommaDelimitedStringValue(DOMAIN_STRING);
	}
	
	private void createTestProtobuf() {
		testProtobuf = ResolverProto.ResolverTest.newBuilder().addAllStringListValue(PROTO_STRINGS).build();
	}
	
	@Test
	public void testDomainToProtobuf() {
		ResolverProto.ResolverTest result = Converter.create().toProtobuf(ResolverProto.ResolverTest.class, testDomain);
		
		Assert.assertNotNull(result);
		Assert.assertEquals(testDomain.getCommaDelimitedStringValue(), String.join(",", result.getStringListValueList()));
	}
}
