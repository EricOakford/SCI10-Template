;;; Sierra Script 1.0 - (do not remove this comment)
;
;	SPEEDTEST.SC
;
;	This is the script that checks the machine speed, then starts the game proper.
;
;

(script# SPEED_TEST)
(include game.sh)
(use Main)
(use Procs)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	speedTest 0
)

(local
	doneTime
	machineSpeed
	versionFile
	fastThreshold
	mediumThreshold
)
(instance fred of Actor)

(instance speedTest of Room
	(properties
		picture vSpeedTest
		style PLAIN
	)
	
	(method (init)
		(= versionFile (FileIO fileOpen {version} fRead))
		(FileIO fileFGets version 6 versionFile)
		(FileIO fileClose versionFile)
		(super init:)
		(sounds eachElementDo: #stop)
		(while (u> (GetTime) $fc00)
		)
		(fred
			view: vSpeedTest
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
		(= fastThreshold   (if (Btst fIsVGA) 90 else 60))
		(= mediumThreshold (if (Btst fIsVGA) 59 else 30))
	)
	
	(method (doit)
		(super doit:)
		(if (== (++ machineSpeed) 1)
			(= doneTime (+ 60 (GetTime)))
		)
		(if (and (u< doneTime (GetTime)) (not (self script?)))
			(cond
				((>= machineSpeed fastThreshold)
					(= howFast fast)
					(theGame detailLevel: 3)
				)
				((and (>= machineSpeed mediumThreshold) (< machineSpeed fastThreshold))
					(= howFast medium)
					(theGame detailLevel: 2)
				)
				(else
					(= howFast slow)
					(theGame detailLevel: 1)
				)
			)
			(self setScript: speedScript)
		)
	)
	
	(method (dispose)
		(super dispose:)
	)
)

(instance speedScript of Script
	(method (changeState newState &tmp nextRoom)
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
				;If debugging is enabled, go to the "Where to?" dialog.
				;Otherwise, go to the title screen...
				(if debugging
					(= nextRoom WHERE_TO)
				else
					(= nextRoom rTitle)
				)
				(curRoom newRoom: nextRoom)
			)
		)
	)
)
