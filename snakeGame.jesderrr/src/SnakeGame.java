import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private static final int gridSize = 20;
    private int width, height;
    private LinkedList<Point> snake;
    private int snakeX, snakeY;
    private int foodX, foodY;
    private int direction;
    private boolean gameOver;
    private Timer timer;
//    private boolean restartRequested = false;
//    private JButton buttonForStart;
//    private JButton buttonForEnd;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width * gridSize, height * gridSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snake = new LinkedList<>();
        snake.add(new Point(5, 5));  // начальное положение змеи
        snakeX = snake.getFirst().x;
        snakeY = snake.getFirst().y;

        spawnFood();  // случайное размещение еды
        direction = 0;  // начальное направление движения

        timer = new Timer(150, this);
        timer.start();

//        this.configureButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            checkCollision();
            repaint();
        }
    }

//    public void configureButton(){
//        this.buttonForStart = new JButton("Start");
//        this.buttonForStart.setBounds(10, 10, 70, 30);
//        this.buttonForStart.addActionListener( e -> {
//            restartRequested = true;
//        });
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * gridSize, p.y * gridSize, gridSize, gridSize);
        }

        g.setColor(Color.RED);
        g.fillRect(foodX * gridSize, foodY * gridSize, gridSize, gridSize);

        g.setColor(Color.GRAY);
        for (int i = 0; i < width; i++) {
            g.drawLine(i * gridSize, 0, i * gridSize, getHeight());
        }
        for (int i = 0; i < height; i++) {
            g.drawLine(0, i * gridSize, getWidth(), i * gridSize);
        }

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", width * gridSize / 4, height * gridSize / 3);

//            g.drawString("Do you want to start?", width * gridSize / 7, height * gridSize / 2);
//            if (restartRequested) {
//                resetGame();
//                this.restartRequested = false;
//            }
        }
    }

//    private void resetGame() {
//        snake.clear();
//        snake.add(new Point(5, 5));
//        foodX = 5;
//        foodY = 5;
//        direction = 0;
//        gameOver = false;
//    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_DOWN;
        }
//        else if (key == KeyEvent.VK_R) {
//            // Пользователь нажал клавишу "R" для перезапуска игры
//            restartRequested = true;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void move() {
        // Получаем координаты новой головы змеи в зависимости от текущего направления
        switch (direction) {
            case KeyEvent.VK_UP -> snakeY--;
            case KeyEvent.VK_DOWN -> snakeY++;
            case KeyEvent.VK_LEFT -> snakeX--;
            case KeyEvent.VK_RIGHT -> snakeX++;
        }

        // Создаем новую голову
        Point newHead = new Point(snakeX, snakeY);

        // Добавляем новую голову в начало змеи
        snake.addFirst(newHead);

        // Проверяем, съела ли змея еду
        if (snakeX == foodX && snakeY == foodY) {
            spawnFood();  // Генерируем новую еду
        } else {
            // Если не съела, убираем последний сегмент хвоста
            snake.removeLast();
        }

        // Проверяем, не столкнулась ли змея сама с собой
        checkSelfCollision();
    }

    private void checkSelfCollision() {
        // Начинаем проверку с индекса 1 (первый элемент - голова, ее не считаем)
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(snake.getFirst())) {
                gameOver = true;
                break;
            }
        }
    }

    private void checkCollision() {
        if (snakeX < 0 || snakeX >= width || snakeY < 0 || snakeY >= height) {
            gameOver = true;  // Змея столкнулась с границами поля
        }
    }

    private void spawnFood() {
        Random rand = new Random();
        foodX = rand.nextInt(width);
        foodY = rand.nextInt(height);

        // Проверяем, чтобы новая еда не появилась внутри змеи
        while (snake.contains(new Point(foodX, foodY))) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);
        }
    }

}
