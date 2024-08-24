package ericmm.maven;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

abstract public class AbstractMenuRow {
	JPanel panel;
	JButton create;
	JButton confirm;
	JTextField textField;
	
	AbstractMenuRow() {}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setBackground(StyleFactory.getBackgroundColorLight());
		this.panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 1, 0));
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.LINE_AXIS));
	}
	
	public void setCreateButtonValues() {
		this.create = new JButton();
		this.create.setHorizontalAlignment(SwingConstants.CENTER);
		this.create.setVerticalAlignment(SwingConstants.CENTER);
		this.create.setForeground(StyleFactory.getForegroundColor());
		this.create.setBackground(StyleFactory.getBackgroundColorLight());
		this.create.setFocusable(false);
	}
	
	public void setConfirmButtonValues() {
		this.confirm = new JButton();
		this.confirm.setText("OK");
		this.confirm.setHorizontalAlignment(SwingConstants.CENTER);
		this.confirm.setVerticalAlignment(SwingConstants.CENTER);
		this.confirm.setForeground(StyleFactory.getForegroundColor());
		this.confirm.setBackground(StyleFactory.getBackgroundColorLight());
		this.confirm.setMargin(new Insets(3,8,3,8));
		this.confirm.setFocusable(false);
	}
	
	public void setTextFieldValues() {
		this.textField = new JTextField();
		this.textField.setPreferredSize(new Dimension(377, 0));
		this.textField.setMaximumSize(new Dimension(0, 29));
		this.textField.setForeground(StyleFactory.getForegroundColor());
		this.textField.setBackground(StyleFactory.getBackgroundColorLight());
		this.textField.setMargin(new Insets(0,4,0,4));
		this.textField.setEditable(true);
	}
	
	public void setCreateButtonText(String text) {
		if (text.equals("create table")) {
			this.create.setText("create table");
		} else {
			this.create.setText("cancel");
		}
	}
	
	public void setCreateButtonMargins(String text) {
		if (text.equals("create table")) {
			this.create.setMargin(new Insets(3,8,3,8));
		} else {
			this.create.setMargin(new Insets(3,23,3,23));
		}
	}
	
	public void clearTextField() {
		this.textField.setText("");
	}
}
