package windows;

import logik.Player;
import logik.Random_example_generation;
import logik.SoundPlayer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game extends JPanel {
    private Menu parentWindow;
    private Player currentPlayer;
    private int complexity = 1;
    private Random_example_generation currentExample;
    private SoundPlayer soundPlayer;

    private JLabel lblComplexityInfo;
    private JLabel lblTimer;
    private JLabel lblExample;
    private JTextField txtAnswer;
    private JButton btnCheck;
    private JLabel lblScore;
    private JButton btnBack;

    private int score = 0;
    private Timer gameTimer;
    private int timeLeft = 60;

    private static final String DATA_FILE = "players_data.dat";
    private static final String BG_SOUND = "Sound/bg_music.wav";
    private static final String CORRECT_SOUND = "Sound/correct.wav";
    private static final String WRONG_SOUND = "Sound/wrong.wav";

    public Game(Menu parentWindow, Player player) {
        this.parentWindow = parentWindow;
        this.currentPlayer = player;
        this.soundPlayer = new SoundPlayer();

        setLayout(null);
        setBackground(new Color(30, 30, 40));

        askForComplexity();

        // Запускаємо фонову музику гри
        soundPlayer.startBackgroundMusic(BG_SOUND);

        // 1. Інформація про складність
        lblComplexityInfo = new JLabel("Рівень складності: " + complexity, SwingConstants.CENTER);
        lblComplexityInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblComplexityInfo.setForeground(new Color(255, 200, 0));
        lblComplexityInfo.setBounds(0, 35, 735, 25);
        add(lblComplexityInfo);

        // 2. Таймер
        lblTimer = new JLabel("Час: " + timeLeft + " с", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 16));
        lblTimer.setForeground(new Color(255, 80, 80));
        lblTimer.setBounds(0, 65, 735, 25);
        add(lblTimer);

        // 3. Напис із прикладом
        lblExample = new JLabel("", SwingConstants.CENTER);
        lblExample.setFont(new Font("Arial", Font.BOLD, 28));
        lblExample.setForeground(Color.WHITE);
        lblExample.setBounds(0, 120, 735, 50);
        add(lblExample);

        // 4. Поле для введення відповіді (суворо по центру: (735 - 150) / 2 = 292)
        txtAnswer = new JTextField();
        txtAnswer.setFont(new Font("Arial", Font.BOLD, 24));
        txtAnswer.setHorizontalAlignment(JTextField.CENTER);
        txtAnswer.setBounds(292, 185, 150, 45);
        add(txtAnswer);

        // 5. Кнопка перевірки (суворо по центру: x = 292)
        btnCheck = new JButton("Перевірити");
        btnCheck.setFont(new Font("Arial", Font.BOLD, 16));
        btnCheck.setBounds(292, 245, 150, 40);
        add(btnCheck);

        // 6. Лічильник балів
        lblScore = new JLabel("Правильних відповідей: 0", SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 14));
        lblScore.setForeground(new Color(200, 200, 200));
        lblScore.setBounds(0, 315, 735, 30);
        add(lblScore);

        // 7. Кнопка повернення в меню суворо по центру внизу ((735 - 120) / 2 = 307)
        btnBack = new JButton("У меню");
        btnBack.setFont(new Font("Arial", Font.BOLD, 13));
        btnBack.setBounds(307, 420, 120, 35);
        btnBack.addActionListener(e -> {
            if (gameTimer != null) gameTimer.stop();
            soundPlayer.stopBackgroundMusic(); // Зупиняємо музику при виході в меню
            parentWindow.showScreen("MENU");
        });
        add(btnBack);

        generateNewTask();

        btnCheck.addActionListener(e -> checkAnswer());
        txtAnswer.addActionListener(e -> checkAnswer());

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            lblTimer.setText("Час: " + timeLeft + " с");

            if (timeLeft <= 0) {
                gameTimer.stop();
                endGame();
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
                soundPlayer.playEffect(CORRECT_SOUND); // Звук правильної відповіді
                score++;
                lblScore.setText("Правильних відповідей: " + score);
                generateNewTask();
            } else {
                soundPlayer.playEffect(WRONG_SOUND);   // Звук неправильної відповіді
                txtAnswer.setText("");
            }
        } catch (NumberFormatException ex) {
            soundPlayer.playEffect(WRONG_SOUND);       // Звук помилки при некоректному вводі
            txtAnswer.setText("");
        }
    }

    private void endGame() {
        soundPlayer.stopBackgroundMusic(); // Зупиняємо фонову музику по завершенню гри
        txtAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        lblExample.setText("Час вийшов!");

        updatePlayerScore();

        JOptionPane.showMessageDialog(
                this,
                "Гра закінчилась!\nВаш результат: " + score + " правильних відповідей.",
                "Результати",
                JOptionPane.INFORMATION_MESSAGE
        );

        parentWindow.showScreen("MENU");
    }

    private void updatePlayerScore() {
        List<Player> players = loadPlayers();
        for (Player p : players) {
            if (p.getUsername().equals(currentPlayer.getUsername())) {
                p.updateBestScore(score);
                currentPlayer.updateBestScore(score);
                break;
            }
        }
        savePlayers(players);
    }

    @SuppressWarnings("unchecked")
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