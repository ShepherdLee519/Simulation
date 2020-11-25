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
	 * 构造函数
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
	 * 鼠标进入目标区域(模拟hover)的事件处理
	 * 需与mouseExited合用
	 * 注意：MouseEvent 有重名风险，使用完整的java.awt.event.MouseEvent
	 */
	public void mouseEntered(java.awt.event.MouseEvent e) {
		target.setFont(new Font("微软雅黑", Font.BOLD, 20));
		target.setForeground(Color.RED);
	}
	
	/**
	 * 鼠标离开目标区域(模拟hover)的事件处理
	 * 需与mouseEntered合用
	 * 注意：MouseEvent 有重名风险，使用完整的java.awt.event.MouseEvent
	 */
	public void mouseExited(java.awt.event.MouseEvent e) {
		target.setFont(new Font("微软雅黑", Font.BOLD, 18));
		target.setForeground(Color.BLACK);
	}

	/**
	 *   当鼠标拖动时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 *  注意：MouseEvent 有重名风险，使用完整的java.awt.event.MouseEvent
	 */
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// 转换坐标系统
		Point newPoint = SwingUtilities.convertPoint(target, 
			e.getPoint(), target.getParent());
		
		// 设置标签的新位置
		target.setLocation(target.getX()
			+ (newPoint.x - point.x), target.getY()
			+ (newPoint.y - point.y));
		
		// 更改坐标点
		point = newPoint;
	}

	/**
	 * 当鼠标按下时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 * 注意：MouseEvent 有重名风险，使用完整的java.awt.event.MouseEvent
	 */
	public void mousePressed(java.awt.event.MouseEvent e) {
		// 得到当前坐标点
		point = SwingUtilities.convertPoint(target, 
			e.getPoint(), target.getParent());
	}
	
	/**
	 * 当鼠标松开时候触发事件 - 判定是否位于目标区域
	 * 注意：MouseEvent 有重名风险，使用完整的java.awt.event.MouseEvent
	 */
	public void mouseReleased(java.awt.event.MouseEvent e) {
		String symbol = ((JLabel)target).getText();
		
		if (zone.contains(target.getX(), target.getY())) {
			// 位于目标区域 - 设置parameterFlag为true
			page.parameter.setParameterFlag(symbol, true);
			main.logger.log(main.logger.DropPara, symbol);
		} else {
			// 位于目标区域之外 - 设置parameterFlag为false 同时复原位置
			page.parameter.setParameterFlag(symbol, false);
			page.parameter.setParameterPower(symbol, 0, true);
			page.parameter.setParameterValue(symbol, 0, true);
			target.setBounds(oldX, oldY, width, height);
		}
	}
	
} // end Class DragPicListener