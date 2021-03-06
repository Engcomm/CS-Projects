public class InfixToPostfix
{
    private String expression;
    @SuppressWarnings("unchecked")
    private Stack<Character> operatorStack = new ArrayStack();
    private String postfix = "";
    
    public InfixToPostfix()
    {
	expression = "";
    }

    public InfixToPostfix(String infixExpression)
    {
	expression = infixExpression;
    }

    public String ConvertToPostfix()
    {
	// original = null ??
	Character nextCharacter = ' ';
	int charIndex = 0;
	Character topOperator = ' ';
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