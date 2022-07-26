package com.example.pet.entity.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Host {

    private String hostname;

    private String typeOfArchitect;

    private Integer numOfProcessors;

    private Integer numOfSocket;

    private Integer numOfCore;

    private Integer numOfThread;

    private Double load;

    private Long memTotal;

    private Long memUsed;

    private Double totalSwapSpace;

    private Double usedSwapSpace;
}
