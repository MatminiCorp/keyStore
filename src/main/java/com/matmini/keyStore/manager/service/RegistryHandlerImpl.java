package com.matmini.keyStore.manager.service;

import java.util.Map;

import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.RegistryHandlerInterface;
import com.matmini.keyStore.screen.handlers.RegistriesHandler;

public class RegistryHandlerImpl implements RegistryHandlerInterface {

    @Override
    public String getKeyMap(Registry registry) {
        return RegistriesHandler.getKeyMap(registry);
    }

    @Override
    public Map<String, Registry> parseRegistryToMap(Registry registry) {
        return RegistriesHandler.parseRegistryToMap(registry);
    }
}
