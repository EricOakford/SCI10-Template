;;; Sierra Script 1.0 - (do not remove this comment)
(script# 954)
(include sci.sh)
(use Main)
(use Intrface)
(use Approach)
(use Grooper)
(use Sight)
(use Avoider)
(use Motion)
(use User)
(use System)

(public
	proc954_0 0
	proc954_1 1
)

(local
	local0
	local1
	gEgoLooper
	gEgoAvoider
)
(procedure (proc954_0 param1 param2 param3 param4 param5 &tmp temp0 temp1)
	(switch
		(= temp1
			(ISSc
				init: param1 (if (and (>= argc 5) param5) param5 else '*/*') 1 0
			)
		)
		(1
			(ego
				setMotion:
					(if (IsObject param3) Approach else MoveTo)
					param3
					param4
					ISSc
				setAvoider: Avoid
			)
		)
		(2
			(if (>= argc 6) (Print &rest))
		)
	)
	(return temp1)
)

(procedure (proc954_1 param1 param2 param3 &tmp temp0)
	(= temp0
		(GetAngle (ego x?) (ego y?) (param1 x?) (param1 y?))
	)
	(return
		(if
			(==
				1
				(ISSc
					init:
						param1
						(if (>= argc 3) param3 else '*/*')
						(CantBeSeen
							param1
							ego
							(/ 360 (Max 4 (* (/ (NumLoops ego) 4) 4)))
						)
						1
				)
			)
			(if (IsObject gEgoLooper) (gEgoLooper dispose:))
			(= gEgoLooper (ego looper?))
			(ego
				looper: 0
				heading: temp0
				setMotion: 0
				setLoop: GradualLooper
			)
			((ego looper?) doit: ego temp0 ISSc)
			1
		else
			0
		)
	)
)

(instance ISSc of Script
	(properties)
	
	(method (init param1 param2 param3 param4)
		(return
			(if param3
				(if
					(and
						(not (if param4 local0 else local1))
						(Said param2)
					)
					(if (User canControl:)
						(if (IsObject gEgoAvoider) (gEgoAvoider dispose:))
						(= gEgoAvoider (ego avoider?))
						(ego avoider: 0)
						(if param4 (= local0 param1) else (= local1 param1))
						(User canControl: 0 canInput: 0)
						1
					else
						((User curEvent?) claimed: 0)
						2
					)
				)
			else
				0
			)
		)
	)
	
	(method (cue &tmp newEvent)
		(User canControl: 1 canInput: 1)
		((= newEvent (Event new:)) type: 128)
		(Parse (User inputLineAddr?) newEvent)
		(ego setAvoider: gEgoAvoider)
		(= gEgoAvoider 0)
		(if local0
			((ego looper?) dispose:)
			(ego looper: gEgoLooper)
			(= gEgoLooper 0)
			(local0 handleEvent: newEvent)
			(= local0 0)
		else
			(local1 handleEvent: newEvent)
			(= local1 0)
		)
		(if (not (newEvent claimed?))
			(regions eachElementDo: #handleEvent newEvent 1)
			(theGame handleEvent: newEvent 1)
		)
		(newEvent dispose:)
	)
)
