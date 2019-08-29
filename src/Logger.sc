;;; Sierra Script 1.0 - (do not remove this comment)
(script# LOGGER)
(include game.sh)
(use Main)
(use Intrface)
(use PolyPath)
(use System)

(public
	sysLogger 0
)

(local
	logHandle
)
(procedure (localproc_000e param1 param2 param3 &tmp [temp0 40] temp40 temp41)
	(Format @temp0 952 0 param2)
	(FileIO 6 logHandle @temp0)
	(= temp0 0)
	(switch param1
		(1
			(StrCpy @temp0 (if param3 else {}))
		)
		(2 (Format @temp0 952 1 param3))
		(3 (Format @temp0 952 2 param3))
		(4 (Format @temp0 952 3 param3))
		(5
			(if param3 (GetInput @temp0 66 param3))
			(= temp41 (StrLen @temp0))
		)
		(6
			(= temp40 (GetTime 2))
			(Format
				@temp0
				952
				4
				(>> temp40 $000b)
				(& (>> temp40 $0005) $003f)
				(* (& temp40 $001f) 2)
			)
		)
		(7
			(= temp40 (GetTime 3))
			(Format
				@temp0
				952
				5
				(& (>> temp40 $0005) $000f)
				(& temp40 $001f)
				(+ 80 (>> temp40 $0009))
			)
		)
	)
	(StrCat @temp0 {\0D\n})
	(FileIO 6 logHandle @temp0)
	(return temp41)
)

(instance sysLogger of Code
	(properties)
	
	(method (doit &tmp temp0 temp1 temp2 temp3 temp4 temp5 temp6 temp7 [temp8 40] [temp48 30] [temp78 30] [temp108 30] [temp138 5] [temp143 40] [temp183 40] [temp223 40] [temp263 40] [temp303 40] [temp343 40])
		(asm
			lag      inputFont
			sat      temp7
			ldi      999
			sag      inputFont
			ldi      0
			sat      temp343
			sat      temp303
			sat      temp263
			sat      temp223
			sat      temp183
			sat      temp143
			sat      temp108
			sat      temp78
			sat      temp8
			pushi    0
			pushi    1
			lea      @sysLogPath
			push    
			callk    StrLen,  2
			eq?     
			sat      temp4
			bnt      code_01b8
code_0181:
			pushi    0
			pushi    1
			lea      @temp78
			push    
			callk    StrLen,  2
			lt?     
			bnt      code_0192
			pprev   
			ldi      19
			lt?     
code_0192:
			not     
			bnt      code_01a9
			pushi    3
			lea      @temp78
			push    
			pushi    40
			lofsa    {Enter drive letter, path and your name\n(no extension, max 40 characters)}
			push    
			calle    GetInput,  6
			jmp      code_0181
code_01a9:
			pushi    3
			lea      @sysLogPath
			push    
			lea      @temp78
			push    
			pushi    40
			callk    StrCpy,  6
code_01b8:
			pushi    4
			lea      @temp78
			push    
			pushi    952
			pushi    6
			lea      @sysLogPath
			push    
			callk    Format,  8
			pushi    65535
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    1
			callk    FileIO,  6
			sal      logHandle
			ne?     
			bnt      code_0205
			pushi    4
			pushi    5
			lea      @temp138
			push    
			pushi    80
			lsl      logHandle
			callk    FileIO,  8
			pushi    4
			pushi    5
			lea      @temp48
			push    
			pushi    80
			lsl      logHandle
			callk    FileIO,  8
			pushi    2
			pushi    1
			lsl      logHandle
			callk    FileIO,  4
			jmp      code_0215
code_0205:
			ldi      0
			sat      temp138
			pushi    2
			lea      @temp48
			push    
			lofsa    {resource.cfg}
			push    
			callk    StrCpy,  4
code_0215:
			lat      temp4
			bnt      code_0236
			pushi    3
			lea      @temp138
			push    
			pushi    5
			lofsa    {Enter your initials (up to 3 characters):}
			push    
			calle    GetInput,  6
			pushi    3
			lea      @temp138
			push    
			pushi    3
			pushi    0
			callk    StrAt,  6
code_0236:
			lat      temp4
			not     
			bt       code_024f
			pushi    3
			lea      @temp48
			push    
			pushi    30
			lofsa    {Enter configuration file name (or hit return to skip):}
			push    
			calle    GetInput,  6
			bnt      code_027c
code_024f:
			pushi    65535
			pushi    3
			pushi    0
			lea      @temp48
			push    
			pushi    1
			callk    FileIO,  6
			sal      logHandle
			eq?     
			bnt      code_027c
			pushi    2
			lea      @temp48
			push    
			pushi    0
			callk    StrAt,  4
			bnt      code_027c
			pushi    3
			lea      @temp48
			push    
			pushi    0
			pushi    0
			callk    StrAt,  6
			jmp      code_0236
code_027c:
			pushi    65535
			lal      logHandle
			ne?     
			bnt      code_042e
code_0284:
			pushi    4
			pushi    5
			lea      @temp8
			push    
			pushi    80
			lsl      logHandle
			callk    FileIO,  8
			bnt      code_0427
			ldi      0
			sat      temp0
code_029a:
			pushi    2
			lea      @temp8
			push    
			lst      temp0
			callk    StrAt,  4
			sat      temp3
			bnt      code_02be
			pushi    3
			push    
			pushi    9
			pushi    32
			calle    OneOf,  6
			bnt      code_02be
			+at      temp0
			jmp      code_029a
code_02be:
			ldi      0
			sat      temp1
code_02c2:
			pushi    2
			lea      @temp8
			push    
			lst      temp0
			callk    StrAt,  4
			sat      temp3
			bnt      code_02fa
			pushi    5
			push    
			pushi    61
			pushi    58
			pushi    9
			pushi    32
			calle    OneOf,  10
			not     
			bnt      code_02fa
			pushi    3
			lea      @temp108
			push    
			lst      temp1
			lst      temp3
			callk    StrAt,  6
			+at      temp0
			+at      temp1
			jmp      code_02c2
code_02fa:
			pushi    3
			lea      @temp108
			push    
			lst      temp1
			pushi    0
			callk    StrAt,  6
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {kbdDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_031d
			lea      @temp143
			jmp      code_0393
code_031d:
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {joyDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_0334
			lea      @temp183
			jmp      code_0393
code_0334:
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {videoDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_034b
			lea      @temp223
			jmp      code_0393
code_034b:
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {soundDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_0364
			lea      @temp263
			jmp      code_0393
code_0364:
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {mouseDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_037d
			lea      @temp303
			jmp      code_0393
code_037d:
			pushi    0
			pushi    2
			lea      @temp108
			push    
			lofsa    {audioDrv}
			push    
			callk    StrCmp,  4
			eq?     
			bnt      code_0393
			lea      @temp343
code_0393:
			sat      temp5
			bnt      code_0284
code_0398:
			pushi    2
			lea      @temp8
			push    
			lst      temp0
			callk    StrAt,  4
			sat      temp3
			bnt      code_03c0
			pushi    5
			push    
			pushi    61
			pushi    58
			pushi    9
			pushi    32
			calle    OneOf,  10
			bnt      code_03c0
			+at      temp0
			jmp      code_0398
code_03c0:
			lat      temp0
			sat      temp1
			ldi      0
			sat      temp2
code_03c8:
			pushi    2
			lea      @temp8
			push    
			lst      temp1
			callk    StrAt,  4
			sat      temp3
			bnt      code_0404
			pushi    4
			push    
			pushi    58
			pushi    92
			pushi    47
			calle    OneOf,  8
			bnt      code_03f0
			lst      temp1
			ldi      1
			add     
			sat      temp0
code_03f0:
			lst      temp3
			ldi      46
			eq?     
			bnt      code_03ff
			lst      temp1
			lat      temp0
			sub     
			sat      temp2
code_03ff:
			+at      temp1
			jmp      code_03c8
code_0404:
			lst      temp2
			ldi      0
			eq?     
			bnt      code_0413
			lst      temp1
			lat      temp0
			sub     
			sat      temp2
code_0413:
			pushi    3
			lst      temp5
			lea      @temp8
			push    
			lat      temp0
			add     
			push    
			lst      temp2
			callk    StrCpy,  6
			jmp      code_0284
code_0427:
			pushi    2
			pushi    1
			lsl      logHandle
			callk    FileIO,  4
code_042e:
			pushi    4
			lea      @temp78
			push    
			pushi    952
			pushi    7
			lea      @sysLogPath
			push    
			callk    Format,  8
			lat      temp4
			bnt      code_04a4
			pushi    65535
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    1
			callk    FileIO,  6
			sal      logHandle
			eq?     
			bt       code_048d
			pushi    4
			lea      @temp8
			push    
			pushi    952
			pushi    8
			lea      @temp78
			push    
			callk    Format,  8
			bnt      code_0472
			ldi      0
			bt       code_048d
code_0472:
			pushi    7
			lea      @temp8
			push    
			pushi    81
			lofsa    {append to it}
			push    
			pushi    0
			pushi    81
			lofsa    {overwrite it}
			push    
			pushi    1
			calle    Print,  14
			bnt      code_04a4
code_048d:
			pushi    2
			pushi    1
			lsl      logHandle
			callk    FileIO,  4
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    2
			callk    FileIO,  6
			sal      logHandle
			jmp      code_04b1
code_04a4:
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    0
			callk    FileIO,  6
			sal      logHandle
code_04b1:
			pushi    65535
			lal      logHandle
			eq?     
			bnt      code_04cb
			pushi    3
			pushi    952
			pushi    9
			lea      @temp78
			push    
			calle    Printf,  6
			jmp      code_0905
code_04cb:
			pushi    3
			pushi    1
			lofsa    {GAME}
			push    
			pushi    #name
			pushi    0
			lag      theGame
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {VERSION}
			push    
			lsg      version
			call     localproc_000e,  6
			pushi    2
			pushi    7
			lofsa    {QA-DATE}
			push    
			call     localproc_000e,  4
			pushi    3
			pushi    1
			lofsa    {ANALYST}
			push    
			lea      @temp138
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {SEVERITY}
			push    
			pushi    11
			pushi    952
			pushi    10
			pushi    81
			lofsa    {FATAL}
			push    
			lofsa    {F}
			push    
			pushi    81
			lofsa    {NON-FATAL}
			push    
			lofsa    {N}
			push    
			pushi    81
			lofsa    {SUGGESTION}
			push    
			lofsa    {S}
			push    
			calle    Print,  22
			push    
			call     localproc_000e,  6
			ldi      1
			sat      temp0
			ldi      1
			sat      temp6
code_0542:
			lst      temp0
			ldi      10
			le?     
			bnt      code_0597
			pushi    4
			lea      @temp108
			push    
			pushi    952
			pushi    11
			lst      temp0
			callk    Format,  8
			pushi    5
			lea      @temp8
			push    
			pushi    952
			pushi    12
			lst      temp0
			pushi    10
			callk    Format,  10
			lat      temp6
			bnt      code_0586
			pushi    3
			pushi    5
			lea      @temp108
			push    
			lea      @temp8
			push    
			call     localproc_000e,  6
			sat      temp6
			jmp      code_0592
code_0586:
			pushi    3
			pushi    1
			lea      @temp108
			push    
			pushi    0
			call     localproc_000e,  6
code_0592:
			+at      temp0
			jmp      code_0542
code_0597:
			pushi    3
			pushi    1
			lofsa    {DEPARTMENT}
			push    
			pushi    11
			pushi    952
			pushi    13
			pushi    81
			lofsa    {PROG}
			push    
			lofsa    {P}
			push    
			pushi    81
			lofsa    {ART}
			push    
			lofsa    {A}
			push    
			pushi    81
			lofsa    {DESIGN}
			push    
			lofsa    {D}
			push    
			calle    Print,  22
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {ROOM}
			push    
			lsg      curRoomNum
			call     localproc_000e,  6
			pushi    #script
			pushi    0
			lag      curRoom
			send     4
			sat      temp0
			pushi    3
			pushi    1
			lofsa    {ROOM-SCRIPT}
			push    
			lat      temp0
			bnt      code_05f4
			pushi    #name
			pushi    0
			send     4
code_05f4:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {ROOM-STATE}
			push    
			lat      temp0
			bnt      code_060a
			pushi    #state
			pushi    0
			send     4
code_060a:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-X}
			push    
			pushi    #x
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-Y}
			push    
			pushi    #y
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-Z}
			push    
			pushi    #z
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    #script
			pushi    0
			lag      ego
			send     4
			sat      temp0
			pushi    3
			pushi    1
			lofsa    {EGO-SCRIPT}
			push    
			lat      temp0
			bnt      code_0663
			pushi    #name
			pushi    0
			send     4
code_0663:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-STATE}
			push    
			lat      temp0
			bnt      code_0679
			pushi    #state
			pushi    0
			send     4
code_0679:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-VIEW}
			push    
			pushi    #view
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-LOOP}
			push    
			pushi    #loop
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-CEL}
			push    
			pushi    #cel
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-PRIORITY}
			push    
			pushi    #priority
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-HEADING}
			push    
			pushi    #heading
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {CYCLER}
			push    
			pushi    #cycler
			pushi    0
			lag      ego
			send     4
			bnt      code_06fc
			pushi    #name
			pushi    0
			pushi    #cycler
			pushi    0
			lag      ego
			send     4
			send     4
