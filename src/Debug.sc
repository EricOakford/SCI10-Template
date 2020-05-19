;;; Sierra Script 1.0 - (do not remove this comment)
(script# DEBUG)
(include game.sh)
(use Main)
(use Intrface)
(use PolyEdit)
(use WriteFtr)
(use Window)
(use User)
(use Invent)
(use Feature)
(use Actor)
(use System)

(public
	debugHandler 0
)

(local
	singleStepOn
	invDButton
)
(procedure (SingleStepMode &tmp evt)
	(while
	(!= ((= evt (Event new:)) message?) $1800)
		(if (== (evt message?) $2200)
			(theGame doit:)
		else
			(evt dispose:)
		)
	)
)

(instance debugHandler of Feature
	
	(method (init)
		(super init:)
		(mouseDownHandler addToFront: self)
		(keyDownHandler addToFront: self)
	)
	
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(super dispose:)
		(DisposeScript DEBUG)
	)
	
	
	(method (handleEvent event &tmp obj i [str 80] nextRoom)
		(switch (event type?)
			(keyDown
				(event claimed: TRUE)
				(switch (event message?)
					(`@t
						(= nextRoom (GetNumber {Which room number?}))
						(curRoom newRoom: nextRoom)
					)
					(`?
						(Print
							"Debug Key commands:\n
							ALT-A Cursor Position\n
							ALT-B Polygon Editor\n
							ALT-C Control\n
							ALT-E Show ego\n
							ALT-F Set/Clr Flag\n
							ALT-G Go one cycle\n
							_____(in single-step mode only)\n
							ALT-I Get InvItem\n
							ALT-L Open Log File\n
							ALT-M Show memory"
						)
						(Print
							"ALT-O Single-Step\n
							ALT-P Priority\n
							ALT-S Show cast\n
							ALT-T Teleport\n
							ALT-V Visual\n
							ALT-W Feature Writer\n
							ALT-X eXit game quickly"
						)
					)
					(`@a
						(Printf "Cursor X: %d Y: %d" (event x?) (event y?))
					)
					(`@b
						(PolygonEditor doit:)
					)
					(`@s
						(= i (cast first:))
						(while i
							(= obj (NodeValue i))
							(Print
								(Format
									@str "view: %d
										(x,y):%d,%d
										STOPUPD=%d
										IGNRACT=%d
										ILLBITS=$%x"
									(obj view?)
									(obj x?)
									(obj y?)
									(/ (& (obj signal?) notUpd) notUpd)
									(/ (& (obj signal?) ignrAct) ignrAct)
									(if
										(or
											(== (obj superClass?) Actor)
											(== (obj superClass?) Ego)
										)
										(obj illegalBits?)
									else
										-1
									)
								)
								#window SysWindow
								#title (obj name?)
								#icon (obj view?) (obj loop?) (obj cel?)
							)
							(= i (cast next: i))
						)
					)
					(`@m
						(theGame showMem:)
					)
					(`@e
						(Format
							@str "ego\nx:%d y:%d\nloop:%d\ncel:%d"
							(ego x?)
							(ego y?)
							(ego loop?)
							(ego cel?)
						)
						(Print @str #icon (ego view?) 0 0)
					)
					(`@v
						(Show VMAP)
					)
					(`@p
						(Show PMAP)
					)
					(`@c
						(Show CMAP)
					)
					(`@k
						(if singleStepOn
							(theGame doit:)
						)
					)
					(`@i
						(dInvD doit:)
					)
					(`@l
						(if (> (MemoryInfo LargestPtr) 1536)
							((ScriptID LOGGER) doit: @sysLogPath)
						else
							(Print "Not Enough Memory!!")
						)
						(event claimed: TRUE)
					)
					(`@o
						(if singleStepOn
							(= singleStepOn FALSE)
							(Print "Single-step mode is off")
						else
							(= singleStepOn TRUE)
							(Print "Single-step mode is on")
							(SingleStepMode)
						)
					)
					(`@f
						(= i 0)
						(= i (GetNumber {Flag Number:}))
						(if (Btst i)
							(Print "clearing flag")
							(Bclr i)
						else
							(Print "setting flag")
							(Bset i)
						)
					)
					(`@w
						(CreateObject doit:)
					)
					(`@x
						(= quit TRUE)
					)
					(else
						(event claimed: FALSE)
					)
				)
			)
		)
	)
)

(instance dInvD of Dialog
	(properties)
	
	(method (init &tmp temp0 temp1 temp2 i newDText inventoryFirst temp6)
		(= temp2 (= temp0 (= temp1 4)))
		(= i 0)
		(= inventoryFirst (inventory first:))
		(while inventoryFirst
			(= temp6 (NodeValue inventoryFirst))
			(++ i)
			(if (temp6 isKindOf: InvItem)
				(self
					add:
						((= newDText (DText new:))
							value: temp6
							text: (temp6 name?)
							nsLeft: temp0
							nsTop: temp1
							state: 3
							font: smallFont
							setSize:
							yourself:
						)
				)
			)
			(if
			(< temp2 (- (newDText nsRight?) (newDText nsLeft?)))
				(= temp2 (- (newDText nsRight?) (newDText nsLeft?)))
			)
			(if
				(>
					(= temp1
						(+ temp1 (- (newDText nsBottom?) (newDText nsTop?)) 1)
					)
					140
				)
				(= temp1 4)
				(= temp0 (+ temp0 temp2 10))
				(= temp2 0)
			)
			(= inventoryFirst (inventory next: inventoryFirst))
		)
		(= window systemWindow)
		(self setSize:)
		(= invDButton (DButton new:))
		(invDButton
			text: {Outta here!}
			setSize:
			moveTo: (- nsRight (+ 4 (invDButton nsRight?))) nsBottom
		)
		(self add: invDButton setSize: center:)
		(return i)
	)
	
	(method (doit &tmp theNewDButton)
		(self init:)
		(self open: MARGIN 15)
		(= theNewDButton invDButton)
		(repeat
			(if
				(or
					(not (= theNewDButton (super doit: theNewDButton)))
					(== theNewDButton -1)
					(== theNewDButton invDButton)
				)
				(break)
			)
			((theNewDButton value?) owner: ego)
		)
		(self dispose:)
	)
	
	(method (handleEvent event &tmp eMsg eType)
		(= eMsg (event message?))
		(switch (= eType (event type?))
			(keyDown
				(switch eMsg
					(UPARROW
						(= eMsg SHIFTTAB)
					)
					(DOWNARROW
						(= eMsg TAB)
					)
				)
			)
			(direction
				(switch eMsg
					(dirN
						(= eMsg SHIFTTAB)
						(= eType keyDown)
					)
					(dirS
						(= eMsg TAB)
						(= eType keyDown)
					)
				)
			)
		)
		(event type: eType message: eMsg)
		(super handleEvent: event)
	)
)
