package diskpassport;

public class Decode691 implements Decoder {
private String[] s1 =
{
"Zoned capabilities is not reported" ,
"Device supports the Host Aware Zones feature set" ,
"Device supports device managed zoned capabilities" ,
"Zoned capabilities reserved"
};
public String decodeValue(long x1)
    {
    String s2="?";
    int x = (int)x1;
    if ( x < s1.length ) { s2 = s1[x]; }
    return s2; }
    
}
