;;; Sierra Script 1.0 - (do not remove this comment)
(script# rSpeedTest)
(include system.sh) (include sci2.sh) (include game.sh)
(use Main)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	speedTest 0
)

(local
	local0
	local1
	local2
	local3
)
(instance fred of Actor
	(properties)
)

(instance speedTest of Room
	(properties
		picture scriptNumber
		style $0064
	)
	
	(method (init)
		(= local2 (FileIO 0 {version} 1))
		(FileIO 5 version 6 local2)
		(FileIO 1 local2)
		(super init:)
		(sounds eachElementDo: #stop)
		(while (u> (GetTime) -1024)
		)
		(fred
			view: 99
			setLoop: 0
			illegalBits: 0
			posn: 20 99
			setStep: 1 1
			setMotion: MoveTo 300 100
			setCycle: Forward
			init:
		)
		(= speed 0)
		(= local1 0)
		(= local3 90)
	)
	
	(method (doit)
		(super doit:)
		(if (== (++ local1) 1) (= local0 (+ 60 (GetTime))))
		(if
		(and (u< local0 (GetTime)) (not (self script?)))
			(if (< local1 local3)
				(= howFast 0)
				(theGame detailLevel: 1)
			else
				(= howFast 2)
			)
			(self setScript: speedScript)
		)
	)
	
	(method (dispose)
		(super dispose:)
	)
)

(instance speedScript of Script
	(properties)
	
	(method (changeState newState &tmp temp0)
		(switch (= state newState)
			(0
				(Palette 4 0 255 100)
				(fred setMotion: 0)
				(= cycles 1)
			)
			(1 (= speed 2) (= cycles 1))
			(2 (curRoom newRoom: rTitle))
		)
	)
)
