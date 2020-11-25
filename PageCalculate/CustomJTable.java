import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CustomJTable {
	public JScrollPane tablePanel;
	public JPanel page;
	
	private int colNum = 2;
	private JTable table;
	private DefaultTableModel model;
	
	final private int width = 553, height1 = 204, height2 = 220;
	final private String[] targets = {
		"模型缩比", "风洞宽", "风洞高", "流速度", "温度", "流动相似", "成本"	
	};
	
	/**
	 * 构造函数
	 */
	CustomJTable(JPanel page) {
		this.page = page;
		// 建立目标表格
		Object[] columnNames = {"Test1"};
        Object[][] rowData = null;
        
        model = new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 3L;

			@Override  
            public boolean isCellEditable(int row, int column){  
				// 所有的列均不能修改
                return false;
            }  
        };
        
        int top = 260;
        for (int i = 0; i < targets.length; i++) {
        	model.addRow(new Object[] {""});
        	JLabel label = new JLabel(targets[i]);
        	label.setBounds(20, top, 100, 35);
        	label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        	page.add(label);
        	top += 25;
        }
        
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(55);
        
        table.setFont(new Font(null, Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 15));

        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(width, height1));
        
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false); 
        
        JScrollPane tablePanel = new JScrollPane(table);
        this.tablePanel = tablePanel;
        tablePanel.setBounds(110, 240, width, height1);
        
	} // CustomJTable
	
	/**
	 * 设置列的值
	 * @param values
	 */
	public void setCol(Object[] values) {
		int col = colNum - 2;
		for (int i = 0; i < targets.length; i++) {
			model.setValueAt(values[i], i, col); 
		}
	}
	
	/**
	 * 动态的添加一个列
	 */
	public void addCol() {
		model.addColumn(new Object[] {"", "", "", "", "", "", "", ""});
		TableColumnModel cols = table.getColumnModel();
		for (int i = 0; i < colNum; i++) {
			cols.getColumn(i).setHeaderValue("Test" + (i + 1));
		}
		colNum++;
		if (colNum == 12) {
			// 调整表格的长 使得竖向的滚动条不会出现
			tablePanel.setBounds(90, 240, width, height2);
		}
		table.setModel(model);
		for (int i = 0; i < colNum - 1; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(55);
		}
		// 滑块移动显示最新一列
		table.changeSelection(0, colNum - 2, false, false);
	}
	
} // end Class CustomJTable
