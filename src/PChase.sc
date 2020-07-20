;;; Sierra Script 1.0 - (do not remove this comment)
(script# PCHASE)
(include game.sh)
(use Main)
(use PolyPath)
(use System)


(class PChase of PolyPath
	(properties
		who 0
		distance 0
		targetX 0
		targetY 0
	)
	
	(method (init actor whom howClose whoCares theObst)
		(cond 
			((>= argc 5)
				(= obstacles theObst)
			)
			((not (IsObject obstacles))
				(= obstacles (curRoom obstacles?))
			)
		)
		(if (>= argc 1)
			(= client actor)
			(if (>= argc 2)
				(= who whom)
				(= targetX (who x?))
				(= targetY (who y?))
				(if (>= argc 3)
					(= distance howClose)
					(if (>= argc 4) (= caller whoCares))
				)
			)
		)
		(super init: client targetX targetY caller TRUE obstacles)
	)
	
	(method (doit &tmp theDistance)
		(cond 
			(
				(>
					(GetDistance targetX targetY (who x?) (who y?))
					distance
				)
				(if points (Memory MDisposePtr points))
				(= points 0)
				(= value 2)
				(self init: client who)
			)
			((<= (= theDistance (client distanceTo: who)) distance)
				(= completed TRUE)
				(self moveDone:)
			)
			(else
				(super doit:)
			)
		)
	)
	
	(method (moveDone &tmp howClose)
		(cond 
			((<= (= howClose (client distanceTo: who)) distance)
				(= completed TRUE)
				(super moveDone:)
			)
			((== (WordAt points value) $7777)
				(if points (Memory MDisposePtr points))
				(= points 0)
				(= value 2)
				(self init: client who)
			)
			(else
				(super init:)
			)
		)
	)
)

(class PFollow of PolyPath
	(properties
		who 0
		distance 0
		targetX 0
		targetY 0
	)
	
	(method (init actor whom howClose theObst &tmp obstList)
		(= obstList
			(if (>= argc 4) theObst else (curRoom obstacles?))
		)
		(if (>= argc 1)
			(= client actor)
			(if (>= argc 2)
				(= who whom)
				(= targetX (who x?))
				(= targetY (who y?))
				(if (>= argc 3) (= distance howClose))
			)
		)
		(super init: client targetX targetY 0 TRUE obstList)
	)
	
	(method (doit &tmp theDistance)
		(cond 
			(
				(>
					(GetDistance targetX targetY (who x?) (who y?))
					distance
				)
				(if points (Memory MDisposePtr points))
				(= points 0)
				(= value 2)
				(self init: client who)
			)
			((<= (= theDistance (client distanceTo: who)) distance)
				(client
					setHeading: (GetAngle (client x?) (client y?) (who x?) (who y?))
				)
				(= xLast (client x?))
				(= yLast (client y?))
				(return)
			)
			(else (super doit:))
		)
	)
	
	(method (moveDone)
		(self init:)
	)
)
