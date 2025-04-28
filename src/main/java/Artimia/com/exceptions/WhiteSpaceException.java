package Artimia.com.exceptions;

public class WhiteSpaceException extends IllegalArgumentException
{
    public WhiteSpaceException()
    {
        super();
    }
    public WhiteSpaceException(String message)
    {
        super(message);
    }
    public WhiteSpaceException(String message , Throwable cause)
    {
        super(message,cause);
    }
    public WhiteSpaceException(Throwable cause)
    {
        super(cause);
    }
}
