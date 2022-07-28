package com.example.pet.provider.host;

import com.example.pet.entity.EngineType;
import com.example.pet.entity.HostFilter;
import com.example.pet.entity.Listing;
import com.example.pet.entity.host.Host;

public interface HostProvider {

    Listing<Host> listHosts(HostFilter hostNames);

    EngineType getProviderType();
}
