CODE86  SEGMENT BYTE USE16
ASSUME CS:CODE86, DS:CODE86, ES:CODE86
ORG 0
PROGRAM PROC FAR
START:

;--- Clear screen and Title Message ---

		push	cs cs			; DS = ES = CS
		pop	es ds
		mov	ax,0003h
		int	10h
		mov	dx,OFFSET Msg1
		mov	ah,9
		int	21h

;--- Blank buffer ---

		mov	di,OFFSET DumpData
		mov	cx,512*3
		mov	al,0
		rep stosb

;--- Make IDENTIFY DEVICE operation ---

; BASE1 EQU 0F070h ; 0EC00h ; 0F070h ; 01F0h ; 0EC00h
; BASE2 EQU 0F062h ; 0E882h ; 0F062h ; 03F6h ; 0E882h

BASE1 EQU 01F0h
BASE2 EQU 03F6h


		mov	dx, BASE1
		mov	bp, BASE2
		mov	bx,0ECA0h
		mov	di,OFFSET DumpData
		call	Detect_ATA_ATAPI

		mov	dx,OFFSET Msg2
		jc	DoneError

		mov	dx, BASE1
		mov	bp, BASE2
		mov	di,OFFSET DumpData+512
		call	SMART_Read_Data

		mov	dx,OFFSET Msg7
		jnc	SOK
		mov	dx,OFFSET Msg8
SOK:		mov	ah,9
		int	21h

		mov	dx, BASE1
		mov	bp, BASE2
		mov	di,OFFSET DumpData+512*2
		call	SMART_Read_Thresholds

		mov	dx,OFFSET Msg9
		jnc	STHOK
		mov	dx,OFFSET Msg10
STHOK:		mov	ah,9
		int	21h



;--- Save file DUMP.BIN, write message ---

		mov	dx,OFFSET Msg3
		mov	ah,9
		int	21h
		mov	dx,OFFSET Name1
		xor	cx,cx
		mov	ah,3Ch		; Function 3Ch = Create file
		int	21h
		jc	FileError
		mov	bx,ax
		mov	dx,OFFSET DumpData
		mov	cx,512*3
		mov	ah,40h		; Function 40h = Write file
		int	21h
		jc	FileError
		mov	ah,03Eh		; Function 3Eh = Close file
		int	21h
		jnc	FileDone
FileError:	mov	dx,OFFSET Msg4
DoneError:	mov	ah,9
		int	21h
;--- Wait ket, exit to OS ---
FileDone:	mov	dx,OFFSET Msg5
		mov	ah,9
		int	21h
		mov	ah,0
		int	16h
		mov	dx,OFFSET Msg6
		mov	ah,9
		int	21h
		mov	ax,4C00h
		int	21h

;--- Library for IDE interface support ---

.286
include idelib.inc
.8086

;--- Data: Text Strings ---

Msg1   DB  'Detecting...',0Ah,0Dh,24h
Msg2   DB  'Device not detected.',0Ah,0Dh,24h
Msg3   DB  'Save binary file...',0Ah,0Dh,24h
Msg4   DB  'File operations error.',0Ah,0Dh,24h
Msg5   DB  'Press any key...',24h
Msg6   DB  0Ah,0Dh,0Ah,0Dh,24h
Msg7   DB  'SMART OK.',0Ah,0Dh,24h
Msg8   DB  'SMART FAILED.',0Ah,0Dh,24h
Msg9   DB  'SMART THRESHOLDS OK.',0Ah,0Dh,24h
Msg10  DB  'SMART THRESHOLDS FAILED.',0Ah,0Dh,24h

Name1  DB  'dump.bin',0


;--- Data: Buffer for disk passport image ---

DumpData  LABEL
DB  512*3 DUP (?)

;--- Code and Data done ---
PROGRAM ENDP
CODE86 ENDS

;--- Stack ---
STACK86  SEGMENT PARA STACK 'STACK' USE16
ASSUME  SS:STACK86, DS:STACK86, ES:STACK86
DB  2048 DUP (?)
STACK86  ENDS

END START

