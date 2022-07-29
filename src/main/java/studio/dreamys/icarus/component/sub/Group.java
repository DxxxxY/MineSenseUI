package studio.dreamys.icarus.component.sub;

import studio.dreamys.icarus.component.Component;
import studio.dreamys.icarus.component.Page;
import studio.dreamys.icarus.component.Window;
import studio.dreamys.icarus.util.RenderUtils;

import java.util.ArrayList;

public class Group extends Component {
    private Window window;
    private Page page;
    private ArrayList<Component> children = new ArrayList<>();

    private double x;
    private double y;
    private double width = 150;
    private double height = 10;

    private String label;

    //relative to window, aka x,y passed in constructor
    private double relativeX;
    private double relativeY;

    public Group(String label) {
        this.label = label;
    }

    public Group(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public Group(double x, double y, double width, double height, String label) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public void render(int mouseX, int mouseY) {
        //update position
        x = window.x + relativeX;
        y = window.y + relativeY;

        //the box itself
        RenderUtils.drawGroupWithString(width, height + 10, x, y, label);
    }

    public void setWindow(Window window) {
        this.window = window;
        relativeX = x;
        relativeY = y;
    }

    public Group addChild(Component child) {
        child.setGroup(this);
//        window.addChild(child);
        child.setWindow(window);
        children.add(child);
        child.setX(x + 12.5);
        child.setY(y + height + (children.size() == 1 ? 0 : child.getClearance()));
        height += child.getHeight() + (children.size() == 1 ? 0 : child.getClearance());
        return this;
    }

    public ArrayList<Component> getChildren() {
        return children;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}