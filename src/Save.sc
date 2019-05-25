;;; Sierra Script 1.0 - (do not remove this comment)
(script# SAVE)
(include game.sh)
(use Main)
(use File)
(use Intrface)
(use Language)

(public
	GetDirectory 0
)

(define  GAMESSHOWN 8)     ;the number of games displayed in the selector
(define  MAXGAMES 20)      ;maximum number of games in a save directory
(define  COMMENTSIZE 36)   ;size of user's description of the game
(define  COMMENTBUFF 18) 	;(/ (+ 1 COMMENTSIZE) 2))

(define  DIRECTORYSIZE 29) ;size of the save directory name
(define  DIRECTORYBUFF 15)	;(/ (+ 1 DIRECTORYSIZE) 2))

(define  BUFFERSIZE 361)	;(+ (* MAXGAMES COMMENTBUFF) 1))

;;;(procedure
;;;   GetDirectory
;;;   HaveSpace
;;;   GetStatus
;;;   NeedDescription
;;;)

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

(enum
   RESTORE        ;Restore games
   HAVESPACE      ;Save, with space on disk
   NOSPACE        ;Save, no space on disk but games to replace
   NOREPLACE      ;Save, no space on disk, no games to replace
)

;EO: This is adapted from SCI16's SAVE.SC. It doesn't fully match the commented 
;assembly code below.
(procedure (GetDirectory where &tmp result [newDir 33] [str 100] len)
;	(theGame parseLang: ENGLISH)
   (repeat
      (= result
         (Print "New save-game directory:"
            #font SYSFONT
            #edit    (StrCpy @newDir where)
            #back
            #button {OK} TRUE
            #button {Cancel} FALSE
         )
      )
      ;No string defaults to current drive.
      (if (not (StrLen @newDir))
         (GetCWD @newDir)
      )
      ;If drive is valid, return TRUE, otherwise complain.
      (if (ValidPath @newDir)
         (StrCpy where @newDir)
         (return TRUE)

      else
         (Print 
         	(Format @str
         		"%s\nis not a valid directory"
         		@newDir
			)
         	#font SYSFONT
		 )
	  )
  )
)
;EO: The disassembled output below. Note the instances of #parseLang here.

;;;(procedure (GetDirectory where &tmp result [newDir 33] [str 100] len)
;;;	(asm
;;;code_088a:
;;;		pushi    #parseLang
;;;		pushi    0
;;;		lag      theGame
;;;		send     4
;;;		sat      len
;;;		pushi    #parseLang
;;;		pushi    1
;;;		pushi    1
;;;		lag      theGame
;;;		send     6
;;;		pushi    13
;;;		pushi    990
;;;		pushi    1
;;;		pushi    #font
;;;		pushi    SYSFONT
;;;		pushi    #edit
;;;		pushi    2
;;;		lea      @newDir
;;;		push    
;;;		lsp      where
;;;		callk    StrCpy,  4
;;;		push    
;;;		pushi    #back
;;;		pushi    #button
;;;		lofsa    {OK}
;;;		push    
;;;		pushi    TRUE
;;;		pushi    #button
;;;		lofsa    {Cancel}
;;;		push    
;;;		pushi    FALSE
;;;		calle    Print,  26
;;;		sat      result
;;;		pushi    #parseLang
;;;		pushi    1
;;;		lst      len
;;;		lag      theGame
;;;		send     6
;;;		lat      result
;;;		not     
;;;		bnt      code_08d9
;;;		ldi      0
;;;		ret     
;;;code_08d9:
;;;		pushi    1
;;;		lea      @newDir
;;;		push    
;;;		callk    StrLen,  2
;;;		not     
;;;		bnt      code_08ed
;;;		pushi    1
;;;		lea      @newDir
;;;		push    
;;;		callk    GetCWD,  2
;;;code_08ed:					;;If drive is valid, return TRUE, otherwise complain.
;;;		pushi    1
;;;		lea      @newDir
;;;		push    
;;;		callk    ValidPath,  2
;;;		bnt      code_0908
;;;		pushi    2
;;;		lsp      where
;;;		lea      @newDir
;;;		push    
;;;		callk    StrCpy,  4
;;;		ldi      TRUE		;return TRUE
;;;		ret     
;;;		jmp      code_088a
;;;code_0908:				;Complain
;;;		pushi    3
;;;		pushi    4
;;;		lea      @str
;;;		push    
;;;		pushi    990		;"%s\nis not a valid directory"
;;;		pushi    2
;;;		lea      @newDir
;;;		push    
;;;		callk    Format,  8
;;;		push    
;;;		pushi    #font
;;;		pushi    SYSFONT
;;;		calle    Print,  6
;;;		jmp      code_088a
;;;		ret     
;;;	)
;;;)

   (procedure (GetStatus)
      (return
         (cond
            ((== self Restore)
               RESTORE
            )
            ((HaveSpace)
               HAVESPACE
            )
            (numGames
               NOSPACE
            )
            (else
               NOREPLACE
            )
         )
      )
   )


