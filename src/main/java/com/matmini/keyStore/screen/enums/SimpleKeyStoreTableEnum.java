package com.matmini.keyStore.screen.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SimpleKeyStoreTableEnum {

	USER("User"), PASSWORD("Password"), WEBSITE("Website"), Copy("Copy");

	private String column;

	SimpleKeyStoreTableEnum(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public static List<String> getAllColumns() {
		return Stream.of(SimpleKeyStoreTableEnum.values()).map(SimpleKeyStoreTableEnum::getColumn)
				.collect(Collectors.toList());
	}

}
