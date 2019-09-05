package models.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;


public class EditorOfID {

    private long id;
    private Random randomGenerator;
    private Map<Long, String> ids;

    public EditorOfID() {
        randomGenerator = new Random();
        id = randomGenerator.nextInt();
        ids = new LinkedHashMap<>();
    }

    public long generateId(String itemName) {
        id = randomGenerator.nextInt(100);
        while (ids.containsKey(id)) {
            id = randomGenerator.nextInt(100);
        }
        ids.put(id, itemName);

        return id;
    }


}
