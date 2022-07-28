package com.example.pet.service;

import com.example.pet.entity.EngineType;
import com.example.pet.entity.HostFilter;
import com.example.pet.entity.Listing;
import com.example.pet.entity.host.Host;
import com.example.pet.provider.host.HostProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HostOperationProviderService {

    private final EngineType engineType = EngineType.SGE;

    private Map<EngineType, HostProvider> providers;

    public Listing<Host> hostListing(HostFilter hostNames) {
        return getProvider().listHosts(hostNames);
    }

    @Autowired
    public void setProviders(final List<HostProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(HostProvider::getProviderType, Function.identity()));
    }

    private HostProvider getProvider() {
        final HostProvider hostProvider = providers.get(engineType);
        Assert.notNull(hostProvider, String.format("Provides for type '%s' is not supported", engineType));
        return hostProvider;
    }
}
