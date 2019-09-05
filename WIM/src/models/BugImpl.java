package models;

import models.common.Validator;
import models.contracts.Bug;
import models.enums.PriorityType;
import models.enums.SeverityType;
import models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends BugStoryBaseImpl implements Bug{
    private static final String STEPS_TO_REPRODUCE_ERROR_MESSSAGE = "Steps' count cannot be zero.";
    private static final String SEVERITY_HEADER = "Severity";

    private SeverityType severity;
    private List<String> stepsToReproduce;

    public BugImpl(String title, String description, PriorityType priority, SeverityType severity, StatusType status) {
        super(title, description, status, priority);
        stepsToReproduce = new ArrayList<>();
        this.severity = severity;

    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
    }

    public void addStepsToReproduce(List<String> inputStepsToReproduce) {

        if (stepsToReproduce.isEmpty()) {
            throw new IllegalArgumentException(STEPS_TO_REPRODUCE_ERROR_MESSSAGE);
        }

        for (String index :
                inputStepsToReproduce) {
            stepsToReproduce.add(inputStepsToReproduce.get(Integer.parseInt(index)));
        }
    }

    public SeverityType getSeverity() {
        return severity;
    }

    @Override
    public void changeSeverity(SeverityType severity) {
        Validator.checkForEmptyValue(severity, SEVERITY_HEADER);
        this.severity = severity;
    }

    @Override
    public String toString(long id){
        StringBuilder result = new StringBuilder(super.toString(id));

        result.append(String.format("Severity: %S%n", this.getSeverity().toString()));

        return result.toString();
    }
}
