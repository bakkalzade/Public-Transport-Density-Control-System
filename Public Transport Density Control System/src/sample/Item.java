package sample;

public class Item {//extended by passenger class and bus class

    private final int id;

    public Item() {

        this.id=Counter.Count.getAllCount();
    }

    private int getId(){

        return id;
    }

}
