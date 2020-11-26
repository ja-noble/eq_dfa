import java.util.ArrayList;

public class DeterministicFiniteAutomata
{
    private ArrayList<State> states = new ArrayList<State>();
    private String[] inputAlphabet;
    private ArrayList<Transition> transitions = new ArrayList<Transition>();
    private String startState;

    public DeterministicFiniteAutomata(String[] states, String[] input, String[] accept, ArrayList<Transition> transitons, String start)
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
        for(Transition transition : transitions)
        {
            this.transitions.add(transition);
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

    public DeterministicFiniteAutomata complement()
    {
        String[] states = new String[this.states.size()];
        ArrayList<String> accepting = new ArrayList<String>();
        ArrayList<Transition> newTrans = new ArrayList<Transition>();
        int iterator = 0;
        for(State state : this.states)
        {
            states[iterator++] = state.getName();
            if(!state.getAccept()) accepting.add(state.getName());
        }
        for (Transition transition : this.transitions)
        {
            newTrans.add(transition);
        }
        String[] acceptance = accepting.toArray(new String[accepting.size()]);
        return new DeterministicFiniteAutomata(states, this.inputAlphabet, acceptance, newTrans, this.startState);
    }

    public DeterministicFiniteAutomata union(DeterministicFiniteAutomata outer)
    {
        ArrayList<State> states = new ArrayList<State>();
        boolean currentAccept;
        String newName;
        String outputTrans;
        ArrayList<Transition> newTrans = new ArrayList<Transition>();

        for(State innerState : this.getStates())
        {
            for(State outerState : outer.getStates())
            {
                currentAccept = innerState.getAccept() && outerState.getAccept();
                newName = innerState.getName()+"+"+outerState.getName();
                states.add(new State(currentAccept, newName));

                for(Transition checkTrans : this.transitions)
                {
                    String input = "move to next transition";
                    if(innerState.getName().equals(checkTrans.getStart()))
                    {
                        input = checkTrans.getInput();
                    }
                    for (Transition checkOuter : outer.getTransitions())
                    {
                        if(checkOuter.getStart().equals(outerState.getName()) && input.equals(checkOuter.getInput()))
                        {
                            outputTrans = checkTrans.getEnd()+"+"+checkOuter.getEnd();
                            newTrans.add(new Transition(newName, input, outputTrans));
                        }
                    }
                }
            }
        }
        String start = this.getStartState()+"+"+outer.getStartState();
        return new DeterministicFiniteAutomata(states, this.inputAlphabet, newTrans, start);
    }

    public boolean isEmpty()
    {
        for(Transition transitions : this.transitions)
        {
            for(State state : this.states)
            {
                if(state.getName().equals(transitions.getStart()) || state.getName().equals(transitions.getEnd()))
                {
                    if(state.getAccept()) return true;
                }
            }
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
}