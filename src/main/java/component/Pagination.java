package component;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;



public class Pagination extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnFirst;
	private JButton btnPrivious;
	private JButton btnNext;
	private JButton btnLast;
	private Integer pageNumber = 1; //Trang thu may
	private Integer rowOfPage = 10; // So dong mac dinh trong trang
	private Integer totalRow = 0; // so dong trong database
	private Double totalPage = 1.0; // Tong so trang
	private JTextField txtPage;
	/**
	 * Create the panel.
	 */
	
	
	
	public Pagination() {
		setLayout(new MigLayout("", "[105.00][107.00][][105.00][105.00]", "[30.00][]"));
		
		btnFirst = new JButton("<< First");
		btnFirst.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		add(btnFirst, "cell 0 1,grow");
		
		btnPrivious = new JButton("< Privious");
		btnPrivious.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		add(btnPrivious, "cell 1 1,grow");
		
		txtPage = new JTextField();
		txtPage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPage.setText("1");
		txtPage.setHorizontalAlignment(SwingConstants.CENTER);
		txtPage.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.textHighlight));
		txtPage.setBackground(new Color(240, 240, 240));
		add(txtPage, "cell 2 1,grow");
		txtPage.setColumns(10);
		
		btnNext = new JButton("Next >");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
			add(btnNext, "cell 3 1,grow");
			
			btnLast = new JButton("Last >>");
			btnLast.setFont(new Font("Tahoma", Font.PLAIN, 16));
			
			add(btnLast, "cell 4 1,grow");
		
//		txtSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String text = txtSearch.getText();
//                if (text.trim().length() == 0) {
//                    rowSorter.setRowFilter(null);
//                } else {
//                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//                }
//            }
//        });
		
		
		
	}
	

	public void addFirstButtonListener(ActionListener listener) {
		btnFirst.addActionListener(listener);
    }

    public void addPreviousButtonListener(ActionListener listener) {
        btnPrivious.addActionListener(listener);
    }

    public void addNextButtonListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    public void addLastButtonListener(ActionListener listener) {
        btnLast.addActionListener(listener);
    }
    
    
	public Integer setPagination(ActionEvent e, Integer num, Double totalPage) {
	String actionCommand = e.getActionCommand();
	
		switch (actionCommand) {
		case "<< First" ->{
			txtPage.setText((num = 1).toString());
		}
			
		case "Next >" -> {
			if (num < totalPage.intValue()) {	
			txtPage.setText((++num).toString());
			}
		}
		
		case "< Privious" -> {
			if(num > 1) {		
			txtPage.setText((--num).toString());
			}
		}
		
		case "Last >>" ->{
			num = totalPage.intValue();
			txtPage.setText(num.toString());
			
		}
	
	}
	 return num;
	}
	
}
