package windows;

import logik.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame {
    private Player currentPlayer;

    public Menu(Player player) {
        super("Швидка Арефметика");
        this.currentPlayer = player;

        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 550);
        setLocationRelativeTo(null);

        add(new Buttonns(currentPlayer));
    }
}


class Buttonns extends JPanel {
    private Player currentPlayer;
    Image fon = new ImageIcon("Photo/fon/fon1.jpg").getImage();
    ImageIcon rawIcon = new ImageIcon("Photo/Botton/Menu_Botton.png");

    JButton autor = new JButton("Про Автора");
    JButton rules = new JButton("Правила гри");
    JButton start = new JButton("Почати гру");
    JButton leaderboard = new JButton("Лідерборд");
    JButton exit = new JButton("Вихід");

    public Buttonns(Player player) { // Оновлений конструктор панелі
        this.currentPlayer = player;
        setLayout(null);
        ButtonsEvents listener = new ButtonsEvents();

        setupButton(autor, rawIcon, listener);
        setupButton(rules, rawIcon, listener);
        setupButton(start, rawIcon, listener);
        setupButton(leaderboard, rawIcon, listener);
        setupButton(exit, rawIcon, listener);
    }

    private void setupButton(JButton btn, ImageIcon icon, ActionListener listener) {
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setIcon(icon);
        btn.addActionListener(listener);
        add(btn);
    }

    private void resizeButtonStyle(JButton btn, int w, int h, int fontSize) {
        if (w > 0 && h > 0) {
            Image scaled = rawIcon.getImage().getScaledInstance(w, h, Image.SCALE_FAST);
            btn.setIcon(new ImageIcon(scaled));
            btn.setFont(new Font("Arial", Font.BOLD, fontSize));
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int w = getWidth();
        int h = getHeight();

        int btnWidth = (int) (w * 0.21);
        int btnHeight = (int) (h * 0.15);

        int startWidth = (int) (w * 0.28);
        int startHeight = (int) (h * 0.20);

        int margin = (int) (w * 0.03);
        int bottomY = h - btnHeight - (int) (h * 0.05);

        int totalButtonsWidth = btnWidth * 4;
        int availableSpace = w - (margin * 2) - totalButtonsWidth;
        int gap = availableSpace / 3;

        int x1 = margin;
        int x2 = x1 + btnWidth + gap;
        int x3 = x2 + btnWidth + gap;
        int x4 = x3 + btnWidth + gap;


        autor.setBounds(x1, bottomY, btnWidth, btnHeight);
        rules.setBounds(x2, bottomY, btnWidth, btnHeight);
        leaderboard.setBounds(x3, bottomY, btnWidth, btnHeight);
        exit.setBounds(x4, bottomY, btnWidth, btnHeight);


        start.setBounds((w - startWidth) / 2, bottomY - startHeight - (int) (h * 0.02), startWidth, startHeight);


        int normalFont = Math.max(11, btnHeight / 5);
        int startFont = Math.max(14, startHeight / 4);

        resizeButtonStyle(autor, btnWidth, btnHeight, normalFont);
        resizeButtonStyle(rules, btnWidth, btnHeight, normalFont);
        resizeButtonStyle(leaderboard, btnWidth, btnHeight, normalFont); // Стиль для нової кнопки
        resizeButtonStyle(exit, btnWidth, btnHeight, normalFont);
        resizeButtonStyle(start, startWidth, startHeight, startFont);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fon, 0, 0, getWidth(), getHeight(), this);
    }


    class ButtonsEvents implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == autor) {
                JFrame proAutor = new Author();
                proAutor.setVisible(true);
            } else if (e.getSource() == start) {
                JFrame proStart = new Game(currentPlayer);
                proStart.setVisible(true);
            } else if (e.getSource() == rules) {
                JFrame proRules = new Rules();
                proRules.setVisible(true);
            } else if (e.getSource() == exit) {
                System.exit(0);
            }else if (e.getSource() == leaderboard) {
                JFrame lbWindow = new LeaderboardWindow();
                lbWindow.setVisible(true);
            }
        }
    }
}