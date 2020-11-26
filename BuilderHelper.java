import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class BuilderHelper {

    private String input1;
    final int BREAK = -1;
    FileInputStream in = null;

    public BuilderHelper(String in1){
        this.input1 = in1;
    }

    public DeterministicFiniteAutomata converter() throws IOException
    {
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(this.input1));

            boolean[] instanceFlags = {true, false, false, false, false};

            ArrayList<String> states1 = new ArrayList<String>();

            ArrayList<String> alpha1 = new ArrayList<String>();

            ArrayList<String> accept1 = new ArrayList<String>();

            ArrayList<Transition> transitions1 = new ArrayList<Transition>();

            boolean[] transitionFlags = {false, false, false};
            String starting = "";
            String input = "";
            String end = "";

            String startState = "";

            int iterator = 0;
            
            for(String entry : lines)
                {
                    if(!entry.equals(","))
                    {
                        if(instanceFlags[0]) states1.add(entry);
                        else if(instanceFlags[1]) alpha1.add(entry);
                        else if(instanceFlags[2]) accept1.add(entry);
                        else if(instanceFlags[3])
                        {
                            if(transitionFlags[0]) 
                            {
                                starting = entry; transitionFlags[0] = false; transitionFlags[1] = true;
                            }
                            else if(transitionFlags[1])
                            {
                                input = entry; transitionFlags[1] = false; transitionFlags[2] = true;
                            }
                            else if(transitionFlags[2])
                            {
                                end = entry; 
                                transitions1.add(new Transition(starting, input, end));
                                starting = "";
                                input = "";
                                end = "";
                                for (int i = 0; i < 3; i++) transitionFlags[i] = false;
                            }
                        }
                        else if(instanceFlags[4]) 
                        {
                            startState = entry;
                        }
                    }
                    else
                    {
                        if(instanceFlags[iterator]) 
                        {
                            instanceFlags[iterator] = false;
                            instanceFlags[iterator+1] = true;
                            iterator++;
                        }
                    }
                }
                String[] state = states1.toArray(new String[states1.size()]);
                String[] alphabet = alpha1.toArray(new String[alpha1.size()]);
                String[] accept = accept1.toArray(new String[accept1.size()]);

                return new DeterministicFiniteAutomata(state, alphabet, accept, transitions1, startState);
            }
        catch (FileNotFoundException io) 
        {
            System.err.println("File not Found.");
            System.exit(BREAK);
        }
        return null;
    }
}