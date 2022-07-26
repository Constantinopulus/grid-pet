package com.example.pet.provider.host;

import com.epam.grid.engine.entity.EngineType;
import com.epam.grid.engine.entity.Listing;
import com.epam.grid.engine.entity.host.Host;

public interface HostProvider {

    Listing<Host> listHosts();

    EngineType getProviderType();
}
