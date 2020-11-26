
public class State {

    private boolean accept;
    private String name;

    public State(boolean a, String n)
    {
        this.accept = a;
        this.name = n;
    }

    public void setOpposite()
    {
        this.accept = !accept;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean getAccept()
    {
        return this.accept;
    }
}