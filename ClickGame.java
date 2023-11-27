import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class ClickGame extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int RECTANGLE_SIZE = 90;
    private static final int MOVE_INTERVAL = 2000; // en millisecondes

    private JPanel gamePanel;
    private JLabel scoreLabel;
    private Timer timer;
    private int score;

    public ClickGame() {
        setTitle("Click Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scoreLabel = new JLabel("Score: 0");
        gamePanel = new JPanel();
        gamePanel.setLayout(null);

        add(scoreLabel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkClick(e.getPoint());
            }
        });

        timer = new Timer(MOVE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveRectangle();
            }
        });

        startGame();
    }

    private void startGame() {
        score = 0;
        scoreLabel.setText("Score: 0");
        spawnRectangle();
        timer.start();
    }

    private void spawnRectangle() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH - RECTANGLE_SIZE);
        int y = rand.nextInt(HEIGHT - RECTANGLE_SIZE);

        gamePanel.removeAll();

        JButton rectangleButton = new JButton();
        rectangleButton.setBounds(x, y, RECTANGLE_SIZE, RECTANGLE_SIZE);
        rectangleButton.setBackground(Color.green);
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseScore();
                spawnRectangle();
            }
        });

        gamePanel.add(rectangleButton);
        gamePanel.repaint();
    }

    private void increaseScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private void checkClick(Point clickPoint) {
        Component[] components = gamePanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton && component.getBounds().contains(clickPoint)) {
                increaseScore();
                spawnRectangle();
                return;
            }
        }
    }

    private void moveRectangle() {
        spawnRectangle();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClickGame().setVisible(true);
            }
        });
    }
}
