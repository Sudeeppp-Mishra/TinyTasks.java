package todopack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UI extends JFrame {
    private JTextField taskField;
    private JSpinner deadlineSpinner;
    private JCheckBox deadlineToggle;
    private JPanel listPanel;
    private JLabel dummyTask;
    private boolean isFirstTaskAdded = false;
    private List<String[]> toDoList = new ArrayList<>(); // [taskText, deadline, timestamp, completed]
    private Color darkBackground = new Color(30, 30, 30);
    private Color panelBackground = new Color(40, 40, 40);
    private Color textColor = new Color(255, 255, 255);
    private Color accentColor = new Color(100, 100, 100);
    private Color buttonBackground = new Color(70, 70, 70);
    private Color completedTaskColor = new Color(128, 128, 128);
    private Color menuItemColor = new Color(33, 33, 33);
    private Color exitMenuColor = new Color(255, 87, 87);
    private JPanel titleBar;
    private Point initialClick;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    UI() {
        initializeFrame();
        setupMenuBar();
        setupUI();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("To Do List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        getContentPane().setBackground(darkBackground);
        setLayout(new BorderLayout());
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(panelBackground);
        titleBar.setPreferredSize(new Dimension(0, 30));
        titleBar.setBorder(BorderFactory.createLineBorder(accentColor));

        JLabel titleLabel = new JLabel("To Do List");
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        titleBar.add(titleLabel, BorderLayout.WEST);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controlPanel.setOpaque(false);

        JButton minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(40, 30));
        minimizeButton.setBackground(buttonBackground);
        minimizeButton.setForeground(textColor);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBorder(BorderFactory.createEmptyBorder());
        minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED));
        controlPanel.add(minimizeButton);

        JButton maximizeButton = new JButton("□");
        maximizeButton.setPreferredSize(new Dimension(40, 30));
        maximizeButton.setBackground(buttonBackground);
        maximizeButton.setForeground(textColor);
        maximizeButton.setFocusPainted(false);
        maximizeButton.setBorder(BorderFactory.createEmptyBorder());
        maximizeButton.addActionListener(e -> {
            if (getExtendedState() == Frame.MAXIMIZED_BOTH) {
                setExtendedState(Frame.NORMAL);
                maximizeButton.setText("□");
            } else {
                setExtendedState(Frame.MAXIMIZED_BOTH);
                maximizeButton.setText("◪");
            }
        });
        controlPanel.add(maximizeButton);

        JButton closeButton = new JButton("×");
        closeButton.setPreferredSize(new Dimension(40, 30));
        closeButton.setBackground(buttonBackground);
        closeButton.setForeground(exitMenuColor);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(e -> System.exit(0));
        controlPanel.add(closeButton);

        titleBar.add(controlPanel, BorderLayout.EAST);

        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialClick.x;
                int y = getLocation().y + e.getY() - initialClick.y;
                setLocation(x, y);
            }
        });

        return titleBar;
    }

    private void setupMenuBar() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(panelBackground);
        menuBar.setForeground(textColor);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenu fileMenu = setupFileMenu();
        menuBar.add(fileMenu);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 14));
        fileMenu.setForeground(textColor);

        JMenu editMenu = setupEditMenu();
        menuBar.add(editMenu);
        editMenu.setFont(new Font("Arial", Font.BOLD, 14));
        editMenu.setForeground(textColor);

        JMenu viewMenu = setupViewMenu();
        menuBar.add(viewMenu);
        viewMenu.setFont(new Font("Arial", Font.BOLD, 14));
        viewMenu.setForeground(textColor);

        JMenu helpMenu = setupHelpMenu();
        menuBar.add(helpMenu);
        helpMenu.setFont(new Font("Arial", Font.BOLD, 14));
        helpMenu.setForeground(textColor);

        setJMenuBar(menuBar);
    }

    private JMenu setupFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(textColor);

        JMenuItem newTask = new JMenuItem("New Task");
        fileMenu.add(newTask);
        newTask.setToolTipText("Create a new task");
        newTask.setForeground(textColor);
        newTask.setBackground(panelBackground);
        newTask.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem saveTask = new JMenuItem("Save Task");
        fileMenu.add(saveTask);
        saveTask.setToolTipText("Save the current task list to a file");
        saveTask.setForeground(textColor);
        saveTask.setBackground(panelBackground);
        saveTask.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem loadTask = new JMenuItem("Load Task");
        fileMenu.add(loadTask);
        loadTask.setToolTipText("Load tasks from a file");
        loadTask.setForeground(textColor);
        loadTask.setBackground(panelBackground);
        loadTask.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem export = new JMenuItem("Export");
        fileMenu.add(export);
        export.setToolTipText("Export tasks as .txt");
        export.setForeground(textColor);
        export.setBackground(panelBackground);
        export.setFont(new Font("Arial", Font.PLAIN, 13));

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.setToolTipText("Close the application");
        exit.setForeground(exitMenuColor);
        exit.setBackground(panelBackground);
        exit.setFont(new Font("Arial", Font.PLAIN, 13));

        return fileMenu;
    }

    private JMenu setupEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(textColor);

        JMenuItem undo = new JMenuItem("Undo");
        editMenu.add(undo);
        undo.setToolTipText("Undo the last change");
        undo.setForeground(textColor);
        undo.setBackground(panelBackground);
        undo.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem redo = new JMenuItem("Redo");
        editMenu.add(redo);
        redo.setToolTipText("Redo the previously undone action");
        redo.setForeground(textColor);
        redo.setBackground(panelBackground);
        redo.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem deleteTask = new JMenuItem("Delete Task");
        editMenu.add(deleteTask);
        deleteTask.setToolTipText("Delete the selected task");
        deleteTask.setForeground(textColor);
        deleteTask.setBackground(panelBackground);
        deleteTask.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem clearAllTasks = new JMenuItem("Clear All Tasks");
        editMenu.add(clearAllTasks);
        clearAllTasks.setToolTipText("Remove all tasks");
        clearAllTasks.setForeground(textColor);
        clearAllTasks.setBackground(panelBackground);
        clearAllTasks.setFont(new Font("Arial", Font.PLAIN, 13));

        return editMenu;
    }

    private JMenu setupViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setForeground(textColor);

        JMenuItem showCompletedTasks = new JMenuItem("Show Completed Tasks");
        viewMenu.add(showCompletedTasks);
        showCompletedTasks.setToolTipText("Toggle view for completed tasks");
        showCompletedTasks.setForeground(textColor);
        showCompletedTasks.setBackground(panelBackground);
        showCompletedTasks.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem sortByDeadline = new JMenuItem("Sort by Deadline");
        viewMenu.add(sortByDeadline);
        sortByDeadline.setToolTipText("Sort tasks by due date");
        sortByDeadline.setForeground(textColor);
        sortByDeadline.setBackground(panelBackground);
        sortByDeadline.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem sortByPriority = new JMenuItem("Sort by Priority");
        viewMenu.add(sortByPriority);
        sortByPriority.setToolTipText("Sort tasks by priority");
        sortByPriority.setForeground(textColor);
        sortByPriority.setBackground(panelBackground);
        sortByPriority.setFont(new Font("Arial", Font.PLAIN, 13));

        return viewMenu;
    }

    private JMenu setupHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(textColor);

        JMenuItem howToUse = new JMenuItem("How to Use");
        helpMenu.add(howToUse);
        howToUse.setToolTipText("Basic guide/instructions for using the app");
        howToUse.setForeground(textColor);
        howToUse.setBackground(panelBackground);
        howToUse.setFont(new Font("Arial", Font.PLAIN, 13));

        JMenuItem about = new JMenuItem("About");
        helpMenu.add(about);
        about.setToolTipText("App version and other info.");
        about.setForeground(textColor);
        about.setBackground(panelBackground);
        about.setFont(new Font("Arial", Font.PLAIN, 13));

        return helpMenu;
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(darkBackground);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel taskInputPanel = setupTaskInputPanel();
        mainPanel.add(taskInputPanel, BorderLayout.NORTH);

        JScrollPane taskScrollPane = setupTaskListPanel();
        mainPanel.add(taskScrollPane, BorderLayout.CENTER);

        JPanel footerPanel = setupFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.setBackground(darkBackground);
        titleBar = createTitleBar();
        parentPanel.add(titleBar, BorderLayout.NORTH);
        parentPanel.add(mainPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(parentPanel, BorderLayout.CENTER);
    }

    private JPanel setupTaskInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(panelBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        taskField = new JTextField();
        taskField.setPreferredSize(new Dimension(200, 35));
        taskField.setFont(new Font("Arial", Font.PLAIN, 16));
        taskField.setBackground(new Color(50, 50, 50));
        taskField.setForeground(textColor);
        taskField.setCaretColor(textColor);
        taskField.setBorder(BorderFactory.createLineBorder(accentColor));

        deadlineToggle = new JCheckBox("Set Deadline");
        deadlineToggle.setFont(new Font("Arial", Font.PLAIN, 14));
        deadlineToggle.setForeground(textColor);
        deadlineToggle.setBackground(panelBackground);
        deadlineToggle.setFocusPainted(false);

        deadlineSpinner = new JSpinner(new SpinnerDateModel());
        deadlineSpinner.setPreferredSize(new Dimension(120, 35));
        deadlineSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        deadlineSpinner.setEditor(new JSpinner.DateEditor(deadlineSpinner, "yyyy-MM-dd"));
        deadlineSpinner.setBackground(new Color(50, 50, 50));
        deadlineSpinner.setForeground(textColor);
        deadlineSpinner.setEnabled(false);
        JComponent editor = deadlineSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setBackground(new Color(50, 50, 50));
            ((JSpinner.DefaultEditor) editor).getTextField().setForeground(textColor);
            ((JSpinner.DefaultEditor) editor).getTextField().setCaretColor(textColor);
            ((JSpinner.DefaultEditor) editor).getTextField().setBorder(BorderFactory.createLineBorder(accentColor));
        }

        deadlineToggle.addActionListener(e -> deadlineSpinner.setEnabled(deadlineToggle.isSelected()));

        JButton addButton = new JButton("Add Task");
        addButton.setPreferredSize(new Dimension(120, 35));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(buttonBackground);
        addButton.setForeground(textColor);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(accentColor));

        addButton.addActionListener(e -> {
            String taskText = taskField.getText().trim();
            if (!taskText.isEmpty()) {
                if (!isFirstTaskAdded) {
                    listPanel.remove(dummyTask);
                    isFirstTaskAdded = true;
                }

                String timestamp = timestampFormat.format(new Date());
                String deadline = deadlineToggle.isSelected() ? dateFormat.format((Date) deadlineSpinner.getValue()) : "No Deadline";
                String[] task = {taskText, deadline, timestamp, "false"};
                toDoList.add(task);

                JPanel taskRow = new JPanel();
                taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.X_AXIS));
                taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);
                taskRow.setOpaque(false);
                taskRow.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JCheckBox taskCheckBox = new JCheckBox(taskText);
                taskCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
                taskCheckBox.setOpaque(false);
                taskCheckBox.setForeground(textColor);
                taskCheckBox.setBackground(panelBackground);

                JLabel timeLabel = new JLabel("  [" + timestamp + "] [Due: " + deadline + "]");
                timeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                timeLabel.setForeground(completedTaskColor);

                taskCheckBox.addActionListener(ev -> {
                    task[3] = String.valueOf(taskCheckBox.isSelected());
                    if (taskCheckBox.isSelected()) {
                        taskCheckBox.setText("<html><strike>" + taskText + "</strike></html>");
                        taskCheckBox.setForeground(completedTaskColor);
                    } else {
                        taskCheckBox.setText(taskText);
                        taskCheckBox.setForeground(textColor);
                    }
                    timeLabel.setForeground(taskCheckBox.isSelected() ? completedTaskColor : textColor);
                });

                taskRow.add(taskCheckBox);
                taskRow.add(Box.createRigidArea(new Dimension(10, 0)));
                taskRow.add(timeLabel);

                listPanel.add(taskRow);
                listPanel.revalidate();
                listPanel.repaint();

                taskField.setText("");
                deadlineToggle.setSelected(false);
                deadlineSpinner.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task!");
            }
        });

        taskField.addActionListener(e -> addButton.doClick());

        panel.add(taskField);
        panel.add(deadlineToggle);
        panel.add(deadlineSpinner);
        panel.add(addButton);

        return panel;
    }

    private JScrollPane setupTaskListPanel() {
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(panelBackground);
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dummyTask = new JLabel("• No tasks yet. Add a task to start!");
        dummyTask.setFont(new Font("Arial", Font.PLAIN, 16));
        dummyTask.setForeground(completedTaskColor);
        listPanel.add(dummyTask);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor));
        scrollPane.setBackground(darkBackground);
        return scrollPane;
    }

    private JPanel setupFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(panelBackground);
        panel.setPreferredSize(new Dimension(600, 50));

        String[] labels = {"All", "Active", "Completed", "Clear All"};

        for (String text : labels) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(120, 35));
            btn.setBackground(buttonBackground);
            btn.setForeground(textColor);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(accentColor));

            btn.addActionListener(e -> {
                switch (text) {
                    case "All":
                        showAllTasks();
                        break;
                    case "Active":
                        showActiveTasks();
                        break;
                    case "Completed":
                        showCompletedTasks();
                        break;
                    case "Clear All":
                        clearAllTasks();
                        break;
                }
            });

            panel.add(btn);
        }

        return panel;
    }

    private void showAllTasks() {
        if (toDoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to show.");
        } else {
            StringBuilder result = new StringBuilder("Your Tasks:\n");
            for (int i = 0; i < toDoList.size(); i++) {
                String[] task = toDoList.get(i);
                result.append(i + 1)
                      .append(". ")
                      .append(task[0])
                      .append(" [Due: ")
                      .append(task[1])
                      .append("]\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());
        }
    }

    private void showActiveTasks() {
        if (toDoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to show.");
        } else {
            StringBuilder result = new StringBuilder("Active Tasks:\n");
            int index = 1;
            for (String[] task : toDoList) {
                if (!Boolean.parseBoolean(task[3])) {
                    result.append(index++)
                          .append(". ")
                          .append(task[0])
                          .append(" [Due: ")
                          .append(task[1])
                          .append("]\n");
                }
            }
            JOptionPane.showMessageDialog(this, result.length() > 13 ? result.toString() : "No active tasks.");
        }
    }

    private void showCompletedTasks() {
        if (toDoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to show.");
        } else {
            StringBuilder result = new StringBuilder("Completed Tasks:\n");
            int index = 1;
            for (String[] task : toDoList) {
                if (Boolean.parseBoolean(task[3])) {
                    result.append(index++)
                          .append(". ")
                          .append(task[0])
                          .append(" [Due: ")
                          .append(task[1])
                          .append("]\n");
                }
            }
            JOptionPane.showMessageDialog(this, result.length() > 16 ? result.toString() : "No completed tasks.");
        }
    }

    private void clearAllTasks() {
        if (!toDoList.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all tasks?", "Clear All Tasks", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                toDoList.clear();
                listPanel.removeAll();
                listPanel.add(dummyTask);
                isFirstTaskAdded = false;
                listPanel.revalidate();
                listPanel.repaint();
                JOptionPane.showMessageDialog(this, "All tasks cleared.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tasks to clear.");
        }
    }
}