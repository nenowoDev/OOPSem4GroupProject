package Actor;

public abstract class Person {
    protected String id;
    protected String name;

    // Constructor, getters, setters, etc.
    public Person() {}

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}