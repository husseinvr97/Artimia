package Artimia.com.exceptions;

public class DuplicateProductSizeException extends RuntimeException
{
    public DuplicateProductSizeException()
    {
        super();
    }
    public DuplicateProductSizeException(String message)
    {
        super(message);
    }
}
