package ericmm.maven;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TableColumnHeading {
	Header header;
	String name;
	String type;
	int position;
	JPanel panel;
	JLabel label;
	
	TableColumnHeading(Header header, String name, String type, int position) {
		this.header = header;
		this.name = name;
		this.type = type;
		this.position = position;
		this.setLabelValues();
		this.setPanelValues();
	}
	
	public void setLabelValues() {
		this.label = new JLabel();
		this.label.setText(name);
		this.label.setFont(StyleFactory.getSubHeaderFont());
		this.label.setForeground(StyleFactory.getForegroundColorLight());
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setVerticalAlignment(SwingConstants.CENTER);
		this.label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.panel.setBackground(StyleFactory.getBackgroundColorLight());
		this.panel.add(this.label);
	}
	
	public void setPanelBorder() {
		this.panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, StyleFactory.getBorderColorLight()));		
	}
}
