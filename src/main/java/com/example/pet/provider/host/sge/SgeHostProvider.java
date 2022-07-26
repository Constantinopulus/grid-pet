package com.example.pet.provider.host.sge;

import com.example.pet.provider.host.HostProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SgeHostProvider implements HostProvider {

    private static String QHOST_XML = "<?xml version='1.0'?>"
            + "<qhost xmlns:xsd=\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qhost/qhost.xsd\">\n"
            + "<host name='global'>\n"
            + "<hostvalue name='arch_string'>-</hostvalue>\n"
            + "<hostvalue name='num_proc'>-</hostvalue>\n"
            + "<hostvalue name='m_socket'>-</hostvalue>\n"
            + "<hostvalue name='m_core'>-</hostvalue>\n"
            + "<hostvalue name='m_thread'>-</hostvalue>\n"
            + "<hostvalue name='load_avg'>-</hostvalue>\n"
            + "<hostvalue name='mem_total'>-</hostvalue>\n"
            + "<hostvalue name='mem_used'>-</hostvalue>\n"
            + "<hostvalue name='swap_total'>-</hostvalue>\n"
            + "<hostvalue name='swap_used'>-</hostvalue>\n"
            + "</host>\n"
            + "<host name='ip-172-31-1-162.eu-central-1.compute.internal'>\n"
            + "<hostvalue name='arch_string'>lx-amd64</hostvalue>\n"
            + "<hostvalue name='num_proc'>2</hostvalue>\n"
            + "<hostvalue name='m_socket'>1</hostvalue>\n"
            + "<hostvalue name='m_core'>1</hostvalue>\n"
            + "<hostvalue name='m_thread'>2</hostvalue>\n"
            + "<hostvalue name='load_avg'>0.00</hostvalue>\n"
            + "<hostvalue name='mem_total'>3.6G</hostvalue>\n"
            + "<hostvalue name='mem_used'>311.6M</hostvalue>\n"
            + "<hostvalue name='swap_total'>0.0</hostvalue>\n"
            + "<hostvalue name='swap_used'>0.0</hostvalue>\n"
            + "</host>\n"
            + "</qhost>";

    @Override
    public EngineType getProviderType() {
        return EngineType.SGE;
    }

    @Override
    public Listing<Host> listHosts() {
        return getSgeHosts(JaxbUtils.unmarshall(QHOST_XML, SgeHostListing.class));
    }

    final Listing<Host> getSgeHosts(SgeHostListing sgeHostListing) {
        return new Listing<>(
                CollectionUtils.emptyIfNull(sgeHostListing.getSgeHost()).stream()
                        .map(this::fillElements).collect(Collectors.toList())
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

    void setXml(String xml) {
        QHOST_XML = xml;
    }
}
