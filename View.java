/**
 * Program Name: View.java
 * Purpose: Creates the JTable with the model provided
 * Coder: Sandeep Singh (Sandy) - 0869722 - Section 01
 * Date: Jul. 30, 2019
 */
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class View extends JFrame
{
	
	public View(TableModel model)
	{
    super("Result");
		
		//boilerplate
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);//centres frame on screen
		this.setLayout(new FlowLayout());//sets the layout manager
		
		//construct the JTable using the passed in TableModel parameter
		JTable table = new JTable(model);
		
		//add table to a JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		
		//add scrollPane 
		this.add(scrollPane);
		
		this.pack();
		//last line
		this.setVisible(true);		
	}

	public static void main(String[] args)
	{
		

	}

}
//end Class