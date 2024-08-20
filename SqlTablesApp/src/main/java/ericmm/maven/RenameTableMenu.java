package ericmm.maven;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JOptionPane;

public class RenameTableMenu extends AbstractMenuRow implements ActionListener {
	MenuRow row;
	
	RenameTableMenu(MenuRow row) {
		super();
		this.row = row;
		this.setCreateButtonValues();
		this.setTextFieldValues();
		this.setConfirmButtonValues();
		this.setPanelValues();
	}
	
	public void setPanelValues() {
		super.setPanelValues();
		this.panel.add(this.create);
		this.panel.add(Box.createHorizontalGlue());
		this.panel.add(this.textField);
		this.panel.add(this.confirm);
	}
	
	public void setCreateButtonValues() {
		super.setCreateButtonValues();
		super.setCreateButtonText("cancel");
		super.setCreateButtonMargins("cancel");
	}
	
	public void addCreateButtonActionListener() {
		this.create.addActionListener(this);
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
	
	public void cancelRename() {
		this.removeCreateButtonActionListener();
		this.removeConfirmButtonActionListener();
		this.row.body.swapMenuRowWithRenameMenuRow(this);
		this.row.addAllActionListeners();
		this.row.body.update();
	}
	
	public void confirmRename(String newTableName) {
		try {
			boolean renameSuccess = this.row.body.renameTable(this.row.tableName, newTableName);
			if (renameSuccess) {
				this.row.renameTable(newTableName);
				this.clearTextField();
				this.removeCreateButtonActionListener();
				this.removeConfirmButtonActionListener();
				this.row.body.swapMenuRowWithRenameMenuRow(this);
				this.row.addAllActionListeners();
				this.row.body.update();				
			} else {
				this.clearTextField();
				JOptionPane.showMessageDialog(null, "That table name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			this.clearTextField();
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.create) {
			this.cancelRename();
		} else if (event.getSource() == this.confirm) {
			String text = this.textField.getText();
			if (text.equals("")) {
				return;
			}
			this.confirmRename(text);
		}
	}
}
