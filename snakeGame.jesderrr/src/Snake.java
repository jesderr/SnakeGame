import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> body;
    private boolean grow;
    private int direction;

    public Snake(){
        this.body = new ArrayList<>();
        this.body.add(new Point(5,5));
        this.grow = false;
        this.direction = KeyEvent.VK_RIGHT;
    }

    public Point getHead() {
        return this.body.get(0);
    }

    public void move() {
        Point newHead = (Point) getHead().clone();

        switch (this.direction) {
            case KeyEvent.VK_UP -> newHead.translate(0, -1);
            case KeyEvent.VK_DOWN -> newHead.translate(0, 1);
            case KeyEvent.VK_LEFT -> newHead.translate(-1, 0);
            case KeyEvent.VK_RIGHT -> newHead.translate(1, 0);
        }

        this.body.add(0, newHead);

        if (!grow) {
            this.body.remove(body.size() - 1);
        } else {
            this.grow = false;
        }
    }

    public void grow() {
        this.grow = true;
    }

    public boolean checkCollision(Point point) {
        return this.body.contains(point);
    }

    public boolean checkSelfCollision() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(head)) {
                return true;
            }
        }
        return false;
    }

    public void setDirection(int newDirection) {
        if (newDirection == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) {
            this.direction = KeyEvent.VK_UP;
        } else if (newDirection == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) {
            this.direction = KeyEvent.VK_DOWN;
        } else if (newDirection == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) {
            this.direction = KeyEvent.VK_LEFT;
        } else if (newDirection == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) {
            this.direction = KeyEvent.VK_RIGHT;
        }
    }

    public List<Point> getBody() {
        return this.body;
    }


}
