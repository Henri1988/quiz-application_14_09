package infrastructure;

public class NonExistentQuestionException extends NonExistentEntityException {

    private static final long serialVersionUID = 8633588908169766368L;

    public NonExistentQuestionException() {
        super("Question does not exist");
    }
}