package windows;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rules extends JPanel {
    private final List<FloatingNumber> numbers = new ArrayList<>();
    private final Timer animationTimer;
    private final Random random = new Random();
    private static final String[] SYMBOLS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "=", "?"};

    public Rules(Menu parentWindow) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

        for (int i = 0; i < 25; i++) {
            numbers.add(createRandomNumber(true));
        }

        JLabel lblTitle = new JLabel("Правила гри «Швидка Арифметика»", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(255, 200, 0));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));
        lblTitle.setOpaque(false);
        add(lblTitle, BorderLayout.NORTH);

        JTextArea txtRules = new JTextArea(
                "1. Гра триває рівно 60 секунд.\n\n" +
                        "2. Вам необхідно якомога швидше розв'язувати арифметичні приклади.\n\n" +
                        "3. Вводьте відповідь у поле та натискайте Enter або кнопку «Перевірити».\n\n" +
                        "4. За кожну правильну відповідь нараховується 1 бал (рамка спалахує зеленим).\n\n" +
                        "5. Після закінчення часу ваш результат автоматично зберігається у таблицю лідерів."
        );
        txtRules.setFont(new Font("Arial", Font.BOLD, 15));
        txtRules.setForeground(new Color(235, 235, 245));
        txtRules.setOpaque(false);
        txtRules.setEditable(false);
        txtRules.setLineWrap(true);
        txtRules.setWrapStyleWord(true);
        txtRules.setMargin(new Insets(25, 65, 20, 65));
        add(txtRules, BorderLayout.CENTER);

        animationTimer = new Timer(30, e -> {
            for (FloatingNumber fn : numbers) {
                fn.y -= fn.speed;
                fn.x += Math.sin(fn.y * 0.03) * 0.6;

                if (fn.y < -30) {
                    fn.y = getHeight() > 0 ? getHeight() + 20 : 560;
                    fn.x = random.nextInt(720);
                    fn.text = SYMBOLS[random.nextInt(SYMBOLS.length)];
                }
            }
            repaint();
        });
        animationTimer.start();

        JButton btnBack = new JButton("Назад у меню");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> {
            animationTimer.stop();
            parentWindow.showScreen("MENU");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private FloatingNumber createRandomNumber(boolean randomY) {
        double x = random.nextInt(730);
        double y = randomY ? random.nextInt(540) : 550;
        double speed = 0.8 + random.nextDouble() * 1.8;
        int fontSize = 16 + random.nextInt(22);
        String text = SYMBOLS[random.nextInt(SYMBOLS.length)];
        Color[] palette = {
                new Color(0, 200, 255, 45),
                new Color(50, 255, 130, 40),
                new Color(255, 200, 0, 40)
        };
        Color color = palette[random.nextInt(palette.length)];
        return new FloatingNumber(x, y, speed, fontSize, text, color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(30, 30, 40));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (FloatingNumber fn : numbers) {
            g2d.setFont(new Font("Arial", Font.BOLD, fn.fontSize));
            g2d.setColor(fn.color);
            g2d.drawString(fn.text, (int) fn.x, (int) fn.y);
        }

        g2d.setColor(new Color(70, 130, 180, 150));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 50, 20, 20);
    }

    private static class FloatingNumber {
        double x, y, speed;
        int fontSize;
        String text;
        Color color;

        FloatingNumber(double x, double y, double speed, int fontSize, String text, Color color) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.fontSize = fontSize;
            this.text = text;
            this.color = color;
        }
    }
}