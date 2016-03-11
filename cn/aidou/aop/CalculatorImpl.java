package cn.aidou.aop;

/**
 * The Class CalculatorImpl.
 * @author Debadatta Mishra
 */
public class CalculatorImpl implements Calculator {
	public CalculatorImpl(){
		
	}
    @Override
    public int calculate(int a, int b) {
    	System.out.println("**********Actual Method Execution**********");
        return a/b;
    }

}
