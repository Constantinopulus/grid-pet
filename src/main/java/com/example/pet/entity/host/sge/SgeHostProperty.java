package com.example.pet.entity.host.sge;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
@XmlAccessorType(XmlAccessType.FIELD)
public enum SgeHostProperty {

    @XmlEnumValue("arch_string")
    TYPE_OF_ARCHITECT,

    @XmlEnumValue("num_proc")
    NUM_OF_PROCESSORS,

    @XmlEnumValue("m_socket")
    NUM_OF_SOCKET,

    @XmlEnumValue("m_core")
    NUM_OF_CORE,

    @XmlEnumValue("m_thread")
    NUM_OF_THREAD,

    @XmlEnumValue("load_avg")
    LOAD,

    @XmlEnumValue("mem_total")
    MEM_TOTAL,

    @XmlEnumValue("mem_used")
    MEM_USED,

    @XmlEnumValue("swap_total")
    TOTAL_SWAP_SPACE,

    @XmlEnumValue("swap_used")
    USED_SWAP_SPACE
}
