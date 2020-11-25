public class Parameter {
	public Main main;
	public Boolean[] flags = {
		false, false, false, false, false, false, false, false, false
	};
	public int[] powers = {
		0, 0, 0, 0, 0, 0, 0, 0, 0
	};
	public double[] values = {
		0, 0, 0, 0, 0, 0, 0, 0, 0
	};
	
	final public String[] names = {
		"飞行高度"	, "飞行速度"	, "飞机长", 
		"飞机宽"		, "飞机高"	, "温度", 
		"压强"		, "密度"		, "粘度"
	};
	final public String[] symbols = {
		"z", 	"V", 	"L", 
		"W", 	"H", 	"T",
		"P", 	"ρ",	"μ"
	};
	final public String[] units = {
		"m", "m/s", "m", "m", "m", "K", "kPa", "kg/m³", "Pa·s"
	};
	final public String[] details = {
		"2000m"	,	"30m/s"	,	"4m",
		"5m"	,	"1m"	,	
		"<html>海平面 T₀ = 288.15K <br>对流层温度 - 高度关系：<br><b>T = T₀-0.0065z</b></html>",
		"<html>海平面p₀ = 101.33kPa 且<br> <b>p=22.6e^((11000-z)/6336)</b>kPa</html>", 	
		"<html>海平面ρ₀ = 1.225kg/m³ <br><b>ρ = p/(RT)</b> 其中R = 287 J/(kg·K)</html>",
		"<html><b>μ = μ<sub>ref</sub>(T / T<sub>ref</sub>)<sup>1.5</sup> * <br>( (T<sub>ref</sub>+110.4) / (T+110.4) )</b>"
		+ "	<br>μ<sub>ref</sub> = 1.716×10⁻⁵Pa·s <br>T<sub>ref</sub> = 273.15K</html>"
	};
	
	/**
	 * 寻找指定的symbol在parameter中的位置
	 * @param symbol
	 * @return
	 */
	private int findParameterIndex(String symbol) {
		int index = -1;
		for (int i = 0; i < symbols.length; i++) {
			if (symbols[i].equals(symbol)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * 设置parameterFlag - true 表示目标进入textArea区域
	 * @param symbol
	 * @param bool
	 */
	public void setParameterFlag(String symbol, Boolean bool) {
		int index = findParameterIndex(symbol);
		if (index == -1) return;
		
		if (flags[index] && !bool) main.logger.log(main.logger.CancelPara, symbol);
		flags[index] = bool;
	}
	
	/**
	 * 获取parameterFlag - true 表示目标进入textArea区域
	 * @param symbol
	 */
	public boolean getParameterFlag(String symbol) {
		int index = findParameterIndex(symbol);
		if (index == -1) return false;
		return flags[index];
	}
	
	/**
	 * 设置parameterPower
	 * @param symbol
	 * @param power
	 * @param isReset
	 */
	public void setParameterPower(String symbol, int power, Boolean isReset) {
		int index = findParameterIndex(symbol);
		if (index == -1) return;
		powers[index] = power;
		
		if ( !isReset ) {
			main.logger.log(
				main.logger.EditPow, 
				symbol + ":" + power
			);
		}
	}
	
	/**
	 * 设置parameterValue
	 * @param symbol
	 * @param value
	 * @param isReset
	 */
	public void setParameterValue(String symbol, double value, Boolean isReset) {
		int index = findParameterIndex(symbol);
		if (index == -1) return;
		values[index] = value;
		
		if ( !isReset ) {
			main.logger.log(
				main.logger.EditVal, 
				symbol + ":" + value
			);
		}
	}
	
} // end Class Parameter
