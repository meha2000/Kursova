package windows;

import logik.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderboardWindow extends JFrame {
    private static final String DATA_FILE = "players_data.dat";

    public LeaderboardWindow() {
        super("Швидка Арифметика — Таблиця лідерів");
        setIconImage(new ImageIcon("Photo/Icon/menu_icon.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(30, 30, 40));
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Таблиця рекордів", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = {"Місце", "Гравець", "Найкращий результат"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Player> players = loadPlayers();
        players.sort(Comparator.comparingInt(Player::getBestScore).reversed());

        int rank = 1;
        for (Player p : players) {
            Object[] row = {rank++, p.getUsername(), p.getBestScore()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

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
}