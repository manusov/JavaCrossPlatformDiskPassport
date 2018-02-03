tasm  idedump.asm /zi /m /jjumps
tlink idedump.obj /3 /v /x
del *.obj
td idedump.exe
del *.exe