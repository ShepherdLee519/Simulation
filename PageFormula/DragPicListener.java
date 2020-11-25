import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

class DragPicListener extends MouseInputAdapter {
	public Main main;
	private Point point = new Point(0, 0);
	private Component target;
	private PageFormula page;
	
	private int oldX, oldY;
	private int width, height;
	private Rectangle zone = new Rectangle(20, 290, 355, 80);
	
	/**
	 * ���캯��
	 * @param target
	 * @param page
	 */
	DragPicListener(Component target, PageFormula page) {
		this.target = target;
		this.page = page;
		this.oldX = target.getX();
		this.oldY = target.getY();
		this.width = target.getWidth();
		this.height = target.getHeight();
	}
	
	/**
	 * ������Ŀ������(ģ��hover)���¼�����
	 * ����mouseExited����
	 * ע�⣺MouseEvent ���������գ�ʹ��������java.awt.event.MouseEvent
	 */
	public void mouseEntered(java.awt.event.MouseEvent e) {
		target.setFont(new Font("΢���ź�", Font.BOLD, 20));
		target.setForeground(Color.RED);
	}
	
	/**
	 * ����뿪Ŀ������(ģ��hover)���¼�����
	 * ����mouseEntered����
	 * ע�⣺MouseEvent ���������գ�ʹ��������java.awt.event.MouseEvent
	 */
	public void mouseExited(java.awt.event.MouseEvent e) {
		target.setFont(new Font("΢���ź�", Font.BOLD, 18));
		target.setForeground(Color.BLACK);
	}

	/**
	 *   ������϶�ʱ�������¼��� ��¼����갴��(��ʼ�϶�)��λ�á�
	 *  ע�⣺MouseEvent ���������գ�ʹ��������java.awt.event.MouseEvent
	 */
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// ת������ϵͳ
		Point newPoint = SwingUtilities.convertPoint(target, 
			e.getPoint(), target.getParent());
		
		// ���ñ�ǩ����λ��
		target.setLocation(target.getX()
			+ (newPoint.x - point.x), target.getY()
			+ (newPoint.y - point.y));
		
		// ���������
		point = newPoint;
	}

	/**
	 * ����갴��ʱ�������¼��� ��¼����갴��(��ʼ�϶�)��λ�á�
	 * ע�⣺MouseEvent ���������գ�ʹ��������java.awt.event.MouseEvent
	 */
	public void mousePressed(java.awt.event.MouseEvent e) {
		// �õ���ǰ�����
		point = SwingUtilities.convertPoint(target, 
			e.getPoint(), target.getParent());
	}
	
	/**
	 * ������ɿ�ʱ�򴥷��¼� - �ж��Ƿ�λ��Ŀ������
	 * ע�⣺MouseEvent ���������գ�ʹ��������java.awt.event.MouseEvent
	 */
	public void mouseReleased(java.awt.event.MouseEvent e) {
		String symbol = ((JLabel)target).getText();
		
		if (zone.contains(target.getX(), target.getY())) {
			// λ��Ŀ������ - ����parameterFlagΪtrue
			page.parameter.setParameterFlag(symbol, true);
			main.logger.log(main.logger.DropPara, symbol);
		} else {
			// λ��Ŀ������֮�� - ����parameterFlagΪfalse ͬʱ��ԭλ��
			page.parameter.setParameterFlag(symbol, false);
			page.parameter.setParameterPower(symbol, 0, true);
			page.parameter.setParameterValue(symbol, 0, true);
			target.setBounds(oldX, oldY, width, height);
		}
	}
	
} // end Class DragPicListener