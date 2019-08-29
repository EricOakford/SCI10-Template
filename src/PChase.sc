;;; Sierra Script 1.0 - (do not remove this comment)
(script# 930)
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
	
	(method (init theClient theWho theDistance theCaller theObstacles)
		(cond 
			((>= argc 5) (= obstacles theObstacles))
			((not (IsObject obstacles)) (= obstacles (curRoom obstacles?)))
		)
		(if (>= argc 1)
			(= client theClient)
			(if (>= argc 2)
				(= who theWho)
				(= targetX (who x?))
				(= targetY (who y?))
				(if (>= argc 3)
					(= distance theDistance)
					(if (>= argc 4) (= caller theCaller))
				)
			)
		)
		(super init: client targetX targetY caller 1 obstacles)
	)
	
	(method (doit &tmp temp0)
		(cond 
			(
				(>
					(GetDistance targetX targetY (who x?) (who y?))
					distance
				)
				(if points (Memory 3 points))
				(= points 0)
				(= value 2)
				(self init: client who)
			)
			(
			(<= (= temp0 (client distanceTo: who)) distance) (self moveDone:))
			(else (super doit:))
		)
	)
	
	(method (moveDone &tmp temp0)
		(cond 
			(
			(<= (= temp0 (client distanceTo: who)) distance) (super moveDone:))
			((== (WordAt points value) 30583)
				(if points (Memory 3 points))
				(= points 0)
				(= value 2)
				(self init: client who)
			)
			(else (self init:))
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
	
	(method (init theClient theWho theDistance param4 &tmp temp0)
		(= temp0
			(if (>= argc 4) param4 else (curRoom obstacles?))
		)
		(if (>= argc 1)
			(= client theClient)
			(if (>= argc 2)
				(= who theWho)
				(= targetX (who x?))
				(= targetY (who y?))
				(if (>= argc 3) (= distance theDistance))
			)
		)
		(super init: client targetX targetY 0 1 temp0)
	)
	
	(method (doit &tmp temp0)
		(cond 
			(
				(>
					(GetDistance targetX targetY (who x?) (who y?))
					distance
				)
				(if points (Memory 3 points))
				(= points 0)
				(= value 2)
				(self init: client who)
				0
			)
			(
			(<= (= temp0 (client distanceTo: who)) distance)
				(client
					setHeading: (GetAngle (client x?) (client y?) (who x?) (who y?))
				)
				(= xLast (client x?))
				(= yLast (client y?))
				(= b-moveCnt gameTime)
				0
			)
			(else (super doit:))
		)
	)
	
	(method (moveDone)
		(self init:)
	)
)