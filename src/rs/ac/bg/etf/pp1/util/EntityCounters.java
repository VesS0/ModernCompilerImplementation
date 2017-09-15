package rs.ac.bg.etf.pp1.util;

import java.util.Stack;

import rs.etf.pp1.symboltable.concepts.Struct;

public final class EntityCounters
{
    
    static int globalVariableCount = 0;
    static int globalConstVariableCount = 0;
    static int mainVariableCount = 0;
    static int mainFunctionCallsCount = 0;
    static int globalClassFunctionCount = 0;
    static int staticClassFunctionCount = 0;
    static int formalParamsCount = 0;
    static int globalFunctionCount = 0;
    static int classCount = 0;
    static int classVariableCount = 0;
    
    public static void IncreaseVariableCount()
    {
        
        if (ScopeEngine.IsInGlobalScope()) // Global scope
        {
            globalVariableCount++;
            return;
        }
        if (ScopeEngine.IsInMainScope())
        {
            mainVariableCount++;
            return;
        }
        if (ScopeEngine.IsInClassScope())
        {
            classVariableCount++;
            return;
        }
        if (ScopeEngine.IsInMethodScope())
        {
            return;
        }
    }
    
    public static void IncreaseConstVariableCount()
    {
        if (ScopeEngine.IsInGlobalScope()) // Global scope
        {
            globalConstVariableCount++;
        }
    }
    
    public static void IncreaseFunctionCount()
    {
        if (ScopeEngine.IsInGlobalScope()) // Global Scope
        {
            globalFunctionCount++;
            return;
        }
        if (ScopeEngine.IsInClassScope())
        {
            globalClassFunctionCount++;
        }
    }
    
    public static void IncreaseStaticFunctionCount()
    {
        if (ScopeEngine.IsInGlobalScope()) // Global Scope
        {
            // globalFunctionCount++;
            return;
        }
        if (ScopeEngine.IsInClassScope())
        {
            staticClassFunctionCount++;
        }
    }
    
    public static void IncreaseFunctionCallsCount()
    {
        if (ScopeEngine.IsInGlobalScope()) // Global scope
        {
            return;
        }
        
        if (ScopeEngine.IsInMainScope())
        {
            mainFunctionCallsCount++;   
            return;
        }
    }
    
    public static void IncreaseClassCount()
    {
    	classCount++;
    }
    
    public static void IncreaseFormalParamsCount()
    {
    	formalParamsCount++;
    }

	public static int getGlobalVariableCount() {
		return globalVariableCount;
	}

	public static void setGlobalVariableCount(int globalVariableCount) {
		EntityCounters.globalVariableCount = globalVariableCount;
	}

	public static int getGlobalConstVariableCount() {
		return globalConstVariableCount;
	}

	public static void setGlobalConstVariableCount(int globalConstVariableCount) {
		EntityCounters.globalConstVariableCount = globalConstVariableCount;
	}

	public static int getMainVariableCount() {
		return mainVariableCount;
	}

	public static void setMainVariableCount(int mainVariableCount) {
		EntityCounters.mainVariableCount = mainVariableCount;
	}

	public static int getMainFunctionCallsCount() {
		return mainFunctionCallsCount;
	}

	public static void setMainFunctionCallsCount(int mainFunctionCallsCount) {
		EntityCounters.mainFunctionCallsCount = mainFunctionCallsCount;
	}

	public static int getGlobalClassFunctionCount() {
		return globalClassFunctionCount;
	}

	public static void setGlobalClassFunctionCount(int globalClassFunctionCount) {
		EntityCounters.globalClassFunctionCount = globalClassFunctionCount;
	}

	public static int getStaticClassFunctionCount() {
		return staticClassFunctionCount;
	}

	public static void setStaticClassFunctionCount(int staticClassFunctionCount) {
		EntityCounters.staticClassFunctionCount = staticClassFunctionCount;
	}

	public static int getFormalParamsCount() {
		return formalParamsCount;
	}

	public static void setFormalParamsCount(int formalParamsCount) {
		EntityCounters.formalParamsCount = formalParamsCount;
	}

	public static int getGlobalFunctionCount() {
		return globalFunctionCount;
	}

	public static void setGlobalFunctionCount(int globalFunctionCount) {
		EntityCounters.globalFunctionCount = globalFunctionCount;
	}

	public static int getClassCount() {
		return classCount;
	}

	public static void setClassCount(int classCount) {
		EntityCounters.classCount = classCount;
	}

	public static int getClassVariableCount() {
		return classVariableCount;
	}

	public static void setClassVariableCount(int classVariableCount) {
		EntityCounters.classVariableCount = classVariableCount;
	}
    
    
}