code_06fc:
			push    
			call     localproc_000e,  6
			pushi    #mover
			pushi    0
			lag      ego
			send     4
			sat      temp0
			pushi    3
			pushi    1
			lofsa    {EGO-MOVER}
			push    
			lat      temp0
			bnt      code_071b
			pushi    #name
			pushi    0
			send     4
code_071b:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {MOVER-X}
			push    
			lat      temp0
			not     
			bnt      code_0732
			ldi      0
			jmp      code_0751
code_0732:
			pushi    #isMemberOf
			pushi    1
			class    PolyPath
			push    
			lat      temp0
			send     6
			bnt      code_074a
			pushi    #finalX
			pushi    0
			lat      temp0
			send     4
			jmp      code_0751
code_074a:
			pushi    #x
			pushi    0
			lat      temp0
			send     4
code_0751:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {MOVER-Y}
			push    
			lat      temp0
			not     
			bnt      code_0768
			ldi      0
			jmp      code_0787
code_0768:
			pushi    #isMemberOf
			pushi    1
			class    PolyPath
			push    
			lat      temp0
			send     6
			bnt      code_0780
			pushi    #finalY
			pushi    0
			lat      temp0
			send     4
			jmp      code_0787
code_0780:
			pushi    #y
			pushi    0
			lat      temp0
			send     4
