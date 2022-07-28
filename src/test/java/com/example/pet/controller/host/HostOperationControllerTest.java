package com.example.pet.controller.host;

import com.example.pet.entity.Listing;
import com.example.pet.entity.host.Host;
import com.example.pet.service.HostOperationProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HostController.class)
public class HostOperationControllerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json;charset=UTF-8";
    protected ObjectMapper objectMapper = createObjectMapper();

    @Autowired
    protected MockMvc mvc;

    private static final String URI = "/hosts";

    @MockBean
    private HostOperationProviderService hostOperationProviderService;

    @Test
    public void shouldReturnStatusAndValue() throws Exception {
        final Host expectedHost = Host.builder()
                .hostname("test-ip")
                .typeOfArchitect("lx-amd64")
                .numOfProcessors(2)
                .numOfSocket(1)
                .numOfCore(1)
                .numOfThread(2)
                .load(0.0)
                .memTotal(3600000000L)
                .memUsed(311600000L)
                .totalSwapSpace(0.0)
                .usedSwapSpace(0.0)
                .build();

        final List<Host> hostList = Collections.singletonList(expectedHost);
        final Listing<Host> hostListing = new Listing<>();
        hostListing.setElements(hostList);
        Mockito.when(hostOperationProviderService.hostListing(null)).thenReturn(hostListing);

        final String response = performMvcRequest(MockMvcRequestBuilders.post(URI))
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(hostListing));
    }

    public MvcResult performMvcRequest(final MockHttpServletRequestBuilder servletRequestBuilder) throws Exception {
        return mvc.perform(servletRequestBuilder
                        .accept(EXPECTED_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                .andReturn();
    }

    public ObjectMapper createObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
