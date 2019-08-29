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
			(= myEGABordColor (Palette PALMatch 63 63 63))
			(= myEGABackColor (Palette PALMatch 95 95 95))
			(= myVGABordColor (Palette PALMatch 127 127 127))
			(= myVGABackColor (Palette PALMatch 159 159 159))
			(= myVGABordColor2 (Palette PALMatch 191 191 191))
			(= myEGABordColor2 (Palette PALMatch 223 223 223))
			(= myHighlightColor (Palette PALMatch 187 35 35))
		else
			(= myTextColor vBLACK)
			(= myEGABordColor vLGREY)
			(= myEGABackColor vGREY)
			(= myEGABordColor2 vWHITE)
		)
		
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
		(= deathMusic sDeath)
		(= startingRoom rTitle)
		;Moved the icon bar, inventory, and control panel into their own scripts.
		((ScriptID GAME_ICONBAR 0) init:)
		((ScriptID GAME_INV 0) init:)
		((ScriptID GAME_CONTROLS 0) init:)	
		((theIconBar at: ICON_INVENTORY) message: (if (HaveMouse) 3840 else 9))		
		(theIconBar disable: hide:)
		(theGame newRoom: SPEED_TEST)
		(DisposeScript GAME_INIT)
	)
)