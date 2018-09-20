package com.zking.enetity;

import com.zking.pojo.Menu;

import java.util.List;

public class MenuBody {
    private Menu bigMenu;
    private List<Menu> smallMenu;

    public Menu getBigMenu() {
        return bigMenu;
    }

    public void setBigMenu(Menu bigMenu) {
        this.bigMenu = bigMenu;
    }

    public List<Menu> getSmallMenu() {
        return smallMenu;
    }

    public void setSmallMenu(List<Menu> smallMenu) {
        this.smallMenu = smallMenu;
    }
}
