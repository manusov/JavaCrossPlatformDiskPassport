package diskpassport;

public class Decode81 implements Decoder {

enum Cmp { LIST , RANGES , ALL }

private Object[][] acsMinorVersions =
{
{ Cmp.LIST  , 0x0000, 0xFFFF, "Minor version is not reported"                },
{ Cmp.RANGES, 0x0001, 0x0012, 
              0x0014, 0x0014, 
              0x0017, 0x0017, "Obsolete"                                     },
{ Cmp.LIST  , 0x0013,         "ATA/ATAPI-5 T13 1321D version 3"              },
{ Cmp.LIST  , 0x0015,         "ATA/ATAPI-5 T13 1321D version 1"              },
{ Cmp.LIST  , 0x0016,         "ATA/ATAPI-5 published, ANSI INCITS 340-2000"  },
{ Cmp.LIST  , 0x0018,         "ATA/ATAPI-6 T13 1410D version 0"              },
{ Cmp.LIST  , 0x0019,         "ATA/ATAPI-6 T13 1410D version 3a"             },
{ Cmp.LIST  , 0x001A,         "ATA/ATAPI-7 T13 1532D version 1"              },
{ Cmp.LIST  , 0x001B,         "ATA/ATAPI-6 T13 1410D version 2"              },
{ Cmp.LIST  , 0x001C,         "ATA/ATAPI-6 T13 1410D version 1"              },
{ Cmp.LIST  , 0x001D,         "ATA/ATAPI-7 published ANSI INCITS 397-2005"   },
{ Cmp.LIST  , 0x001E,         "ATA/ATAPI-7 T13 1532D version 0"              },
{ Cmp.LIST  , 0x001F,         "ACS-3 Revision 3b"                            },
{ Cmp.RANGES, 0x0020, 0x0020,
              0x0023, 0x0026,
              0x0030, 0x0030,
              0x0032, 0x0032,
              0x0034, 0x0038,
              0x003A, 0x0041,
              0x0043, 0x0051,
              0x0053, 0x005D,
              0x005F, 0x006C,
              0x006E, 0x0081,
              0x0083, 0x0106,
              0x0108, 0x0109,
              0x010B, 0x010F,
              0x0111, 0x011A,
              0x011C, 0xFFFE, "Reserved"                                     },
{ Cmp.LIST  , 0x0021,         "ATA/ATAPI-7 T13 1532D version 4a"             },
{ Cmp.LIST  , 0x0022,         "ATA/ATAPI-6 published, ANSI INCITS 361-2002"  },
{ Cmp.LIST  , 0x0027,         "ATA8-ACS version 3c"                          },
{ Cmp.LIST  , 0x0028,         "ATA8-ACS version 6"                           },
{ Cmp.LIST  , 0x0029,         "ATA8-ACS version 4"                           },
{ Cmp.LIST  , 0x0039,         "ATA8-ACS version 4c"                          },
{ Cmp.LIST  , 0x0042,         "ATA8-ACS version 3f"                          },
{ Cmp.LIST  , 0x0052,         "ATA8-ACS version 3b"                          },
{ Cmp.LIST  , 0x005E,         "ACS-4 Revision 5"                             },
{ Cmp.LIST  , 0x006D,         "ACS-3 Revision 5"                             },
{ Cmp.LIST  , 0x0082,         "ACS-2 published, ANSI INCITS 482-2012"        },
{ Cmp.LIST  , 0x0107,         "ATA8-ACS version 2d"                          },
{ Cmp.LIST  , 0x010A,         "ACS-3 published, ANSI INCITS 522-2014"        },
{ Cmp.LIST  , 0x0110,         "ACS-2 Revision 3"                             },
{ Cmp.LIST  , 0x011B,         "ACS-3 Revision 4"                             },
{ Cmp.ALL   ,                 "Unknown"                                      }
};

public String decodeValue(long x1)
    {
    int x = (int)x1;
    String s="?";
    Boolean b=false;
    int n = acsMinorVersions.length;
    int i=0,j=0;
    for (i=0; ((i<n)&&(b==false)); i++)
        {
        switch((Cmp)acsMinorVersions[i][0])
            {
            case LIST:
                j=1;
                while( !(acsMinorVersions[i][j] instanceof String ) )
                    { 
                    if ( x == (int)acsMinorVersions[i][j] ) { b=true; }
                    j++;
                    }
                break;
            case RANGES:
                j=1;
                while( !(acsMinorVersions[i][j] instanceof String ) )
                    { 
                    if ( x >= (int)acsMinorVersions[i][j]  &&
                         x <= (int)acsMinorVersions[i][j+1] )
                        { b=true; }
                    j+=2;
                    }
                break;
            case ALL:
                b=true;
                j=1;
                break;
            default:
                throw new AssertionError(((Cmp)acsMinorVersions[i][0]).name());
            }
        }
    s = (String)acsMinorVersions[i-1][j];
    return s;
    }
   
}
