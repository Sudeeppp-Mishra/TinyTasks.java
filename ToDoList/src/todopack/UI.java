package todopack;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame{
	private JTextField taskField;
	private JPanel listPanel;
	private JLabel dummyTask;
	private boolean isFirstTaskAdded = false;
	
	Color menuItemColor = new Color(33, 33, 33);
	
	UI() {
		initializeFrame();
		setupMenuBar();
		setupUI();
		
		setVisible(true);
	}
	
	// ========== INIT FRAME ==========
	private void initializeFrame() {
		//setExtendedState(MAXIMIZED_VERT);
		setTitle("To Do List");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,600);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		
	}
	
	private void setupMenuBar() {
		  try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.DARK_GRAY); 
		menuBar.setBackground(Color.LIGHT_GRAY);
		
		JMenu fileMenu = setupFileMenu();
		menuBar.add(fileMenu);
		fileMenu.setFont(new Font("Chalkboard", Font.BOLD, 14));
		
		JMenu editMenu = setupEditMenu();
		menuBar.add(editMenu);
		editMenu.setFont(new Font("Chalkboard", Font.BOLD, 14));
		
		JMenu viewMenu = setupViewMenu();
		menuBar.add(viewMenu);
		viewMenu.setFont(new Font("Chalkboard", Font.BOLD, 14));
		
		JMenu helpMenu = setupHelpMenu();
		menuBar.add(helpMenu);
		helpMenu.setFont(new Font("Chalkboard", Font.BOLD, 14));
		
		setJMenuBar(menuBar);
	}
	
	// ========== FILE MENU ==========
	private JMenu setupFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newTask = new JMenuItem("New Task");
		fileMenu.add(newTask);
		newTask.setToolTipText("Create a new task");
		newTask.setForeground(menuItemColor);
		newTask.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem saveTask = new JMenuItem("Save Task");
		fileMenu.add(saveTask);
		saveTask.setToolTipText("Save teh current task list to a file");
		saveTask.setForeground(menuItemColor); 
		saveTask.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem loadTask = new JMenuItem("Load Task");
		fileMenu.add(loadTask);
		loadTask.setToolTipText("Load tasks from a file");
		loadTask.setForeground(menuItemColor); 
		loadTask.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem export = new JMenuItem("Export");
		fileMenu.add(export);
		export.setToolTipText("Export tasks as .txt");
		export.setForeground(menuItemColor);
		export.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		fileMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.setToolTipText("Close the application");
		exit.setForeground(new Color(255, 87, 87)); // Coral red 
		
		
		return fileMenu;
	}
	
	// ========== EDIT MENU ==========
	private JMenu setupEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem undo = new JMenuItem("Undo");
		editMenu.add(undo);
		undo.setToolTipText("Undo the last change");
		undo.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem redo = new JMenuItem("Redo");
		editMenu.add(redo);
		redo.setToolTipText("Redo the previously undone action");
		redo.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem deleteTask = new JMenuItem("Delete Task");
		editMenu.add(deleteTask);
		deleteTask.setToolTipText("Delete the selected task");
		deleteTask.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem clearAllTasks = new JMenuItem("Clear All Tasks");
		editMenu.add(clearAllTasks);
		clearAllTasks.setToolTipText("Remove all tasks");
		clearAllTasks.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		return editMenu;
	}
	
	// ========== VIEW MENU ==========
	private JMenu setupViewMenu() {
		JMenu viewMenu = new JMenu("View");
		
		JMenuItem showCompletedTasks = new JMenuItem("Show Completed Tasks");
		viewMenu.add(showCompletedTasks);
		showCompletedTasks.setToolTipText("Toggle view for completed tasks");
		showCompletedTasks.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem sortByDeadline = new JMenuItem("Sort by Deadline");
		viewMenu.add(sortByDeadline);
		sortByDeadline.setToolTipText("Sort tasks by due date");
		sortByDeadline.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem sortByPriority = new JMenuItem("Sort by Priority");
		viewMenu.add(sortByPriority);
		sortByPriority.setToolTipText("Sort tasks by priority");
		sortByPriority.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		return viewMenu;
	}
	
	// ========== HELP MENU =========
	private JMenu setupHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem howToUse = new JMenuItem("How to Use");
		helpMenu.add(howToUse);
		howToUse.setToolTipText("Basic guide/instructions for using the app");
		howToUse.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		about.setToolTipText("App version and other info.");
		about.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		
		return helpMenu;
	}
	
	// ========== SETUP UI LAYOUT ==========
	private void setupUI() {
	    JPanel mainPanel = new JPanel(new BorderLayout(20, 20)); // Add spacing between regions
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Top, left, bottom, right

	    // Task Input Panel (Top)
	    JPanel taskInputPanel = setupTaskInputPanel();
	    mainPanel.add(taskInputPanel, BorderLayout.NORTH);

	    // Task List Panel (Center)
	    JScrollPane taskScrollPane = setupTaskListPanel();
	    mainPanel.add(taskScrollPane, BorderLayout.CENTER);

	    // Footer Controls (Bottom)
	    JPanel footerPanel = setupFooterPanel();
	    mainPanel.add(footerPanel, BorderLayout.SOUTH);

	    JScrollPane contentScrollPane = new JScrollPane(mainPanel);
	    contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    setLayout(new BorderLayout());
	    add(contentScrollPane, BorderLayout.CENTER); 
	}
	
	private JPanel setupTaskInputPanel() {
	    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

	    taskField = new JTextField();
	    taskField.setPreferredSize(new Dimension(400, 35));
	    taskField.setFont(new Font("Chalkboard", Font.PLAIN, 16));

	    JButton addButton = new JButton("Add Task");
	    addButton.setPreferredSize(new Dimension(120, 35));
	    addButton.setFont(new Font("Chalkboard", Font.BOLD, 14));

	    // Add task action
	    addButton.addActionListener(e -> {
	        String taskText = taskField.getText().trim();
	        if (!taskText.isEmpty()) {
	            if (!isFirstTaskAdded) {
	                listPanel.remove(dummyTask);
	                isFirstTaskAdded = true;
	            }

	            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new java.util.Date());

	            // Horizontal panel for a single task
	            JPanel taskRow = new JPanel();
	            taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.X_AXIS));
	            taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);
	            taskRow.setOpaque(false);
	            taskRow.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

	            // Checkbox for task
	            JCheckBox checkBox = new JCheckBox(taskText);
	            checkBox.setFont(new Font("Chalkboard", Font.PLAIN, 16));
	            checkBox.setOpaque(false);

	            // Timestamp label
	            JLabel timeLabel = new JLabel("  [" + timestamp + "]");
	            timeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
	            timeLabel.setForeground(new Color(80, 80, 80)); // darker gray for visibility

	            // Handle checkbox toggle (HTML strike-through)
	            checkBox.addActionListener(ev -> {
	                if (checkBox.isSelected()) {
	                    checkBox.setText("<html><strike>" + taskText + "</strike></html>");
	                    checkBox.setForeground(Color.GRAY);
	                } else {
	                    checkBox.setText(taskText);
	                    checkBox.setForeground(Color.BLACK);
	                }
	            });

	            // Add checkbox and timestamp to row
	            taskRow.add(checkBox);
	            taskRow.add(Box.createRigidArea(new Dimension(10, 0)));
	            taskRow.add(timeLabel);

	            listPanel.add(taskRow);
	            listPanel.revalidate();
	            listPanel.repaint();

	            taskField.setText("");
	        } else {
	            JOptionPane.showMessageDialog(this, "Please enter a task!");
	        }
	    });

	    // Pressing Enter = add button click
	    taskField.addActionListener(e -> addButton.doClick());

	    panel.add(taskField);
	    panel.add(addButton);

	    return panel;
	}

	private JScrollPane setupTaskListPanel() {
	    listPanel = new JPanel();
	    listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	    listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    // Dummy placeholder task
	    dummyTask = new JLabel("• Sample Task");
	    dummyTask.setFont(new Font("Chalkboard", Font.PLAIN, 16));
	    listPanel.add(dummyTask);

	    JScrollPane scrollPane = new JScrollPane(listPanel);
	    scrollPane.setPreferredSize(new Dimension(800, 500));
	    return scrollPane;
	}

	private JPanel setupFooterPanel() {
	    JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
	    panel.setPreferredSize(new Dimension(600, 50));

	    String[] labels = {"All", "Active", "Completed", "Clear All"};

	    for (String text : labels) {
	        JButton btn = new JButton(text);
	        btn.setFont(new Font("Arial", Font.BOLD, 14));
	        btn.setPreferredSize(new Dimension(140, 40));
	        panel.add(btn);
	    }

	    return panel;
	}
	
}
