package net.badata.protobuf.converter.domain;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.proto.ResolverProto.ResolverTest;
import net.badata.protobuf.converter.type.TypeConverter;

public class ResolverDomain {
	
	@ProtoClass(ResolverTest.class)
	public static class Test {
		
		@ProtoField(name = "stringListValue", converter = CommaDelimitedStringListConverter.class)
		private String commaDelimitedStringValue;

		public String getCommaDelimitedStringValue() {
			return commaDelimitedStringValue;
		}

		public void setCommaDelimitedStringValue(String commaDelimitedStringValue) {
			this.commaDelimitedStringValue = commaDelimitedStringValue;
		}
	}
	
	public static class CommaDelimitedStringListConverter implements TypeConverter<String, List<String>> {

		@Override
		public String toDomainValue(Object instance) {
			if (instance instanceof List) {
				List<?> list = (List<?>) instance;
				if (String.class.equals(
						((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0])) {
					@SuppressWarnings("unchecked")
					List<String> stringList = (List<String>) instance;
					return String.join(",", stringList);
				}
			}
			return null;
		}

		@Override
		public List<String> toProtobufValue(Object instance) {
			if (instance instanceof String)
			{
				String stringValue = (String) instance;
				String[] splitStrings = stringValue.split(",");
				LinkedList<String> stringList = new LinkedList<String>();
				for(String s : splitStrings) {
					stringList.add(s);
				}
				return stringList;
			}
			return null;
		}
		
	}
}
