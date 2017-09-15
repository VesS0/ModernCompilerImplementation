package rs.ac.bg.etf.pp1.util;

import java.util.Stack;

import rs.etf.pp1.symboltable.concepts.Struct;

public final class EntityCounters
{
    
    int globalVariableCount = 0;
    int globalConstVariableCount = 0;
    int mainVariableCount = 0;
    int mainFunctionCallsCount = 0;
    int globalClassFunctionCount = 0;
    int staticClassFunctionCount = 0;
    int formalParamsCount = 0;
    int globalFunctionCount = 0;
    int classCount = 0;
    int classVariableCount = 0;
    
    void IncreaseVariableCount()
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
    
    void IncreaseConstVariableCount()
    {
        if (ScopeEngine.IsInGlobalScope()) // Global scope
        {
            globalConstVariableCount++;
        }
    }
    
    void IncreaseFunctionCount()
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
    
    void IncreaseStaticFunctionCount()
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
    
    void IncreaseFunctionCallsCount()
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
}