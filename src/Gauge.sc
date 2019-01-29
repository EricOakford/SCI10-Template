;;; Sierra Script 1.0 - (do not remove this comment)
(script# 987)
(include sci.sh)
(use Main)
(use Intrface)
(use Window)


(local
	textI
	upI
	downI
	gaugeI
	okI
	normalI
	noI
	[str 100]
)
(class Gauge of Dialog
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
		description 0
		higher {up}
		lower {down}
		normal 7
		minimum 0
		maximum 15
	)
	
	(method (init param1 &tmp temp0 temp1)
		(= window SysWindow)
		(self update: param1)
		((= downI (DButton new:))
			text: lower
			moveTo: 4 4
			setSize:
		)
		(self add: downI setSize:)
		((= gaugeI (DText new:))
			text: @str
			moveTo: (+ (downI nsRight?) 4) 4
			font: 0
			setSize:
		)
		(self add: gaugeI setSize:)
		((= upI (DButton new:))
			text: higher
			moveTo: (+ (gaugeI nsRight?) 4) 4
			setSize:
		)
		(self add: upI setSize:)
		(= nsBottom (+ nsBottom 8))
		((= okI (DButton new:))
			text: {OK}
			setSize:
			moveTo: 4 nsBottom
		)
		((= normalI (DButton new:))
			text: {Normal}
			setSize:
			moveTo: (+ (okI nsRight?) 4) nsBottom
		)
		((= noI (DButton new:))
			text: {Cancel}
			setSize:
			moveTo: (+ (normalI nsRight?) 4) nsBottom
		)
		(self add: okI normalI noI setSize:)
		(= temp0 (- (- nsRight (noI nsRight?)) 4))
		((= textI (DText new:))
			text: description
			font: smallFont
			setSize: (- nsRight 8)
			moveTo: 4 4
		)
		(= temp1 (+ (textI nsBottom?) 4))
		(self add: textI)
		(upI move: 0 temp1)
		(downI move: 0 temp1)
		(gaugeI move: 0 temp1)
		(okI move: temp0 temp1)
		(normalI move: temp0 temp1)
		(noI move: temp0 temp1)
		(self setSize: center: open: 4 15)
	)
	
	(method (doit param1 &tmp temp0 temp1)
		(asm
			pushi    #init
			pushi    1
			lsp      param1
			self     6
			lap      param1
			sat      temp1
code_01d4:
			pushi    #update
			pushi    1
			lst      temp1
			self     6
			pushi    #draw
			pushi    0
			lal      gaugeI
			send     4
			pushi    #doit
			pushi    1
			lsl      okI
			super    Dialog,  6
			sat      temp0
			push    
			lal      upI
			eq?     
			bnt      code_0201
			lst      temp1
			pToa     maximum
			lt?     
			bnt      code_01d4
			+at      temp1
			jmp      code_01d4
code_0201:
			lst      temp0
			lal      downI
			eq?     
			bnt      code_0216
			lst      temp1
			pToa     minimum
			gt?     
			bnt      code_01d4
			-at      temp1
			jmp      code_01d4
code_0216:
			lst      temp0
			lal      okI
			eq?     
			bnt      code_0224
			jmp      code_024d
			jmp      code_01d4
code_0224:
			lst      temp0
			lal      normalI
			eq?     
			bnt      code_0233
			pToa     normal
			sat      temp1
			jmp      code_01d4
code_0233:
			lst      temp0
			ldi      0
			eq?     
			bt       code_0243
			lst      temp0
			lal      noI
			eq?     
			bnt      code_01d4
code_0243:
			lap      param1
			sat      temp1
			jmp      code_024d
			jmp      code_01d4
code_024d:
			pushi    #dispose
			pushi    0
			self     4
			lat      temp1
			ret     
		)
	)
	
	(method (handleEvent pEvent)
		(switch (pEvent type?)
			(evKEYBOARD
				(switch (pEvent message?)
					(KEY_NUMPAD4
						(pEvent claimed: 1)
						(return downI)
					)
					(KEY_RIGHT
						(pEvent claimed: 1)
						(return upI)
					)
				)
			)
			(evJOYSTICK
				(switch (pEvent message?)
					(JOY_LEFT
						(pEvent claimed: 1)
						(return downI)
					)
					(JOY_RIGHT
						(pEvent claimed: 1)
						(return upI)
					)
				)
			)
		)
		(return (super handleEvent: pEvent))
	)
	
	(method (update param1 &tmp temp0 temp1)
		(= temp1 (- maximum minimum))
		(= temp0 0)
		(while (< temp0 temp1)
			(StrAt @str temp0 (if (< temp0 param1) 6 else 7))
			(++ temp0)
		)
	)
)
