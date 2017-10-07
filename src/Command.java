public interface Command {
    Object execute();
    void unexecute();
}
