package windows;

import javax.swing.*;
import java.awt.*;

public class Rules extends JPanel {
    public Rules(Menu parentWindow) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

        JLabel lblTitle = new JLabel("Правила гри «Швидка Арифметика»", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JTextArea txtRules = new JTextArea(
                "1. Гра триває рівно 60 секунд.\n" +
                        "2. Вам необхідно якомога швидше розв'язувати арифметичні приклади.\n" +
                        "3. Вводьте відповідь у поле та натискайте Enter або кнопку «Перевірити».\n" +
                        "4. За кожну правильну відповідь нараховується 1 бал.\n" +
                        "5. Після закінчення часу ваш результат буде автоматично збережено у загальну таблицю лідерів."
        );
        txtRules.setFont(new Font("Arial", Font.PLAIN, 15));
        txtRules.setForeground(new Color(220, 220, 220));
        txtRules.setBackground(new Color(30, 30, 40));
        txtRules.setEditable(false);
        txtRules.setMargin(new Insets(20, 50, 20, 50));
        add(txtRules, BorderLayout.CENTER);

        JButton btnBack = new JButton("Назад у меню");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> parentWindow.showScreen("MENU"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 40));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}