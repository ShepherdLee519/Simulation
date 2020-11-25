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
		"ģ������", "�綴��", "�綴��", "���ٶ�", "�¶�", "��������", "�ɱ�"	
	};
	
	/**
	 * ���캯��
	 */
	CustomJTable(JPanel page) {
		this.page = page;
		// ����Ŀ����
		Object[] columnNames = {"Test1"};
        Object[][] rowData = null;
        
        model = new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 3L;

			@Override  
            public boolean isCellEditable(int row, int column){  
				// ���е��о������޸�
                return false;
            }  
        };
        
        int top = 260;
        for (int i = 0; i < targets.length; i++) {
        	model.addRow(new Object[] {""});
        	JLabel label = new JLabel(targets[i]);
        	label.setBounds(20, top, 100, 35);
        	label.setFont(new Font("΢���ź�", Font.PLAIN, 16));
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
	 * �����е�ֵ
	 * @param values
	 */
	public void setCol(Object[] values) {
		int col = colNum - 2;
		for (int i = 0; i < targets.length; i++) {
			model.setValueAt(values[i], i, col); 
		}
	}
	
	/**
	 * ��̬�����һ����
	 */
	public void addCol() {
		model.addColumn(new Object[] {"", "", "", "", "", "", "", ""});
		TableColumnModel cols = table.getColumnModel();
		for (int i = 0; i < colNum; i++) {
			cols.getColumn(i).setHeaderValue("Test" + (i + 1));
		}
		colNum++;
		if (colNum == 12) {
			// �������ĳ� ʹ������Ĺ������������
			tablePanel.setBounds(90, 240, width, height2);
		}
		table.setModel(model);
		for (int i = 0; i < colNum - 1; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(55);
		}
		// �����ƶ���ʾ����һ��
		table.changeSelection(0, colNum - 2, false, false);
	}
	
} // end Class CustomJTable
