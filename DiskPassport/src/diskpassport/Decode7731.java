package diskpassport;

public class Decode7731 implements Decoder {
String[] speedReports =
{
"Reporting of current singaling speed is not supported" ,
"Current signaling speed is Gen1 1.5 Gb/s" ,
"Current signaling speed is Gen2 3.0 Gb/s" ,
"Current signaling speed is Gen3 6.0 Gb/s" ,
"Reserved" ,
"Reserved" ,
"Reserved" ,
"Reserved"
};

public String decodeValue (long x1)
    {
    int x=(int)x1;
    String s1="?";
    s1 = speedReports[x];
    return s1;
    }

}
