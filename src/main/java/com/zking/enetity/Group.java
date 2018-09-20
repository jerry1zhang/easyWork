package com.zking.enetity;

import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;

/**
 * Group
 *
 * @anthor zhanghy9
 * @date 2018-05-06 10:44
 */
public class Group {
    private String id;
    private String name;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 转换为activiti中group
     * @return
     */
    public org.activiti.engine.identity.Group getActGroup(){
        org.activiti.engine.identity.Group result = new GroupEntityImpl();
        result.setId(this.getId());
        result.setName(this.getName());
        result.setType(this.getType());
        return result;
    }
}
