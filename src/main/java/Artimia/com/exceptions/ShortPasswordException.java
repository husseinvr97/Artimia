package Artimia.com.exceptions;

public class ShortPasswordException extends RuntimeException
{
    public ShortPasswordException()
    {
        super();
    }
    public ShortPasswordException(String message)
    {
        super(message);
    }
}
