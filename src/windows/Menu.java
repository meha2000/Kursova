package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame {
    public Menu() {
        super("Швидка Арефметика");
        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 550);
        setLocationRelativeTo(null);
        add(new Buttonns());
    }
}


class Buttonns extends JPanel {
    Image fon = new ImageIcon("Photo/fon/fon1.jpg").getImage();
    ImageIcon rawIcon = new ImageIcon("Photo/Botton/Menu_Botton.png");

    JButton autor = new JButton("Про Автора");
    JButton rules = new JButton("Правила гри");
    JButton start = new JButton("Почати гру");
    JButton exit = new JButton("Вихід");

    public Buttonns() {

        setLayout(null);
        ButtonsEvents listener = new ButtonsEvents();

        setupButton(autor, rawIcon, listener);
        setupButton(rules, rawIcon, listener);
        setupButton(start, rawIcon, listener);
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

        int btnWidth = (int) (getWidth() * 0.20);
        int btnHeight = (int) (getHeight() * 0.16);

        int startWidth = (int) (getWidth() * 0.28);
        int startHeight = (int) (getHeight() * 0.20);

        int margin = (int) (getHeight() * 0.04);
        int bottomY = getHeight() - btnHeight - margin;

        autor.setBounds(margin, bottomY, btnWidth, btnHeight);
        rules.setBounds((getWidth() - btnWidth) / 2, bottomY, btnWidth, btnHeight);
        exit.setBounds(getWidth() - btnWidth - margin, bottomY, btnWidth, btnHeight);

        start.setBounds((getWidth() - startWidth) / 2, bottomY - startHeight - 10, startWidth, startHeight);

        int normalFont = Math.max(12, btnHeight / 5);
        int startFont = Math.max(14, startHeight / 4);

        resizeButtonStyle(autor, btnWidth, btnHeight, normalFont);
        resizeButtonStyle(rules, btnWidth, btnHeight, normalFont);
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
                JFrame proStart = new Game();
                proStart.setVisible(true);
            } else if (e.getSource() == rules) {
                JFrame proRules = new Rules();
                proRules.setVisible(true);
            } else if (e.getSource() == exit) {
                System.exit(0);
            }
        }

    }
}
