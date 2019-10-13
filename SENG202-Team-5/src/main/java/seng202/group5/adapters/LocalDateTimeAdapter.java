package seng202.group5.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * An adapter for jaxb to be able to marshal and unmarshal LocalDateTime objects.
 *
 * @author Yu Duan
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    /**
     * @param v the LocalDateTime string
     * @return the LocalDateTime object
     * @throws Exception if the unmarshalling fails
     */
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    /**
     * @param v the LocalDateTime object
     * @return the LocalDateTime string
     * @throws Exception if the marshalling fails
     */
    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}