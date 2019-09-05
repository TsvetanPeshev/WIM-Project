package models.common;

import models.enums.StatusType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class WorkiitemStatusValidator {
    private static final String BUG_STATUS_ERROR_MESSAGE = "Bug status can be Active or Fixed";
    private static final String STORY_STATUS_ERROR_MESSAGE = "Story status can be Notdone, Inprogress or Done";
    private static final String FEEDBACK_STATUS_ERROR_MESSAGE = "Feedback status can be New, Scheduled or Unscheduled";

    public static void workitemStatusValidation(StatusType status, String workitemTitle) {

        Map<Long, String> workitemData = new LinkedHashMap<>(WorkitemInfoRepository.getWorkItemData());
        Map<String, Long> workitemTitles = new LinkedHashMap<>(WorkitemInfoRepository.getWorkitemNameIDData());

        try {
            if (workitemTitles.containsKey(workitemTitle) && !workitemData.get(workitemTitles.get(workitemTitle)).isEmpty()) {
                if (workitemData.get(workitemTitles.get(workitemTitle)).compareTo("Bug") == 0) {
                    if (status.equals(StatusType.ACTIVE) || status.equals(StatusType.FIXED)) {
                        return;
                    } else {
                        throw new IllegalArgumentException(BUG_STATUS_ERROR_MESSAGE);
                    }
                } else if (workitemData.get(workitemTitles.get(workitemTitle)).compareTo("Story") == 0) {
                    if (status.equals(StatusType.NOTDONE) ||
                            status.equals(StatusType.INPROGRESS) ||
                            status.equals(StatusType.DONE)) {
                        return;
                    } else {
                        throw new IllegalArgumentException(
                                STORY_STATUS_ERROR_MESSAGE);
                    }
                } else if (workitemData.get(workitemTitles.get(workitemTitle)).compareTo("Feedback") == 0) {
                    if (status.equals(StatusType.NEW) ||
                            status.equals(StatusType.SCHEDULED) ||
                            status.equals(StatusType.UNSCHEDULED)) {
                        return;
                    } else {
                        throw new IllegalArgumentException(FEEDBACK_STATUS_ERROR_MESSAGE);
                    }
                }
            } else {
                return;
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
    }
}