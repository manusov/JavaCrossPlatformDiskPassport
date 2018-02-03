cls
tasm  idedump.asm /m /jjumps
tlink idedump.obj /x
del *.obj
idedump