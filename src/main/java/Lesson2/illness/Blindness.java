package Lesson2.illness;

public class Blindness implements Disease {
    @Override
    public void problem() {
        System.out.println("Пациент: Ничего не вижу!");
    }

    @Override
    public String toString() {
        return "blindness";
    }
}
