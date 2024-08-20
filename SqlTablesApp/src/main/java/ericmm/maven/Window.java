package ericmm.maven;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	Database db;
	Header header;
	Body body;
	JFrame frame;
	JPanel panel;
	
	Window(Database db) {
		this.db = db;
		this.header = new Header(this);
		this.body = new Body(this);
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
	
	public void selectTable(String tableName) {
		/**
		 * results.getString("type") == "NULL" | "INTEGER" | "REAL" | "TEXT" | "BLOB"
		 * SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
		 * https://www.sqlite.org/datatype3.html
		 */
		try {
			ResultSet results = this.db.getHeaderNames(tableName);
			String name, type;
			
			//Map<TableColumnHeading, List<TableCell>>;
			
			while (results.next()) {
				name = results.getString("name");
				type = results.getString("type");
				
				if (!name.equals("id")) {
					//this.columnHeadings.add(new TableColumnHeading(this, name, type));
					System.out.println(name + " " + type);
				}
			}
		} catch (SQLException e) {
			//throw e;
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

/*
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Menu {
	Window window;
	MenuHeader header;
	MenuBody body;
	JPanel panel;
	
	Menu(Window window) {
		this.window = window;
		this.header = new MenuHeader(this);
		this.body = new MenuBody(this);
		this.setPanelValues();
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.header.panel, BorderLayout.NORTH);
		this.panel.add(this.body.scrollPane, BorderLayout.CENTER);
	}
}
*/