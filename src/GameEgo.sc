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
	
	(method (init param1 &tmp temp0)
		;EO: this bit of code was taken from PQ3, as Mixed-Up Fairy Tales' 
		;Head init: did not decompile correctly, causing graphical glitches!
		(self
			view: (param1 view?)
			client: param1
			ignoreActors: 1
		)
		(= loop (- (NumLoops self) 2))
		;On the plus side, ego's head now displays properly, and DOESN'T move around 
		;way too fast. This is good, as I don't want to modify the system scripts any
		;more than necessary.
;;;		(= view (param1 view?))
;;;		(self
;;;			client: param1
;;;			ignoreActors: 1
;;;			posn:
;;;				(param1 species?)
;;;				(param1 y?)
;;;				(CelHigh view (param1 loop?) (param1 cel?))
;;;		)
;;;		(= temp0 (== (client loop?) 5))
;;;		(if (& (param1 signal?) fixPriOn)
;;;			(self setPri: (param1 priority?))
;;;		)
		(param1 head: self)
		(super init:)
		(if (or (not temp0) (not (param1 normal?)))
			(self hide:)
		)
		(if moveHead (self cue: look:))
	)
	
	(method (doit &tmp temp0 clientNormal)
		(= clientNormal (client normal?))
		(= temp0 (== (client loop?) 5))
		(cond 
			((or (not clientNormal) (not temp0)) (self hide:))
			((and (& signal $0080) temp0) (self show:))
			(
			(and (not (& signal $0080)) (not (client isStopped:))) (self hide:))
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
	
	(method (dispose)
		(if script (script caller: 0))
		(super dispose:)
	)
	
	(method (doVerb theVerb)
		(client doVerb: theVerb)
	)
	
	(method (cue &tmp temp0)
		(if (and (not (curRoom script?)) moveHead)
			(client look: (- (Random 0 2) 1))
		)
		(self setScript: (HeadScript new:) self)
	)
	
	(method (look &tmp clientLoop clientLookingDir)
		(if
			(==
				(= clientLoop (client loop?))
				(- (NumLoops client) 1)
			)
			(= clientLoop (client cel?))
			(= clientLookingDir (client lookingDir?))
		else
			(= clientLookingDir 0)
		)
		(= cel
			(+
				(& (StrAt headCel clientLoop) $000f)
				clientLookingDir
			)
		)
	)
)

(instance HeadScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= ticks (Random 60 150)))
			(1 (self dispose:))
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
		(if (not head) ((= head (Head new:)) init: self))
	)
	
	(method (dispose)
		(= head 0)
		(super dispose:)
	)
	
	(method (cue)
		(if head (head setCel: 0 setCycle: 0))
	)
	
	(method (look theLookingDir)
		(= lookingDir theLookingDir)
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
		(super init: &rest)
	)
	
	(method (doit)
		(super doit:)
		(if (<= x 10) (= edgeHit 4))
		(if (>= x 310) (= edgeHit 2))
		(if (>= y 166) (= edgeHit 3))
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
				)
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
)
