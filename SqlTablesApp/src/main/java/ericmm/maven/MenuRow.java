package ericmm.maven;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuRow implements ActionListener {
	Body body;
	RenameTableMenu renameMenu;
	String tableName;
	int position;
	JPanel panel;
	JButton select;
	JButton rename;
	JButton delete;
	Component rigidArea;
	
	MenuRow(Body body, String tableName, int position) {
		this.body = body;
		this.renameMenu = new RenameTableMenu(this);
		this.tableName = tableName;
		this.position = position;
		
		this.setSelectButtonValues();
		this.setRenameButtonValues();
		this.setDeleteButtonValues();
		this.setPanelValues();
		this.setRigidArea();
	}
	
	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setBackground(new Color(0xf6f8fd));
		this.panel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x1b1e29)), 
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.LINE_AXIS));
		this.panel.add(this.select);
		this.panel.add(Box.createHorizontalGlue());
		this.panel.add(this.rename);
		this.panel.add(Box.createRigidArea(new Dimension(2, 0)));
		this.panel.add(this.delete);
	}
	
	public void setSelectButtonValues() {
		this.select = new JButton();
		this.select.setText(this.tableName);
		this.select.setForeground(new Color(0x1b1e29));
		this.select.setBackground(new Color(0xf6f8fd));
		this.select.setFocusable(false);
		this.addSelectButtonActionListener();
	}
	
	public void setRenameButtonValues() {
		this.rename = new JButton();
		this.rename.setText("rename");
		this.rename.setForeground(new Color(0x1b1e29));
		this.rename.setBackground(new Color(0xf6f8fd));
		this.rename.setFocusable(false);
		this.addRenameButtonActionListener();
	}
	
	public void setDeleteButtonValues() {
		this.delete = new JButton();
		this.delete.setText("delete");
		this.delete.setForeground(new Color(0x1b1e29));
		this.delete.setBackground(new Color(0xf6f8fd));
		this.delete.setFocusable(false);
		this.addDeleteButtonActionListener();
	}
	
	public void setRigidArea() {
		this.rigidArea = Box.createRigidArea(new Dimension(0, 10));
	}
	
	public void decrementPosition() {
		this.position--;
	}
	
	public void renameTable(String newTableName) {
		this.tableName = newTableName;
		this.select.setText(this.tableName);
	}
	
	public void addSelectButtonActionListener() {
		this.select.addActionListener(this);
	}
	
	public void addRenameButtonActionListener() {
		this.rename.addActionListener(this);
	}
	
	public void addDeleteButtonActionListener() {
		this.delete.addActionListener(this);
	}
	
	public void addAllActionListeners() {
		this.addSelectButtonActionListener();
		this.addRenameButtonActionListener();
		this.addDeleteButtonActionListener();
	}
	
	public void removeSelectButtonActionListener() {
		this.select.removeActionListener(this);
	}
	
	public void removeRenameButtonActionListener() {
		this.rename.removeActionListener(this);
	}
	
	public void removeDeleteButtonActionListener() {
		this.delete.removeActionListener(this);
	}
	
	public void removeAllActionListeners() {
		this.removeSelectButtonActionListener();
		this.removeRenameButtonActionListener();
		this.removeDeleteButtonActionListener();
	}
	
	public void openRenameMenu() {
		this.body.swapMenuRowWithRenameMenuRow(this.renameMenu);
		this.renameMenu.addCreateButtonActionListener();
		this.renameMenu.addConfirmButtonActionListener();
		this.body.update();
	}
	
	public void selectTable() {
		//System.out.println(this.select.getText());
		this.body.window.selectTable(this.select.getText());
		//this.body.openTable(this.panel, this.body.panel);
	}
	
	public void tableDeletion() {
		if (this.tableDeletionConfirmation()) {
			this.deleteTable();
		}
	}
	
	public boolean tableDeletionConfirmation() {
		// YES = 0; NO = 1; CANCEL = -1
		int responseCode = -1;
		responseCode = JOptionPane.showConfirmDialog(null,
			"Are you sure you want to delete the " + this.tableName + " table?",
			"Table Deletion",
			JOptionPane.YES_NO_OPTION);

		if (responseCode == 0) {
			return true;	
		}
		return false;
	}
	
	public void deleteTable() {
		try {
			this.body.dropTable(this);
			this.removeAllActionListeners();
			this.body.removeRowFromList(this);
			this.body.updateListAfterRowRemoval();
			this.body.repopulateRows();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.select) {
			//this.removeAllActionListeners();
			this.selectTable();
		} else if (event.getSource() == this.rename) {
			this.removeAllActionListeners();
			this.openRenameMenu();
		} else if (event.getSource() == this.delete) {
			this.tableDeletion();
		}
	}
}
