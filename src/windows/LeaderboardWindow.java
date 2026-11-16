package windows;

import logik.Player;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderboardWindow extends JPanel {
    private static final String DATA_FILE = "players_data.dat";

    public LeaderboardWindow(Menu parentWindow) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

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

        JButton btnBack = new JButton("Назад у меню");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> parentWindow.showScreen("MENU"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 40));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private List<Player> loadPlayers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Player>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}