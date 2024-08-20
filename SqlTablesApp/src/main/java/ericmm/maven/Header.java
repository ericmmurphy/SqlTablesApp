package ericmm.maven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Header {
	Window window;
	LinkedList<TableColumnHeading> columnHeadings;
	JPanel panel;
	JLabel label;
	
	Header(Window window) {
		this.window = window;
		this.columnHeadings = new LinkedList<TableColumnHeading>();
		this.setLabelValues();
		this.setPanelValues();
	}
	
	public void setLabelValues() {
		this.label = new JLabel();
		this.label.setText("Tables");
		this.label.setFont(new Font("Arial", Font.PLAIN, 24));
		this.label.setForeground(new Color(0x1b1e29));
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setVerticalAlignment(SwingConstants.CENTER);
		this.label.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		this.panel.setBackground(new Color(0xf6f8fd));
		this.panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x677090)));
		this.panel.add(this.label);
	}
	
	/*
	public void addColumnHeading(TableColumnHeading columnHeading) {
		this.columnHeadings.add(columnHeading);
	}
	
	public void addColumnHeading(String name, String type, int position) {
		this.columnHeadings.add(new TableColumnHeading(this, name, type, position));
	}
	*/
}
