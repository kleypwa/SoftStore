package me.kley.soft_store.dto;


import me.kley.soft_store.models.Toy;

public class ToyWithCountDTO {
    private Toy toy;
    private int count;

    public ToyWithCountDTO(Toy toy, int count) {
        this.toy = toy;
        this.count = count;
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
