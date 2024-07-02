package flowpro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;

public class FlowPro extends JFrame implements KeyListener {
    private Thread thread;
    private static Random random = new Random();
    private static final int DIR_STEP = 2;
    private boolean isSpace = false;
    private int x;
    private int y;
    private int height;
    private int width;
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;

    public FlowPro(int width, int height) {
        this.setSize(width, height);
        this.height = height;
        this.width = width;
        this.x = width / 2;
        this.y = height / 2;
        this.addKeyListener(this);
        this.thread = new MoveThread(this);
        this.thread.start();
    }

    public static void main(String... string) {
        JFrame frame = new FlowPro(1550, 830);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            this.isLeft = true;
        }

        if (e.getKeyCode() == 39) {
            this.isRight = true;
        }

        if (e.getKeyCode() == 38) {
            this.isUp = true;
        }

        if (e.getKeyCode() == 40) {
            this.isDown = true;
        }

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            this.isLeft = false;
        }

        if (e.getKeyCode() == 39) {
            this.isRight = false;
        }

        if (e.getKeyCode() == 38) {
            this.isUp = false;
        }

        if (e.getKeyCode() == 40) {
            this.isDown = false;
        }

    }

    public void keyTyped(KeyEvent arg0) {
    }

    public void paint(Graphics gr) {
        Graphics2D g2d = (Graphics2D)gr;
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        g2d.setColor(new Color(r, g, b));
        g2d.setStroke(new BasicStroke(4.0F));
        if (this.isLeft) {
            g2d.drawLine(this.x + 2, this.y, this.x, this.y);
        } else if (this.isRight) {
            g2d.drawLine(this.x - 2, this.y, this.x, this.y);
        } else if (this.isUp) {
            g2d.drawLine(this.x, this.y + 2, this.x, this.y);
        } else if (this.isDown) {
            g2d.drawLine(this.x, this.y - 2, this.x, this.y);
        }

    }

    public void animate() {
        if (this.isLeft && this.x - 2 > 5) {
            this.x -= 2;
        }

        if (this.isRight && this.x + 2 < this.width - 5) {
            this.x += 2;
        }

        if (this.isUp && this.y - 2 > 30) {
            this.y -= 2;
        }

        if (this.isDown && this.y + 2 < this.height) {
            this.y += 2;
        }

        this.repaint();
    }

    private class MoveThread extends Thread {
        FlowPro runKeyboard;

        public MoveThread(FlowPro runKeyboard) {
            super("MoveThread");
            this.runKeyboard = runKeyboard;
        }

        public void run() {
            while(true) {
                this.runKeyboard.animate();

                try {
                    Thread.sleep(20L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
        }
    }
}