;;; Sierra Script 1.0 - (do not remove this comment)
(script# 990)
(include sci.sh)
(use Main)
(use Intrface)
(use Language)
(use PrintD)
(use File)

(public
	GetDirectory 0
)

(local
	gGameParseLang
	default
	i
	numGames
	selected
	theStatus
	[butbuf1 4] = [{Restore} {__Save__} {Replace} {Replace}]
	[butbuf2 4] = [{Select the game that you would like to restore.} {Type the description of this saved game.} {This directory/disk can hold no more saved games. You must replace one of your saved games or use Change Directory to save on a different directory/disk.} {This directory/disk can hold no more saved games. You must replace one of your saved games or use Change Directory to save on a different directory/disk.}]
)
(procedure (GetDirectory param1 &tmp temp0 [temp1 33] [temp34 100] temp134)
	(asm
code_0888:
		pushi    #parseLang
		pushi    0
		lag      theGame
		send     4
		sat      temp134
		pushi    #parseLang
		pushi    1
		pushi    1
		lag      theGame
		send     6
		pushi    13
		pushi    990
		pushi    1
		pushi    33
		pushi    0
		pushi    41
		pushi    2
		lea      @temp1
		push    
		lsp      param1
		callk    StrCpy,  4
		push    
		pushi    29
		pushi    81
		lofsa    {OK}
		push    
		pushi    1
		pushi    81
		lofsa    {Cancel}
		push    
		pushi    0
		calle    Print,  26
		sat      temp0
		pushi    #parseLang
		pushi    1
		lst      temp134
		lag      theGame
		send     6
		lat      temp0
		not     
		bnt      code_08d7
		ldi      0
		ret     
code_08d7:
		pushi    1
		lea      @temp1
		push    
		callk    StrLen,  2
		not     
		bnt      code_08eb
		pushi    1
		lea      @temp1
		push    
		callk    GetCWD,  2
code_08eb:
		pushi    1
		lea      @temp1
		push    
		callk    ValidPath,  2
		bnt      code_0906
		pushi    2
		lsp      param1
		lea      @temp1
		push    
		callk    StrCpy,  4
		ldi      1
		ret     
		jmp      code_0888
code_0906:
		pushi    3
		pushi    4
		lea      @temp34
		push    
		pushi    990
		pushi    2
		lea      @temp1
		push    
		callk    Format,  8
		push    
		pushi    33
		pushi    0
		calle    Print,  6
		jmp      code_0888
		ret     
	)
)

(procedure (localproc_05b2)
	(return
		(cond 
			((== self Restore) 0)
			((localproc_0925) 1)
			(numGames 2)
			(else 3)
		)
	)
)

(procedure (localproc_0925)
	(if (< numGames 20) (CheckFreeSpace curSaveDir))
)

(procedure (localproc_0934)
	(Print 990 3 #font 0)
)

(class SRDialog of Dialog
	(properties
		elements 0
		size 0
		text 0
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		busy 0
		caller 0
		seconds 0
		lastSeconds 0
	)
	
	(method (init param1 param2 param3)
		(proc932_3)
		(= gGameParseLang (theGame parseLang?))
		(theGame parseLang: 1)
		(= window systemWindow)
		(= nsBottom 0)
		(if
			(==
				(= numGames
					(GetSaveFiles (theGame name?) param2 param3)
				)
				-1
			)
			(return 0)
		)
		(if (== (= theStatus (localproc_05b2)) 1)
			(editI
				text: (StrCpy param1 param2)
				font: smallFont
				setSize:
				moveTo: 4 4
			)
			(self add: editI setSize:)
		)
		(selectorI
			text: param2
			font: smallFont
			setSize:
			moveTo: 4 (+ nsBottom 4)
			state: 2
		)
		(= i (+ (selectorI nsRight?) 4))
		(okI
			text: [butbuf1 theStatus]
			setSize:
			moveTo: i (selectorI nsTop?)
			state:
				(if
					(or
						(and (== theStatus 0) (not numGames))
						(== theStatus 3)
					)
					0
				else
					3
				)
		)
		(deleteI
			setSize:
			moveTo: i (+ (okI nsBottom?) 4)
			state: (if (not numGames) 0 else 3)
		)
		(changeDirI
			setSize:
			moveTo: i (+ (deleteI nsBottom?) 4)
			state: (& (changeDirI state?) $fff7)
		)
		(cancelI
			setSize:
			moveTo: i (+ (changeDirI nsBottom?) 4)
			state: (& (cancelI state?) $fff7)
		)
		(self
			add: selectorI okI deleteI changeDirI cancelI
			setSize:
		)
		(textI
			text: [butbuf2 theStatus]
			setSize: (- (- nsRight nsLeft) 8)
			moveTo: 4 4
		)
		(= i (+ (textI nsBottom?) 4))
		(self eachElementDo: #move 0 i)
		(self add: textI setSize: center: open: 4 -1)
		(return 1)
	)
	
	(method (doit param1 &tmp temp0 temp1 temp2 [temp3 361] [temp364 21] [temp385 140])
		(asm
			pushSelf
			class    Restore
			eq?     
			bnt      code_024c
			lap      argc
			bnt      code_024c
			lap      param1
			bnt      code_024c
			pushi    2
			pushi    0
			pushi    4
			lea      @temp385
			push    
			pushi    990
			pushi    0
			pushi    #name
			pushi    0
			lag      theGame
			send     4
			push    
			callk    Format,  8
			push    
			callk    FileIO,  4
			sat      temp0
			push    
			ldi      65535
			eq?     
			bnt      code_0245
			ret     
code_0245:
			pushi    2
			pushi    1
			lst      temp0
			callk    FileIO,  4
code_024c:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			not     
			bnt      code_0265
			ldi      65535
			ret     
code_0265:
			lsl      theStatus
			dup     
			ldi      0
			eq?     
			bnt      code_027f
			lal      numGames
			bnt      code_029c
			lofsa    okI
			jmp      code_029c
			lofsa    changeDirI
			jmp      code_029c
code_027f:
			dup     
			ldi      1
			eq?     
			bnt      code_028c
			lofsa    editI
			jmp      code_029c
code_028c:
			dup     
			ldi      2
			eq?     
			bnt      code_0299
			lofsa    okI
			jmp      code_029c
code_0299:
			lofsa    changeDirI
code_029c:
			toss    
			sal      default
			pushi    #doit
			pushi    1
			push    
			super    Dialog,  6
			sal      i
			pushi    #indexOf
			pushi    1
			pushi    #cursor
			pushi    0
			lofsa    selectorI
			send     4
			push    
			lofsa    selectorI
			send     6
			sal      selected
			push    
			ldi      18
			mul     
			sat      temp2
			lsl      i
			lofsa    changeDirI
			eq?     
			bnt      code_0316
			pushi    #dispose
			pushi    0
			self     4
			pushi    1
			lsg      curSaveDir
			call     GetDirectory,  2
			bnt      code_0301
			pushi    3
			pushi    #name
			pushi    0
			lag      theGame
			send     4
			push    
			lea      @temp3
			push    
			lea      @temp364
			push    
			callk    GetSaveFiles,  6
			sal      numGames
			push    
			ldi      65535
			eq?     
			bnt      code_0301
			ldi      65535
			sat      temp1
			jmp      code_0595
code_0301:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0265
code_0316:
			lsl      theStatus
			ldi      2
			eq?     
			bnt      code_0363
			lsl      i
			lofsa    okI
			eq?     
			bnt      code_0363
			pushi    #dispose
			pushi    0
			self     4
			pushi    #doit
			pushi    1
			pushi    2
			lsp      param1
			lat      temp2
			leai     @temp3
			push    
			callk    StrCpy,  4
			push    
			lofsa    GetReplaceName
			send     6
			bnt      code_034e
			lal      selected
			lati     temp364
			sat      temp1
			jmp      code_0595
code_034e:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0265
code_0363:
			lsl      theStatus
			ldi      1
			eq?     
			bnt      code_0430
			lsl      i
			lofsa    okI
			eq?     
			bt       code_037d
			lsl      i
			lofsa    editI
			eq?     
			bnt      code_0430
code_037d:
			pushi    1
			lsp      param1
			callk    StrLen,  2
			push    
			ldi      0
			eq?     
			bnt      code_03a9
			pushi    #dispose
			pushi    0
			self     4
			pushi    0
			call     localproc_0934,  0
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0265
code_03a9:
			ldi      65535
			sat      temp1
			ldi      0
			sal      i
code_03b1:
			lsl      i
			lal      numGames
			lt?     
			bnt      code_03d3
			pushi    2
			lsp      param1
			lsl      i
			ldi      18
			mul     
			leai     @temp3
			push    
			callk    StrCmp,  4
			sat      temp1
			not     
			bnt      code_03ce
code_03ce:
			+al      i
			jmp      code_03b1
code_03d3:
			lat      temp1
			not     
			bnt      code_03e3
			lal      i
			lati     temp364
			sat      temp1
			jmp      code_0595
code_03e3:
			lsl      numGames
			ldi      20
			eq?     
			bnt      code_03f5
			lal      selected
			lati     temp364
			sat      temp1
			jmp      code_0595
code_03f5:
			ldi      0
			sat      temp1
code_03f9:
			ldi      1
			bnt      code_0595
			ldi      0
			sal      i
code_0402:
			lsl      i
			lal      numGames
			lt?     
			bnt      code_041a
			lst      temp1
			lal      i
			lati     temp364
			eq?     
			bnt      code_0415
code_0415:
			+al      i
			jmp      code_0402
code_041a:
			lsl      i
			lal      numGames
			eq?     
			bnt      code_0425
			jmp      code_0595
code_0425:
			+at      temp1
			jmp      code_03f9
			jmp      code_0595
			jmp      code_0265
code_0430:
			lsl      i
			lofsa    deleteI
			eq?     
			bnt      code_053c
			pushi    #dispose
			pushi    0
			self     4
			pushi    8
			lofsa    {Are you sure you want to\0D\ndelete this saved game?}
			push    
			pushi    106
			pushi    81
			lofsa    { No_}
			push    
			pushi    0
			pushi    81
			lofsa    {Yes}
			push    
			pushi    1
			calle    PrintD_940,  16
			not     
			bnt      code_0473
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0265
code_0473:
			pushi    #name
			pushi    1
			pushi    3
			pushi    7
			lea      @temp385
			push    
			pushi    #name
			pushi    0
			lag      theGame
			send     4
			push    
			callk    DeviceInfo,  6
			push    
			pushi    179
			pushi    1
			pushi    2
			pushi    #new
			pushi    0
			class    File
			send     4
			sat      temp0
			send     12
			ldi      2570
			sat      temp1
			ldi      0
			sal      i
code_04a5:
			lsl      i
			lal      numGames
			lt?     
			bnt      code_04e9
			lsl      i
			lal      selected
			ne?     
			bnt      code_04e4
			pushi    #write
			pushi    2
			lal      i
			leai     @temp364
			push    
			pushi    2
			lat      temp0
			send     8
			pushi    330
			pushi    #superClass
			lsl      i
			ldi      18
			mul     
			leai     @temp3
			push    
			lat      temp0
			send     6
			pushi    #write
			pushi    2
			lea      @temp1
			push    
			pushi    1
			lat      temp0
			send     8
code_04e4:
			+al      i
			jmp      code_04a5
code_04e9:
			ldi      65535
			sat      temp1
			pushi    #write
			pushi    2
			lea      @temp1
			push    
			pushi    2
			pushi    334
			pushi    0
			pushi    108
			pushi    0
			lat      temp0
			send     16
			pushi    4
			pushi    8
			lea      @temp385
			push    
			pushi    #name
			pushi    0
			lag      theGame
			send     4
			push    
			lal      selected
			lsti     temp364
			callk    DeviceInfo,  8
			pushi    2
			pushi    4
			lea      @temp385
			push    
			callk    FileIO,  4
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0265
code_053c:
			lsl      i
			lofsa    okI
			eq?     
			bnt      code_0552
			lal      selected
			lati     temp364
			sat      temp1
			jmp      code_0595
			jmp      code_0265
code_0552:
			lsl      i
			ldi      0
			eq?     
			bt       code_0563
			lsl      i
			lofsa    cancelI
			eq?     
			bnt      code_056d
code_0563:
			ldi      65535
			sat      temp1
			jmp      code_0595
			jmp      code_0265
code_056d:
			lsl      theStatus
			ldi      1
			eq?     
			bnt      code_0265
			pushi    #cursor
			pushi    1
			pushi    1
			pushi    2
			lsp      param1
			lat      temp2
			leai     @temp3
			push    
			callk    StrCpy,  4
			push    
			callk    StrLen,  2
			push    
			pushi    83
			pushi    0
			lofsa    editI
			send     10
			jmp      code_0265
code_0595:
			pushi    1
			pushi    993
			callk    DisposeScript,  2
			pushi    1
			pushi    940
			callk    DisposeScript,  2
			pushi    #dispose
			pushi    0
			self     4
			pushi    1
			pushi    990
			callk    DisposeScript,  2
			lat      temp1
			ret     
		)
	)
	
	(method (dispose)
		(proc932_4)
		(theGame parseLang: gGameParseLang)
		(super dispose: &rest)
	)
)

(class Restore of SRDialog
	(properties
		elements 0
		size 0
		text {Restore a Game}
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		busy 0
		caller 0
		seconds 0
		lastSeconds 0
	)
)

(class Save of SRDialog
	(properties
		elements 0
		size 0
		text {Save a Game}
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		busy 0
		caller 0
		seconds 0
		lastSeconds 0
	)
)

(instance GetReplaceName of Dialog
	(properties)
	
	(method (doit param1 &tmp temp0 theGameParseLang)
		(= theGameParseLang (theGame parseLang?))
		(theGame parseLang: 1)
		(= window systemWindow)
		(text1 setSize: moveTo: 4 4)
		(self add: text1 setSize:)
		(oldName
			text: param1
			font: smallFont
			setSize:
			moveTo: 4 nsBottom
		)
		(self add: oldName setSize:)
		(text2 setSize: moveTo: 4 nsBottom)
		(self add: text2 setSize:)
		(newName
			text: param1
			font: smallFont
			setSize:
			moveTo: 4 nsBottom
		)
		(self add: newName setSize:)
		(button1 nsLeft: 0 nsTop: 0 setSize:)
		(button2 nsLeft: 0 nsTop: 0 setSize:)
		(button2
			moveTo: (- nsRight (+ (button2 nsRight?) 4)) nsBottom
		)
		(button1
			moveTo: (- (button2 nsLeft?) (+ (button1 nsRight?) 4)) nsBottom
		)
		(self add: button1 button2 setSize: center: open: 0 -1)
		(= temp0 (super doit: newName))
		(self dispose:)
		(if (not (StrLen param1))
			(localproc_0934)
			(= temp0 0)
		)
		(theGame parseLang: theGameParseLang)
		(return (if (== temp0 newName) else (== temp0 button1)))
	)
)

(instance selectorI of DSelector
	(properties
		x 36
		y 8
	)
)

(instance editI of DEdit
	(properties
		max 35
	)
)

(instance okI of DButton
	(properties)
)

(instance cancelI of DButton
	(properties
		text { Cancel_}
	)
)

(instance changeDirI of DButton
	(properties
		text {Change\0D\nDirectory}
	)
)

(instance deleteI of DButton
	(properties
		text { Delete_}
	)
)

(instance textI of DText
	(properties
		font 0
	)
)

(instance text1 of DText
	(properties
		text {Replace}
		font 0
	)
)

(instance text2 of DText
	(properties
		text {with:}
		font 0
	)
)

(instance oldName of DText
	(properties)
)

(instance newName of DEdit
	(properties
		max 35
	)
)

(instance button1 of DButton
	(properties
		text {Replace}
	)
)

(instance button2 of DButton
	(properties
		text {Cancel}
	)
)
