package ericmm.maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Header implements TableLoadedObserver {
	Window window;
	TableColumnHeading[] columnHeadings;
	JPanel panel;
	JPanel labelPanel;
	JLabel label;
	JPanel columnPanel;
	JPanel fillerPanel;
	
	Header(Window window) {
		this.window = window;
		
		this.setLabelValues();
		this.setLabelPanelValues();
		this.setFillerPanelValues();
		this.setColumnPanelValues();
		this.setPanelValues();
	}
	
	public void setLabelValues() {
		this.label = new JLabel();
		this.label.setText("Tables");
		this.label.setFont(StyleFactory.getHeaderFont());
		this.label.setForeground(StyleFactory.getForegroundColor());
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setVerticalAlignment(SwingConstants.CENTER);
		this.label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	}
	
	public void setLabelPanelValues() {
		this.labelPanel = new JPanel();
		this.labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.labelPanel.setBackground(StyleFactory.getBackgroundColorLight());
		this.labelPanel.add(this.label);
	}
	
	public void setColumnPanelValues() {
		this.columnPanel = new JPanel();
	}
	
	public void setFillerPanelValues() {
		this.fillerPanel = new JPanel();
		this.fillerPanel.setPreferredSize(new Dimension(17, 0));
		this.fillerPanel.setBackground(StyleFactory.getBackgroundColorLight());
		this.fillerPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, StyleFactory.getBorderColorLight()));
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBackground(StyleFactory.getBackgroundColorLight());
		this.panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, StyleFactory.getBorderColorLight()));
		this.panel.add(this.labelPanel);
		this.panel.add(this.fillerPanel, BorderLayout.EAST);
	}
	
	public void update() {
		this.panel.revalidate();
		this.panel.repaint();
	}
	
	public void swapToMenuHeader() {

	}
	
	public void removeMenuHeaderComponents() {
		Utilities.removeComponents(this.label, this.panel);
	}
	
	public void populateColumnHeadings() {
		for (String tableNameKey : this.window.tableInfo.keySet()) {
			this.columnHeadings = new TableColumnHeading[this.window.tableInfo.get(tableNameKey).size()];
			for (TableColumnHeading headingKey : this.window.tableInfo.get(tableNameKey).keySet()) {
				this.columnHeadings[headingKey.position] = headingKey;
			}
		}
	}
	
	public void setColumnPanelLayout() {
		this.columnPanel.setLayout(new GridLayout(1,this.columnHeadings.length,0,0));
	}
	
	public void addTableHeaderComponents() {
		for (int i = 0; i < this.columnHeadings.length; i++) {
			this.columnPanel.add(this.columnHeadings[i].panel);
			if (i < this.columnHeadings.length - 1) {
				this.columnHeadings[i].setPanelBorder();
			}
		}
		this.panel.add(this.columnPanel);
		this.panel.add(this.fillerPanel, BorderLayout.EAST);
	}
	
	public void swapToTableHeader() {
		this.removeMenuHeaderComponents();
		this.setColumnPanelLayout();
		this.addTableHeaderComponents();
		this.update();
	}
	
	public void tableLoadedCallback() {
		this.populateColumnHeadings();
		this.swapToTableHeader();
	}
}
