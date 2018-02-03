package diskpassport;
import javax.swing.table.AbstractTableModel;

public class DiskSmartDumpTableModel extends AbstractTableModel {

private String[] columnsNames =
    {
    "Parameter"  ,
    "Offset"     ,
    "Dump, hex"  ,
    "Comments"
    };

private String rowsNamesVersion   = "SMART structure version";  // Bytes 0-1
private String rowsNamesAttribute = "Entry";                    // Bytes 2-361
private String[] rowsNamesCommon  =                             // Bytes 362-511
    {
    "Off-line data collection status"     ,  // 362
    "Self-test execution status"          ,
    "Vendor specific"                     ,
    "Vendor specific"                     ,
    "Vendor specific"                     ,
    "Off-line data collection capability" , // 367
    "SMART capability"                    ,
    "SMART capability"                    ,
    "Error logging capability"            , // 370
    "Vendor specific"                     ,
    "Short self-test routine recommended polling time" ,
    "Extended self-test routine recommended polling time" ,
    "Conveyance self-test routine recommended polling time" ,
    "Extended self-test routine recommended polling time, low" , 
    "Extended self-test routine recommended polling time, high" ,  // 376
    "Reserved" ,  // 377 , OPTIMIZE THIS BY PACK , MOVE TO CPR !
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,
    "Reserved" ,  // 385
    "Vendor specific" ,   // 386 , OPTIMIZE THIS BY PACK , MOVE TO CPR !
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 390
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 400
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 410
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 420
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 430
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 440
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 450
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 460
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 470
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 480
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 490
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,  // 500
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,
    "Vendor specific" ,         // 510
    "Data structure checksum"   // 511
    };

private String[] stringsNames, stringsOffsets, stringsDumps, stringsComments;

private int columnsCount = 4;
private int rowCountVersion = 1, rowCountAttributes = 30, rowCountCommon = 150;
private int rowsCount = rowCountVersion + rowCountAttributes + rowCountCommon;

private int smartLength = 512;
private int smartOffset = 512;
private byte[] smartData;
        
//---------- Table model constructor -------------------------------------------
DiskSmartDumpTableModel() {
//--- Initializing and blank ---
    int n1 = smartLength;
    int n2 = rowsCount;
    String s = " ";
    smartData = new byte[n1];
    for (int i=0; i<n1; i++)
        {
        smartData[i]=0;
        }
    stringsNames    = new String[n2];
    stringsOffsets  = new String[n2];
    stringsDumps    = new String[n2];
    stringsComments = new String[n2];
    for (int i=0; i<n2; i++)
        {
        stringsNames[i]    = s;
        stringsOffsets[i]  = s;
        stringsDumps[i]    = s;
        stringsComments[i] = s;
        }
}

//---------- Get strings for output --------------------------------------------
public Object getValueAt (int row, int column)
    { 
        switch (column)
        {
        case 0:  return " " + stringsNames[row];
        case 1:  return " " + stringsOffsets[row];
        case 2:  return " " + stringsDumps[row];
        case 3:  return " " + stringsComments[row];
        default: return null;            
        }
    }

//---------- Table configuration -----------------------------------------------
public int getColumnCount ()             { return columnsCount;           }
public int getRowCount ()                { return rowsCount;              }
public boolean isCellEditable ()         { return false;                  }
public String getColumnName (int column) { return columnsNames[column];   }

//---------- Specific methods --------------------------------------------------
public byte[] getSmart()
    {
    byte[] dsb = new byte[smartLength];
    for (int i=0; i<smartLength; i++) { dsb[i] = smartData[i]; }
    return dsb;
    }

public void setSmart(byte[] dsb )
    {
    for (int i=0; i<smartLength; i++) { smartData[i] = dsb[i]; }
    }

public void builtSmart()
    {
//--- Initializing CPR classes ---
    CprAcs acs = new CprAcs();

//--- Initializing fields ---
    int n = rowsCount;
    String s = " ";
    stringsNames    = new String[n];
    stringsOffsets  = new String[n];
    stringsDumps    = new String[n];
    stringsComments = new String[n];
    int i=0;  // cycle counter for required number of rows
    int j=0;  // bytes offset inside SMART data 512 bytes
    for (i=0; i<n; i++)
        {
            stringsNames[i]    = s;
            stringsOffsets[i]  = s;
            stringsDumps[i]    = s;
            stringsComments[i] = s;
            
            if (i==0)
            {
            stringsNames[i]    = s + rowsNamesVersion;
            stringsOffsets[i]  = printOffsets(j,j+1); 
            stringsDumps[i]    = printBytes(j,2); j+=2;
            }
            if ( (i>0)&&(i<=30) )
            {
            stringsNames[i]    = s + rowsNamesAttribute + s + i;
            stringsOffsets[i]  = printOffsets(j,j+11);
            stringsDumps[i]    = printBytes(j,12);
            stringsComments[i] = s + acs.decodeSmartAttribute( smartData[j] );
            j+=12;
            }
            if ( i>30 )
            {
            stringsNames[i]    = s + rowsNamesCommon[i-31];
            stringsOffsets[i]  = printOffsets(j,j);
            stringsDumps[i]    = printBytes(j,1); 
            
            if (j==362) { stringsComments[i] = s + acs.
                          decodeOffLineDataCollectionStatus( smartData[j] ); }

            if (j==363) { stringsComments[i] = s + acs.
                          decodeSelfTestExecutionStatus( smartData[j] ); }
            
            j++;
            }
            
            

            
        }
        
    }

//---------- Helper methods ----------------------------------------------------

private String printOffsets (int x1, int x2)
    {
    String s=" ";
    if (x1==x2) { s = s + x1; }
    else        { s = s + x1 + "-" + x2; }
    return s; }

private String printBytes (int base, int length)
    {
    String s=" ";
    for ( int i=base; i<base+length; i++ )
        {
        s = s + String.format( "%02X" , smartData[i] );
        if ( i >= base+length-1 ) break;
        s = s + ",";
        }
    return s; }

}
