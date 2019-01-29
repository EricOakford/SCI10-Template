;;; Sierra Script 1.0 - (do not remove this comment)
(script# 945)
(include sci.sh)
(use Main)
(use Motion)
(use System)


(class PolyPath of Motion
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
		b-di 0
		b-xAxis 0
		b-incr 0
		completed 0
		xLast 0
		yLast 0
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
				(if points (Memory 3 points))
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
		(if points (Memory 3 points))
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
