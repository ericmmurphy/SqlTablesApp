package ericmm.maven;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JOptionPane;

public class MenuCreateRow extends AbstractMenuRow implements ActionListener {
	Body body;
	
	MenuCreateRow(Body body) {
		super();
		
		this.body = body;
		this.setCreateButtonValues();
		this.setPanelValues();
	}
	
	public void setPanelValues() {
		super.setPanelValues();
		this.panel.add(this.create);
		this.panel.add(Box.createHorizontalGlue());
	}
	
	public void setCreateButtonValues() {
		super.setCreateButtonValues();
		super.setCreateButtonText("create table");
		super.setCreateButtonMargins("create table");
		this.create.addActionListener(this);
	}
	
	public void changeCreateButtonStatus() {
		if (this.create.getText().equals("create table")) {
			this.create.setText("cancel");
			//this.create.setMargin(new Insets(5,23,5,23));
			this.create.setMargin(new Insets(3,23,3,23));
		} else {
			this.create.setText("create table");
			//this.create.setMargin(new Insets(5,8,5,8));
			this.create.setMargin(new Insets(3,8,3,8));
		}
	}
	
	public void removeTextFieldComponent() {
		this.panel.remove(this.textField);
	}
	
	public void removeConfirmButtonComponent() {
		this.panel.remove(this.confirm);
	}
	
	public void addConfirmButtonActionListener() {
		this.confirm.addActionListener(this);
	}
	
	public void removeCreateButtonActionListener() {
		this.create.removeActionListener(this);
	}
	
	public void removeConfirmButtonActionListener() {
		this.confirm.removeActionListener(this);
	}
	
	public void startTableCreation() {
		this.changeCreateButtonStatus();
		this.setTextFieldValues();
		this.setConfirmButtonValues();
		this.addConfirmButtonActionListener();
		this.panel.add(this.textField);
		this.panel.add(this.confirm);
	}
	
	public void cancelTableCreation() {
		this.changeCreateButtonStatus();
		this.removeConfirmButtonActionListener();
		this.removeTextFieldComponent();
		this.removeConfirmButtonComponent();
		this.body.update();
	}
	
	public void confirmTableCreation() {
		String text = this.textField.getText();
		if(text.equals("")) return;
		
		System.out.println(text);
		try {
			boolean tableCreationSuccess = this.body.createNewTable(text);
			if (tableCreationSuccess) {
				// Success
				this.clearTextField();
				this.removeConfirmButtonActionListener();
				this.body.removeCreateRow();
				this.body.addRowToList(text);
				this.body.addLastMenuRow();
				this.body.addCreateTableRow();
				this.changeCreateButtonStatus();
				this.removeTextFieldComponent();
				this.removeConfirmButtonComponent();
				//this.body.update();
			} else {
				// Fail
				this.clearTextField();
				JOptionPane.showMessageDialog(null, "That table already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			this.clearTextField();
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.create) {
			if (this.create.getText().equals("create table")) {
				this.startTableCreation();
			} else if (this.create.getText().equals("cancel")) {
				this.cancelTableCreation();
			}
		} else if (event.getSource() == this.confirm) {
			this.confirmTableCreation();
		}
	}
}