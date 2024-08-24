package ericmm.maven;

import java.awt.Component;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class Body implements TableLoadedObserver {
	Window window;
	/* Menu */
	LinkedList<MenuRow> rows;
	MenuCreateRow createRow;
	/* Table */
	LinkedList<TableColumn> tableColumns;
	LinkedList<GridLayout> panelLayouts;
	LinkedList<Border> panelBorders;
	JScrollPane scrollPane;
	JPanel panel;
	JPanel leftPanel;
	JPanel centerPanel;
	JPanel rightPanel;
	
	Body(Window window) {
		this.window = window;
		this.rows = new LinkedList<MenuRow>();
		this.tableColumns = new LinkedList<TableColumn>();
		
		this.populateRows();
		this.setLeftPanelValues();
		this.setCenterPanelValues();
		this.setRightPanelValues();
		this.addRowsToColumnPanels();
		this.setPanelLayouts();
		this.setPanelBorders();
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
				//System.out.println("" + count);
				finalComponent.remove(count);
				finalComponent.add(menu.row.panel, count);				
				break;
			} else if (menu.row.panel == curr) {
				//System.out.println("" + count);
				finalComponent.remove(count);
				finalComponent.add(menu.panel, count);
				break;
			}
			count++;
		}
	}
	
	public void tableLoadedCallback() {
		try {
			Utilities.removeComponents(this.rows.getFirst().panel, this.panel);
			this.removeTableLayout();
			this.addTableLayout();
			this.swapPanelLayouts();
			this.swapPanelBorders();
			this.createTableColumns();
			this.addTableCellPanelsToTableColumnPanels();
			this.addTableColumnPanelsToPanel();		
			this.update(); // CAN REMOVE AFTER ADD MENU PART			
		} catch (Exception e) {
			//
		}
	}
	
	public void removeCreateRow() {
		JComponent finalComponent = this.getColumnPanel(this.rows.size());
		finalComponent.remove(this.createRow.panel);
	}
	
	public void setLeftPanelValues() {
		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.PAGE_AXIS));
		this.leftPanel.setBackground(StyleFactory.getBackgroundColorLight());
		this.leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	}
	
	public void setCenterPanelValues() {
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.PAGE_AXIS));
		this.centerPanel.setBackground(StyleFactory.getBackgroundColorLight());
		this.centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	}

	public void setRightPanelValues() {
		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.PAGE_AXIS));
		this.rightPanel.setBackground(StyleFactory.getBackgroundColorLight());
		this.rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	}
	
	public void setPanelLayouts() {
		this.panelLayouts = new LinkedList<GridLayout>();
		this.panelLayouts.add(new GridLayout(1, 3, 10, 10));
	}
	
	public void setPanelBorders() {
		this.panelBorders = new LinkedList<Border>();
		this.panelBorders.add(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.panelBorders.add(
			BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 0, StyleFactory.getBorderColorLight()), 
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));
	}
	
	public void removeTableLayout() {
		if (this.panelLayouts.size() > 1) {
			this.panelLayouts.removeLast();			
		}
	}
	
	public void addTableLayout() throws Exception {
		this.panelLayouts.add(new GridLayout(1, this.window.getTableColumnCount(), 0, 0));
	}
	
	public void swapPanelLayouts() {
		this.panel.setLayout(this.panelLayouts.getLast());
	}
	
	public void swapPanelBorders() {
		if (this.panel.getBorder() == this.panelBorders.getFirst()) {
			this.panel.setBorder(this.panelBorders.getLast());
		} else {
			this.panel.setBorder(this.panelBorders.getFirst());
		}
	}
	
	public void createTableColumns() throws Exception {
		for(int i = 0; i < this.window.getTableColumnCount(); i++) {
			this.tableColumns.add(new TableColumn(this));
		}
	}
	
	public void addTableCellPanelsToTableColumnPanels() {
		for (String tableNameKey : this.window.tableInfo.keySet()) {
			for (TableColumnHeading headingKey : this.window.tableInfo.get(tableNameKey).keySet()) {
				this.tableColumns.get(headingKey.position).addTableCellPanels(this.window.tableInfo.get(tableNameKey).get(headingKey));
			}
		}
	}
	
	public void addTableColumnPanelsToPanel() {
		for (TableColumn column : this.tableColumns) {
			this.panel.add(column.panel);
		}
	}

	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(this.panelLayouts.getFirst());
		this.panel.setBackground(StyleFactory.getBackgroundColorLight());
		this.panel.setBorder(this.panelBorders.getFirst());
		this.panel.add(this.leftPanel);
		this.panel.add(this.centerPanel);
		this.panel.add(this.rightPanel);
	}
	
	public void setScrollPaneValues() {
		this.scrollPane = new JScrollPane(this.panel);
		this.scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, StyleFactory.getBorderColorLight()));
		this.scrollPane.getVerticalScrollBar().setBackground(StyleFactory.getBackgroundColorLight());
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}
