package com.matmini.keyStore.manager.interfaces;

import com.matmini.keyStore.manager.Registry;

import java.util.Map;

public interface RegistryHandlerInterface {
  String getKeyMap(Registry registry);
  Map<String, Registry> parseRegistryToMap(Registry registry);
}
