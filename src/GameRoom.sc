;;; Sierra Script 1.0 - (do not remove this comment)
(script# GAME_ROOM)
(include game.sh)
(use Main)
(use Intrface)
(use PolyPath)
(use Motion)
(use Game)
(use User)
(use System)

(use Main)
(use Intrface)
(use PolyPath)
(use Game)
(use User)
(use System)

(public
	eRS 0
	sWI 1
	sWO 2
	sRW 3
)

(local
	local0
	local1
)
(procedure (localproc_0322)
	(cond 
		((< (ego x?) 0) (ego x: (+ 0 (* (ego xStep?) 2))))
		((> (ego x?) 319) (ego x: (- 319 (* (ego xStep?) 2))))
	)
)

(procedure (localproc_036f)
	(cond 
		((< (ego y?) (curRoom horizon?)) (ego y: (+ (curRoom horizon?) (* (ego yStep?) 2))))
		((> (ego y?) 189) (ego y: (- 189 (* (ego yStep?) 2))))
	)
)

(class GameRoom of Room
	(properties
		x 0
		y 0
	)
	
	(method (init &tmp [temp0 2])
		(= number curRoomNum)
		(= perspective picAngle)
		(if picture (self drawPic: picture))
		(cond 
			((not (cast contains: ego)) 0)
			(script 0)
			(else (self setScript: eRS 0 prevRoomNum))
		)
	)
	
	(method (doit &tmp temp0)
		(cond 
			(script (script doit:))
			((not (cast contains: ego)) 0)
			(
				(= temp0
					(switch ((User alterEgo?) edgeHit?)
						(NORTH north)
						(EAST east)
						(SOUTH south)
						(WEST west)
					)
				)
				(HandsOff)
				(if (== temp0 -1)
					(self setScript: sRW 0 ((User alterEgo?) edgeHit?))
				else
					(self setScript: lRS 0 temp0)
				)
			)
		)
	)
	
	(method (posn theX theY)
		(= x theX)
		(= y theY)
	)
)

(instance lRS of Script
	(properties)
	
	(method (changeState newState &tmp temp0 temp1)
		(switch (= state newState)
			(0
				(HandsOff)
				(= temp1
					(/ (CelWide (ego view?) (ego loop?) (ego cel?)) 2)
				)
				(switch register
					((client north?)
						(curRoom newRoom: register)
					)
					((client south?)
						(= temp0 (CelHigh (ego view?) (ego loop?) (ego cel?)))
						(if (IsObject (ego head?))
							(= temp0
								(+
									temp0
									(CelHigh
										((ego head?) view?)
										((ego head?) loop?)
										((ego head?) cel?)
									)
								)
							)
						)
						(ego setMotion: PolyPath (ego x?) (+ 189 temp0) self)
					)
					((client east?)
						(ego setMotion: PolyPath (+ 319 temp1) (ego y?) self)
					)
					((client west?)
						(ego setMotion: PolyPath (- 0 temp1) (ego y?) self)
					)
				)
			)
			(1 (ego hide:) (= cycles 2))
			(2
				(curRoom setScript: 0 newRoom: register)
			)
		)
	)
)

(instance eRS of Script
	(properties)
	
	(method (changeState newState &tmp temp0 temp1)
		(switch (= state newState)
			(0
				(= cycles 0)
				(HandsOff)
				(= temp0 (CelHigh (ego view?) (ego loop?) (ego cel?)))
				(= temp1
					(/ (CelWide (ego view?) (ego loop?) (ego cel?)) 2)
				)
				(switch register
					((client north?)
						(localproc_0322)
						(ego y: (+ (curRoom horizon?) (ego yStep?)))
						(= cycles 1)
					)
					((client south?)
						(localproc_0322)
						(ego
							y: (+ 189 temp0)
							setMotion: PolyPath (ego x?) (- 189 (* (ego yStep?) 2)) self
						)
					)
					((client east?)
						(localproc_036f)
						(ego
							x: (+ 319 temp1)
							setMotion: PolyPath (- 319 (* (ego xStep?) 2)) (ego y?) self
						)
					)
					((client west?)
						(localproc_036f)
						(ego
							x: (- 0 temp1)
							setMotion: PolyPath (+ 0 (* (ego xStep?) 2)) (ego y?) self
						)
					)
					(else  (= cycles 1))
				)
			)
			(1 (HandsOn) (self dispose:))
		)
	)
)

(instance sWO of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego
					ignoreActors: 1
					setMotion: PolyPath (curRoom x?) (curRoom y?) self
				)
			)
			(1
				(curRoom posn: 0 0 setScript: 0 newRoom: register)
			)
		)
	)
)

(instance sWI of Script
	(properties)
	
	(method (changeState newState &tmp [temp0 2])
		(switch (= state newState)
			(0
				(HandsOff)
				(ego setMotion: PolyPath (curRoom x?) (curRoom y?) self)
			)
			(1
				(curRoom posn: 0 0)
				(HandsOn)
				(ego ignoreActors: 0 setPri: -1)
				(self dispose:)
			)
		)
	)
)

(instance sRW of Script
	(properties)
	
	(method (changeState newState &tmp egoX egoY temp2 temp3)
		(switch (= state newState)
			(0
				(= local0 (= egoX (ego x?)))
				(= local1 (= egoY (ego y?)))
				(= temp3 (CelWide (ego view?) (ego loop?) (ego cel?)))
				(= temp2 (CelHigh (ego view?) (ego loop?) (ego cel?)))
				(if (IsObject (ego head?))
					(= temp2
						(+
							temp2
							(CelHigh
								((ego head?) view?)
								((ego head?) loop?)
								((ego head?) cel?)
							)
						)
					)
				)
				(switch register
					(1 (= local1 (+ local1 5)))
					(2
						(= egoX (+ egoX temp3))
						(= local0 (- local0 5))
					)
					(3
						(= egoY (+ egoY temp2))
						(= local1 (- local1 5))
					)
					(4
						(= egoX (- egoX temp3))
						(= local0 (+ local0 5))
					)
				)
				(ego ignoreActors: 1 setMotion: PolyPath egoX egoY self)
			)
			(1 (= seconds 2))
			(2
				(ego setMotion: PolyPath local0 local1 self)
			)
			(3
				(ego ignoreActors: 0)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)
