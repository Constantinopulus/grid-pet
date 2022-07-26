package com.example.pet.entity.host.sge;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "qhost")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgeHostListing {

    @XmlElement(name = "host")
    public List<SgeHost> sgeHost;
}
