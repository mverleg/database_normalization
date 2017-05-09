package nl.markv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group extends Element {
    private List<Element> members = new ArrayList<>();

    public Group(Element ... members) {
        this.members.addAll(Arrays.asList(members));
    }

    public void add(Element member) {
        this.members.add(member);
    }

    @Override
    public void disp(int indent) {
        for (Element member : this.members) {
            member.disp(indent + 1);
        }
    }

    @Override
    public Value getDependOn() {
        return null;
    }

    public List<Element> getMembers() {
        return members;
    }
}
