package com.example.pet.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "host")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgeHost {

    @XmlAttribute(name = "name")
    private String hostname;

    @XmlElement(name = "hostvalue")
    private List<SgeHostValue> hostAttributes;

}
