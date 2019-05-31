;;; Sierra Script 1.0 - (do not remove this comment)
(script# 800)
(include system.sh) (include sci2.sh)
(use Main)
(use Intrface)
(use PolyEdit)
(use WriteFtr)
(use Window)
(use User)
(use Actor)
(use System)

(public
	debugRm 0
)

(local
	singleStepOn
)
(procedure (SingleStepMode &tmp newEvent)
	(while
	(!= ((= newEvent (Event new:)) message?) 6144)
		(if (== (newEvent message?) 8704)
			(theGame doit:)
		else
			(newEvent dispose:)
		)
	)
)

(instance debugRm of Script
	(properties)
	
	(method (handleEvent event &tmp temp0 temp1 [temp2 2] castFirst [temp5 80] temp85 temp86)
		(switch (event type?)
			(keyDown
				(event claimed: TRUE)
				(switch (event message?)
					(KEY_ALT_t
						;(Bset 4)
						(= temp85 (GetNumber {Which room number?}))
						(curRoom newRoom: temp85)
					)
					(KEY_QUESTION
						(Print
							"Debug Key commands:\n
							ALT-A   Cursor Position\n
							ALT-B   Polygon Editor\n
							ALT-C   Control\n
							ALT-E   Show ego\n
							ALT-F   Set/Clr Flag\n
							ALT-G   Go one cycle\n
							_____(in single-step mode only)\n
							ALT-L   Open Log File\n
							ALT-M   Show memory"
						)
						(Print
							"ALT-O   Single-Step\n
							ALT-P   Priority\n
							ALT-S   Show cast\n
							ALT-T   Teleport\n
							ALT-V   Visual\n
							ALT-W   Feature Writer\n
							ALT-X   eXit game quickly"
						)
					)
					(KEY_ALT_a
						(Printf "Cursor X: %d Y: %d" (event x?) (event y?))
					)
					(KEY_ALT_b (PolygonEditor doit:))
					(KEY_ALT_s
						(= castFirst (cast first:))
						(while castFirst
							(= temp1 (NodeValue castFirst))
							(Print
								(Format
									@temp5 "view: %d
(x,y):%d,%d
STOPUPD=%d
IGNRACT=%d
ILLBITS=$%x"
									(temp1 view?)
									(temp1 x?)
									(temp1 y?)
									(/ (& (temp1 signal?) $0004) 4)
									(/ (& (temp1 signal?) $4000) 16384)
									(if
										(or
											(== (temp1 superClass?) Actor)
											(== (temp1 superClass?) Ego)
										)
										(temp1 illegalBits?)
									else
										-1
									)
								)
								#window
								SysWindow
								#title
								(temp1 name?)
								#icon
								(temp1 view?)
								(temp1 loop?)
								(temp1 cel?)
							)
							(= castFirst (cast next: castFirst))
						)
					)
					(KEY_ALT_m (theGame showMem:))
					(KEY_ALT_e
						(Format
							@temp5 "ego\nx:%d y:%d\nloop:%d\ncel:%d"
							(ego x?)
							(ego y?)
							(ego loop?)
							(ego cel?)
						)
						(Print @temp5 #icon (ego view?) 0 0)
					)
					(KEY_ALT_v (Show VMAP))
					(KEY_ALT_p (Show PMAP))
					(KEY_ALT_c (Show CMAP))
					(KEY_ALT_k
						(if singleStepOn (theGame doit:))
					)
					(KEY_ALT_l
						(if (> (MemoryInfo LargestPtr) 1536)
							((ScriptID LOGGER) doit: @sysLogPath)
						else
							(Print "Not Enough Memory!!")
						)
						(event claimed: TRUE)
					)
					(KEY_ALT_o
						(if singleStepOn
							(= singleStepOn 0)
							(Print "Single-step mode is off")
						else
							(= singleStepOn 1)
							(Print "Single-step mode is on")
							(SingleStepMode)
						)
					)
					(KEY_ALT_f
						(= castFirst 0)
						(= castFirst (GetNumber {Flag Number:}))
						(if (Btst castFirst)
							(Print "clearing flag")
							(Bclr castFirst)
						else
							(Print "setting flag")
							(Bset castFirst)
						)
					)
					(KEY_ALT_w (CreateObject doit:))
					(KEY_ALT_x (= quit TRUE))
					(else  (event claimed: FALSE))
				)
			)
		)
	)
)