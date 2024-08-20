package ericmm.maven;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TableCell {
	//Header header;
	String name;
	String type;
	JPanel panel;
	JTextField textField;
	
	TableCell(String name, String type) {
		//this.header = header;
		this.name = name;
		this.type = type;
	}
}
