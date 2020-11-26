public class Transition {
    private String start;
    private String input;
    private String end;

    public Transition(String start, String input, String end)
    {
        this.start = start;
        this.input = input;
        this.end = end;
    }
 
    public String getStart()
    {
        return this.start;
    }

    public String getEnd()
    {
        return this.end;
    }

    public String getInput()
    {
        return this.input;
    }
}