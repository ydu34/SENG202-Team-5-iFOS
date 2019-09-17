package seng202.group5.adapters;

import org.joda.money.Money;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyMapAdapter extends XmlAdapter<AdaptedMap, Map<Money, Integer>>{

    @Override
    public Map<Money, Integer> unmarshal(AdaptedMap v) throws Exception {
        List<AdaptedEntry> adaptedEntries = v.entries;
        Map<Money, Integer> map = new HashMap<Money, Integer>(adaptedEntries.size());
        for (AdaptedEntry adaptedEntry : adaptedEntries) {
            map.put(Money.parse(adaptedEntry.key), adaptedEntry.value);
        }
        return map;
    }

    @Override
    public AdaptedMap marshal(Map<Money, Integer> v) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Map.Entry<Money, Integer> entry : v.entrySet()) {
            AdaptedEntry adaptedEntry = new AdaptedEntry();
            adaptedEntry.key = entry.getKey().toString();
            adaptedEntry.value = entry.getValue();
            adaptedMap.entries.add(adaptedEntry);
        }
        return adaptedMap;
    }


}
