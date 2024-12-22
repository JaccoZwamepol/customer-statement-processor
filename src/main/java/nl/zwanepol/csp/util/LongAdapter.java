package nl.zwanepol.csp.util;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LongAdapter extends XmlAdapter<String, Long> {
    @Override
    public Long unmarshal(String v) {
        return Long.parseLong(v);
    }

    @Override
    public String marshal(Long v) {
        return v.toString();
    }
}
