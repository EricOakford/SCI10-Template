;;; Sierra Script 1.0 - (do not remove this comment)
(script# 947)
(include sci.sh)
(use Approach)
(use Actor)
(use System)


(class DelayedEvent of Event
	(properties
		client 0
		caller 0
		x 0
		y 0
		dx 0
		dy 0
		b-moveCnt 0
	)
	
	(method (perform param1 param2 &tmp superPerform)
		(= superPerform (super perform:))
		(if argc
			(superPerform client: param1)
			(if (>= argc 2)
				(superPerform
					type: (param2 type?)
					message: (param2 message?)
					modifiers: (param2 modifiers?)
					y: (param2 y?)
					x: (param2 x?)
					claimed: (param2 claimed?)
				)
			else
				(GetEvent 32767 superPerform)
			)
		)
		(return superPerform)
	)
	
	(method (seconds)
		(if b-moveCnt (b-moveCnt last: self))
		(self topString:)
	)
)

(class GoToDlyEvt of Approach
	(properties
		client 0
		caller 0
		x 0
		y 0
		dx 0
		dy 0
		b-moveCnt 0
		b-i1 0
		b-i2 0
	)
	
	(method (perform)
		(= b-i1 (View description?))
		(= b-i2 (View noun?))
		(View notFacing: 0 isNotHidden: 0)
		(super perform: &rest)
	)
	
	(method (seconds)
		(View notFacing: b-i1 isNotHidden: b-i2)
		(Parse (View approachY?) self)
		(super seconds: &rest)
	)
)
