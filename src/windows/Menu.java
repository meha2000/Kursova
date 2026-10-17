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

    JButton autor = new JButton("Про Автора");

    public Buttonns() {

        setLayout(null);



        autor.setContentAreaFilled(false);
        autor.setBorderPainted(false);
        autor.setFocusPainted(false);

        autor.setHorizontalTextPosition(SwingConstants.CENTER);
        autor.setVerticalTextPosition(SwingConstants.CENTER);
        autor.setFont(new Font("Arial", Font.BOLD, 16));

        add(autor);
        ImageIcon rawIcon = new ImageIcon("Photo/Botton/Menu_Botton.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(150, 90, Image.SCALE_SMOOTH);
        autor.setIcon(new ImageIcon(scaledImg));
        autor.addActionListener(new ButtonsEvents());


    }


    public void doLayout() {
        super.doLayout();
        int btnWidth = 150;
        int btnHeight = 90;

        int x = getWidth() - btnWidth - 30;
        int y = getHeight() - btnHeight - 30;

        autor.setBounds(x, y, btnWidth, btnHeight);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fon, 0, 0, getWidth(), getHeight(), this);

    }


    class ButtonsEvents implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame proAutor = new Author();
            proAutor.setVisible(true);

        }
    }

}