;;; Sierra Script 1.0 - (do not remove this comment)
(script# SPEED)
(include game.sh)
(use Main)
(use Motion)
(use Game)
(use Actor)
(use Intrface)
(use System)

(public
	speedTest 0
)

(local
	doneTime
	machineSpeed
	versionFile
	fastSpeed
)

(instance fred of Actor)

(instance speedTest of Room
	(properties
		picture pSpeedTest
		style PLAIN
	)
	
	(method (init)
		(= versionFile (FileIO fileOpen {version} 1))
		(FileIO fileFGets version 6 versionFile)
		(FileIO fileClose versionFile)
		(super init:)
		(sounds eachElementDo: #stop)
		(while (u> (GetTime) $fc00)
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
		(= machineSpeed 0)
		(= fastSpeed 90)
	)
	
	(method (doit)
		(super doit:)
		(if (== (++ machineSpeed) 1)
			(= doneTime (+ 60 (GetTime)))
		)
		(if (and (u< doneTime (GetTime)) (not (self script?)))
			(if (< machineSpeed fastSpeed)
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
	
	(method (changeState newState &tmp [inputRoom 10] nextRoom)
		(switch (= state newState)
			(0
				(Palette PALIntensity 0 255 100)
				(fred setMotion: 0)
				(= cycles 1)
			)
			(1
				(= speed 2)
				(= cycles 1)
			)
			(2
				(if debugging
					(repeat
						(= inputRoom 0)
						(= nextRoom
							(Print "Where to, boss?"
								#edit @inputRoom 5
							)
						)
						(if inputRoom
							(= nextRoom (ReadNumber @inputRoom))
						)
						(if (> nextRoom 0)
							(break)
						)
					)
					(theIconBar enable:)
					(HandsOn)
				else
					(= nextRoom rTitle)
				)
				(curRoom newRoom: nextRoom)
			)
		)
	)
)