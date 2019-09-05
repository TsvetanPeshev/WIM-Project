package models;
import models.common.Validator;
import models.contracts.MemberBoardBase;
import models.contracts.Workitem;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemberBoardBaseImpl extends ActivityBase implements MemberBoardBase {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 15;
    private static final String NAME_LENGTH_ERROR_MESSAGE = "Name length can not be less than %d and more than %d";
    private static final String NAME_HEADER = "Name";
    private static final String WORKITEM_HEADER = "Workitem";

    private String name;
    private Map<Long, Workitem> workitems;

    public MemberBoardBaseImpl(String name){
        setName(name);
        workitems = new LinkedHashMap<>();
    }

    public void addWorkitem( long id, Workitem workitem){
        Validator.checkForEmptyValue(workitem, WORKITEM_HEADER);
        workitems.put(id, workitem);
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        Validator.checkForEmptyValue(name, NAME_HEADER);
        Validator.validateLength(
                name.length(), getNameMinLength(), getNameMaxLength(), NAME_LENGTH_ERROR_MESSAGE);
        this.name = name;
    }

    protected int getNameMinLength(){
        return MIN_LENGTH;
    }

    protected int getNameMaxLength(){
        return MAX_LENGTH;
    }

    @Override
    public Workitem getWorkitem(Long id){
        return workitems.get(id);
    }
}
