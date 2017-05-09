package nl.markv;

public class Result {
    private Group PandQ;
    private Group minQ;

    public Result(Group PandQ, Group minQ) {
        this.PandQ = PandQ;
        this.minQ = minQ;
    }

    public Group getPandQ() {
        return PandQ;
    }

    public Group getMinQ() {
        return minQ;
    }

    public void disp() {
        System.out.println("~ P+Q ~");
        this.PandQ.disp();
        System.out.println("~ -Q ~");
        this.minQ.disp();
    }
}
