
public class FormulaCalculator {
	private CustomJSlider[] sliders;
	private CustomJTable table;
	private Main main;
	private Parameter parameter;
	
	/**
	 * 构造函数
	 * @param sliders
	 * @param table
	 * @param main
	 */
	FormulaCalculator(CustomJSlider[] sliders, CustomJTable table, Main main) {
		this.sliders = sliders;
		this.table = table;
		this.main = main;
		this.parameter = main.pageFormula.parameter;
	}
	
	/**
	 * 取出Rer的值并返回
	 * @return
	 */
	public double getRer() {
		return main.pageFormula.resultController.getValue();
	}
	
	/**
	 * 计算 g 与 cost
	 */
	public void calculate() {		
		// 1. 计算模型缩比
		double x1 = sliders[0].getValue();
		
		// 2. 计算风洞宽
		double x2 = sliders[1].getValue();
		
		// 3. 计算风洞高
		double h = 0.75 * x2;
		
		// 4. 计算流速度
		double x3 = sliders[2].getValue();
		
		// 5. 计算温度
		double x4 = sliders[3].getValue();
		
		// 6. 计算流动相似
		double Rer = getRer();
		double g = calculateG(Rer, x1, x2, x3, x4);
		
		// 7. 计算成本
		double cost = calculateCost(x1, x2, x3, x4);
		
		table.setCol(new Object[] {
			format(x1, 2), format(x2, 2), format(h, 2), 
			format(x3, 2), format(x4, 2), format(g, 3), 
			format(cost, 3)
		});
		main.logger.log(main.logger.g, format(g, 3));
		main.logger.log(main.logger.cost, format(cost, 3));
		
	} // end calculate
	
	/**
	 * 调试用 输出值内容
	 * @param tag
	 * @param value
	 */
	private void show(String tag, double value) {
		System.out.println("the value of <" + tag + "> is: " + value);
	}
	
	/**
	 * 将浮点数转为保留指定位数的字符串
	 * @param num
	 * @param pre
	 * @return
	 */
	private String format(double num, int pre) {
		return String.format("%." + pre + "f", num);
	}
	
	/**
	 * 粘度计算 - 与温度(开尔文)有关
	 * @param T
	 * @return
	 */
	private double calculateMiu(double T) {
		double Tref = 273.15, miu0 = 1.716e-5;
		double miu = miu0 * Math.pow(T/Tref, 1.5) * (Tref + 110.4) / (T + 110.4);
		show("μ at T: " + T, miu);
		return miu;
	}

	/**
	 * 密度计算 - 当与温度有关时
	 * @param T
	 */
	private double calculateRou(double T) {
		double R = 287.0;
		double P = 101325.0;
		double rou = P / R / T;
		show("ρ at T: " + T, rou);
		return rou;
	}
	
	/**
	 * 流场相似指标计算
	 * @param Rer
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @return
	 */
	private double calculateG(double Rer, double x1, double x2, double x3, double x4) {
		// 1. 计算 Re => ρVW/μ
		double W = 5.0, p = 4.0;
		boolean flagW = parameter.getParameterFlag("W");
		boolean flagH = parameter.getParameterFlag("H");
		boolean flagL = parameter.getParameterFlag("L");
		if (flagW && !flagH && !flagL) p = 5.0;
		else if (flagH && !flagW && !flagL) p = 1.0;
		show("p", p);
		
		x4 += 273.15; // ℃ -> K
		double rou = calculateRou(x4);
		double miu = calculateMiu(x4);
		double Re = rou / miu * x3 * x1 * p;
		show("Re", Re);
		
		// 2. 计算 f1(Re) => 1 - abs(Re/Rer - 1)
		double f1 = 1 - Math.abs(Re/Rer - 1);
		show("f1", f1);
		if (f1 < 0) f1 = 0;
		show("f1", f1);
		
		// 3. 计算 f2 => 1 - e^[-5(1 - x1W/x2) - 0.7]
		double f2 = 1 - Math.exp(-5 * (1 - x1 * W / x2) - 0.7);
		show("f2", f2);
		
		double g = f1 * f2;
		show("g", g);
		return g;
		
	} // calculateG
	
	/**
	 * 成本计算
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @return
	 */
	private double calculateCost(double x1, double x2, double x3, double x4) {
		// c1 = x1^3
		double c1 = Math.pow(x1, 3);
		
		// c2 = (x2 / 4) ^ 3
		double c2 = Math.pow(x2 / 4.0, 3);
		
		// c3 = (x2 / 4) ^ 2 * (x3 / 45) ^ 3
		double c3 = Math.pow(x2 / 4.0, 2) * Math.pow(x3 / 45.0, 3);
		
		// c4 = 1/5|x4 - 20| * (x2 / 4) ^ 2 * (x3 / 45)
		double c4 = (x4 >= 20) ?
			0.2 * (x4 - 20.0) * Math.pow(x2 / 4.0, 2) * (x3 / 45.0) :
			0.4 * (20.0 - x4) * Math.pow(x2 / 4.0, 2) * (x3 / 45.0);
		
		double c = c1 + c2 + c3 + c4;
		show("c", c);
		return c;
		
	} // calculateCost
	
} // end Class FormulaCalculator
