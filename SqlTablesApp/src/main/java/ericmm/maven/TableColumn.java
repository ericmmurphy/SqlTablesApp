package ericmm.maven;

import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TableColumn {
	Body body;
	JPanel panel;
	
	TableColumn(Body body) {
		this.body = body;
		this.setPanelValues();
	}

	public void setPanelValues() {
		this.panel = new JPanel();
		this.panel.setBackground(StyleFactory.getBackgroundColorLight());
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
	}
	
	public void addTableCellPanels(LinkedList<TableCell> tableCells) {
		for (TableCell tableCell : tableCells) {
			this.panel.add(tableCell.panel);
		}
	}
}
