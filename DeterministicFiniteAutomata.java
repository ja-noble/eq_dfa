import java.util.ArrayList;

public class DeterministicFiniteAutomata
{
    private ArrayList<State> states = new ArrayList<State>();
    private String[] inputAlphabet;
    private ArrayList<Transition> transitions = new ArrayList<Transition>();
    private String startState;

    public DeterministicFiniteAutomata(String[] states, String[] input, String[] accept, ArrayList<Transition> transitonsToCopy, String start)
    {
        for (String name : states)
        {
            this.states.add(new State(false, name));
        }
        this.inputAlphabet = input;
        for (String name : accept)
        {
            for (State state : this.states)
            {
                if(name.equals(state.getName())) 
                {
                    state.setOpposite();
                }
            }
        }
        for(Transition transition1 : transitonsToCopy)
        {
            Transition toCopy = new Transition(transition1);
            this.transitions.add(toCopy);
        }
        this.startState = start;
    }

    public DeterministicFiniteAutomata(ArrayList<State> states, String[] input, ArrayList<Transition> transitions, String start)
    {
        for (State state : states)
        {
            this.states.add(state);
        }
        this.inputAlphabet = input;
        for(Transition tran : transitions)
        {
            this.transitions.add(tran);
        }
        this.startState = start;
    }

    public DeterministicFiniteAutomata(DeterministicFiniteAutomata copy)
    {
        for(State stateToCopy : copy.getStates())
        {
            this.states.add(new State(stateToCopy));
        }
        this.inputAlphabet = copy.getAlphabet();
        for(Transition tran : copy.getTransitions())
        {
            this.transitions.add(new Transition(tran));
        }
        this.startState = copy.getStartState();
    }

    public DeterministicFiniteAutomata complement()
    {
        DeterministicFiniteAutomata comp = new DeterministicFiniteAutomata(this);
        for(State oppositeState : comp.getStates())
        {
            oppositeState.setOpposite();
        }
        return comp;
    }

    public DeterministicFiniteAutomata union(DeterministicFiniteAutomata outer)
    {
        ArrayList<State> newStates = new ArrayList<State>();
        boolean currentAccept;
        String newName;
        String outputTrans;
        String input;
        ArrayList<Transition> newTrans = new ArrayList<Transition>();

        for(State innerState : this.getStates())
        {
            for(State outerState : outer.getStates())
            {
                currentAccept = innerState.getAccept() && outerState.getAccept();
                newName = innerState.getName()+outerState.getName();
                newStates.add(new State(currentAccept, newName));
            }
        }
        for (State checkingStates : newStates)
        {
            for(Transition innerTrans : this.transitions)
            {
                if(innerTrans.getStart().equals(checkingStates.getName().substring(0, innerTrans.getStart().length())))
                {
                    input = innerTrans.getInput();
                    outputTrans = innerTrans.getEnd();
                    for(Transition outerTrans : outer.getTransitions())
                    {
                        if(outerTrans.getStart().equals(checkingStates.getName().substring(innerTrans.getStart().length())) && outerTrans.getInput().equals(input))
                        {
                            outputTrans = outputTrans+outerTrans.getEnd();
                            newTrans.add(new Transition(checkingStates.getName(), input, outputTrans));
                        }
                    }
                }
            }
        }
        String start = this.getStartState()+outer.getStartState();
        return new DeterministicFiniteAutomata(newStates, this.inputAlphabet, newTrans, start);
    }

    public boolean isEmpty()
    {
        String nextState = "";
        ArrayList<String> checkedStates = new ArrayList<String>();

        for(State startState : this.states)
        {
            if(startState.getName().equals(this.startState))
            {
                if(startState.getAccept()) return false;
                for(Transition checkTrans : this.transitions)
                {
                    if(checkTrans.getStart().equals(startState.getName()))
                    {
                        if(!noAcceptStates(checkTrans.getEnd()))
                        {
                            return false;
                        }
                        nextState = checkTrans.getEnd();
                        checkedStates.add(this.startState);
                    }
                }
            }
        }

        for(int i = 0; i < this.inputAlphabet.length; i++)
        {
            while(!allArrayIsSame(checkedStates, nextState))
            {
                for(Transition checkTrans2 : this.transitions)
                {
                    if(checkTrans2.getStart().equals(nextState))
                    {
                        if(!noAcceptStates(checkTrans2.getEnd()))
                        {
                            return false;
                        }
                        if(!noAcceptStates(checkTrans2.getStart()))
                        {
                            return false;
                        }
                        checkedStates.add(nextState);
                        if(checkTrans2.getInput().equals(this.inputAlphabet[i])) nextState = checkTrans2.getEnd();
                    }
                }
            } 
            checkedStates.clear();
            nextState =  this.startState;
        }
        return true;
    }

    public boolean noAcceptStates(String name)
    {
        for(Transition checkTrans : this.transitions)
        {
            if(checkTrans.getStart().equals(name))
            {
                for(State checkStates : this.states)
                {
                    if(checkStates.getName().equals(name))
                    {
                        if(checkStates.getAccept()) return false;
                    }
                    if(checkStates.getName().equals(checkTrans.getEnd()))
                    {
                        if(checkStates.getAccept()) return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<String> copyArray(ArrayList<String> toCopy)
    {
        ArrayList<String> newList = new ArrayList<String>();
        for (String copy : toCopy)
        {
            newList.add(copy);
        }
        return newList;
    }

    public boolean allArrayIsSame(ArrayList<String> checks, String input)
    {
        for(String check : checks)
        {
            if(input.equals(check)) return true;
        }
        return false;
    }

    public ArrayList<State> getStates()
    {
        return this.states;
    }

    public String getStartState()
    {
        return this.startState;
    }

    public ArrayList<Transition> getTransitions()
    {
        return this.transitions;
    }

    public String[] getAlphabet()
    {
        return this.inputAlphabet;
    }
}