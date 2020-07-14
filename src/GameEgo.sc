;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEEGO.SC
;
;	The custom Ego subclasses are here. They consist of the body and head
;	(to allow Ego to look around when he's standing still).
;
;

(script# GAME_EGO)
(include game.sh)
(use Main)
(use Intrface)
(use User)
(use Actor)
(use System)

(public
	egoBody 0
)

(class Head of Prop
	(properties
		client 0
		cycleCnt 0
		moveHead 1
		headCel {15372406}
	)
	
	(method (init obj &tmp temp0)
		;EO: this bit of code was taken from PQ3, as Mixed-Up Fairy Tales' 
		;Head init: did not decompile correctly, causing graphical glitches!
		(self
			view: (obj view?)
			client: obj
			ignoreActors: TRUE
		)
		(= loop (- (NumLoops self) 2))
		;On the plus side, ego's head now displays properly, and DOESN'T move around 
		;way too fast. This is good, as I don't want to modify the system scripts any
		;more than necessary.
;;;		(= view (obj view?))
;;;		(self
;;;			client: obj
;;;			ignoreActors: 1
;;;			posn:
;;;				(obj species?)
;;;				(obj y?)
;;;				(CelHigh view (obj loop?) (obj cel?))
;;;		)
;;;		(= temp0 (== (client loop?) 5))
;;;		(if (& (obj signal?) fixPriOn)
;;;			(self setPri: (obj priority?))
;;;		)
		(obj head: self)
		(super init:)
		(if (or (not temp0) (not (obj normal?)))
			(self hide:)
		)
		(if moveHead
			(self cue: look:)
		)
	)
	
	(method (doit &tmp temp0 clientNormal)
		(= clientNormal (client normal?))
		(= temp0 (== (client loop?) 5))
		(cond 
			((or (not clientNormal) (not temp0))
				(self hide:)
			)
			((and (& signal actorHidden) temp0)
				(self show:)
			)
			((and (not (& signal actorHidden)) (not (client isStopped:)))
				(self hide:)
			)
		)
		(if moveHead
			(self setPri: (client priority?) look:)
			(if (!= (self view?) (client view?))
				(self view: (client view?) loop: 4)
			)
			(= x (client x?))
			(= y (client y?))
			(= z
				(CelHigh (client view?) (client loop?) (client cel?))
			)
		)
		(super doit:)
	)
	
	(method (doVerb theVerb)
		(client doVerb: theVerb)
	)
	
	(method (cue)
		(if (and (not (curRoom script?)) moveHead)
			(client look: (- (Random 0 2) 1))
		)
		(self setScript: (HeadScript new:) self)
	)
	
	(method (look &tmp clientLoop dir)
		(if
			(==
				(= clientLoop (client loop?))
				(- (NumLoops client) 1)
			)
			(= clientLoop (client cel?))
			(= dir (client lookingDir?))
		else
			(= dir 0)
		)
		(= cel
			(+
				(& (StrAt headCel clientLoop) $000f)
				dir
			)
		)
	)
)

(instance HeadScript of Script
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= ticks (Random 60 150))
			)
			(1
				(self dispose:)
			)
		)
	)
)

(class Body of Ego
	(properties
		head 0
		caller 0
		lookingDir 1
		normal 1
	)
	
	(method (init)
		(super init:)
		(if (not head)
			((= head (Head new:)) init: self)
		)
	)
	
	(method (cue)
		(if head
			(head setCel: 0 setCycle: 0)
		)
	)
	
	(method (look dir)
		(= lookingDir dir)
	)
)

(instance egoHead of Head
	(properties
		view vEgo
		loop 4
		name {your head}
		lookStr {There's nothing going on in your stupid little head.}
	)
)

(instance egoBody of Body
	(properties
		name "ego"
		description {you}
		sightAngle 180
		lookStr {It's you!}
		view vEgo
	)
	
	(method (init)
		(super init:)
		(= head egoHead)
		(= theEgoHead egoHead)
	)

	(method (doit)
		(super doit:)
		(if (<= x 10)
			(= edgeHit WEST)
		)
		(if (>= x 310)
			(= edgeHit EAST)
		)
		(if (>= y 166)
			(= edgeHit SOUTH)
		)
	)
	
	(method (doVerb theVerb theItem)
		(switch theVerb
			(verbTalk
				(Print "You talk to yourself but are stumped for a reply.")
			)
			(verbDo
				(Print "Hey! Keep your hands off yourself! This is a family game.")
			)
			(verbTaste
				(Print "I'll bet you wish you could!")
			)
			(verbSmell
				(Print "Ahhh!  The aroma of several adventure games emanates from your person.")
			)
			(verbUse
				(switch theItem
					(iCoin
						(Print "You pay yourself some money.")
					)
					(iBomb
						(EgoDead "Maybe messing with the unstable ordinance wasn't such a hot idea...")
					)
					(else
						(Print "That doesn't do anything for you.")
					)
				)
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
)
