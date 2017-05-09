package nl.markv;

public abstract class Element {

    public void disp() {
        this.disp(0);
    }

    public abstract void disp(int indent);

    public abstract Value getDependOn();
}
