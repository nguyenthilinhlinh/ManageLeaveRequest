package component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import constants.UIConstants;

public class StyledTable extends JTable {

	private static final long serialVersionUID = 7293486670405215663L;
	
	public StyledTable() {
		this.decorateTable();
	}
	
	public StyledTable(TableModel model) {
		super(model);
		this.decorateTable();
	}
	
	private void decorateTable() {
		// Set Font for Table Content
        this.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, UIConstants.FONT_SIZE));
        this.setRowHeight(UIConstants.TABLE_ROW_HEIGHT);

        // Set Font for Header
        JTableHeader header = this.getTableHeader();
        header.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, UIConstants.FONT_SIZE));

        // Set Grid Color
        this.setGridColor(Color.LIGHT_GRAY);

        // Set Selection Behavior
        this.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        this.setRowSelectionAllowed(true);
        this.setAutoCreateRowSorter(true);
        this.setColumnSelectionAllowed(false);
	}
}