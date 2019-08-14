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
(use GameEgo)
(use BordWind)
(use GameIconBar)
(use GameInv)
(use Talker)
(use ColorInit)
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
		(PalInit)
		(= version {x.yyy.zzz})
		(= deathMusic sDeath)
		(= useSortedFeatures TRUE)
		(User alterEgo: ego canControl: FALSE canInput: FALSE)
		(= waitCursor HAND_CURSOR)
		(= possibleScore 0)
		(= userFont USERFONT)
		(= musicChannels (DoSound NumVoices))
		(if
			(and
				(>= (= colorCount (Graph GDetect)) 2)
				(<= colorCount 16)
			)
			(Bclr fIsVGA)
		else
			(Bset fIsVGA)
		)	
		(= startingRoom rTitle)
		((theIconBar at: ICON_INVENTORY) message: (if (HaveMouse) 3840 else 9))		
		(theIconBar disable: hide:)
		(theGame newRoom: SPEED_TEST)
		(DisposeScript GAME_INIT)
	)
)