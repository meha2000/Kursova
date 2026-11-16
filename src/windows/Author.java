package windows;

import javax.swing.*;
import java.awt.*;

public class Author extends JPanel {
    public Author(Menu parentWindow) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        AuthorCanvas canvas = new AuthorCanvas();
        add(canvas, BorderLayout.CENTER);

        JButton btnBack = new JButton("Назад у меню");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> parentWindow.showScreen("MENU"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

class AuthorCanvas extends Canvas {
    private Image avatar = new ImageIcon("Photo/Icon/Autor_icon.jpg").getImage();

    @Override
    public void paint(Graphics gr) {
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(250, 0, 0));
        g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 25, 25);

        int imgX = 45;
        int imgY = 45;
        int imgSize = 120;
        g2d.drawImage(avatar, imgX, imgY, imgSize, imgSize, this);

        g2d.setColor(new Color(70, 130, 180));
        g2d.drawRoundRect(imgX - 2, imgY - 2, imgSize + 4, imgSize + 4, 15, 15);

        Font titleFont = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(titleFont);
        g2d.setColor(new Color(188, 255, 3, 255));
        g2d.drawString("Картка розробника", 190, 70);

        g2d.setColor(new Color(220, 100, 50));
        g2d.drawLine(190, 85, 480, 85);

        Font textFont = new Font("Arial", Font.PLAIN, 15);
        g2d.setFont(textFont);
        g2d.setColor(new Color(25, 255, 0));

        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = fm.getHeight() + 6;

        int textX = 190;
        int startY = 120;

        g2d.drawString("Автор: Мороз Михайло Анатолійович", textX, startY);
        g2d.drawString("Факультет: ФІТКІ", textX, startY + lineHeight);
        g2d.drawString("Група: 1ЕХКБ-25б", textX, startY + lineHeight * 2);
        g2d.drawString("Дисципліна: Програмування", textX, startY + lineHeight * 3);

        g2d.setColor(new Color(0, 180, 255));
        g2d.drawString("Telegram: @Miha20089", textX, startY + lineHeight * 4 + 8);
        g2d.drawString("GitHub: https://github.com/meha2000", textX, startY + lineHeight * 5 + 14);
    }
}