package com.example.pet.provider.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;

public final class JaxbUtils {

    private JaxbUtils() {
    }

    public static <T> T unmarshall(final String xml, final Class<T> clazz) {

        try {
            final JAXBContext context = JAXBContext.newInstance(clazz);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            return  (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
