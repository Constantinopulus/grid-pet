package com.example.pet.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "hostvalue")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgeHostValue {
    @XmlAttribute(name = "name")
    private SgeHostProperty name;
    @XmlValue
    private String value;
}
