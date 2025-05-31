package com.matmini.keyStore.screen.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SimpleKeyStoreTableEnum {
  
  NAME("Name", 0), URL("URL", 1), USERNAME("Username", 2), PASSWORD("Password",
      3), NOTE("Note", 4);
  
  private final String column;
  private final Integer code;
  
  SimpleKeyStoreTableEnum(String column, Integer code) {
    this.column = column;
    this.code = code;
  }
  
  public String getColumn() {
    return column;
  }
  
  public Integer getCode() {
    return code;
  }
  
  public static List<String> getAllColumns() {
    return Stream.of(SimpleKeyStoreTableEnum.values())
        .map(SimpleKeyStoreTableEnum::getColumn).collect(Collectors.toList());
  }
}
