import javax.swing.JLabel;

import java.lang.Math;

class ResultController {
	public Boolean flag = false; // 是否计算过值
	
	private JLabel resultLabel;
	private PageFormula page;
	private Boolean isSetFormula = false;
	private String formula;
	private double value;
	
	final private String[] upper = {
		"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹", "⁻"	
	};
	
	/**
	 * 构造函数
	 * @param resultLabel
	 * @param page
	 */
	ResultController(JLabel resultLabel, PageFormula page) {
		this.resultLabel = resultLabel;
		this.page = page;
	}
	
	/**
	 * 设定幂指数后显示公式
	 */
	public void setFormula() {
		Boolean[] flags = page.parameter.flags;
		int[] powers = page.parameter.powers;
		String[] symbols = page.parameter.symbols;
		int[] order = {0, 7, 8, 1, 2, 3, 4, 5, 6};
		
		String positive = "", negative = "";
		for (int index = 0; index < order.length; index++) {
			int i = order[index];
			if ( !flags[i] || powers[i] == 0 ) continue;
			
			int power = powers[i];
			if (power > 0) {
				positive += symbols[i];
				if (power != 1) positive += upper[power];
			} else if (power < 0) {
				negative += symbols[i];
				if (power != -1) negative += upper[-power];
			}
		} // end for
		
		String result;
		if (positive.length() != 0 && negative.length() == 0) {
			// 只有正指数
			result = positive;
		} else if (positive.length() == 0 && negative.length() != 0) {
			// 只有负指数
			result = "1/";
			if (negative.length() != 1) result += "(";
			result += negative;
			if (negative.length() != 1) result += ")";
		} else {
			// 既有正指数也有负指数
			result = positive;
			result += "/";
			if (negative.length() != 1) result += "(";
			result += negative;
			if (negative.length() != 1) result += ")";
		}
		resultLabel.setText(result);
		isSetFormula = true;
		this.formula = result;
		
	} // end setFormula
	
	/**
	 * 设置参数值
	 */
	public void setValue() {
		if ( !isSetFormula ) return;
		
		Boolean[] flags = page.parameter.flags;
		int[] powers = page.parameter.powers;
		double[] values = page.parameter.values;
		double result = 1;
		
		for (int i = 0; i < flags.length; i++) {
			if ( !flags[i] ) continue;
			result *= Math.pow(values[i], powers[i]);
		}
		this.value = Double.valueOf(String.format("%.2f", result));
		this.flag = true;
		resultLabel.setText(formula + " = " + String.format("%.2f", result));
		
	} // end setValue
	
	/**
	 * 取出参数值的getter
	 * @return
	 */
	public double getValue() {
		return this.value;
	}
	
} // end class ResultController