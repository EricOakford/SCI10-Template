;;; Sierra Script 1.0 - (do not remove this comment)
(script# 961)
(include game.sh)
(use PChase)
(use Motion)

(public
	StopWalk 0
)

(class StopWalk of Forward
	(properties
		cycleCnt 0
		completed 0
		vWalking 0
		vStopped 0
	)
	
	(method (init theClient theVStopped)
		(if argc
			(= vWalking ((= client theClient) view?))
			(if (>= argc 2) (= vStopped theVStopped))
		)
		(super init: client)
	)
	
	(method (doit &tmp temp0 clientMover clientLoop temp3)
		(= clientLoop (client loop?))
		(= temp3 (- (NumLoops client) 1))
		(if (client isStopped:)
			(if (!= clientLoop temp3)
				(client loop: temp3 cel: clientLoop)
				(if
					(and
						(= clientMover (client mover?))
						(not (clientMover completed?))
						(not (clientMover isKindOf: PFollow))
					)
					(client setMotion: 0)
				)
			)
		else
			(if (== clientLoop temp3)
				(client loop: (client cel?) cel: 0)
			)
			(super doit:)
		)
	)
	
	(method (dispose)
		(super dispose:)
	)
)
