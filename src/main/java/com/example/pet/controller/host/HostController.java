package com.example.pet.controller.host;

import com.example.pet.entity.Listing;
import com.example.pet.entity.host.Host;
import com.example.pet.service.HostOperationProviderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostOperationProviderService hostOperationProviderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lists of host nodes",
            notes = "Returns list that contains information about host",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Listing<Host> listOfNode() {
        return hostOperationProviderService.hostListing();
    }
}
