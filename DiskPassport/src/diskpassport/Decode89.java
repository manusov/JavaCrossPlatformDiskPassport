package diskpassport;

public class Decode89 implements Decoder {
public String decodeValue(long x1)
    {
    String s1 = "?";

    if ( (x1 & 0x8000) == 0)
        {
        x1 = x1 & 0xFF;
        if (x1==0)        { s1 = "Value not specified"; }
        else if (x1==255) { s1 = "Above 508 minutes";   }
        else              { s1 = String.format( "%d minutes", x1*2 ); }
        }

    else
        {
        x1 = x1 & 0x7FFF;
        if (x1==0)        { s1 = "Value not specified"; }
        else              { s1 = String.format( "%d minutes", x1*2 ); }
        }
            
    return s1; }
    
}
