package windows;

import java.awt.*;
import javax.swing.*;

public class Rules extends JFrame {
    public Rules() {
        super("Правила гри");
        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(750, 180, 600, 450);
        setResizable(false);

        add(new RulesCanvas());
    }
}

class RulesCanvas extends Canvas {
    @Override
    public void paint(Graphics gr) {
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(250, 0, 0));
        g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 25, 25);

        Font titleFont = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(titleFont);
        g2d.setColor(new Color(188, 255, 3));
        g2d.drawString("Правила гри «Швидка Арифметика»", 45, 70);

        g2d.setColor(new Color(220, 100, 50));
        g2d.drawLine(45, 85, 550, 85);

        Font textFont = new Font("Arial", Font.PLAIN, 15);
        g2d.setFont(textFont);
        g2d.setColor(new Color(25, 255, 0));

        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = fm.getHeight() + 8;

        int textX = 45;
        int startY = 125;

        g2d.drawString("1. Гра призначена для перевірки швидкості обчислень.", textX, startY);
        g2d.drawString("2. На екрані з'являється випадковий математичний приклад.", textX, startY + lineHeight);
        g2d.drawString("3. Твоє завдання — якомога швидше ввести правильну відповідь.", textX, startY + lineHeight * 2);
        g2d.drawString("4. За правильні відповіді нараховуються бали.", textX, startY + lineHeight * 3);
        g2d.drawString("5. Поспішай, адже час на роздуми обмежений!", textX, startY + lineHeight * 4);

        g2d.setColor(new Color(0, 180, 255));
        g2d.setFont(new Font("Arial", Font.ITALIC, 14));
        g2d.drawString("Успіхів у проходженні гри!", textX, startY + lineHeight * 5 + 15);
    }
}