package Lesson2.illness;

public class Trauma implements Disease{
    @Override
    public void problem() {
        System.out.println("Мне кажется я сломался!");
    }

    @Override
    public String toString() {
        return "trauma";
    }
}
