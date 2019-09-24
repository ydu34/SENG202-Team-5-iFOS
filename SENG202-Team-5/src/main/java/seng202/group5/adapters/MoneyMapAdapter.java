package seng202.group5.adapters;

import org.joda.money.Money;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.List;

/**
 * A class created so that Jaxb can deal with Money Objects
 */
public final class MoneyMapAdapter extends XmlAdapter<AdaptedMoneyMap, HashMap<Money, Integer>>{

    /**
     * @param v Takes in an AdaptedMoneyMap that contains a list of AdaptedMoneyEntry
     * @return HashMap containing money objects and the quantity of each money
     */
    @Override
    public HashMap<Money, Integer> unmarshal(AdaptedMoneyMap v){
        List<AdaptedMoneyEntry> adaptedEntries = v.entries;
        HashMap<Money, Integer> map = new HashMap<Money, Integer>(adaptedEntries.size());
        for (AdaptedMoneyEntry adaptedMoneyEntry : adaptedEntries) {
            map.put(Money.parse(adaptedMoneyEntry.key), adaptedMoneyEntry.value);
        }
        return map;
    }

    /**
     * @param v Takes in an HashMap containing money objects and the quantity of each money
     * @return AdaptedMoneyMap that contains a list of AdaptedMoneyEntry
     */
    @Override
    public AdaptedMoneyMap marshal(HashMap<Money, Integer> v){
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
