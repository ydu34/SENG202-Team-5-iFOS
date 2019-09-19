package seng202.group5.adapters;

import org.joda.money.Money;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.List;

public final class MoneyMapAdapter extends XmlAdapter<AdaptedMoneyMap, HashMap<Money, Integer>>{

    @Override
    public HashMap<Money, Integer> unmarshal(AdaptedMoneyMap v) throws Exception {
        List<AdaptedMoneyEntry> adaptedEntries = v.entries;
        HashMap<Money, Integer> map = new HashMap<Money, Integer>(adaptedEntries.size());
        for (AdaptedMoneyEntry adaptedMoneyEntry : adaptedEntries) {
            map.put(Money.parse(adaptedMoneyEntry.key), adaptedMoneyEntry.value);
        }
        return map;
    }

    @Override
    public AdaptedMoneyMap marshal(HashMap<Money, Integer> v) throws Exception {
        AdaptedMoneyMap adaptedMoneyMap = new AdaptedMoneyMap();
        for(HashMap.Entry<Money, Integer> entry : v.entrySet()) {
            AdaptedMoneyEntry adaptedMoneyEntry = new AdaptedMoneyEntry();
            adaptedMoneyEntry.key = entry.getKey().toString();
            adaptedMoneyEntry.value = entry.getValue();
            adaptedMoneyMap.entries.add(adaptedMoneyEntry);
        }
        return adaptedMoneyMap;
    }


}
