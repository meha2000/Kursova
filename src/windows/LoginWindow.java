package windows;

import logik.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LoginWindow extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    // Файл, де зберігатимуться зареєстровані гравці
    private static final String DATA_FILE = "players_data.dat";

    public LoginWindow() {
        super("Швидка Арифметика — Авторизація");
        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 40));

        // 1. Мітка та поле для логіна
        JLabel lblUser = new JLabel("Логін:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setBounds(50, 30, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 30, 180, 30);
        add(txtUsername);

        // 2. Мітка та поле для пароля
        JLabel lblPass = new JLabel("Пароль:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        lblPass.setBounds(50, 80, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 180, 30);
        add(txtPassword);

        // 3. Кнопка "Увійти"
        btnLogin = new JButton("Увійти");
        btnLogin.setBounds(50, 140, 130, 35);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnLogin);

        // 4. Кнопка "Реєстрація"
        btnRegister = new JButton("Реєстрація");
        btnRegister.setBounds(200, 140, 130, 35);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnRegister);

        // Обробка події входу
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        // Обробка події реєстрації нового гравця
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }

    // Завантаження списку всіх гравців із файлу
    private List<Player> loadPlayers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Player>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Збереження списку гравців у файл
    private void savePlayers(List<Player> players) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Логіка входу в аккаунт
    private void loginUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Будь ласка, заповніть всі поля!", "Помилка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Player> players = loadPlayers();
        boolean found = false;

        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                found = true;
                // Перевірка через хеш, який ми реалізували в класі Player!
                if (p.checkPassword(password)) {
                    JOptionPane.showMessageDialog(this, "Успішний вхід, " + username + "!", "Вітаємо", JOptionPane.INFORMATION_MESSAGE);
                    openMainMenu(p);
                } else {
                    JOptionPane.showMessageDialog(this, "Невірний пароль!", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Користувача з таким логіном не знайдено. Будь ласка, зареєструйтесь.", "Помилка", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Логіка реєстрації нового гравця
    private void registerUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Логін і пароль не можуть бути порожніми!", "Помилка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Player> players = loadPlayers();

        // Перевіряємо, чи немає вже такого користувача
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(this, "Користувач із таким ім'ям вже існує!", "Помилка", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Створюємо нового гравця (пароль автоматично хешується в конструкторі Player)
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
        savePlayers(players);

        JOptionPane.showMessageDialog(this, "Реєстрація успішна! Тепер ви можете увійти.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
    }

    // Відкриття головного меню програми після успішного входу
    private void openMainMenu(Player player) {
        // Тут ми передаємо поточного авторизованого гравця у головне меню
        dispose(); // Закриваємо вікно логіна

        // Відкриваємо головне меню (Buttons), передаючи гравця
        // (Переконайся, що у твоєму класі Buttons є конструктор, що приймає Player, або збережемо його в загальний контекст)
        Menu mainMenu = new Menu(player);
        mainMenu.setVisible(true);
    }
}