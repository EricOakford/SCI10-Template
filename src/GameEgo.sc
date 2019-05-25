;;; Sierra Script 1.0 - (do not remove this comment)
(script# 813)
(include game.sh)
(use Main)
(use Grooper)
(use User)
(use Actor)
(use System)


(public
	Body 0
)

(local
	[theCel 24] = [6 0 4 5 1 7 4 2 5 7 3 6 0 4 2 2 5 1 3 6 0 1 7 3]
	local24 =  3
	[theXOffset 75] = [0 -1 0 0 0 0 0 0 0 -1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 -1 0 0 0 0 0 1 0 0 0 0 0 0 0 1]
	[theYOffset 75] = [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 -1 0 0 0 0 0 1 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 4 0 0 1 1 0 0 0 0 0 0 0 0 -1 0 0 0 0 -1]
)
(class Body of Ego
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
		signal $2000
		lsTop 0
		lsLeft 0
		lsBottom 0
		lsRight 0
		brTop 0
		brLeft 0
		brBottom 0
		brRight 0
		palette 0
		cycleSpeed 6
		script 0
		cycler 0
		timer 0
		detailLevel 0
		illegalBits $8000
		xLast 0
		yLast 0
		xStep 3
		moveSpeed 6
		blocks 0
		baseSetter 0
		mover 0
		looper 0
		viewer 0
		avoider 0
		code 0
		edgeHit 0
		head 0
		normal 1
		moveHead 1
	)
	
	(method (doit &tmp bodyLoop temp1)
		(super doit:)
		(cond 
			((self isStopped:)
				(if
					(and
						(!=
							(= bodyLoop (self loop?))
							(= temp1 (- (NumLoops self) 1))
						)
						(cast contains: self)
						(IsObject (self cycler?))
						(not ((self cycler?) isKindOf: GradualCycler))
						(self normal?)
					)
					(self loop: temp1 cel: bodyLoop)
				)
			)
			(
				(and
					(== (self loop?) (- (NumLoops self) 1))
					(not (& signal noTurn))
				)
				(self loop: (self cel?))
			)
		)
	)
	
	(method (dispose)
		(if head (head dispose:))
		(super dispose:)
	)
	
	(method (stopUpd)
		(if head (head stopUpd:))
		(super stopUpd:)
	)
	
	(method (hide)
		(if head (head hide:))
		(super hide:)
	)
)

(class Head of Prop
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
		cycleSpeed 6
		script 0
		cycler 0
		timer 0
		detailLevel 0
		client 0
		cnt 0
		offSet 0
		xOffset 0
		yOffset 0
		rand 0
	)
	
	(method (init param1)
		(self
			view: (param1 view?)
			client: param1
			ignoreActors: 1
		)
		(= loop (- (NumLoops self) 2))
		(param1 head: self)
		(super init:)
		(self hide: signal: (| (self signal?) $0010))
	)
	
	(method (doit)
		(cond 
			(
				(and
					(client normal?)
					(not (& (client signal?) $0008))
					(client isStopped:)
					(== (client loop?) (- (NumLoops client) 1))
				)
				(if (and (& signal $0004) (not (& signal $0002)))
					(if (& (client signal?) $0004)
						(return)
					else
						(self startUpd:)
					)
				)
				(self showSelf:)
			)
			((not (& signal $0008)) (self hide:))
		)
		(super doit:)
	)
	
	(method (showSelf &tmp temp0 temp1)
		(= temp1 0)
		(if (& signal $0008)
			(= cel (client cel?))
			(= rand 0)
			(= offSet (+ (* (client cel?) 3) 1))
			(= cnt cycleSpeed)
		)
		(if
			(and
				(< (-- cnt) 1)
				(client moveHead?)
				(!= (NumLoops self) 6)
			)
			(= cnt cycleSpeed)
			(= cel
				[theCel (+ offSet (= rand (- (Random 0 2) 1)))]
			)
		)
		(= temp0 0)
		(while (< temp0 (* 25 local24))
			(if (== (client view?) [theXOffset temp0])
				(= temp1 1)
				(break)
			)
			(= temp0 (+ temp0 25))
		)
		(if temp1
			(= xOffset [theXOffset (+ 1 temp0 offSet rand)])
			(= yOffset [theYOffset (+ 1 temp0 offSet rand)])
		else
			(= xOffset (= yOffset 0))
		)
		(self view: (client view?) priority: (client priority?))
		(self
			loop: (- (NumLoops self) 2)
			x: (+ (client x?) xOffset)
			y: (client y?)
			z: (+ (CelHigh view (client loop?) (client cel?)) yOffset)
			show:
		)
	)
)
