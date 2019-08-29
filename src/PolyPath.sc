;;; Sierra Script 1.0 - (do not remove this comment)
(script# 945)
(include game.sh)
(use Main)
(use Motion)
(use System)


(class PolyPath of Motion
	(properties
		value 2
		points 0
		finalX 0
		finalY 0
		obstacles 0
	)
	
	(method (init theClient theFinalX theFinalY theCaller param5 theObstacles)
		(if argc
			(= client theClient)
			(if (> argc 1)
				(cond 
					((>= argc 6) (= obstacles theObstacles))
					((not (IsObject obstacles)) (= obstacles (curRoom obstacles?)))
				)
				(if points (Memory MDisposePtr points))
				(= points
					(AvoidPath
						(theClient x?)
						(theClient y?)
						(= finalX theFinalX)
						(= finalY theFinalY)
						(if obstacles (obstacles elements?) else 0)
						(if obstacles (obstacles size?) else 0)
						(if (>= argc 5) param5 else 1)
					)
				)
				(if (> argc 3) (= caller theCaller))
			)
		)
		(self setTarget:)
		(super init:)
	)
	
	(method (dispose)
		(if points (Memory MDisposePtr points))
		(= points 0)
		(super dispose:)
	)
	
	(method (moveDone)
		(if (== (WordAt points value) 30583)
			(super moveDone:)
		else
			(self init:)
		)
	)
	
	(method (setTarget)
		(if (!= (WordAt points value) 30583)
			(= x (WordAt points value))
			(= y (WordAt points (++ value)))
			(++ value)
		)
	)
)
