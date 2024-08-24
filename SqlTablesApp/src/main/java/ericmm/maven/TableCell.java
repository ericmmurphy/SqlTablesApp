package ericmm.maven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TableCell {
	TableColumnHeading heading;
	String value;
	int id;
	JPanel panel;
	JTextField textField;
	
	TableCell(TableColumnHeading heading, String value, int id) {
		this.heading = heading;
		this.value = value;
		this.id = id;
		
		this.setTextFieldValues();
		this.setPanelValues();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setTextFieldValues() {
		this.textField = new JTextField();
		this.textField.setText(this.value);
		this.textField.setHorizontalAlignment(SwingConstants.CENTER);
		this.textField.setForeground(StyleFactory.getForegroundColor());
		this.textField.setBackground(StyleFactory.getBackgroundColorLight());
		this.textField.setMargin(new Insets(0,4,0,4));
		this.textField.setEditable(true);
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setMaximumSize(new Dimension(2000, 40));
		this.panel.setLayout(new BorderLayout());
		this.panel.setBackground(Color.BLUE);
		this.panel.add(this.textField, BorderLayout.CENTER);
	}
}
