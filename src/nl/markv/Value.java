package nl.markv;

public class Value extends Element {
    private String name;
    private Value dependOn = null;
    private Value originalDependOn = null;
    private boolean isId = false;
    private boolean id;

    public Value(String name) {
        this.name = name;
    }

    public Value(String name, boolean isId) {
        this(name);
        this.isId = isId;
    }

    public Value(String name, Value dependOn) {
        this(name);
        this.dependOn = dependOn;
        this.originalDependOn = dependOn;
    }

    public Value(String name, Value dependOn, boolean isId) {
        this(name, isId);
        this.dependOn = dependOn;
        this.originalDependOn = dependOn;
    }

    @Override
    public void disp(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.printf("%s%s%s\n",
                this.isId ? "*" : " ",
                this.name,
                this.originalDependOn != null ? " (" + this.originalDependOn + ")" : ""
        );
    }

    public String toString() {
        return this.name;
    }

    public boolean getIsId() {
        return isId;
    }

    @Override
    public Value getDependOn() {
        return dependOn;
    }

    public void removeDependsOn() {
        this.dependOn = null;
    }
}
