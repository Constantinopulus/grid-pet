package com.example.pet.provider.host.sge;

import com.example.pet.cmd.SimpleCmdExecutor;
import com.example.pet.entity.CommandResult;
import com.example.pet.entity.EngineType;
import com.example.pet.entity.HostFilter;
import com.example.pet.entity.Listing;
import com.example.pet.entity.host.Host;
import com.example.pet.entity.host.sge.SgeHost;
import com.example.pet.entity.host.sge.SgeHostListing;
import com.example.pet.entity.host.sge.SgeHostProperty;
import com.example.pet.entity.host.sge.SgeHostValue;
import com.example.pet.provider.host.HostProvider;
import com.example.pet.provider.utils.JaxbUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.pet.provider.utils.NumberParseUtils.*;

@Service
@RequiredArgsConstructor
public class SgeHostProvider implements HostProvider {

    private static final String NEW_LINE = "\n";
    private static final String QHOST_COMMAND = "qhost";
    private static final String TYPE_XML = "-xml";
    private static final String HOST = "-h";
    private static final String GLOBAL = "global";
    private final SimpleCmdExecutor simpleCmdExecutor;

    @Override
    public EngineType getProviderType() {
        return EngineType.SGE;
    }

    @Override
    public Listing<Host> listHosts(HostFilter hostNames) {
        final String[] hostCommand = buildRequest(hostNames);
        final CommandResult commandResult = simpleCmdExecutor.execute(hostCommand);

        if (commandResult.getExitCode() != 0) {
            throw new IllegalStateException(String.format("Exit code: %s; Error output: %s; Output: %s",
                    commandResult.getExitCode(), String.join(NEW_LINE, commandResult.getStdErr()),
                    String.join(NEW_LINE, commandResult.getStdOut())));
        }
        return getSgeHosts(JaxbUtils.unmarshall(String.join(NEW_LINE,
                        commandResult.getStdOut()),
                SgeHostListing.class));
    }

    private Listing<Host> getSgeHosts(final SgeHostListing sgeHostListing) {
        return new Listing<>(CollectionUtils.emptyIfNull(sgeHostListing.getSgeHost())
                .stream()
                .map(this::fillElements)
                .filter(host -> !host.getHostname().equals(GLOBAL))
                .collect(Collectors.toList())
        );
    }

    private Host fillElements(final SgeHost sgeHost) {
        final Map<SgeHostProperty, String> hostPropertyToName = sgeHost
                .getHostAttributes()
                .stream()
                .collect(Collectors.toMap(SgeHostValue::getName, SgeHostValue::getValue));
        return Host.builder()
                .hostname(sgeHost.getHostname())
                .typeOfArchitect(checkString(hostPropertyToName.get(SgeHostProperty.TYPE_OF_ARCHITECT)))
                .numOfProcessors(toInt(hostPropertyToName.get(SgeHostProperty.NUM_OF_PROCESSORS)))
                .numOfSocket(toInt(hostPropertyToName.get(SgeHostProperty.NUM_OF_SOCKET)))
                .numOfCore(toInt(hostPropertyToName.get(SgeHostProperty.NUM_OF_CORE)))
                .numOfThread(toInt(hostPropertyToName.get(SgeHostProperty.NUM_OF_THREAD)))
                .load(toDouble(hostPropertyToName.get(SgeHostProperty.LOAD)))
                .memTotal(toLong(hostPropertyToName.get(SgeHostProperty.MEM_TOTAL)))
                .memUsed(toLong(hostPropertyToName.get(SgeHostProperty.MEM_USED)))
                .totalSwapSpace(toDouble(hostPropertyToName.get(SgeHostProperty.TOTAL_SWAP_SPACE)))
                .usedSwapSpace(toDouble(hostPropertyToName.get(SgeHostProperty.USED_SWAP_SPACE)))
                .build();
    }

    private static String[] buildRequest(HostFilter hostFilter) {
        final List<String> commands = new ArrayList<>();
        commands.add(QHOST_COMMAND);
        Optional.ofNullable(hostFilter).map(HostFilter::getHosts)
                .filter(CollectionUtils::isNotEmpty)
                .ifPresent(hosts -> {
                    commands.add(HOST);
                    commands.addAll(hosts);
                });
        commands.add(TYPE_XML);
        return commands.toArray(new String[0]);
    }
}