code_0787:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {EGO-MOVESPD}
			push    
			pushi    #moveSpeed
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    4
			lofsa    {SIGNAL-BITS}
			push    
			pushi    #signal
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    4
			lofsa    {ILLEGAL-BITS}
			push    
			pushi    #illegalBits
			pushi    0
			lag      ego
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {HOWFAST}
			push    
			lsg      howFast
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {ICONBAR}
			push    
			lag      theIconBar
			bnt      code_07e5
			pushi    #name
			pushi    0
			send     4
code_07e5:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {CUR-ICON}
			push    
			lag      theIconBar
			bnt      code_080c
			pushi    #curIcon
			pushi    0
			send     4
			bnt      code_080c
			pushi    #name
			pushi    0
			pushi    #curIcon
			pushi    0
			lag      theIconBar
			send     4
			send     4
code_080c:
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {DETAIL-LEVEL}
			push    
			pushi    #detailLevel
			pushi    0
			lag      theGame
			send     4
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    2
			lofsa    {CD-AUDIO}
			push    
			lsg      cDAudio
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {VIDEO-DRV}
			push    
			lea      @temp223
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {SOUND-DRV}
			push    
			lea      @temp263
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {AUDIO-DRV}
			push    
			lea      @temp343
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {KEYBOARD-DRV}
			push    
			lea      @temp143
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {JOY-DRV}
			push    
			lea      @temp183
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    1
			lofsa    {MOUSE}
			push    
			lea      @temp303
			push    
			call     localproc_000e,  6
			pushi    3
			dup     
			lofsa    {LARGEST-HEAP}
			push    
			pushi    1
			pushi    0
			callk    MemoryInfo,  2
			push    
			call     localproc_000e,  6
			pushi    3
			dup     
			lofsa    {FREE-HEAP}
			push    
			pushi    1
			pushi    1
			callk    MemoryInfo,  2
			push    
			call     localproc_000e,  6
			pushi    3
			dup     
			lofsa    {TOTAL-HUNK}
			push    
			pushi    1
			pushi    4
			callk    MemoryInfo,  2
			push    
			ldi      6
			shr     
			push    
			call     localproc_000e,  6
			pushi    3
			dup     
			lofsa    {LARGEST-HUNK}
			push    
			pushi    1
			pushi    2
			callk    MemoryInfo,  2
			push    
			call     localproc_000e,  6
			pushi    3
			dup     
			lofsa    {FREE-HUNK}
			push    
			pushi    1
			pushi    3
			callk    MemoryInfo,  2
			push    
			ldi      6
			shr     
			push    
			call     localproc_000e,  6
			pushi    3
			pushi    6
			lsl      logHandle
			lofsa    {**********************************\0D\n}
			push    
			callk    FileIO,  6
			pushi    2
			pushi    1
			lsl      logHandle
			callk    FileIO,  4
