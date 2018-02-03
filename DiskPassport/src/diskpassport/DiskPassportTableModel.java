package diskpassport;
import javax.swing.table.AbstractTableModel;

public class DiskPassportTableModel extends AbstractTableModel {

Decoder Decx;
    
private String[] columnsNames =
    {
    "Parameter"  ,
    "Word(s) #"  ,
    "Hex"        ,
    "Bit(s) #"   ,
    "Value"     ,
    "Comments"
    };

enum t { STR , PARM , CONT , TWO, TWOS , FOUR }
private Object[][] rowsControl =
{
//--- Model, revision, serial number strings ---
{ t.STR , "Drive model"           ,    27, 46                                 },
{ t.STR , "Firmware revision"     ,    23, 26                                 },
{ t.STR , "Serial number"         ,    10, 19                                 },
//--- Default geometry and size ---
{ t.PARM, "Default logical cylinders",  1, 15,  0, ""                         },
{ t.PARM, "Default logical heads",      3, 15,  0, ""                         },    
{ t.PARM, "Default logical sectors",    6, 15,  0, ""                         },
{ t.TWO , "Logical sector size, words",117,63,  0, new Decode117()            },
{ t.TWOS, "Total sectors, 28-bit mode", 60, 31, 0, new Decode60()             },
{ t.FOUR, "Total sectors, 48-bit mode",100, 63, 0, new Decode60()             },
//--- Current geometry and size ---
{ t.PARM, "CHS current logical cylinders" ,     54, 15,  0, ""                },
{ t.PARM, "CHS current logical heads" ,         55, 15,  0, ""                },    
{ t.PARM, "CHS current logical sectors" ,       56, 15,  0, ""                },
{ t.TWOS, "CHS current total sectors" ,         57, 31,  0, new Decode60()    },
//--- Size extensions ---
{ t.FOUR, "Extended total sectors, 48-bit mode", 230, 63, 0, new Decode60()   },
//--- General configuration ---
{ t.PARM, "General configuration" ,     0, 15, 14, new Decode0015()           },
{ t.CONT,                                  13, 13, "Reserved"                 },
{ t.CONT,                                  12,  8, "Device command set"       },
{ t.CONT,                                   7,  7, "Removable media"          },
{ t.CONT,                                   6,  6, "Not removable controller" },
{ t.CONT,                                   5,  5, "Retired"                  },
{ t.CONT,                                   4,  4, "Retired"                  },
{ t.CONT,                                   3,  3, "Retired"                  },
{ t.CONT,                                   2,  2, "Incomplete response"      },
{ t.CONT,                                   1,  1, "Retired"                  },
{ t.CONT,                                   0,  0, "Reserved"                 },
//--- Versions ---
{ t.PARM, "Major version number"  ,    80, 15, 12, "Reserved"                 },
{ t.CONT,                                  11, 11, "ACS-4"                    },
{ t.CONT,                                  10, 10, "ACS-3"                    },
{ t.CONT,                                   9,  9, "ACS-2"                    },
{ t.CONT,                                   8,  8, "ATA8-ACS"                 },
{ t.CONT,                                   7,  7, "ATA/ATAPI-7"              },
{ t.CONT,                                   6,  6, "ATA/ATAPI-6"              },
{ t.CONT,                                   5,  5, "ATA/ATAPI-5"              },
{ t.CONT,                                   4,  4, "ATA-4"                    },
{ t.CONT,                                   3,  3, "ATA3"                     },
{ t.CONT,                                   2,  2, "ATA-2"                    },
{ t.CONT,                                   1,  1, "ATA-1"                    },
{ t.CONT,                                   0,  0, "Reserved"                 },
{ t.PARM, "Minor version number"  ,    81, 15,  0, new Decode81()             },
{ t.PARM, "Transport major version number",222,15,12, new Decode22215()       },
{ t.CONT,                  11,11, "Reserved"                                  },
{ t.CONT,                  10,10, "Reserved"                                  },
{ t.CONT,                   9, 9, "Reserved"                                  },
{ t.CONT,                   8, 8, "Reserved"                                  },
{ t.CONT,                   7, 7, "Reserved"                                  },
{ t.CONT,                   6, 6, "Reserved"                                  },
{ t.CONT,                   5, 5, "SATA Rev 3.0"                              },
{ t.CONT,                   4, 4, "SATA Rev 2.6"                              },
{ t.CONT,                   3, 3, "SATA Rev 2.5"                              },
{ t.CONT,                   2, 2, "SATA II: Extensions"                       },
{ t.CONT,                   1, 1, "SATA 1.0a , ATA/ATAPI-7"                   },
{ t.CONT,                   0, 0, "ATA8-APT , ATA8-AST"                       },
{ t.PARM, "Transport minor version number"  , 223, 15, 0, ""                  },
//--- Specific configuration ---
{ t.PARM, "Specific configuration" ,            2, 15, 0, new Decode02()      },
{ t.PARM, "Vendor-specific",                    4, 15, 0, ""                  },
{ t.PARM, "Vendor-specific",                    5, 15, 0, ""                  },
{ t.TWO , "Number of sectors for CFA cards",    7, 31, 0, ""                  },
{ t.PARM, "Retired",                            9, 15, 0, ""                  },
{ t.PARM, "Retired",                           20, 15, 0, ""                  },
{ t.PARM, "Buffer size, 512-b units (retired)",21, 15, 0, new Decode21()      },
{ t.PARM, "Number of ECC bytes for READ/WRITE LONG", 22, 15, 0, ""            },
{ t.PARM, "READ/WRITE MULTIPLE support", 47, 15, 8, "Reserved"                },
{ t.CONT,                   7, 0, "Sectors per data request"                  },
{ t.PARM, "Trusted computing support",48,15,15, "Shall be 0"                  },
{ t.CONT,                   14,14, "Shall be 1"                               },
{ t.CONT,                   13, 1, "Reserved for the Trusted Computing Group" },
{ t.CONT,                    0, 0, "Trusted computing feature"                },
{ t.PARM, "Device capabilities word 1",49,15,14,"Reserved for packet device"  },
{ t.CONT,                   13,13, "Standby timer values is standard"         },
{ t.CONT,                   12,12, "Reserved for packet device"               },
{ t.CONT,                   11,11, "IORDY support"                            },
{ t.CONT,                   10,10, "IORDY may be disabled"                    },
{ t.CONT,                    9, 9, "LBA support"                              },
{ t.CONT,                    8, 8, "DMA support"                              },
{ t.CONT,                    7, 2, "Reserved"                                 },
{ t.CONT,                    1, 0, new Decode491()                            },
{ t.PARM, "Device capabilities word 2",50,15,15,"Shall be 0"                  },
{ t.CONT,                   14,14, "Shall be 1"                               },
{ t.CONT,                   13, 2, "Reserved"                                 },
{ t.CONT,                    1, 1, "Obsolete"                                 },
{ t.CONT,                    0, 0, "Device-specific standby timer minimum"    },
//--- Timings ---
{ t.PARM, "Standard PIO modes", 51, 15,11, "Reserved"                         },
{ t.CONT,                   10, 10, "PIO2 support"                            },
{ t.CONT,                    9,  9, "PIO1 support"                            },
{ t.CONT,                    8,  8, "PIO0 support"                            },
{ t.CONT,                    7,  0, "Vendor specific"                         },
{ t.PARM, "Obsolete",           52, 15, 0, ""                                 },
{ t.PARM, "Free fall control and validity flags", 53,15,8,
          "Reserved for e06144"                                               },
{ t.CONT,                    7,  3, "Reserved"                                },
{ t.CONT,                    2,  2, "Word 88 valid"                           },
{ t.CONT,                    1,  1, "Words 70:64 valid"                       },
{ t.CONT,                    0,  0, "Obsolete"                                },
//--- ACS features ---
{ t.PARM, "ATA Command Set features",   59, 15,15, "BLOCK ERASE EXT command"  },
{ t.CONT,                            14,14, "OVERWRITE EXT command"           },
{ t.CONT,                            13,13, "CRYPTO SCRAMBLE EXT command"     },
{ t.CONT,                            12,12, "The Sanitize feature set"        },
{ t.CONT,                            11,11, "Reserved"                        },
{ t.CONT,                            10,10, "Reserved"                        },
{ t.CONT,                             9, 9, "Reserved"                        },
{ t.CONT,                             8, 8, "Multiple logical sector setting" },
{ t.CONT,                             7, 0, "Current sectors per data request"},
{ t.PARM, "Obsolete" ,               62, 15, 0, ""                            },
//--- Multiword DMA modes ---
{ t.PARM, "Multiword DMA modes" ,     63, 15, 11, "Reserved"                  },
{ t.CONT,             10, 10, "Multiword DMA mode 2 selected"                 },
{ t.CONT,              9,  9, "Multiword DMA mode 1 selected"                 },
{ t.CONT,              8,  8, "Multiword DMA mode 0 selected"                 },
{ t.CONT,              7,  3, "Reserved"                                      },
{ t.CONT,              2,  2, "Multiword DMA mode 2 and below are supported"  },
{ t.CONT,              1,  1, "Multiword DMA mode 1 and below are supported"  },
{ t.CONT,              0,  0, "Multiword DMA mode 0 is supported"             },
//--- Advanced PIO modes ---
{ t.PARM, "Advanced PIO modes" ,     64, 15, 2, "Reserved"                    },
{ t.CONT,              1,  1, "PIO mode 4 support"                            },
{ t.CONT,              0,  0, "PIO mode 3 support"                            },
//--- Timings ---
{ t.PARM, "Minimum multiword DMA cycle time"        , 65,15,0, "ns"           },
{ t.PARM, "Recommended multiword DMA cycle time"    , 66,15,0, "ns"           },
{ t.PARM, "Minimum PIO cycle time w/o flow control" , 67,15,0, "ns"           },
{ t.PARM, "Minimum PIO cycle time with IORDY"       , 68,15,0, "ns"           },
//--- Additional features supported ---
{ t.PARM, "Additional features supported", 69, 15, 15, "Reserved for CFA"     },
{ t.CONT, 14, 14, "Deterministic data in trimmed LBA range(s)"                },
{ t.CONT, 13, 13, "Long physical sector alignment error reporting control"    },
{ t.CONT, 12, 12, "Obsolete"                                                  },
{ t.CONT, 11, 11, "READ BUFFER DMA"                                           },
{ t.CONT, 10, 10, "WRITE BUFFER DMA"                                          },
{ t.CONT,  9,  9, "Obsolete"                                                  },
{ t.CONT,  8,  8, "DOWNLOAD MICROCODE DMA is supported"                       },
{ t.CONT,  7,  7, "Reserved for IEEE 1667"                                    },
{ t.CONT,  6,  6, "Optional ATA device 28-bit commands"                       },
{ t.CONT,  5,  5, "Trimmed LBA range(s) returning zeroed data"                },
{ t.CONT,  4,  4, "Device encrypts all user data"                             },
{ t.CONT,  3,  3, "Extended number of user addressable sectors"               },
{ t.CONT,  2,  2, "All write cache is non-volatile"                           },
{ t.CONT,  1,  0, new Decode691()                                             },
//--- Reserved ---
{ t.PARM,  "Reserved"                            , 70, 15, 0 , ""             },
{ t.PARM,  "Reserved for IDENTIFY PACKET DEVICE" , 71, 15, 0 , ""             },
{ t.PARM,  "Reserved for IDENTIFY PACKET DEVICE" , 72, 15, 0 , ""             },
{ t.PARM,  "Reserved for IDENTIFY PACKET DEVICE" , 73, 15, 0 , ""             },
{ t.PARM,  "Reserved for IDENTIFY PACKET DEVICE" , 74, 15, 0 , ""             },
//--- NCQ and other SATA capabilities ---
{ t.PARM, "NCQ queue depth" ,       75, 15, 5, "Reserved"                     },
{ t.CONT,                    4, 0, "Queue depth as last element"              },
{ t.PARM, "Serial ATA Capabilities", 76, 15,15, "Supports READ LOG DMA EXT"   },
{ t.CONT,                 14,14, "Device auto Partial to Slumber transitions" },
{ t.CONT,                 13,13, "Host auto Partial to Slumber transitions"   },
{ t.CONT,                 12,12, "NCQ priority information"                   },
{ t.CONT,                 11,11, "Unload while NCQ command are outstanding"   },
{ t.CONT,                 10,10, "SATA Phy event counters log"                },
{ t.CONT,                  9, 9, "Receipt of host initiated power management" },
{ t.CONT,                  8, 8, "NCQ feature set"                            },
{ t.CONT,                  7, 7, "Reserved for SATA"                          },
{ t.CONT,                  6, 6, "Reserved for SATA"                          },
{ t.CONT,                  5, 5, "Reserved for SATA"                          },
{ t.CONT,                  4, 4, "Reserved for SATA"                          },
{ t.CONT,                  3, 3, "SATA Gen3 signaling speed 6.0 Gb/s"         },
{ t.CONT,                  2, 2, "SATA Gen2 signaling speed 3.0 Gb/s"         },
{ t.CONT,                  1, 1, "SATA Gen1 signaling speed 1.5 Gb/s"         },
{ t.CONT,                  0, 0, "Shall be zero"                              },
{ t.PARM, "Serial ATA capabilities",      77,15,15, "Reserved for SATA"       },
{ t.CONT,                                    14,14, "Reserved for SATA"       },
{ t.CONT,                                    13,13, "Reserved for SATA"       },
{ t.CONT,                                    12,12, "Reserved for SATA"       },
{ t.CONT,                                    11,11, "Reserved for SATA"       },
{ t.CONT,                                    10,10, "Reserved for SATA"       },
{ t.CONT,                                     9, 9, "Reserved for SATA"       },
{ t.CONT,                                     8, 8, "Reserved for SATA"       },
{ t.CONT,                                     7, 7, "Reserved for SATA"       },
{ t.CONT,                           6,6, "RECEIVE/SEND FPDMA QUEUED commands" },
{ t.CONT,                           3,1, new Decode7731()                     },
{ t.CONT,                           0,0, "Shall be zero"                      },
{ t.PARM, "Serial ATA capabilities",      78,15,13, "Reserved for SATA"       },
{ t.CONT,            12, 12, "Power disable feature"                          },
{ t.CONT,            11, 11, "Rebuild assist feature set"                     },
{ t.CONT,            10,  9, "Reserved for SATA"                              },
{ t.CONT,             8,  8, "Device sleep feature"                           },
{ t.CONT,             7,  7, "NCQ autosense"                                  },
{ t.CONT,             6,  6, "Software settings preservation"                 },
{ t.CONT,             5,  5, "Hardware feature Control"                       },
{ t.CONT,             4,  4, "In-order data delivery"                         },
{ t.CONT,             3,  3, "Initiating power management"                    },
{ t.CONT,             2,  2, "DMA setup auto-activation"                      },
{ t.CONT,             1,  1, "Nonzero buffer offsets"                         },
{ t.CONT,             0,  0, "Shall be 0"                                     },
{ t.PARM, "Serial ATA capabilities enabled", 79,15,12, "Reserved for SATA"    },
{ t.CONT,            11, 11,  "Rebuild assist feature set enabled"            },
{ t.CONT,            10, 10,  "Power disable feature enabled"                 },
{ t.CONT,             9,  9,  "Reserved for SATA"                             },
{ t.CONT,             8,  8,  "Device sleep feature enabled"                  },
{ t.CONT,             7,  7,  "Auto Partial to Slumber transitions enabled"   },
{ t.CONT,             6,  6,  "Software settings preservation enabled"        },
{ t.CONT,             5,  5,  "Hardware feature control is enabled"           },
{ t.CONT,             4,  4,  "In-order data delivery enabled"                },
{ t.CONT,             3,  3,  "Device initiated power management enabled"     },
{ t.CONT,             2,  2,  "DMA setup auto-activation enabled"             },
{ t.CONT,             1,  1,  "Nonzero buffer offsets enabled"                },
{ t.CONT,             0,  0,  "Shall be 0"                                    },
//--- Command set supported ---
{ t.PARM, "Commands and features support",  82,15,15,"Obsolete"               },
{ t.CONT,                         14, 14, "NOP command"                       },
{ t.CONT,                         13, 13, "READ BUFFER command"               },
{ t.CONT,                         12, 12, "WRITE BUFFER command"              },
{ t.CONT,                         11, 11, "Obsolete"                          },
{ t.CONT,                         10, 10, "Host Protected Area feature set"   },
{ t.CONT,                          9,  9, "DEVICE RESET command"              },
{ t.CONT,                          8,  8, "SERVICE interrupt"                 },
{ t.CONT,                          7,  7, "Release interrupt"                 },
{ t.CONT,                          6,  6, "Look-ahead"                        },
{ t.CONT,                          5,  5, "Write cache"                       },
{ t.CONT,                          4,  4, "PACKET feature set"                },
{ t.CONT,                     3,  3, "Mandatory Power Management feature set" },
{ t.CONT,                          2,  2, "Obsolete"                          },
{ t.CONT,                          1,  1, "Security Mode feature set"         },
{ t.CONT,                          0,  0, "SMART feature set"                 },
{ t.PARM, "Commands and features support",  83,15,15,"Shall be 0"             },
{ t.CONT,                         14,14,  "Shall be 1"                        },
{ t.CONT,                         13,13,  "FLUSH CACHE EXT command"           },
{ t.CONT,                         12,12,  "Mandatory FLUSH CACHE EXT command" },
{ t.CONT,                         11,11,  "Device Configuration Overlay"      },
{ t.CONT,                         10,10,  "48-bit address feature set"        },
{ t.CONT,                          9, 9,  "Automatic Acoustic Management"     },
{ t.CONT,                          8, 8,  "SET MAX security extension"        },
{ t.CONT,    7, 7,  "Set address offset reserved area boot, INCITS TR27:2001" },
{ t.CONT,    6, 6,  "SET FEATURES subcommand required to spin-up"             },
{ t.CONT,    5, 5,  "Power-Up in standby feature set"                         },
{ t.CONT,    4, 4,  "Obsolete"                                                },
{ t.CONT,    3, 3,  "Advanced Power Management feature set"                   },
{ t.CONT,    2, 2,  "CFA feature set"                                         },
{ t.CONT,    1, 1,  "READ/WRITE DMA QUEUED"                                   },
{ t.CONT,    0, 0,  "DOWNLOAD MICROCODE command"                              },
{ t.PARM, "Commands and features support", 84,15,15, "Shall be 0"             },
{ t.CONT,   14, 14, "Shall be 1"                                              },
{ t.CONT,   13, 13, "IDLE immediate with UNLOAD FEATURE"                      },
{ t.CONT,   12, 12, "Reserved for technical report INCITS TR-37-2004(TLC)"    },
{ t.CONT,   11, 11, "Reserved for technical report INCITS TR-37-2004(TLC)"    },
{ t.CONT,   10,  9, "Obsolete"                                                },
{ t.CONT,    8,  8, "64-bit World Wide Name"                                  },
{ t.CONT,    7,  7, "WRITE DMA QUEUED FUA EXT command"                        },
{ t.CONT,    6,  6, "WRITE DMA FUA EXT, WRITE MULTIPLE FUA EXT commands"      },
{ t.CONT,    5,  5, "General Purpose Logging feature set"                     },
{ t.CONT,    4,  4, "Streaming feature set supported"                         },
{ t.CONT,    3,  3, "Media card pass through command feature set"             },
{ t.CONT,    2,  2, "Media serial number supported"                           },
{ t.CONT,    1,  1, "SMART self-test supported"                               },
{ t.CONT,    0,  0, "SMART error logging supported"                           },
{ t.PARM, "Commands and features enable / support", 85,15,15, "Obsolete"      },
{ t.CONT,   14, 14, "NOP command"                                             },
{ t.CONT,   13, 13, "READ BUFFER command"                                     },
{ t.CONT,   12, 12, "WRITE BUFFER command"                                    },
{ t.CONT,   11, 11, "Obsolete"                                                },
{ t.CONT,   10, 10, "Host Protected Area has been established, for max.LBA"   },
{ t.CONT,    9,  9, "DEVICE RESET command"                                    },
{ t.CONT,    8,  8, "SERVICE interrupt enabled"                               },
{ t.CONT,    7,  7, "Release interrupt enabled"                               },
{ t.CONT,    6,  6, "Look-ahead enabled"                                      },
{ t.CONT,    5,  5, "Write cache enabled"                                     },
{ t.CONT,    4,  4, "PACKET feature set"                                      },
{ t.CONT,    3,  3, "Power Management feature set"                            },
{ t.CONT,    2,  2, "Obsolete"                                                },
{ t.CONT,    1,  1, "Security Mode feature set enabled"                       },
{ t.CONT,    0,  0, "SMART feature set enabled"                               },
{ t.PARM, "Commands and features enable / support", 86,15,15,
          "Words 120:119 valid" },
{ t.CONT,   14, 14, "Reserved"                                                },
{ t.CONT,   13, 13, "FLUSH CACHE EXT command"                                 },
{ t.CONT,   12, 12, "FLUSH CACHE command"                                     },
{ t.CONT,   11, 11, "Device configuration overlay supported"                  },
{ t.CONT,   10, 10, "48-bit address feature set"                              },
{ t.CONT,    9,  9, "Automatic acoustic management feature set enabled"       },
{ t.CONT,    8,  8, "SET MAX security extension enabled"                      },
{ t.CONT,    7,  7, "Reserved for Address offset reserved area boot"          },
{ t.CONT,    6,  6, "SET FEATURES subcommand required to spin-up"             },
{ t.CONT,    5,  5, "Power-Up in standby (PUIS) feature set enabled"          },
{ t.CONT,    4,  4, "Obsolete"                                                },
{ t.CONT,    3,  3, "Advanced Power Management feature set enabled"           },
{ t.CONT,    2,  2, "CFA feature set"                                         },
{ t.CONT,    1,  1, "READ/WRITE DMA QUEUED command"                           },
{ t.CONT,    0,  0, "DOWNLOAD MICROCODE command"                              },
{ t.PARM, "Commands and features support", 87,15,15, "Shall be 0"             },
{ t.CONT,   14, 14, "Shall be 1"                                              },
{ t.CONT,   13, 13, "IDLE IMMEDIATE with UNLOAD FEATURE"                      },
{ t.CONT,   12, 12, "Reserved for technical report INCITS TR-37-2004(TLC)"    },
{ t.CONT,   11, 11, "Reserved for technical report INCITS TR-37-2004(TLC)"    },
{ t.CONT,   10,  9, "Obsolete"                                                },
{ t.CONT,    8,  8, "64 bit World Wide Name supported"                        },
{ t.CONT,    7,  7, "WRITE DMA QUEUED FUA EXT command"                        },
{ t.CONT,    6,  6, "WRITE DMA FUA EXT, WRITE MULTIPLE FUA EXT commands"      },
{ t.CONT,    5,  5, "General Purpose Logging feature set"                     },
{ t.CONT,    4,  4, "Obsolete"                                                },
{ t.CONT,    3,  3, "Media card pass through command feature set"             },
{ t.CONT,    2,  2, "Media serial number is valid"                            },
{ t.CONT,    1,  1, "SMART self-test supported"                               },
{ t.CONT,    0,  0, "SMART error logging supported"                           },
//--- Ultra DMA modes ---
{ t.PARM, "Ultra DMA modes enables / supported",88,15,15, "Reserved"          },
{ t.CONT,    14, 14, "UDMA6 selected"                                         },
{ t.CONT,    13, 13, "UDMA5 selected"                                         },
{ t.CONT,    12, 12, "UDMA4 selected"                                         },
{ t.CONT,    11, 11, "UDMA3 selected"                                         },
{ t.CONT,    10, 10, "UDMA2 selected"                                         },
{ t.CONT,     9,  9, "UDMA1 selected"                                         },
{ t.CONT,     8,  8, "UDMA0 selected"                                         },
{ t.CONT,     7,  7, "Reserved"                                               },
{ t.CONT,     6,  6, "UDMA6 and below supported"                              },
{ t.CONT,     5,  5, "UDMA5 and below supported"                              },
{ t.CONT,     4,  4, "UDMA4 and below supported"                              },
{ t.CONT,     3,  3, "UDMA3 and below supported"                              },
{ t.CONT,     2,  2, "UDMA2 and below supported"                              },
{ t.CONT,     1,  1, "UDMA1 and below supported"                              },
{ t.CONT,     0,  0, "UDMA0 supported"                                        },
//--- Times required for Secure Erase ---
{ t.PARM, "Normal erase time, SECURITY ERASE UNIT"  , 89,15,0, new Decode89() },
{ t.PARM, "Enhanced erase time, SECURITY ERASE UNIT", 90,15,0, new Decode89() },
//--- Device state ---
{ t.PARM, "Advanced Power Management level" ,         91,15,0, ""             },
{ t.PARM, "Master Password Identifier" ,              92,15,0, ""             },
{ t.PARM, "Hardware reset result" ,                   93,15,0, "Shall be 0"   },
{ t.CONT,     14, 14, "Shall be 1"                                            },
{ t.CONT,     13, 13, "Device CBLID (Cable ID) signal level relative Vihb"    },
{ t.CONT,     12, 12, "Reserved"                                              },
{ t.CONT,     11, 11, "Device 1 asserted PDIAG-"                              },
{ t.CONT,     10,  9, new Decode9310()                                        },
{ t.CONT,      8,  8, "Shall be 1"                                            },
{ t.CONT,      7,  7, "Reserved"                                              },
{ t.CONT,      6,  6, "Device 0 responds when Device 1 is selected"           },
{ t.CONT,      5,  5, "Device 0 detected the assertion of DASP-"              },
{ t.CONT,      4,  4, "Device 0 detected the assertion of PDIAG-"             },
{ t.CONT,      3,  3, "Device 0 passed diagnostics"                           },
{ t.CONT,      2,  1, new Decode932()                                         },
{ t.CONT,      0,  0, "Shall be 1 for PATA devices"                           },
//--- AAM ---
{ t.PARM, "Automatic acoustic management", 94, 15, 8,
          "Vendor recommended AAM value"                                      },
{ t.CONT, 7, 0, "Current AAM value"                                           },
//--- Stream and data set management ---
{ t.PARM, "Stream minimum request size" ,               95,15,0 , "sectors"   },
{ t.PARM, "Streaming transfer time - DMA" ,             96,15,0 , ""          },
{ t.PARM, "Streaming access latency - DMA and PIO" ,    97,15,0 , ""          },
{ t.TWO,  "Streaming performance granularity" ,         98,31,0 , ""          },
{ t.PARM, "Streaming transfer time - PIO" ,                 104, 15, 0, ""    },
{ t.PARM, "Max. 512-byte blocks per DATA SET MANAGEMENT" ,  105, 15, 0, ""    },
//--- Physical sector / logical sector ---
{ t.PARM, "Physical sector / Logical sector size", 106,15,15,"Shall be 0"     },
{ t.CONT,   14, 14, "Shall be 1"                                              },
{ t.CONT,   13, 13, "Multiple logical sectors per physical sector"            },
{ t.CONT,   12, 12, "Device logical sector longer than 256 words"             },
{ t.CONT,   11,  4, "Reserved"                                                },
{ t.CONT,    3,  0, "2^X logical sectors per physical sector"                 },
//--- Inter-seek delay ---
{ t.PARM, "Inter-seek delay for ISO/IEC 7779 acoustic test", 107, 15, 0, "us" },
{ t.PARM, "World wide name, word 1" , 108 , 15 , 0 , ""                       },
{ t.PARM, "World wide name, word 2" , 109 , 15 , 0 , ""                       },
{ t.PARM, "World wide name, word 3" , 110 , 15 , 0 , ""                       },
{ t.PARM, "World wide name, word 4" , 111 , 15 , 0 , ""                       },
{ t.PARM, "Reserved"                , 112 , 15 , 0 , ""                       },
{ t.PARM, "Reserved"                , 113 , 15 , 0 , ""                       },
{ t.PARM, "Reserved"                , 114 , 15 , 0 , ""                       },
{ t.PARM, "Reserved"                , 115 , 15 , 0 , ""                       },
{ t.PARM, "Reserved for TLC"        , 116 , 15 , 0 , ""                       },
//--- Continue from words 84-82 ---
{ t.PARM, "Supported settings",                    119,15,15, "Shall be 0"    },
{ t.CONT,  14, 14, "Shall be 1"                                               },
{ t.CONT,  13,  6, "Reserved"                                                 },
{ t.CONT,   5,  5, "Reserved for e06144"                                      },
{ t.CONT,   4,  4, "Segment feature for DOWNLOAD MICROCODE"                   },
{ t.CONT,   3,  3, "READ / WRITE DMA EXT GPL optional commands"               },
{ t.CONT,   2,  2, "WRITE UNCORRECTABLE command"                              },
{ t.CONT,   1,  1, "Write-Read-Verify feature set"                            },
{ t.CONT,   0,  0, "Reserved for DT1825"                                      },
//--- Continue from words 87-85 ---
{ t.PARM, "Commands and features enable / support", 120,15,15, "Shall be 0"   },
{ t.CONT,  14, 14, "Shall be 1"                                               },
{ t.CONT,  13,  6, "Reserved"                                                 },
{ t.CONT,   5,  5, "Reserved for e06144"                                      },
{ t.CONT,   4,  4, "Segment feature for DOWNLOAD MICROCODE"                   },
{ t.CONT,   3,  3, "READ / WRITE DMA EXT GPL optional commands"               },
{ t.CONT,   2,  2, "WRITE UNCORRECTABLE command"                              },
{ t.CONT,   1,  1, "Write-Read-Verify feature set"                            },
{ t.CONT,   0,  0, "Reserved for DT1825"                                      },
//--- Reserved and Obsolete ---
{ t.PARM, "Reserved" , 121, 15, 0, ""                                         },
{ t.PARM, "Reserved" , 122, 15, 0, ""                                         },
{ t.PARM, "Reserved" , 123, 15, 0, ""                                         },
{ t.PARM, "Reserved" , 124, 15, 0, ""                                         },
{ t.PARM, "Reserved" , 125, 15, 0, ""                                         },
{ t.PARM, "Reserved" , 126, 15, 0, ""                                         },
{ t.PARM, "Obsolete" , 127, 15, 0, ""                                         },
//--- Security status ---
{ t.PARM, "Security status", 128,15,9, "Reserved"                             },
{ t.CONT,  8,  8, "Security level: 0=High, 1=Maximum"                         },
{ t.CONT,  7,  6, "Reserved"                                                  },
{ t.CONT,  5,  5, "Enhanced security erase"                                   },
{ t.CONT,  4,  4, "Security count expired"                                    },
{ t.CONT,  3,  3, "Security frozen"                                           },
{ t.CONT,  2,  2, "Security locked"                                           },
{ t.CONT,  1,  1, "Security enabled"                                          },
{ t.CONT,  0,  0, "Security support"                                          },
//--- Vendor-specific, reserved ---
{ t.PARM, "Vendor specific"                             , 129 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 130 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 131 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 132 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 133 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 134 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 135 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 136 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 137 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 138 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 139 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 140 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 141 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 142 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 143 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 144 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 145 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 146 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 147 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 148 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 149 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 150 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 151 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 152 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 153 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 154 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 155 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 156 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 157 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 158 , 15 , 0 , ""   },
{ t.PARM, "Vendor specific"                             , 159 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 160 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 161 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 162 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters4"                             , 163 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 164 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 165 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 166 , 15 , 0 , ""   },
{ t.PARM, "CFA parameters"                              , 167 , 15 , 0 , ""   },
{ t.PARM, "Device nominal form factor"                  , 168 , 15 , 0 , ""   },
{ t.PARM, "DATA SET MANAGEMENT command support options" , 169 , 15 , 0 , ""   },
{ t.PARM, "Additional product identifier, word 1"       , 170 , 15 , 0 , ""   },
{ t.PARM, "Additional product identifier, word 2"       , 171 , 15 , 0 , ""   },
{ t.PARM, "Additional product identifier, word 3"       , 172 , 15 , 0 , ""   },
{ t.PARM, "Additional product identifier, word 4"       , 173 , 15 , 0 , ""   },
{ t.PARM, "Reserved"                                    , 174 , 15 , 0 , ""   },
{ t.PARM, "Reserved"                                    , 175 , 15 , 0 , ""   },
//--- Current media serial number ---
{ t.STR , "Current media serial number" ,   176, 205                          },
//--- SCT ---
{ t.PARM, "SCT Command Transport", 206, 15, 12, "Vendor specific"             },
{ t.CONT,                               11, 11, "Reserved"                    },
{ t.CONT,                               10, 10, "Reserved"                    },
{ t.CONT,                                9,  9, "Reserved"                    },
{ t.CONT,                                8,  8, "Reserved"                    },
{ t.CONT,                                7,  7, "Reserved"                    },
{ t.CONT,                                6,  6, "Reserved"                    },
{ t.CONT,                                5,  5, "SCT data tables"             },
{ t.CONT,                                4,  4, "SCT features control"        },
{ t.CONT,                                3,  3, "SCT error recovery control"  },
{ t.CONT,                                2,  2, "SCT write same"              },
{ t.CONT,                                1,  1, "SCT long sector access"      },
{ t.CONT,                                0,  0, "SCT command transport"       },
//--- Common and reserved features ---
{ t.PARM, "Reserved for CE-ATA",   207, 15, 15, ""                            },
{ t.PARM, "Reserved for CE-ATA",   208, 15, 15, ""                            },
{ t.PARM, "Alignment logical block at physical",209,15,15,"Shall be zero"     },
{ t.CONT,                                14,14, "Shall be one"                },
{ t.CONT,                                13, 0, "Logical sector offset"       },
{ t.TWO , "Write-Read-Verify sector mode 3 count", 210, 63, 0, ""             },
{ t.TWO , "Write-Read-Verify sector mode 2 count", 212, 63, 0, ""             },
{ t.PARM, "Obsolete",                              214, 15, 0, ""             },
{ t.PARM, "Obsolete",                              215, 15, 0, ""             },
{ t.PARM, "Obsolete",                              216, 15, 0, ""             },
//--- Rotation, plus ---
{ t.PARM, "Nominal media rotation rate",           217, 15, 0, ""             },
{ t.PARM, "Reserved",                              218, 15, 0, ""             },
{ t.PARM, "Obsolete",                              219, 15, 0, ""             },
//--- Reserved for CE-ATA ---
{ t.PARM, "Reserved for CE-ATA",   224, 15,  0, ""                            },
{ t.PARM, "Reserved for CE-ATA",   225, 15,  0, ""                            },
{ t.PARM, "Reserved for CE-ATA",   226, 15,  0, ""                            },
{ t.PARM, "Reserved for CE-ATA",   227, 15,  0, ""                            },
{ t.PARM, "Reserved for CE-ATA",   228, 15,  0, ""                            },
{ t.PARM, "Reserved for CE-ATA",   229, 15,  0, ""                            },
//--- Download microcode support ---
{ t.PARM, "Minimum 512-b data blocks per download microcode", 234,15,0, ""    },
{ t.PARM, "Maximum 512-b data blocks per download microcode", 235,15,0, ""    },
//--- Reserved ---
{ t.PARM, "Reserved",              236, 15,  0, ""                            },
{ t.PARM, "Reserved",              237, 15,  0, ""                            },
{ t.PARM, "Reserved",              238, 15,  0, ""                            },
{ t.PARM, "Reserved",              239, 15,  0, ""                            },
{ t.PARM, "Reserved",              240, 15,  0, ""                            },
{ t.PARM, "Reserved",              241, 15,  0, ""                            },
{ t.PARM, "Reserved",              242, 15,  0, ""                            },
{ t.PARM, "Reserved",              243, 15,  0, ""                            },
{ t.PARM, "Reserved",              244, 15,  0, ""                            },
{ t.PARM, "Reserved",              245, 15,  0, ""                            },
{ t.PARM, "Reserved",              246, 15,  0, ""                            },
{ t.PARM, "Reserved",              247, 15,  0, ""                            },
{ t.PARM, "Reserved",              248, 15,  0, ""                            },
{ t.PARM, "Reserved",              249, 15,  0, ""                            },
{ t.PARM, "Reserved",              250, 15,  0, ""                            },
{ t.PARM, "Reserved",              251, 15,  0, ""                            },
{ t.PARM, "Reserved",              252, 15,  0, ""                            },
{ t.PARM, "Reserved",              253, 15,  0, ""                            },
{ t.PARM, "Reserved",              254, 15,  0, ""                            },
//--- Integrity word ---
{ t.PARM, "Integrity word",        255, 15,  8, "Checksum"                    },
{ t.CONT,                                7,  0, "Signature"                   },


//--- End of passport data ---
};

private String[] wordsNames, wordsNumbers, wordsHex, 
                 wordsBits, wordsValues, parmsComments;

private short[] diskPassport;
private final static int diskPassportLength = 256;

//---------- Table model constructor -------------------------------------------
DiskPassportTableModel()
    {
//--- Initializing and blank ---
    diskPassport = new short[diskPassportLength];
    for (int i=0; i<diskPassportLength; i++)
        {
        diskPassport[i]=0;
        }
    int n = rowsControl.length;
    String s = " ";
    wordsNames    = new String[n];
    wordsNumbers  = new String[n];
    wordsHex      = new String[n];
    wordsBits     = new String[n];
    wordsValues   = new String[n];
    parmsComments = new String[n];
    for (int i=0; i<n; i++)
        {
        wordsNames[i]    = s;
        wordsNumbers[i]  = s;
        wordsHex[i]      = s;
        wordsBits[i]     = s;
        wordsValues[i]   = s;
        parmsComments[i] = s;
        }
    }

//---------- Get strings for output --------------------------------------------
public Object getValueAt (int row, int column)
    { 
        switch (column)
        {
        case 0:  return " " + wordsNames[row];
        case 1:  return " " + wordsNumbers[row];
        case 2:  return " " + wordsHex[row];
        case 3:  return " " + wordsBits[row];
        case 4:  return " " + wordsValues[row];
        case 5:  return " " + parmsComments[row];
        default: return null;            
        }
    }

//---------- Table configuration -----------------------------------------------
public int getColumnCount ()             { return columnsNames.length;  }
public int getRowCount ()                { return rowsControl.length;   }
public boolean isCellEditable ()         { return false;                }
public String getColumnName (int column) { return columnsNames[column]; }

//---------- Specific methods --------------------------------------------------
public byte[] getDiskPassport()
    {
    byte[] dpb = new byte[diskPassportLength*2];
    int x;
    for (int i=0; i<diskPassportLength; i++)
        {
        x = diskPassport[i];
        dpb[i*2]   = ( byte )( x & 0xFF );
        dpb[i*2+1] = ( byte )( (x >> 8) & 0xFF );
        }
    return dpb;
    }

public void setDiskPassport(byte[] dpb )
    {
    int x1, x2;
    for (int i=0; i<diskPassportLength; i++)
        {
        x1 = dpb[i*2];
        x2 = dpb[i*2+1];
        x1 = x1 & 0xFF;
        x2 = (x2 & 0xFF) << 8;
        x1 = x1 | x2;
        diskPassport[i] = (short)x1;
        }
    }

public void builtDiskPassport()
    {
//--- Blank strings ---
    int n = rowsControl.length;
    String s = " ";
    wordsNames    = new String[n];
    wordsNumbers  = new String[n];
    wordsHex      = new String[n];
    wordsBits     = new String[n];
    wordsValues   = new String[n];
    parmsComments = new String[n];
    for (int i=0; i<n; i++)
        {
        wordsNames[i]    = s;
        wordsNumbers[i]  = s;
        wordsHex[i]      = s;
        wordsBits[i]     = s;
        wordsValues[i]   = s;
        parmsComments[i] = s;
        }
//--- Built strings ---
    int x1=0, x2=0, x3=0, y1=0, y2=0, z1=0, z2=0;

    long sector0 = diskPassport[117];
    long sector1 = diskPassport[118] << 16;
    long sector = sector0 + sector1;
    if ( (sector<256) | (sector>2048) | ((sector&0xFF)>0) | (sector==0)  )
          { sector = 512; }
    else  { sector = sector*2; }
    
    for (int i=0; i<n; i++)
        {
        switch((t)(rowsControl[i][0]))
            {
            
            case STR:
                {
                wordsNames[i] = wordsNames[i] + rowsControl[i][1];
                x1 = (int)rowsControl[i][2];
                x2 = (int)rowsControl[i][3];
                if ((x1<x2)&&((x2-x1)<20)) { x3=x2-x1+1; } else { x3=0; }
                wordsNumbers[i] = String.format( "%d-%d", x1, x2 );
                int k=x1;
                for (int j=0; j<x3; j++)
                    {
                    y1 = diskPassport[k++];
                    wordsHex[i] = wordsHex[i] + String.format("%04X",y1);
                    char c1 = (char)((y1 >> 8) & 0xFF);
                    if ((c1<0x20)|(c1>0x7E)) { c1 = '_'; }
                    parmsComments[i] = parmsComments[i] + c1;
                    c1 = (char)(y1 & 0xFF);
                    parmsComments[i] = parmsComments[i] + c1;
                    if (j==x3-1) break;
                    wordsHex[i] = wordsHex[i] + ",";
                    }
                break;
                }
                
            case PARM:
                {
                wordsNames[i] = wordsNames[i] + rowsControl[i][1];
                x1 = (int)rowsControl[i][2];
                y1 = diskPassport[x1] & 0xFFFF;
                z1 = (int)rowsControl[i][3];
                z2 = (int)rowsControl[i][4];
                wordsNumbers[i] = wordsNumbers[i] + 
                        String.format( "%d", x1 );
                wordsHex[i] = wordsHex[i] + 
                        String.format("%04X", y1);
                if (z1!=z2) { s = String.format("%d-%d",z1,z2); }
                else        { s = String.format("%d",z1);       }
                wordsBits[i] = s;
                y2 = fieldExtract(y1,z1,z2);
                wordsValues[i] = String.format("%d",y2);
                parmsComments[i] = parmsComments[i] + 
                    stringOrDecoder ( rowsControl[i][5] , y2 );
                break;
                }
            
            case CONT:
                {
                z1 = (int)rowsControl[i][1];
                z2 = (int)rowsControl[i][2];
                if (z1!=z2) { s = String.format("%d-%d",z1,z2); }
                else        { s = String.format("%d",z1);       }
                wordsBits[i] = s;
                y2 = fieldExtract(y1,z1,z2);
                wordsValues[i] = String.format("%d",y2);
                parmsComments[i] = parmsComments[i] + 
                    stringOrDecoder ( rowsControl[i][3] , y2 );
                break;
                }
                
            case TWO:
                {
                wordsNames[i] = wordsNames[i] + rowsControl[i][1];
                x1 = (int)rowsControl[i][2];
                x2 = x1+1;
                wordsNumbers[i] = String.format( "%d-%d", x1, x2 );
                x3=2;
                int k=x1;
                for (int j=0; j<x3; j++)
                    {
                    y1 = diskPassport[k++] & 0xFFFF;
                    wordsHex[i] = wordsHex[i] + String.format("%04X",y1);
                    if (j==x3-1) break;
                    wordsHex[i] = wordsHex[i] + ",";
                    }
                z1 = (int)rowsControl[i][3];
                z2 = (int)rowsControl[i][4];
                if (z1!=z2) { s = String.format("%d-%d",z1,z2); }
                else        { s = String.format("%d",z1);       }
                wordsBits[i] = s;
                long data0 = diskPassport[x1];
                long data1 = diskPassport[x1+1] << 16;
                long data = data0 + data1;
                wordsValues[i] = String.format("%d", data);
                parmsComments[i] = parmsComments[i] + 
                    stringOrDecoder ( rowsControl[i][5] , data );
                break;
                }

            case TWOS:
                {
                wordsNames[i] = wordsNames[i] + rowsControl[i][1];
                x1 = (int)rowsControl[i][2];
                x2 = x1+1;
                wordsNumbers[i] = String.format( "%d-%d", x1, x2 );
                x3=2;
                int k=x1;
                for (int j=0; j<x3; j++)
                    {
                    y1 = diskPassport[k++] & 0xFFFF;
                    wordsHex[i] = wordsHex[i] + String.format("%04X",y1);
                    if (j==x3-1) break;
                    wordsHex[i] = wordsHex[i] + ",";
                    }
                z1 = (int)rowsControl[i][3];
                z2 = (int)rowsControl[i][4];
                if (z1!=z2) { s = String.format("%d-%d",z1,z2); }
                else        { s = String.format("%d",z1);       }
                wordsBits[i] = s;
                long data0 = diskPassport[x1];
                long data1 = diskPassport[x1+1] << 16;
                long data = data0 + data1;
                wordsValues[i] = String.format("%d", data);
                parmsComments[i] = parmsComments[i] + 
                    stringOrDecoder ( rowsControl[i][5] , data*sector );
                break;
                }
                
            case FOUR:
                {
                wordsNames[i] = wordsNames[i] + rowsControl[i][1];
                x1 = (int)rowsControl[i][2];
                x2 = x1+3;
                wordsNumbers[i] = String.format( "%d-%d", x1, x2 );
                x3=4;
                int k=x1;
                for (int j=0; j<x3; j++)
                    {
                    y1 = diskPassport[k++] & 0xFFFF;
                    wordsHex[i] = wordsHex[i] + String.format("%04X",y1);
                    if (j==x3-1) break;
                    wordsHex[i] = wordsHex[i] + ",";
                    }
                z1 = (int)rowsControl[i][3];
                z2 = (int)rowsControl[i][4];
                if (z1!=z2) { s = String.format("%d-%d",z1,z2); }
                else        { s = String.format("%d",z1);       }
                wordsBits[i] = s;
                long data0 = diskPassport[x1];
                long data1 = diskPassport[x1+1] << 16;
                long data2 = diskPassport[x1+2] << 32;
                long data3 = diskPassport[x1+3] << 48;
                long data = data0 + data1 + data2 + data3;
                wordsValues[i] = String.format("%d", data);
                parmsComments[i] = parmsComments[i] + 
                    stringOrDecoder ( rowsControl[i][5] , data*sector );
                break;
                }
                
            }
        }
    }

//---------- Helpers methods for builtDiskPassport -----------------------------

private int fieldExtract(int y1, int z1, int z2)
    {
    long mask = 0xFFFF0000;
    long value = y1;
    mask = mask   >> (z1-z2+1);  // Field width
    mask = mask   &  0xFFFF;
    mask = mask   >> (15-z1);
    value = value &  mask;
    value = value >> z2;         // Field position
    return (int)value;
    }

private String stringOrDecoder(Object x, long y)
    {
    if (x instanceof String)   { return (String)x; }
    if (x instanceof Decoder)  { return ((Decoder) x).decodeValue(y); }
    return null;
    }

}
