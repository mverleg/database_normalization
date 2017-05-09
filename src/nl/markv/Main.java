package nl.markv;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Main {

    public static void main(String[] args) {
        Group remainder = TestDataGen.generatePatientMedicineDosage();
        List<Group> tables = new ArrayList<>();
        remainder.disp();
        Result res = new Result(remainder, null);
        try {
            while (true) {
                res = Main.normOne(res.getPandQ());
                tables.add(res.getMinQ());
                System.out.println("1~~~~~~~~~~~~~~~~~~~~");
                res.disp();
            }
        } catch (IllegalStateException ignore) {}
        try {
            while (true) {
                res = Main.normTwo(res.getPandQ());
                tables.add(res.getMinQ());
                System.out.println("2~~~~~~~~~~~~~~~~~~~~");
                res.disp();
            }
        } catch (IllegalStateException ignore) {}
        try {
            while (true) {
                res = Main.normThree(res.getPandQ());
                System.out.println("3~~~~~~~~~~~~~~~~~~~~");
                res.disp();
            }
        } catch (IllegalStateException ignore) {}
        tables.add(res.getPandQ());
        for (Group table : tables) {
            System.out.println("********************");
            table.disp();
        }
//        while (res != null) {
//            res = Main.normOne(remainder);
//            if (res != null) {
//                remainder = res.getPandQ();
//                System.out.println("1~~~~~~~~~~~~~~~~~~~~");
//                res.disp();
//            }
//        }
//        while (res != null) {
//            res = Main.normTwo(remainder);
//            if (res != null) {
//                System.out.println("2~~~~~~~~~~~~~~~~~~~~");
//                res.disp();
//            }
//        }
//        while (res != null) {
//            res = Main.normThree(remainder);
//            if (res != null) {
//                System.out.println("3~~~~~~~~~~~~~~~~~~~~");
//                res.disp();
//            }
//        }
    }

    public static Result normOne(Group scopeGroup) {
        Group PandQ = new Group();
        Group minQ = new Group();
        for (Element elem : scopeGroup.getMembers()) {
            if (elem instanceof Group) {
                Group expandGroup = (Group) elem;
                for (Element outer : scopeGroup.getMembers()) {
                    if (outer instanceof Value) {
                        Value val = (Value) outer;
                        /* Add all outer IDs to P+Q */
                        if (val.getIsId()) {
                            PandQ.add(val);
                        }
                    }
                    /* Add all outer elements to -Q */
                    if (outer != expandGroup) {
                        minQ.add(outer);
                    }
                }
                /* Add the content of the group to P+Q */
                for (Element other : expandGroup.getMembers()) {
                    PandQ.add(other);
                }
                return new Result(PandQ, minQ);
            }
        }
        throw new IllegalStateException();
    }

    public static Result normTwo(Group scopeGroup) {
         Group PandQ = new Group();
        Group minQ = new Group();
        for (Element refElem : scopeGroup.getMembers()) {
            if (refElem instanceof Value) {
                Value refParent = refElem.getDependOn();
                if (refParent != null && refParent.getIsId()) {
                    PandQ.add(refParent);
                    /* We found an arrow to an ID, find all (in)direct descendants */
                    for (Element elem : scopeGroup.getMembers()) {
                        if (elem instanceof Value) {
                            Value parent = elem.getDependOn();
                            parentCheck: {
                                while (parent != null) {
                                    if (parent == refParent) {
                                        /* This IS (in)directly dependent on refParent */
                                        PandQ.add(elem);
                                        break parentCheck;
                                    }
                                    parent = parent.getDependOn();
                                }
                                /* This is NOT (in)directly dependent on refParent */
                                minQ.add(elem);
                            }
                        }
                    }
                    for (Element elem : scopeGroup.getMembers()) {
                        if (elem instanceof Value) {
                            if (elem.getDependOn() == refParent) {
                                ((Value) elem).removeDependsOn();
                            }
                        }
                    }

                    if (minQ.getMembers().size() == 1) {
                        System.out.println("NEED TO REMOVE ARROWS TO " + refParent);
                    }
                    return new Result(PandQ, minQ);
                }
            }
        }
        throw new IllegalStateException();
    }

    /*
    Just checks that the structure is already level 3 normalized, throws exception otherwise.
     */
    public static Result normThree(Group scopeGroup) throws NotImplementedException {
        for (Element elem : scopeGroup.getMembers()) {
            if (elem instanceof Value) {
                Value parent = ((Value)elem).getDependOn();
                if (parent != null && ! parent.getIsId()) {
                    /* Dependencies on non-IDs are not implemented */
                    throw new NotImplementedException();
                }
            }
            if (elem instanceof Group) {
                normThree((Group)elem);
            }
        }
        throw new IllegalStateException();
    }
}
