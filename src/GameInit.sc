;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEINIT.SC
;
;	Add things to initialize at game start here.
;	Make sure they don't require any objects or methods in MAIN.SC.
;
;
(script# GAME_INIT)
(include game.sh)
(use Main)
(use BordWind)
(use GameIconBar)
(use GameInv)
(use Talker)
(use User)
(use System)

(public
	GameInitCode 0
)

(instance GameInitCode of Code
	(properties)
	
	(method (init)
		(= debugging TRUE)
		(= systemWindow BorderWindow)
		
		(if (> (Graph GDetect) 16)
			(= myTextColor (Palette PALMatch 31 31 31))
			(= myInsideColor (Palette PALMatch 63 63 63))
			(= myBotBordColor (Palette PALMatch 95 95 95))
			(= myRgtBordColor (Palette PALMatch 127 127 127))
			(= myBackColor (Palette PALMatch 159 159 159))
			(= myLftBordColor (Palette PALMatch 191 191 191))
			(= myTopBordColor (Palette PALMatch 223 223 223))
			(= myHighlightColor (Palette PALMatch 187 35 35))
		else
			(= myTextColor vBLACK)
			(= myInsideColor vLGREY)
			(= myBotBordColor vGREY)
			(= myTopBordColor vWHITE)
		)
		
		(= version {x.yyy.zzz})
		(= deathMusic sDeath)
		(= useSortedFeatures TRUE)
		(= waitCursor HAND_CURSOR)
		(= possibleScore 0)
		(= userFont USERFONT)
		(= numVoices (DoSound NumVoices))
		(if
			(and
				(>= (= numColors (Graph GDetect)) 2)
				(<= numColors 16)
			)
			(Bclr fIsVGA)
		else
			(Bset fIsVGA)
		)
		(= deathMusic sDeath)
		(= startingRoom rTitle)
		(theIconBar disable: hide:)
		(theGame newRoom: SPEED)
		(DisposeScript GAME_INIT)
	)
)