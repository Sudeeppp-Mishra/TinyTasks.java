package todopack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame{
	UI() {
		initializeFrame();
		setupMenuBar();
		
		setVisible(true);
	}
	
	// ========== INIT FRAME ==========
	private void initializeFrame() {
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
	}
	
	private void setupMenuBar() {
		Color aestheticTextColor = new Color(80, 80, 160); // Muted Indigo
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(aestheticTextColor); // menuBar.setBackground(new Color(80, 80, 160);
		
		JMenu fileMenu = setupFileMenu();
		menuBar.add(fileMenu);
		
		JMenu editMenu = setupEditMenu();
		menuBar.add(editMenu);
		
		JMenu viewMenu = setupViewMenu();
		menuBar.add(viewMenu);
		
		JMenu helpMenu = setupHelpMenu();
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	// ========== FILE MENU ==========
	private JMenu setupFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newTask = new JMenuItem("New Task");
		fileMenu.add(newTask);
		newTask.setToolTipText("Create a new task");
		
		JMenuItem saveTask = new JMenuItem("Save Task");
		fileMenu.add(saveTask);
		saveTask.setToolTipText("Save teh current task list to a file");
		
		JMenuItem loadTask = new JMenuItem("Load Task");
		fileMenu.add(loadTask);
		loadTask.setToolTipText("Load tasks from a file");
		
		JMenuItem export = new JMenuItem("Export");
		fileMenu.add(export);
		export.setToolTipText("Export tasks as .txt");
		
		fileMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.setToolTipText("Close the application");
		
		return fileMenu;
	}
	
	// ========== EDIT MENU ==========
	private JMenu setupEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem undo = new JMenuItem("Undo");
		editMenu.add(undo);
		undo.setToolTipText("Undo the last change");
		
		JMenuItem redo = new JMenuItem("Redo");
		editMenu.add(redo);
		redo.setToolTipText("Redo the previously undone action");
		
		JMenuItem deleteTask = new JMenuItem("Delete Task");
		editMenu.add(deleteTask);
		deleteTask.setToolTipText("Delete the selected task");
		
		JMenuItem clearAllTasks = new JMenuItem("Clear All Tasks");
		editMenu.add(clearAllTasks);
		clearAllTasks.setToolTipText("Remove all tasks");
		
		return editMenu;
	}
	
	// ========== VIEW MENU ==========
	private JMenu setupViewMenu() {
		JMenu viewMenu = new JMenu("View");
		
		JMenuItem showCompletedTasks = new JMenuItem("Show Completed Tasks");
		viewMenu.add(showCompletedTasks);
		showCompletedTasks.setToolTipText("Toggle view for completed tasks");
		
		JMenuItem sortByDeadline = new JMenuItem("Sort by Deadline");
		viewMenu.add(sortByDeadline);
		sortByDeadline.setToolTipText("Sort tasks by due date");
		
		JMenuItem sortByPriority = new JMenuItem("Sort by Priority");
		viewMenu.add(sortByPriority);
		sortByPriority.setToolTipText("Sort tasks by priority");
		
		return viewMenu;
	}
	
	// ========== HELP MENU =========
	private JMenu setupHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem howToUse = new JMenuItem("How to Use");
		helpMenu.add(howToUse);
		howToUse.setToolTipText("Basic guide/instructions for using the app");
		
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		about.setToolTipText("App version and other info.");
		
		return helpMenu;
	}
}
