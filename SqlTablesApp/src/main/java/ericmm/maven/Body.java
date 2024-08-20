package ericmm.maven;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Body {
	Window window;
	LinkedList<MenuRow> rows;
	MenuCreateRow createRow;
	LinkedList<GridLayout> panelLayouts;
	JScrollPane scrollPane;
	JPanel panel;
	JPanel leftPanel;
	JPanel centerPanel;
	JPanel rightPanel;
	
	Body(Window window) {
		this.window = window;
		this.rows = new LinkedList<MenuRow>();
		
		this.populateRows();
		this.setLeftPanelValues();
		this.setCenterPanelValues();
		this.setRightPanelValues();
		this.addRowsToColumnPanels();
		this.setPanelLayouts();
		this.setPanelValues();
		this.setScrollPaneValues();
	}
	
	public void update() {
		this.panel.revalidate();
		this.panel.repaint();
	}
	
	public void populateRows() {
		try {
			ResultSet tableNames = this.window.db.getTableNames();
			int count = 0;
			while(tableNames.next()) {
				String name = tableNames.getString("name");
				//System.out.println(name);
				this.rows.add(new MenuRow(this, name, count));
				count++;
			}
			this.createRow = new MenuCreateRow(this);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void repopulateRows() {
		this.leftPanel.removeAll();
		this.centerPanel.removeAll();
		this.rightPanel.removeAll();
		this.addRowsToColumnPanels();
		this.update();
	}
	
	public boolean tableExists(String tableName) throws SQLException {
		try {
			return this.window.db.tableExists(tableName);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	public void selectTable(String tableName) {
		try {
			ResultSet results = this.window.db.getHeaderNames(tableName);
			String name, type;
			
			//Map<TableColumnHeading, List<TableCell>>;
			
			//ResultSet results = this.window.db.selectTable(tableName);
			// results.getString("type") == "NULL" | "INTEGER" | "REAL" | "TEXT" | "BLOB"
			// SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
			// https://www.sqlite.org/datatype3.html
			while (results.next()) {
				name = results.getString("name");
				type = results.getString("type");
				
				
				if (name.equals("id")) {
					System.out.println("TRUE");
				}
				System.out.println(name + " " + type);
			}
			
			ResultSet values = this.window.db.selectTable(tableName);
			while (values.next()) {
				//name = values.getString("name");
				//type = values.getString("type");
				
				
				if (name.equals("id")) {
					System.out.println("TRUE");
				}
				System.out.println(values.getString("name"));
			}
		} catch (SQLException e) {
			//throw e;
		}
	} */
	
	
	public boolean createNewTable(String tableName) throws SQLException {
		if (!this.tableExists(tableName)) {
			try {
				this.window.db.createTable(tableName);
				return true;
			} catch (SQLException e) {
				throw e;
			}
		}
		return false;
	}
	
	public boolean renameTable(String oldTableName, String newTableName) throws SQLException {
		if (!this.tableExists(newTableName)) {
			try {
				this.window.db.renameTable(oldTableName, newTableName);
				return true;
			} catch (SQLException e) {
				throw e;
			}
		}
		return false;
	}
	
	public void dropTable(MenuRow row) throws SQLException {
		try {
			this.window.db.dropTable(row.tableName);
		} catch (SQLException e) {
			throw e;
		}
	}
		
	public void addRowToList(MenuRow row) {
		this.rows.add(row);
	}
	
	public void addRowToList(String name) {
		this.rows.add(new MenuRow(this, name, this.rows.size()));
	}
	
	/*
	public void addRowToList(String name, int position) {
		this.rows.add(new MenuRow(this, name, position));
	}
	*/
	
	// Will have to write own LinkedList, if I want better time complexity
	public void removeRowFromList(MenuRow row) {
		if (row.position == this.rows.size() - 1) {
			this.rows.removeLast();
		} else {
			this.rows.remove(row);
		}
	}
	
	public void updateListAfterRowRemoval() {
		MenuRow prevRow = null;
		for (MenuRow currRow : this.rows) {
			if (prevRow != null && currRow.position - prevRow.position > 1) {
				currRow.decrementPosition();
			}
			prevRow = currRow;
		}		
	}
	
	public JPanel getColumnPanel(int position) {
		if (position % 3 == 0) {
			return this.leftPanel;
		} else if (position % 3 == 1) {
			return this.centerPanel;
		} else {
			return this.rightPanel;
		}		
	}
	
	public void addRowsToColumnPanels() {
		for(MenuRow row : this.rows) {
			this.getColumnPanel(row.position).add(row.panel);
			this.getColumnPanel(row.position).add(row.rigidArea);
		}
		this.addCreateTableRow();
	}
	
	public void addMenuRow(MenuRow row) {
		this.getColumnPanel(row.position).add(row.panel);
		this.getColumnPanel(row.position).add(row.rigidArea);		
	}
	
	public void addLastMenuRow() {
		MenuRow lastRow = this.rows.getLast();
		this.getColumnPanel(lastRow.position).add(lastRow.panel);
		this.getColumnPanel(lastRow.position).add(lastRow.rigidArea);		
	}
	
	public void addCreateTableRow() {
		this.getColumnPanel(this.rows.size()).add(this.createRow.panel);
	}
	
	public void swapMenuRowWithRenameMenuRow(RenameTableMenu menu) {
		JComponent finalComponent = this.getColumnPanel(menu.row.position);
		int count = 0;
		for (Component curr : finalComponent.getComponents()) {
			if (menu.panel == curr) {
				System.out.println("" + count);
				finalComponent.remove(count);
				finalComponent.add(menu.row.panel, count);				
				break;
			} else if (menu.row.panel == curr) {
				System.out.println("" + count);
				finalComponent.remove(count);
				finalComponent.add(menu.panel, count);
				break;
			}
			count++;
		}
	}
	
	public void removeComponents(JComponent start, JComponent finish) {
		JComponent prevComponent = null;
		JComponent currComponent = start;
		JComponent finalComponent = (JComponent)finish.getParent();
				
		while (currComponent != finalComponent) {
			System.out.println(currComponent);
						
			if (prevComponent != null) {
				currComponent.removeAll();
			}
			
			prevComponent = currComponent;
			currComponent = (JComponent)currComponent.getParent();
		}
	}
	
	public void openTable(JComponent start, JComponent finish) {
		this.removeComponents(start, finish);
		this.swapPanelLayouts();
		this.update(); // CAN REMOVE AFTER ADD MENU PART
	}
	
	public void removeCreateRow() {
		JComponent finalComponent = this.getColumnPanel(this.rows.size());
		finalComponent.remove(this.createRow.panel);
	}
	
	public void setLeftPanelValues() {
		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.PAGE_AXIS));
		this.leftPanel.setBackground(new Color(0xf6f8fd));
		this.leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		//this.leftPanel.setBackground(Color.red);
	}
	
	public void setCenterPanelValues() {
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.PAGE_AXIS));
		this.centerPanel.setBackground(new Color(0xf6f8fd));
		this.centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		//this.rightPanel.setBackground(Color.blue);		
	}
	
	public void setRightPanelValues() {
		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.PAGE_AXIS));
		this.rightPanel.setBackground(new Color(0xf6f8fd));
		this.rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		//this.rightPanel.setBackground(Color.blue);		
	}
	
	public void setPanelLayouts() {
		this.panelLayouts = new LinkedList<GridLayout>();
		this.panelLayouts.add(new GridLayout(1, 3, 10, 10));
		this.panelLayouts.add(new GridLayout(1, 1, 10, 10));
	}
	
	public void swapPanelLayouts() {
		if (this.panel.getLayout() == this.panelLayouts.getFirst()) {
			this.panel.setLayout(this.panelLayouts.getLast());
		} else {
			this.panel.setLayout(this.panelLayouts.getFirst());
		}
	}

	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(this.panelLayouts.getFirst());
		this.panel.setBackground(new Color(0xf6f8fd));
		this.panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.panel.add(this.leftPanel);
		this.panel.add(this.centerPanel);
		this.panel.add(this.rightPanel);
	}
	
	public void setScrollPaneValues() {
		this.scrollPane = new JScrollPane(this.panel);
		this.scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0x677090)));
		this.scrollPane.getVerticalScrollBar().setBackground(new Color(0xf6f8fd));
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}
