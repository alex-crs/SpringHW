package Lesson2.illness;

public class Blindness implements Disease {
    @Override
    public void problem() {
        System.out.println("Ничего не вижу!");
    }

    @Override
    public String toString() {
        return "blindness";
    }
}
