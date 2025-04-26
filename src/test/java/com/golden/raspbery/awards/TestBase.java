package com.golden.raspbery.awards;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestBase {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao desserializar JSON", e);
		}
	}


	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao converter para JSON", e);
		}
	}
}