(procedure (HaveSpace)
   (return (and (< numGames MAXGAMES) (CheckFreeSpace curSaveDir)))
)

(procedure (NeedDescription)
	(Print "You must type a description for the game."
		#font SYSFONT)
)

(class SRDialog kindof Dialog
   ;;; The SRDialog class implements the user interface for save/restore.
   ;;; Its subclasses are the specific save and restore game dialogs,
   ;;; Save and Restore.

	
	(method (init theComment names nums)
		(proc932_3)
		(= gGameParseLang (theGame parseLang?))
		(theGame parseLang: ENGLISH)
      ; give ourself the system window as our window
      (= window systemWindow)

      ;Re-init our size, with no elements.
      (= nsBottom 0)

      ;Get some files for this directory.
      (= numGames (GetSaveFiles (theGame name?) names nums))
      (if (== numGames -1)
         (return FALSE)
      )

      (= theStatus (GetStatus))

      ;Set up the edit item for saved games.
      (if (== theStatus HAVESPACE)
         (editI
            text: (StrCpy theComment names),
            font: smallFont,
            setSize:,
            moveTo: MARGIN MARGIN
         )
         (self add: editI, setSize:)
      )

      ;Set up the selectorI box.
      (selectorI
         text: names,
         font: smallFont,
         setSize:,
         moveTo: MARGIN (+ nsBottom MARGIN),
         state: dExit
      )

		(= i (+ (selectorI nsRight?) 4))
		(okI
			text: [butbuf1 theStatus]
			setSize:
			moveTo: i (selectorI nsTop?)
			state:
				(if
					(or
						(and (== theStatus RESTORE) (not numGames))
						(== theStatus NOREPLACE)
					)
					0
				else
					(| dActive dExit)
				)
		)
		(deleteI
			setSize:
			moveTo: i (+ (okI nsBottom?) MARGIN)
			state: (if (not numGames) 0 else (| dActive dExit))
		)
		(changeDirI
			setSize:
			moveTo: i (+ (deleteI nsBottom?) MARGIN)
			state: (& (changeDirI state?) (~ dSelected))
		)
		(cancelI
			setSize:
			moveTo: i (+ (changeDirI nsBottom?) MARGIN)
			state: (& (cancelI state?) (~ dSelected))
		)
		(self
			add: selectorI okI deleteI changeDirI cancelI
			setSize:
		)
		(textI
			text: [butbuf2 theStatus]
			setSize: (- (- nsRight nsLeft) (* 2 MARGIN))
			moveTo: MARGIN MARGIN
		)
		(= i (+ (textI nsBottom?) MARGIN))
		(self eachElementDo: #move 0 i)
		(self add: textI setSize: center: open: wTitled -1)
		(return TRUE)
	)
;EO: This is adapted from SCI16's SAVE.SC. It doesn't fully match the commented 
;assembly code below.
;A known issue is that pressing ESC when in the Save Game dialog causes some junk text to
;appear on the description field. No idea why. Somebody might want to check the original disassembly,
;or, better yet, unearth an earlier version of SAVE.SC...
	
   (method  (doit theComment
                  &tmp  fd ret offset 
                        [names BUFFERSIZE] [nums 21]
                        [str 100] [dir 40]
            )

      ;If restore: is called with a TRUE parameter, do nothing if there
      ;are no saved games.  This allows optionally presenting the user
      ;with his saved games at the start of the game.
      (if
         (and
            (== self Restore)
            argc
            theComment
         )

         (= fd (FileIO fileOpen (Format @str "%ssg.dir" (theGame name?))))
         (if (== fd -1)
            ;no directory -> no saved games
            (return)
         )
         (FileIO fileClose fd)
      )

      (if (not (self init: theComment @names @nums))
         (return -1)
      )

      (repeat
         (= default
            (switch theStatus
               (RESTORE
                  (if numGames okI else changeDirI)
               )
               (HAVESPACE
                  ;Edit item of save games is active if present
                  editI
               )
               (NOSPACE
                  ;If there are save-games to replace, 'Replace'
                  ;button is active.
                  okI
               )
               (else
                  ;Otherwise 'Change Directory' button is active.
                  changeDirI
               )
            )
         )

         (= i (super doit: default))

         (= selected (selectorI indexOf: (selectorI cursor?)))
         (= offset (* selected COMMENTBUFF))
         (cond
            ((== i changeDirI)
               ;; kill save window to save hunk
               (self dispose:)
               (if (GetDirectory curSaveDir)
                  (= numGames
                     (GetSaveFiles (theGame name?) @names @nums)
                  )
                  (if (== numGames -1)
                     (= ret -1)
                     (break)
                  )
               )
               ;; open save back up with new directory
               (self init: theComment @names @nums)
            )

            ((and (== theStatus NOSPACE) (== i okI))
               (self dispose:)
               (if (GetReplaceName doit: (StrCpy theComment @[names offset]))
                  (= ret [nums selected])
                  (break)
               )
               (self init: theComment @names @nums)
            )

            ((and (== theStatus HAVESPACE) (or (== i okI) (== i editI)))
               (if (== (StrLen theComment) 0)
                  (self dispose:)
                  (NeedDescription)
                  (self init: theComment @names @nums)
                  (continue)
               )

               (= ret -1)
               (for  ((= i 0))
                     (< i numGames)
                     ((++ i))

                  (= ret (StrCmp theComment @[names (* i COMMENTBUFF)]))
                  (breakif (not ret))
               )

               (cond
                  ((not ret)
                     (= ret [nums i])
                  )
                  ((== numGames MAXGAMES)
                     (= ret [nums selected])
                  )
                  (else
                     ; find the lowest unused game number
                     (for ((= ret 0)) TRUE ((++ ret))
                        (for ((= i 0)) (< i numGames) ((++ i))
                           (breakif (== ret [nums i])) ; this number is used
                        )
                        (if (== i numGames)  ; checked all entries in nums
                           (break)           ; and none matched
                        )
                     )
                  )
               )
               (break)
            )
            
            ((== i deleteI)
               ;; kill save window to save hunk
               (self dispose:)
               ; confirm deletion
               (if (not
                  (Print
                     {Are you sure you want to\ndelete this saved game?}
                     #button {No_} FALSE
                     #button {Yes} TRUE
                   ))
                  (self init: theComment @names @nums)
                  (continue)
               )
               
               ; open the saved game directory file
               ((= fd (File new:))
                  name: (DeviceInfo MakeSaveDirName @str (theGame name?)),
                  open: fTrunc
               )
               
               ; (File write:) requires a pointer to the data it is to write,
               ; so need to put values into variables, rather than just 
               ; passing them as immediates
               (= ret $0a0a)  ; so both high and low byte is correct

               ; write the number and name of each saved game, except the
               ; one that was selected for deletion
               (for ((= i 0)) (< i numGames) ((++ i))
                  (if (!= i selected)
                     (fd write: @[nums i] 2)
                     (fd writeString: @[names (* i COMMENTBUFF)])
                     (fd write: @ret 1)
                  )
               )

               ; terminate saved game directory with a word of f's
               (= ret -1)
               (fd
                  write: @ret 2,
                  close:
                  dispose:
               )

               ; delete the save game file itself
               (DeviceInfo MakeSaveFileName 
                     @str (theGame name?) [nums selected]
               )
               (FileIO fileUnlink @str)

               ; reinit to display updated dialog
               (self init: theComment @names @nums)
            )

            ((== i okI)
               (= ret [nums selected])
               (break)
            )

            ((or (== i -1) (== i cancelI))
               (= ret -1)
               (break)
            )

            ((== theStatus HAVESPACE)
               (editI
                  cursor:
                     (StrLen (StrCpy theComment @[names offset])),
                  draw:
               )
            )
         )
      )
      (DisposeScript FILE)
      (self dispose:)
      (DisposeScript SAVE)
      (return ret)
   )
	
	;EO: Here is the disassembled doit
;;;	(method (doit theComment &tmp fd printRet offset [names BUFFERSIZE] [nums 21] [str 140])
;;;		(asm
;;;			pushSelf
;;;			lofsa    Restore
;;;			eq?     
;;;			bnt      code_024d
;;;			lap      argc
;;;			bnt      code_024d
;;;			lap      theComment
;;;			bnt      code_024d
;;;			pushi    2
;;;			pushi    0
;;;			pushi    4
;;;			lea      @str
;;;			push    
;;;			pushi    990
;;;			pushi    0
;;;			pushi    #name
;;;			pushi    fileOpen
;;;			lag      theGame
;;;			send     4
;;;			push    
;;;			callk    Format,  8
;;;			push    
;;;			callk    FileIO,  4
;;;			sat      fd
;;;			push    
;;;			ldi      65535	
;;;			eq?     
;;;			bnt      code_0246
;;;			ret     
;;;code_0246:						;(FileIO fileClose fd)
;;;			pushi    2
;;;			pushi    fileClose
;;;			lst      fd
;;;			callk    FileIO,  4
;;;code_024d:
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			not     
;;;			bnt      code_0266
;;;			ldi      65535
;;;			ret     
;;;code_0266:
;;;			lsl      theStatus
;;;			dup     
;;;			ldi      RESTORE
;;;			eq?     
;;;			bnt      code_0280
;;;			lal      numGames
;;;			bnt      code_029d
;;;			lofsa    okI
;;;			jmp      code_029d
;;;			lofsa    changeDirI
;;;			jmp      code_029d
;;;code_0280:
;;;			dup     
;;;			ldi      HAVESPACE
;;;			eq?     
;;;			bnt      code_028d
;;;			lofsa    editI
;;;			jmp      code_029d
;;;code_028d:
;;;			dup     
;;;			ldi      NOSPACE
;;;			eq?     
;;;			bnt      code_029a
;;;			lofsa    okI
;;;			jmp      code_029d
;;;code_029a:
;;;			lofsa    changeDirI
;;;code_029d:
;;;			toss    
;;;			sal      default
;;;			pushi    #doit
;;;			pushi    1
;;;			push    
;;;			super    Dialog,  6
;;;			sal      i
;;;			pushi    #indexOf
;;;			pushi    1
;;;			pushi    #cursor
;;;			pushi    0
;;;			lofsa    selectorI
;;;			send     4
;;;			push    
;;;			lofsa    selectorI
;;;			send     6
;;;			sal      selected
;;;			push    
;;;			ldi      18
;;;			mul     
;;;			sat      offset
;;;			lsl      i
;;;			lofsa    changeDirI
;;;			eq?     
;;;			bnt      code_0316
;;;			pushi    #dispose
;;;			pushi    0
;;;			self     4
;;;			pushi    1
;;;			lsg      curSaveDir
;;;			call     GetDirectory,  2
;;;			bnt      code_0301
;;;			pushi    3
;;;			pushi    #name
;;;			pushi    0
;;;			lag      theGame
;;;			send     4
;;;			push    
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			callk    GetSaveFiles,  6
;;;			sal      numGames
;;;			push    
;;;			ldi      65535
;;;			eq?     
;;;			bnt      code_0301
;;;			ldi      65535
;;;			sat      printRet
;;;			jmp      code_0595
;;;code_0301:
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			jmp      code_0266
;;;code_0316:
;;;			lsl      theStatus
;;;			ldi      NOSPACE
;;;			eq?     
;;;			bnt      code_0363
;;;			lsl      i
;;;			lofsa    okI
;;;			eq?     
;;;			bnt      code_0363
;;;			pushi    #dispose
;;;			pushi    0
;;;			self     4
;;;			pushi    #doit
;;;			pushi    1
;;;			pushi    2
;;;			lsp      theComment
;;;			lat      offset
;;;			leai     @names
;;;			push    
;;;			callk    StrCpy,  4
;;;			push    
;;;			lofsa    GetReplaceName
;;;			send     6
;;;			bnt      code_034e
;;;			lal      selected
;;;			lati     nums
;;;			sat      printRet
;;;			jmp      code_0595
;;;code_034e:
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			jmp      code_0266
;;;code_0363:
;;;			lsl      theStatus
;;;			ldi      HAVESPACE
;;;			eq?     
;;;			bnt      code_0430
;;;			lsl      i
;;;			lofsa    okI
;;;			eq?     
;;;			bt       code_037d
;;;			lsl      i
;;;			lofsa    editI
;;;			eq?     
;;;			bnt      code_0430
;;;code_037d:
;;;			pushi    1
;;;			lsp      theComment
;;;			callk    StrLen,  2
;;;			push    
;;;			ldi      0
;;;			eq?     
;;;			bnt      code_03a9
;;;			pushi    #dispose
;;;			pushi    0
;;;			self     4
;;;			pushi    0
;;;			call     NeedDescription,  0
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			jmp      code_0266
;;;code_03a9:
;;;			ldi      65535
;;;			sat      printRet
;;;			ldi      0
;;;			sal      i
;;;code_03b1:
;;;			lsl      i
;;;			lal      numGames
;;;			lt?     
;;;			bnt      code_03d3
;;;			pushi    2
;;;			lsp      theComment
;;;			lsl      i
;;;			ldi      18
;;;			mul     
;;;			leai     @names
;;;			push    
;;;			callk    StrCmp,  4
;;;			sat      printRet
;;;			not     
;;;			bnt      code_03ce
;;;code_03ce:
;;;			+al      i
;;;			jmp      code_03b1
;;;code_03d3:
;;;			lat      printRet
;;;			not     
;;;			bnt      code_03e3
;;;			lal      i
;;;			lati     nums
;;;			sat      printRet
;;;			jmp      code_0595
;;;code_03e3:
;;;			lsl      numGames
;;;			ldi      MAXGAMES
;;;			eq?     
;;;			bnt      code_03f5
;;;			lal      selected
;;;			lati     nums
;;;			sat      printRet
;;;			jmp      code_0595
;;;code_03f5:
;;;			ldi      0
;;;			sat      printRet
;;;code_03f9:
;;;			ldi      1
;;;			bnt      code_0595
;;;			ldi      0
;;;			sal      i
;;;code_0402:
;;;			lsl      i
;;;			lal      numGames
;;;			lt?     
;;;			bnt      code_041a
;;;			lst      printRet
;;;			lal      i
;;;			lati     nums
;;;			eq?     
;;;			bnt      code_0415
;;;code_0415:
;;;			+al      i
;;;			jmp      code_0402
;;;code_041a:
;;;			lsl      i
;;;			lal      numGames
;;;			eq?     
;;;			bnt      code_0425
;;;			jmp      code_0595
;;;code_0425:
;;;			+at      printRet
;;;			jmp      code_03f9
;;;			jmp      code_0595
;;;			jmp      code_0266
;;;code_0430:
;;;			lsl      i
;;;			lofsa    deleteI
;;;			eq?     
;;;			bnt      code_053c
;;;			pushi    #dispose
;;;			pushi    0
;;;			self     4
;;;			pushi    8
;;;			lofsa    {Are you sure you want to\0D\ndelete this saved game?}
;;;			push    
;;;			pushi    105
;;;			pushi    #button
;;;			lofsa    { No_}
;;;			push    
;;;			pushi    FALSE
;;;			pushi    #button
;;;			lofsa    {Yes}
;;;			push    
;;;			pushi    TRUE
;;;			calle    Print,  16
;;;			not     
;;;			bnt      code_0473
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			jmp      code_0266
;;;code_0473:
;;;			pushi    #name
;;;			pushi    1
;;;			pushi    3
;;;			pushi    7
;;;			lea      @str
;;;			push    
;;;			pushi    #name
;;;			pushi    0
;;;			lag      theGame
;;;			send     4
;;;			push    
;;;			callk    DeviceInfo,  6
;;;			push    
;;;			pushi    181
;;;			pushi    1
;;;			pushi    2
;;;			pushi    #new
;;;			pushi    0
;;;			class    54
;;;			send     4
;;;			sat      fd
;;;			send     12
;;;			ldi      2570
;;;			sat      printRet
;;;			ldi      0
;;;			sal      i
;;;code_04a5:
;;;			lsl      i
;;;			lal      numGames
;;;			lt?     
;;;			bnt      code_04e9
;;;			lsl      i
;;;			lal      selected
;;;			ne?     
;;;			bnt      code_04e4
;;;			pushi    #write
;;;			pushi    2
;;;			lal      i
;;;			leai     @nums
;;;			push    
;;;			pushi    2
;;;			lat      fd
;;;			send     8
;;;			pushi    331
;;;			pushi    #superClass
;;;			lsl      i
;;;			ldi      18
;;;			mul     
;;;			leai     @names
;;;			push    
;;;			lat      fd
;;;			send     6
;;;			pushi    #write
;;;			pushi    2
;;;			lea      @printRet
;;;			push    
;;;			pushi    1
;;;			lat      fd
;;;			send     8
;;;code_04e4:
;;;			+al      i
;;;			jmp      code_04a5
;;;code_04e9:
;;;			ldi      65535
;;;			sat      printRet
;;;			pushi    #write
;;;			pushi    2
;;;			lea      @printRet
;;;			push    
;;;			pushi    2
;;;			pushi    335
;;;			pushi    0
;;;			pushi    107
;;;			pushi    0
;;;			lat      fd
;;;			send     16
;;;			pushi    4
;;;			pushi    8
;;;			lea      @str
;;;			push    
;;;			pushi    #name
;;;			pushi    0
;;;			lag      theGame
;;;			send     4
;;;			push    
;;;			lal      selected
;;;			lsti     nums
;;;			callk    DeviceInfo,  8
;;;			pushi    2
;;;			pushi    4
;;;			lea      @str
;;;			push    
;;;			callk    FileIO,  4
;;;			pushi    #init
;;;			pushi    3
;;;			lsp      theComment
;;;			lea      @names
;;;			push    
;;;			lea      @nums
;;;			push    
;;;			self     10
;;;			jmp      code_0266
;;;code_053c:
;;;			lsl      i
;;;			lofsa    okI
;;;			eq?     
;;;			bnt      code_0552
;;;			lal      selected
;;;			lati     nums
;;;			sat      printRet
;;;			jmp      code_0595
;;;			jmp      code_0266
;;;code_0552:
;;;			lsl      i
;;;			ldi      0
;;;			eq?     
;;;			bt       code_0563
;;;			lsl      i
;;;			lofsa    cancelI
;;;			eq?     
;;;			bnt      code_056d
;;;code_0563:
;;;			ldi      65535
;;;			sat      printRet
;;;			jmp      code_0595
;;;			jmp      code_0266
;;;code_056d:
;;;			lsl      theStatus
;;;			ldi      1
;;;			eq?     
;;;			bnt      code_0266
;;;			pushi    #cursor
;;;			pushi    1
;;;			pushi    1
;;;			pushi    2
;;;			lsp      theComment
;;;			lat      offset
;;;			leai     @names
;;;			push    
;;;			callk    StrCpy,  4
;;;			push    
;;;			callk    StrLen,  2
;;;			push    
;;;			pushi    83
;;;			pushi    0
;;;			lofsa    editI
;;;			send     10
;;;			jmp      code_0266
;;;code_0595:
;;;			pushi    1
;;;			pushi    FILE
;;;			callk    DisposeScript,  2
;;;			pushi    1
;;;			pushi    PRINTD
;;;			callk    DisposeScript,  2
;;;			pushi    #dispose
;;;			pushi    0
;;;			self     4
;;;			pushi    1
;;;			pushi    SAVE
;;;			callk    DisposeScript,  2
;;;			lat      printRet
;;;			ret     
;;;		)
;;;	)
	
	(method (dispose)
		(proc932_4)
		(theGame parseLang: gGameParseLang)
		(super dispose: &rest)
	)
)

(class Restore of SRDialog
	(properties
		text {Restore a Game}
	)
)

(class Save of SRDialog
	(properties
		text {Save a Game}
	)
)

(instance GetReplaceName of Dialog
	(properties)
	
	(method (doit theComment &tmp ret theGameParseLang)
		(= theGameParseLang (theGame parseLang?))
		(theGame parseLang: ENGLISH)
		(= window systemWindow)
		(text1 setSize: moveTo: MARGIN MARGIN)
		(self add: text1 setSize:)
		(oldName
			text: theComment
			font: smallFont
			setSize:
			moveTo: MARGIN nsBottom
		)
		(self add: oldName setSize:)
		(text2 setSize: moveTo: MARGIN nsBottom)
		(self add: text2 setSize:)
		(newName
			text: theComment
			font: smallFont
			setSize:
			moveTo: MARGIN nsBottom
		)
		(self add: newName setSize:)
		(button1 nsLeft: 0 nsTop: 0 setSize:)
		(button2 nsLeft: 0 nsTop: 0 setSize:)
		(button2
			moveTo: (- nsRight (+ (button2 nsRight?) MARGIN)) nsBottom
		)
		(button1
			moveTo: (- (button2 nsLeft?) (+ (button1 nsRight?) MARGIN)) nsBottom
		)
		(self add: button1 button2 setSize: center: open: stdWindow -1)
		(= ret (super doit: newName))
		(self dispose:)
		(if (not (StrLen theComment))
			(NeedDescription)
			(= ret 0)
		)
		(theGame parseLang: theGameParseLang)
		(return (if (== ret newName) else (== ret button1)))
	)
)

(instance selectorI of DSelector
	(properties
		x COMMENTSIZE
		y GAMESSHOWN
	)
)

(instance editI of DEdit
	(properties
		max (- COMMENTSIZE 1)
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
		font SYSFONT
	)
)

(instance text1 of DText
	(properties
		text {Replace}
		font SYSFONT
	)
)

(instance text2 of DText
	(properties
		text {with:}
		font SYSFONT
	)
)

(instance oldName of DText
	(properties)
)

(instance newName of DEdit
	(properties
		max (- COMMENTSIZE 1)
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
