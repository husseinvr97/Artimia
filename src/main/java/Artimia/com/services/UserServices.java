package Artimia.com.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Artimia.com.enums.PasswordStrength;
import Artimia.com.exceptions.WhiteSpaceException;

@Service
public class UserServices 
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    public PasswordStrength passwordStrength(String plainPassword) throws WhiteSpaceException
    {
        if(StringUtils.containsAny(plainPassword,"       "))
            throw new WhiteSpaceException("Password cannot contain any whitespaces");
        final int containsUpper = StringUtils.containsAny(plainPassword, "ABCDEFGHIJKLMNOPQRSTUVWXYZ") ? 1 : 0;
        final int containsLower = StringUtils.containsAny(plainPassword,"abcdefghijklmnopqrstuvwxyz") ? 1 : 0;
        final int containsDigits = StringUtils.containsAny(plainPassword,"0123456789") ? 1 : 0;
        final int containsSpecial = StringUtils.containsAny(plainPassword,"!@#$%^&*()_+-=[]{};:'\",.<>/?") ? 1 : 0;
        final int Status = containsDigits + containsLower + containsSpecial + containsUpper;

        if(Status == 1)
            return PasswordStrength.WEAK;
        if(Status == 2)
            return PasswordStrength.BELOW_AVERAGE;
        if(Status == 3)
            return PasswordStrength.ABOVE_AVERAGE;
        return PasswordStrength.STRONG;
    }
    public boolean IsValidPassword(String plainPassword) throws WhiteSpaceException
    {
        try 
        {
            PasswordStrength passwordStrength = passwordStrength(plainPassword);
            if(passwordStrength.ordinal() < PasswordStrength.ABOVE_AVERAGE.ordinal())
                return false;
        } catch (WhiteSpaceException e) 
        {
            throw e;
        }
        
        return true;
    }
    public String hashPassword(String plainPassword)
    {
        return passwordEncoder.encode(plainPassword);
    }
}
