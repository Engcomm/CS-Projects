public class PostfixToInfix
{
    private String expression;
    @SuppressWarnings("unchecked")
    private Stack<String> output = new ArrayStack();
    private char ch;
    
    public PostfixToInfix()
    {
	expression = null;
    }

    public PostfixToInfix(String postfixExpression)
    {
	expression = postfixExpression;
    } 

    private boolean isOperator(char ch)
    {
	if(ch == '+' || ch == '-' || ch == '*' || ch =='/' || ch == '^')
	    return true;
	return false;
    }
  
    public String ConvertToInfix()
    {
	for(int i = 0; i < expression.length(); i++)
	{
	    ch = expression.charAt(i);
	    if(isOperator(ch))
	    {
		String operandB = output.pop();
		String operandA = output.pop();
		output.push("("+operandA+ch+operandB+")");
	    }
	    else
		output.push(""+ch);
	}	
	return output.pop();
    }
    
}