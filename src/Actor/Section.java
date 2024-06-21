package Actor;

public class Section {
    int capacity;
    boolean flag;

    Section(int capacity,boolean flag){
        this.capacity=capacity;
    }

    public void setCapacity(int capacity){
        this.capacity=capacity;
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
