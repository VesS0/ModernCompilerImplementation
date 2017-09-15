package rs.ac.bg.etf.pp1.util;

import java.util.Stack;

public final class ScopeEngine{
	
    static Stack<SCOPE> nestedScopes = new Stack<SCOPE>();
    
    public static void InScope(SCOPE newScope)
    {
        if (nestedScopes.empty())
        {
            nestedScopes.push(newScope);
            return;
        }
        
        if (nestedScopes.peek() == SCOPE.CLASS_SCOPE && newScope == SCOPE.METHOD_SCOPE)
        {
            nestedScopes.push(SCOPE.CLASS_METHOD_SCOPE);
            return;
        }
        nestedScopes.push(newScope);   
    }
    
    public static void OutOfCurrentScope()
    {
        if (!nestedScopes.empty())
        {
            nestedScopes.pop();
        }
    }
    
    public static boolean IsInClassScope()
    {
    	return !nestedScopes.empty() && nestedScopes.peek() == SCOPE.CLASS_SCOPE ; // || nestedScopes.peek() == SCOPE.CLASS_SCOPE );
    }
    
    public static boolean IsInGlobalScope()
    {
    	return nestedScopes.empty();
    }
    
    public static boolean IsInMainScope()
    {
    	return !nestedScopes.empty() && nestedScopes.peek() == SCOPE.MAIN_SCOPE;
    }
    
    public static boolean IsInMethodScope()
    {
    	return !nestedScopes.empty() && nestedScopes.peek() == SCOPE.METHOD_SCOPE;
    }
    
    public static boolean IsInClassMethodScope()
    {
    	return !nestedScopes.empty() && nestedScopes.peek() == SCOPE.CLASS_METHOD_SCOPE;
    }
}