code_0905:
			pushi    4
			lea      @temp78
			push    
			pushi    952
			pushi    6
			lea      @sysLogPath
			push    
			callk    Format,  8
			pushi    65535
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    2
			callk    FileIO,  6
			sal      logHandle
			eq?     
			bnt      code_094f
			pushi    65535
			pushi    3
			pushi    0
			lea      @temp78
			push    
			pushi    0
			callk    FileIO,  6
			sal      logHandle
			eq?     
			bnt      code_094f
			pushi    3
			pushi    952
			pushi    14
			lea      @temp78
			push    
			calle    Printf,  6
			jmp      code_098a
code_094f:
			pushi    3
			pushi    6
			lsl      logHandle
			lea      @temp138
			push    
			callk    FileIO,  6
			pushi    3
			pushi    6
			lsl      logHandle
			lofsa    {\n}
			push    
			callk    FileIO,  6
			pushi    3
			pushi    6
			lsl      logHandle
			lea      @temp48
			push    
			callk    FileIO,  6
			pushi    3
			pushi    6
			lsl      logHandle
			lofsa    {\n}
			push    
			callk    FileIO,  6
			pushi    2
			pushi    1
			lsl      logHandle
			callk    FileIO,  4
code_098a:
			lat      temp7
			sag      inputFont
			pushi    1
			pushi    952
			callk    DisposeScript,  2
			ret     
		)
	)
)
