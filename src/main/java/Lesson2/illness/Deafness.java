package Lesson2.illness;

public class Deafness implements Disease {
    @Override
    public void problem() {
        System.out.println("Ничего не слышу!");
    }

    @Override
    public String toString() {
        return "deafness";
    }
}
