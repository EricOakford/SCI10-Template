;;; Sierra Script 1.0 - (do not remove this comment)
(script# 928)
(include sci.sh)
(use Main)
(use Intrface)
(use RandCyc)
(use Actor)
(use System)


(class RTRandCycle of RandCycle
	(properties
		client 0
		caller 0
		cycleDir 1
		cycleCnt 0
		completed 0
		count -1
	)
	
	(method (init param1 param2 theCaller)
		(super init: param1)
		(client cel: 0)
		(= cycleCnt (GetTime))
		(if (>= argc 2)
			(= count (+ (GetTime) param2))
			(if (>= argc 3) (= caller theCaller))
		else
			(= count -1)
		)
	)
	
	(method (doit &tmp temp0)
		(if (> count (= temp0 (GetTime)))
			(if (> (- temp0 cycleCnt) (client cycleSpeed?))
				(client cel: (self nextCel:))
				(= cycleCnt (GetTime))
			)
		else
			(client cel: 0)
			(self cycleDone:)
		)
	)
)

(class Talker of Prop
	(properties
		bust 0
		eyes 0
		mouth 0
		ticks 0
		disposeWhenDone 1
		caller 0
	)
	
	(method (init theBust theEyes theMouth param4 param5 param6 theDisposeWhenDone theCaller &tmp temp0)
		(if argc
			(= bust theBust)
			(if (>= argc 2)
				(= eyes theEyes)
				(if (>= argc 3)
					(= mouth theMouth)
					(if (>= argc 7)
						(= disposeWhenDone theDisposeWhenDone)
						(if (>= argc 8) (= caller theCaller))
					)
				)
			)
		)
		(self show:)
		(if (>= argc 4)
			(self say: param4 param5 param6 disposeWhenDone caller)
		)
	)
	
	(method (doit)
		(if (> (GetTime) ticks)
			(self dispose: disposeWhenDone)
			(return)
		)
		(if eyes (self cycle: eyes))
		(if mouth (self cycle: mouth))
	)
	
	(method (dispose param1 &tmp theCaller)
		(fastCast delete: self release:)
		(fastCast dispose:)
		(= fastCast 0)
		(if (or (not argc) param1)
			(if (and mouth (mouth cycler?))
				(if ((mouth cycler?) respondsTo: #cue)
					((mouth cycler?) cue:)
				)
				(mouth setCycle: 0)
			)
			(cond 
				(cDAudio (DoAudio 3))
				(modelessDialog (modelessDialog dispose:))
			)
			(if eyes (eyes setCycle: 0))
			(self hide:)
		)
		(if caller
			(= theCaller caller)
			(= caller 0)
			(theCaller cue:)
		)
		(DisposeClone self)
	)
	
	(method (handleEvent pEvent)
		(if (super handleEvent: pEvent) (return))
		(if
			(or
				(& (pEvent type?) evMOUSEBUTTON)
				(& (pEvent type?) $4000)
				(and
					(& (pEvent type?) evKEYBOARD)
					(== (pEvent message?) KEY_RETURN)
				)
			)
			(pEvent claimed: 1)
			(self dispose: disposeWhenDone)
		)
	)
	
	(method (hide)
		(Graph grRESTORE_BOX underBits)
		(= underBits 0)
		(Graph grREDRAW_BOX nsTop nsLeft nsBottom nsRight)
	)
	
	(method (show &tmp temp0)
		(= nsRight
			(+
				nsLeft
				(Max
					(CelWide view loop cel)
					(if (IsObject bust)
						(+
							(bust nsLeft?)
							(CelWide (bust view?) (bust loop?) (bust cel?))
						)
					else
						0
					)
					(if (IsObject eyes)
						(+
							(eyes nsLeft?)
							(CelWide (eyes view?) (eyes loop?) (eyes cel?))
						)
					else
						0
					)
					(if (IsObject mouth)
						(+
							(mouth nsLeft?)
							(CelWide (mouth view?) (mouth loop?) (mouth cel?))
						)
					else
						0
					)
				)
			)
		)
		(= nsBottom
			(+
				nsTop
				(Max
					(CelHigh view loop cel)
					(if (IsObject bust)
						(+
							(bust nsTop?)
							(CelHigh (bust view?) (bust loop?) (bust cel?))
						)
					else
						0
					)
					(if (IsObject eyes)
						(+
							(eyes nsTop?)
							(CelHigh (eyes view?) (eyes loop?) (eyes cel?))
						)
					else
						0
					)
					(if (IsObject mouth)
						(+
							(mouth nsTop?)
							(CelHigh (mouth view?) (mouth loop?) (mouth cel?))
						)
					else
						0
					)
				)
			)
		)
		(if (not underBits)
			(= underBits
				(Graph grSAVE_BOX nsTop nsLeft nsBottom nsRight 1)
			)
		)
		(= temp0 (PicNotValid))
		(PicNotValid 1)
		(if bust
			(DrawCel
				(bust view?)
				(bust loop?)
				(bust cel?)
				(+ (bust nsLeft?) nsLeft)
				(+ (bust nsTop?) nsTop)
				-1
			)
		)
		(if eyes
			(DrawCel
				(eyes view?)
				(eyes loop?)
				(eyes cel?)
				(+ (eyes nsLeft?) nsLeft)
				(+ (eyes nsTop?) nsTop)
				-1
			)
		)
		(if mouth
			(DrawCel
				(mouth view?)
				(mouth loop?)
				(mouth cel?)
				(+ (mouth nsLeft?) nsLeft)
				(+ (mouth nsTop?) nsTop)
				-1
			)
		)
		(DrawCel view loop cel nsLeft nsTop -1)
		(Graph grUPDATE_BOX nsTop nsLeft nsBottom nsRight 1)
		(PicNotValid temp0)
	)
	
	(method (say param1 param2 param3 theDisposeWhenDone theCaller)
		(if (>= argc 4)
			(= disposeWhenDone theDisposeWhenDone)
			(if (>= argc 5) (= caller theCaller))
		)
		(if cDAudio
			(self startAudio: param1 param2 param3)
		else
			(self startText: param1 param2 param3)
		)
		(if fastCast
			(fastCast add: self)
		else
			(= fastCast (EventHandler new:))
			(fastCast name: {fastCast} add: self)
		)
		(= ticks (+ ticks 60 (GetTime)))
	)
	
	(method (startAudio param1 &tmp temp0)
		(asm
			lap      param1
			sat      temp0
			pushi    2
			pushi    1
			push    
			callk    DoAudio,  4
			pToa     mouth
			bnt      code_0452
			pushi    #setCycle
			pushi    2
			class    40
			push    
			lst      temp0
			pToa     mouth
			send     8
code_0452:
			pushi    2
			pushi    2
			lst      temp0
			callk    DoAudio,  4
			aTop     ticks
			pToa     eyes
			bnt      code_046d
			pushi    #setCycle
			pushi    2
			class    RTRandCycle
			push    
			pTos     ticks
			pToa     eyes
			send     8
code_046d:
			ret     
		)
	)
	
	(method (startText param1 param2 &tmp [temp0 500])
		(Format @temp0 param1 param2)
		(= ticks (* 5 (StrLen @temp0)))
		(if mouth (mouth setCycle: RTRandCycle ticks))
		(if eyes (eyes setCycle: RTRandCycle ticks))
		(Print @temp0 #at x y #dispose)
	)
	
	(method (cycle param1 &tmp temp0)
		(if (and param1 (param1 cycler?))
			(= temp0 (param1 cel?))
			((param1 cycler?) doit:)
			(if (!= temp0 (param1 cel?))
				(DrawCel
					(param1 view?)
					(param1 loop?)
					(param1 cel?)
					(+ (param1 nsLeft?) nsLeft)
					(+ (param1 nsTop?) nsTop)
					-1
				)
				(param1
					nsRight:
						(+
							(param1 nsLeft?)
							(CelWide (param1 view?) (param1 loop?) (param1 cel?))
						)
				)
				(param1
					nsBottom:
						(+
							(param1 nsTop?)
							(CelHigh (param1 view?) (param1 loop?) (param1 cel?))
						)
				)
				(Graph
					grUPDATE_BOX
					(+ (param1 nsTop?) nsTop)
					(+ (param1 nsLeft?) nsLeft)
					(+ (param1 nsBottom?) nsTop)
					(+ (param1 nsRight?) nsLeft)
					1
				)
			)
		)
	)
)
