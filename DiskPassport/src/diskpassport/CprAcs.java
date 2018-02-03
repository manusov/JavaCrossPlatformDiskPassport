package diskpassport;
import java.util.*;

public class CprAcs {
private Map < Integer, String > id = new HashMap();
private Map < Integer, String > st = new HashMap();

//---------- Constructor -------------------------------------------------------
CprAcs()
    {
    id.put( 0x00 , "Not valid" );
    id.put( 0x01 , "Raw read error rate" );
    id.put( 0x02 , "Throughput performance" );
    id.put( 0x03 , "Spinup time" );
    id.put( 0x04 , "Start/Stop count" );
    id.put( 0x05 , "Reallocated sector count" );
    id.put( 0x06 , "Read channel margin" );
    id.put( 0x07 , "Seek error rate" );
    id.put( 0x08 , "Seek timer performance" );
    id.put( 0x09 , "Power-on hours count" );
    id.put( 0x0A , "Spin-up retry count" );
    id.put( 0x0B , "Calibration retry count" );
    id.put( 0x0C , "Power cycle count" );
    id.put( 0x0D , "Soft read error rate" );
    id.put( 0xAA , "Available reserved space" );              // from intel doc
    id.put( 0xAB , "Program fail count" );                    // from intel doc
    id.put( 0xAC , "Erase fail count" );                      // from intel doc
    id.put( 0xAE , "Unexpected power loss" );                 // from intel doc
    // id.put( 0xAF , "Power loss protection failure" );      // from intel doc
    id.put( 0xB7 , "SATA downshift count" );                  // from intel doc
    id.put( 0xB8 , "End-to-End error detection count" );      // from intel doc
    id.put( 0xBB , "Uncorrectable error count" );             // from intel doc
    // id.put( 0xBB , "Vendor specific" );
    id.put( 0xBD , "Vendor specific" );
    id.put( 0xBE , "Case temperature" );                      // from intel doc
    // id.put( 0xBE , "Vendor specific" );
    id.put( 0xBF , "G-sense error rate" );
    id.put( 0xC0 , "Power-off retract count" );
    id.put( 0xC1 , "Load/Unload cycle count" );
    id.put( 0xC2 , "HDA temperature" );
    id.put( 0xC3 , "Hardware ECC recovered" );
    id.put( 0xC4 , "Reallocation count" );
    id.put( 0xC5 , "Current pending sector count" );
    id.put( 0xC6 , "Offline scan uncorrectable count" );
    id.put( 0xC7 , "UDMA/SATA CRC error rate" );
    id.put( 0xC8 , "Write error rate" );
    id.put( 0xC9 , "Soft read error rate" );
    id.put( 0xCA , "Data address mark errors" );
    id.put( 0xCB , "Run out cancel" );
    id.put( 0xCC , "Soft ECC correction" );
    id.put( 0xCD , "Thermal asperity rate" );
    id.put( 0xCE , "Flying height" );
    id.put( 0xCF , "Spin high current" );
    id.put( 0xD0 , "Spin buzz" );
    id.put( 0xD1 , "Offline seek performance" );
    id.put( 0xDC , "Disk shift" );
    id.put( 0xDD , "G-sense error rate" );
    id.put( 0xDE , "Loaded hours" );
    id.put( 0xDF , "Load/Unload retry count" );
    id.put( 0xE0 , "Load friction" );
    id.put( 0xE1 , "Load/Unload cycle count (Host writes)" );
    id.put( 0xE2 , "Load-in time" );
    id.put( 0xE3 , "Torque amplification count (Host Read/Write Ratio)" );
    id.put( 0xE4 , "Power-off retract count (Timed workload timer)" );
    id.put( 0xE6 , "GMR head amplitude" );
    id.put( 0xE7 , "Temperature" );
    id.put( 0xE8 , "Available reserved space" );              // from intel doc
    id.put( 0xE9 , "Media wearout indicator" );               // from intel doc
    id.put( 0xEA , "Thermal throttle status" );               // from intel doc
    id.put( 0xF0 , "Head flying hours" );
    id.put( 0xF1 , "Total LBAs written by Host" );            // from intel doc
    id.put( 0xF2 , "Total LBAs read by Host" );               // from intel doc
    id.put( 0xF3 , "Total LBAs written to the NAND" );        // from intel doc
    id.put( 0xFA , "Read error retry rate" );

    id.put( 0xD4 , "SATA PHY Errors" );                       // additional
    id.put( 0x64 , "Erase count" );                           // additional
    id.put( 0xA8 , "SATA PHY Errors" );                       // additional
    id.put( 0xA9 , "Bad sectors count" );                     // additional
    id.put( 0xAF , "Program errors (chip)" );                 // additional
    id.put( 0xB0 , "Erase errors (chip)" );                   // additional
    id.put( 0xB1 , "Delta useable range" );                   // additional
    id.put( 0xB2 , "Used spare blocks (chip)" );              // additional
    id.put( 0xB3 , "Used spare blocks (total)" );             // additional
    id.put( 0xB4 , "Unused spare blocks" );                   // additional
    id.put( 0xBC , "Commands delays" );                       // additional
    
    st.put (  0 , "Previous self-test routine completed" +
                  " without error or no self-test status is available"  );
    st.put(   1 , "The self-test routine was aborted by the host" );
    st.put(   2 , "The self-test routine was interrupted by the host" +
                  " with a hardware or software reset" );
    st.put(   3 , "A fatal error or unknown test error occured, unable" +
                  " to complete self-test" );
    st.put(   4 , "Completed failed, element that failed is unknown" );
    st.put(   5 , "Completed failed, electrical element failed" );
    st.put(   6 , "Completed failed, servo and/or seek element failed" );
    st.put(   7 , "Completed failed, read element failed" );
    st.put(   8 , "Completed failed, suspected of the handling damage" );
    st.put(  15 , "Self-test routine in progress" );
    }

//---------- Target methods ----------------------------------------------------

public String decodeSmartAttribute(int x)
    {
    String s="?";
    x = x & 0xFF;
    s = id.get(x);
    if ( (s==null) && (x<0xDB)  ) { s = "Reserved"; }
    if ( (s==null) && (x>=0xDB) ) { s = "Vendor specific"; }
    return s;
    }

public String decodeOffLineDataCollectionStatus(int x)
    { 
    String s="?";
    x = x & 0xFF;
    if ( (x==0x00) | (x==0x80) ) 
        { s = "Off-line data collection activity was never started"; }
    if ( (x==0x01) |
         (x>=0x07) && (x<=0x3F) |
         (x==0x81) |
         (x==0x83) |
         (x>=0x87) | (x<=0xBF) )
            { s = "Reserved"; }
    if ( (x==0x02) | (x==0x82) )
        { s = "Off-line data collection activity was completed without error"; }
    if (x==0x03)
        { s = "Off-line activity in progress"; }
    if ( (x==0x04) | (x==0x84) )
        { s = "Off-line data collection activity was suspended by an" + 
              " interrupting command from host"; }
    if ( (x==0x05) | (x==0x85) )
        { s = "Off-line data collection activity was aborted by an" + 
              " interrupting command from host"; }
    if ( (x==0x06) | (x==0x86) )
        { s = "Off-line data collection activity was aborted by the" + 
              " device with a fatal error"; }
    if ( (x>=0xC0) && (x<=0xFF) )
        { s = "Vendor specific"; }
    return s;
    }

public String decodeSelfTestExecutionStatus(int x)
    { 
    String s="?";
    x = x & 0xFF;
    s = st.get(x);
    if ( (s==null) && (x<0x0F)  ) { s = "Reserved"; }
    return s;
    }


}
