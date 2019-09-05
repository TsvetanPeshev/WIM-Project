package models.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkitemInfoRepository {
    private static final String KIND_OF_WORKITEM_ERROR_MESSAGE = "Kind is not workitem-kind.";

    private static Map<Long, String> workitemData = new LinkedHashMap<>();
    private static Map<String, Long> workitemNameIDData = new LinkedHashMap<>();

    public Map<Long, String> getWorkitemData() {
        return new LinkedHashMap<>(workitemData);
    }

    public static void addInfoOnIDItem(long id, String kindOfWorkitem) {

        if (!kindOfWorkitem.equals("Bug") &&
                !kindOfWorkitem.equals("Story") &&
                !kindOfWorkitem.equals("Feedback")) {
            throw new IllegalArgumentException(KIND_OF_WORKITEM_ERROR_MESSAGE);
        }

        try {

            workitemData.put(id, kindOfWorkitem);

        } catch (NumberFormatException ex) {
            System.out.println("ID to be a long integer value.");
            ex.printStackTrace();

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void addIDTitleInfo(String title, long id) {
        Validator.checkForEmptyValue(title, "Workitem title");
        workitemNameIDData.put(title, id);
    }

    public static String getWorkitemKindInfo(long id) {
        return workitemData.get(id);
    }

    public static Map<Long, String> getWorkItemData() {
        return new LinkedHashMap<>(workitemData);
    }

    public static Map<String, Long> getWorkitemNameIDData() {
        return new LinkedHashMap<>(workitemNameIDData);
    }
}

