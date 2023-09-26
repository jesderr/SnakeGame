import java.util.Random;

public class Food {
    private int x;
    private int y;

    public Food() {
        randomizePosition();
    }

    public void randomizePosition() {
        Random random = new Random();
        x = random.nextInt(20); // Генерируем случайную позицию по x в диапазоне [0, 19]
        y = random.nextInt(15); // Генерируем случайную позицию по y в диапазоне [0, 14]
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}