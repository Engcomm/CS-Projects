public class PrefixToPostfix
{
    private String expression;
    @SuppressWarnings("unchecked")
    private Stack<String> infixOutput = new ArrayStack();
    @SuppressWarnings("unchecked")
    private Stack<Character> operatorStack = new ArrayStack();
    private char ch;
    private String postfix = "";
    
    public PrefixToPostfix()
    {
	expression = null;
    }

    public PrefixToPostfix(String prefixExpression)
    {
	expression = prefixExpression;
    } 
   private boolean isOperator(char ch)
    {
	if(ch == '+' || ch == '-' || ch == '*' || ch =='/' || ch == '^')
	    return true;
	return false;
    }
  
    private String ConvertToInfix()
    {
	for(int i = expression.length() - 1; i >= 0; i--)
	{
	    ch = expression.charAt(i);
	    if(isOperator(ch))
	    {
		String operandA = infixOutput.pop();
		String operandB = infixOutput.pop();
		infixOutput.push("("+operandA+ch+operandB+")");
	    }
	    else
		infixOutput.push(""+ch);
	}	
	return infixOutput.pop();
    }

    public String ConvertToPostfix()
    {
	// original = null ??
	Character nextCharacter = ' ';
	int charIndex = 0;
	Character topOperator = ' ';
	expression = ConvertToInfix();
	while(charIndex < expression.length())
	{
	    nextCharacter = expression.charAt(charIndex);	   
	    if(!(nextCharacter >= 65 && nextCharacter <= 90) && !(nextCharacter >= 97 && nextCharacter <= 122))
	    {
		switch(nextCharacter)
		{
		case '^':
		    operatorStack.push(nextCharacter);
		    break;
		case '*': case '/':	 
		    if(operatorStack.peek() != null)
			{
			    while(operatorStack.peek() == '^'  || operatorStack.peek() == '*' || operatorStack.peek() == '/')
				{
				    postfix += operatorStack.pop(); 
				    if(operatorStack.peek() == null)
					break;
				}
			} 		       
		    operatorStack.push(nextCharacter);     
		    break;
		case '+': case '-':
		    if(operatorStack.peek() != null)
			{
			    while(operatorStack.peek() == '^' || operatorStack.peek() == '+' || operatorStack.peek() == '-' || operatorStack.peek() == '*' || operatorStack.peek() == '/')
				{
				    postfix += operatorStack.pop();
				    if(operatorStack.peek() == null)
					break;
				}
			}
		    operatorStack.push(nextCharacter);
		    break;
		case '(':
		    operatorStack.push(nextCharacter);
		    break;
		case ')':
		    topOperator = operatorStack.pop();
		    while(topOperator != '(')
			{
			    postfix += topOperator;
			    topOperator = operatorStack.pop();
			}
		    break;
		default: break;
		}
	    }
	    else
		postfix += nextCharacter;
	    charIndex++;
	}
	while(operatorStack.peek() != null)
	{
	    topOperator = operatorStack.pop();
	    postfix += topOperator;
	}
	return postfix;	
    }
   
}