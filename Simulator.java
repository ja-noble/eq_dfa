import java.io.*;

public final class Simulator {

    public static void main(String[] args)
    {
        BuilderHelper dfa1 = new BuilderHelper("sampleInputs/input1.txt");
        BuilderHelper dfa2 = new BuilderHelper("sampleInputs/input1.txt");

        BufferedWriter out = null;

        try
        {
            out = new BufferedWriter(new FileWriter("sampleOutputs/output.txt"));
            DeterministicFiniteAutomata ex1 = dfa1.converter();
            DeterministicFiniteAutomata ex2 = dfa2.converter();

            DeterministicFiniteAutomata ex1c = ex1.complement();
            DeterministicFiniteAutomata union1 = ex1c.union(ex2);

            DeterministicFiniteAutomata ex2c = ex2.complement();
            DeterministicFiniteAutomata union2 = ex2c.union(ex1);

            DeterministicFiniteAutomata finalUnion = union1.union(union2);

            out.write("Do the two DFA's accept all the same languages: "+ finalUnion.isEmpty());
            
            out.close();
        }
        catch(IOException io)
        {
            System.err.println("Error with file.");
            System.exit(-1);
        }
    }
}