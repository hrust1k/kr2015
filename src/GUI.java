import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class GUI extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JFileChooser fc = new JFileChooser();
    private JButton add = new JButton("Add");
    private JButton openButton = new JButton("Open new csv file");
    private JTextArea LeagueTableOutput = new JTextArea(10, 10);
    private TeamDatabase tdb;
    private File file;

    public GUI() {
        super("Football League Table");
        this.setSize(700, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        LeagueTableOutput.setEditable(false);
        LeagueTableOutput.setLineWrap(true);
        LeagueTableOutput.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(LeagueTableOutput);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        ListenForButton lForButton = new ListenForButton();
        openButton.addActionListener(lForButton);

        CSVfilter filter = new CSVfilter();
        fc.setFileFilter(filter);

        JMenuBar menuBar = new JMenuBar();
        JMenu sortMenu = new JMenu("Sort");
        JMenuItem sortName = new JMenuItem("Sort by Name");
        sortName.addActionListener(new NameMenuListener());
        JMenuItem sortGoals = new JMenuItem("Sort by Goals");
        sortGoals.addActionListener(new GoalsMenuListener());
        JMenuItem sortDraws = new JMenuItem("Sort by Draws");
        sortDraws.addActionListener(new DrawsMenuListener());
        JMenuItem sortLosses = new JMenuItem("Sort by Losses");
        sortLosses.addActionListener(new LossesMenuListener());
        JMenuItem sortPoints = new JMenuItem("Sort by Points");
        sortPoints.addActionListener(new PointsMenuListener());
        add.addActionListener(lForButton);

        sortMenu.add(sortLosses);
        sortMenu.add(sortGoals);
        sortMenu.add(sortDraws);
        sortMenu.add(sortLosses);
        sortMenu.add(sortPoints);
        menuBar.add(sortMenu);
        this.setJMenuBar(menuBar);

        JPanel jp = new JPanel(new FlowLayout());
        jp.add(openButton);
        jp.add(add);
        this.getContentPane().add(jp,BorderLayout.SOUTH);

        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });

    }

    private String display(){
        JComboBox combo1 = new JComboBox(tdb.getTeamStrings());
        JComboBox combo2 = new JComboBox(tdb.getTeamStrings());
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField(new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.ENGLISH).format(new Date()).toString());
        field2.setEditable(false);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Team 1"));
        panel.add(combo1);
        panel.add(new JLabel("Team 2"));
        panel.add(combo2);
        panel.add(new JLabel("Score"));
        panel.add(field1);
        panel.add(new JLabel("Date"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(combo1.getSelectedItem().equals(combo2.getSelectedItem()) || field1.getText().isEmpty())
                return null;
            System.out.println(String.format(Locale.ENGLISH,"%s,%s,%s,%s",combo1.getSelectedItem(),field1.getText().replace(":","-"),combo2.getSelectedItem(),field2.getText()));

            return String.format(Locale.ENGLISH,"%s,%s-%s,%s,%s",combo1.getSelectedItem(),field1.getText().replace(":","-"),combo2.getSelectedItem(),field2.getText());
        } else {
            return null;
        }
    }

    private class ListenForButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == openButton) {
                int returnVal = fc.showOpenDialog(GUI.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    FileParse fp = new FileParse(file);
                    tdb = new TeamDatabase(fp.getLineArray());
                    LeagueTableOutput.setText(tdb.getFormattedTeamList());

                }
            }
            if(e.getActionCommand() == "Add" && tdb != null){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String[] s = new String[]{display()};
                        FileWrite fw = new FileWrite(file);
                        fw.write(s[0]);
                        fw.close();
                        tdb.parseData(s);
                        tdb.updateTeamList();
                        tdb.doSmt();
                        LeagueTableOutput.setText(tdb.getFormattedTeamList());
                    }
                });
            }

        }

    }

    // This object extends FileFilter to only accept directories or files ending
    // in .csv
    private class CSVfilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory() == true) {
                return true;
            }
            String name = f.getName();
            String[] parts = name.split("\\.");

            if (parts.length > 1) {
                return parts[parts.length - 1].equalsIgnoreCase("csv");
            } else {
                return false;
            }

        }

        @Override
        public String getDescription() {
            return "CSV Filter";
        }
    }

    private class GoalsMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LeagueTableOutput.setText(tdb.customCompare("goals"));
        }

    }

    private class LossesMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LeagueTableOutput.setText(tdb.customCompare("losses"));
        }

    }

    private class DrawsMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LeagueTableOutput.setText(tdb.customCompare("draws"));
        }

    }

    private class NameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LeagueTableOutput.setText(tdb.customCompare("name"));
        }

    }

    private class PointsMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LeagueTableOutput.setText(tdb.getFormattedTeamList());
        }

    }
}