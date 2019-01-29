;;; Sierra Script 1.0 - (do not remove this comment)
(script# 976)
(include sci.sh)
(use Main)
(use Intrface)
(use Actor)
(use System)


(class Cat of Actor
	(properties
		x 0
		y 0
		z 0
		heading 0
		noun 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		description 0
		sightAngle 26505
		actions 0
		onMeCheck $6789
		approachX 0
		approachY 0
		approachDist 0
		_approachVerbs 26505
		lookStr 0
		yStep 2
		view 0
		loop 0
		cel 0
		priority 0
		underBits 0
		signal $0000
		lsTop 0
		lsLeft 0
		lsBottom 0
		lsRight 0
		brTop 0
		brLeft 0
		brBottom 0
		brRight 0
		palette 0
		cycleSpeed 0
		script 0
		cycler 0
		timer 0
		detailLevel 0
		illegalBits $8000
		xLast 0
		yLast 0
		xStep 3
		moveSpeed 0
		blocks 0
		baseSetter 0
		mover 0
		looper 0
		viewer 0
		avoider 0
		code 0
		top -1
		left -1
		bottom -1
		right -1
		diagonal 0
		doCast 0
		outOfTouch 1
		caller 0
		active 0
		dx 0
		dy 0
	)
	
	(method (doit &tmp userCurEvent)
		(cond 
			((not doCast) (= active 0))
			(active
				(= userCurEvent (user curEvent?))
				(self
					posn: (+ (userCurEvent x?) dx) (+ (userCurEvent y?) dy) z
				)
			)
		)
		(super doit:)
	)
	
	(method (handleEvent pEvent)
		(cond 
			((super handleEvent: pEvent))
			(
			(and active (== (pEvent type?) evMOUSERELEASE))
				(= active 0)
				(pEvent claimed: 1)
				(LocalToGlobal pEvent)
				(self posn: (+ (pEvent x?) dx) (+ (pEvent y?) dy) z)
				(GlobalToLocal pEvent)
				(if caller (caller cue: self))
			)
			((MousedOn self pEvent) (pEvent claimed: 1) (self track: pEvent))
		)
	)
	
	(method (posn theX theY theZ &tmp temp0)
		(if argc
			(= x theX)
			(if (>= argc 2)
				(= y theY)
				(if (>= argc 3) (= z theZ))
			)
		)
		(= temp0 (sign diagonal))
		(if
			(not
				(if
				(and (== -1 top) (== top bottom) (== bottom left))
					(== left right)
				)
			)
			(switch (Abs diagonal)
				(1
					(= y
						(+
							(if (> temp0 0) top else bottom)
							(/
								(* (- right x) (- bottom top) temp0)
								(- right left)
							)
						)
					)
				)
				(2
					(= x
						(+
							(if (> temp0 0) left else right)
							(/
								(* (- bottom y) (- right left) temp0)
								(- bottom top)
							)
						)
					)
				)
			)
			(= x (Max left (Min right x)))
			(= y (Max top (Min bottom y)))
		)
		(super posn: x y z)
	)
	
	(method (findPosn)
		(return 1)
	)
	
	(method (canBeHere)
		(return 1)
	)
	
	(method (track param1 &tmp newCollect)
		(LocalToGlobal param1)
		(= dx (- x (param1 x?)))
		(= dy (- y (param1 y?)))
		(if doCast
			(= active 1)
		else
			((= newCollect (Collection new:)) add: self)
			(while
				(and
					(!= 2 (param1 type?))
					(or
						outOfTouch
						(MousedOn self (param1 type: 1 yourself:))
					)
				)
				(self posn: (+ (param1 x?) dx) (+ (param1 y?) dy) z)
				(Animate (newCollect elements?) 1)
				(GetEvent 32767 param1)
			)
			(newCollect release: dispose:)
			(if caller (caller cue: self))
			(GlobalToLocal param1)
		)
	)
)
