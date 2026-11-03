package windows;

import logik.Player;
import logik.Random_example_generation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game extends JFrame {
    private Player currentPlayer; // Поточний гравець
    private int complexity = 1;
    private Random_example_generation currentExample;

    private GamePanel gamePanel;
    private JLabel lblComplexityInfo;
    private JLabel lblTimer;
    private JLabel lblExample;
    private JTextField txtAnswer;
    private JButton btnCheck;
    private JLabel lblScore;

    private int score = 0;
    private Timer gameTimer;
    private int timeLeft = 60;

    private static final String DATA_FILE = "players_data.dat";

    // Змінили конструктор, щоб він приймав гравця
    public Game(Player player) {
        super("Швидка Арифметика — Гра");
        this.currentPlayer = player;

        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        askForComplexity();

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        setContentPane(gamePanel);

        // 1. Інформація про складність
        lblComplexityInfo = new JLabel("Рівень складності: " + complexity, SwingConstants.CENTER);
        lblComplexityInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblComplexityInfo.setForeground(new Color(255, 200, 0));
        lblComplexityInfo.setBounds(50, 30, 450, 25);
        gamePanel.add(lblComplexityInfo);

        // 2. Таймер
        lblTimer = new JLabel("Час: " + timeLeft + " с", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 16));
        lblTimer.setForeground(new Color(255, 80, 80));
        lblTimer.setBounds(50, 60, 450, 25);
        gamePanel.add(lblTimer);

        // 3. Напис із прикладом
        lblExample = new JLabel("", SwingConstants.CENTER);
        lblExample.setFont(new Font("Arial", Font.BOLD, 28));
        lblExample.setForeground(Color.WHITE);
        lblExample.setBounds(50, 105, 450, 50);
        gamePanel.add(lblExample);

        // 4. Поле для введення відповіді
        txtAnswer = new JTextField();
        txtAnswer.setFont(new Font("Arial", Font.BOLD, 24));
        txtAnswer.setHorizontalAlignment(JTextField.CENTER);
        txtAnswer.setBounds(200, 175, 150, 45);
        gamePanel.add(txtAnswer);

        // 5. Кнопка перевірки
        btnCheck = new JButton("Перевірити");
        btnCheck.setFont(new Font("Arial", Font.BOLD, 16));
        btnCheck.setBounds(200, 235, 150, 40);
        gamePanel.add(btnCheck);

        // 6. Лічильник балів
        lblScore = new JLabel("Правильних відповідей: 0", SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 14));
        lblScore.setForeground(new Color(200, 200, 200));
        lblScore.setBounds(50, 305, 450, 30);
        gamePanel.add(lblScore);

        generateNewTask();

        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        txtAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                lblTimer.setText("Час: " + timeLeft + " с");

                if (timeLeft <= 0) {
                    gameTimer.stop();
                    endGame();
                }
            }
        });
        gameTimer.start();
    }

    private void askForComplexity() {
        String[] options = {"Рівень 1 (1-10)", "Рівень 2 (1-25)", "Рівень 3 (1-50)"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Оберіть рівень складності гри:",
                "Складність",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        complexity = (choice >= 0) ? choice + 1 : 1;
    }

    private void generateNewTask() {
        currentExample = new Random_example_generation(complexity);
        String text = currentExample.getNamber_1() + " " +
                currentExample.getZnak() + " " +
                currentExample.getNamber_2() + " = ?";
        lblExample.setText(text);
        txtAnswer.setText("");
        txtAnswer.requestFocus();
    }

    private void checkAnswer() {
        if (timeLeft <= 0) return;

        try {
            int playerAnswer = Integer.parseInt(txtAnswer.getText().trim());

            if (playerAnswer == currentExample.getExample()) {
                score++;
                lblScore.setText("Правильних відповідей: " + score);
                generateNewTask();
            } else {
                txtAnswer.setText("");
            }
        } catch (NumberFormatException ex) {
            txtAnswer.setText("");
        }
    }

    private void endGame() {
        txtAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        lblExample.setText("Час вийшов!");

        // Оновлюємо результат у файлі гравців
        updatePlayerScore();

        JOptionPane.showMessageDialog(
                this,
                "Гра закінчилась!\nВаш результат: " + score + " правильних відповідей.",
                "Результати",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    // Метод для оновлення рекорду у файлі
    private void updatePlayerScore() {
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equals(currentPlayer.getUsername())) {
                p.updateBestScore(score); // Оновлюємо рекорд
                currentPlayer.updateBestScore(score);
                break;
            }
        }
        savePlayers(players);
    }

    private List<Player> loadPlayers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Player>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void savePlayers(List<Player> players) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics gr) {
            super.paintComponent(gr);
            Graphics2D g2d = (Graphics2D) gr;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(30, 30, 40));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(new Color(250, 0, 0));
            g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 50, 20, 20);
        }
    }
}