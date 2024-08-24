package ericmm.maven;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	Database db;
	Header header;
	Body body;
	LinkedList<TableLoadedObserver> tableLoadedObservers;
	HashMap<String, HashMap<TableColumnHeading, LinkedList<TableCell>>> tableInfo;
	JFrame frame;
	JPanel panel;
	
	Window(Database db) {
		this.db = db;
		this.header = new Header(this);
		this.body = new Body(this);
		this.tableLoadedObservers = new LinkedList<TableLoadedObserver>();
		this.addTableLoadedObservers();
		this.tableInfo = new HashMap<String, HashMap<TableColumnHeading, LinkedList<TableCell>>>();
		
		this.setPanelValues();
		this.setFrameValues();
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.header.panel, BorderLayout.NORTH);
		this.panel.add(this.body.scrollPane, BorderLayout.CENTER);
	}
	
	public void setFrameValues() {
		this.frame = new JFrame();
		this.frame.setSize(1600,900);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(this.panel);
		this.frame.setVisible(true);
	}
	
	public int getTableColumnCount() throws Exception {
		for (String tableNameKey : this.tableInfo.keySet()) {
			return this.tableInfo.get(tableNameKey).keySet().size();
		}
		throw new Exception("There's no table currently loaded");
	}
	
	// Remove components first!
	public void clearTable() {
		if (this.tableInfo.isEmpty()) return;
		//System.out.println(this.tableInfo.size());
				
		for (String tableNameKey : this.tableInfo.keySet()) {
			for (TableColumnHeading headingKey : this.tableInfo.get(tableNameKey).keySet()) {
				this.tableInfo.get(tableNameKey).get(headingKey).clear();
			}
			this.tableInfo.get(tableNameKey).clear();
		}
		this.tableInfo.clear();
		//System.out.println(this.tableInfo.size());
	}
	
	public void loadTable(String tableName) throws SQLException {
		this.clearTable();
		/**
		 * results.getString("type") == "NULL" | "INTEGER" | "REAL" | "TEXT" | "BLOB"
		 * SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
		 * https://www.sqlite.org/datatype3.html
		 */
		try {
			ResultSet headerNamesResultSet = this.db.getHeaderNames(tableName);
			HashMap<TableColumnHeading, LinkedList<TableCell>> tableMap = new HashMap<TableColumnHeading, LinkedList<TableCell>>();
			String name, type;
			int count = 0;
			
			while (headerNamesResultSet.next()) {
				name = headerNamesResultSet.getString("name");
				type = headerNamesResultSet.getString("type");
				
				if (!name.equals("id")) {
					//System.out.println(name + " " + type);
					tableMap.put(new TableColumnHeading(this.header, name, type, count), new LinkedList<TableCell>());
					count++;
				}
			}
			
			ResultSet valuesResultSet = this.db.selectTable(tableName);
			
			while (valuesResultSet.next()) {
				for (TableColumnHeading headingKey : tableMap.keySet()) {
					tableMap.get(headingKey).add(
						new TableCell(headingKey, valuesResultSet.getString(headingKey.name), Integer.parseInt(valuesResultSet.getString("id"))));
				}
			}
			this.tableInfo.put(tableName, tableMap);
		} catch (SQLException e) {
			throw e;
		}
		this.notifyTableLoadedObservers();
	}
	
	public void addTableLoadedObservers() {
		this.tableLoadedObservers.add(this.header);
		this.tableLoadedObservers.add(this.body);
	}
	
	public void notifyTableLoadedObservers() {
		for (TableLoadedObserver obs : this.tableLoadedObservers) {
			obs.tableLoadedCallback();
		}
	}
	
	/*
	public void setAbsoluteSizingValues() {
		this.width = this.frame.getContentPane().getWidth();
		this.height = this.frame.getContentPane().getHeight();
		this.width = 1584;
		this.height = 861;
	}
	*/
}