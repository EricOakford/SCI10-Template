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
(use Procs)
(use User)
(use BordWind)
(use System)

(public
	gameInitCode 0
)

(instance gameInitCode of Code
	(method (doit)
		(= numVoices (DoSound NumVoices))
		(= numColors (Graph GDetect))
		(= useSortedFeatures TRUE)
		(= systemWindow BorderWindow)
		(theGame
			egoMoveSpeed: 4
			setCursor: theCursor TRUE 304 172
		)
		
		;at this point, the color globals have already been initialized,
		;so set the interface colors using them.	
		(= myTextColor colBlack)
		(= myBackColor (FindColor colGray4 colGray1))
		(systemWindow
			color: myTextColor
			back: myBackColor
			;comment these lines out if not using a BorderWindow!
			topBordColor: colWhite
			lftBordColor: (FindColor colGray5 colWhite)
			rgtBordColor: (FindColor colGray3 colGray1)
			botBordColor: (FindColor colGray2 colGray1)
		)
		
		(= eatMice 30)
		(= possibleScore 999)
		(= score 0)
		(= waitCursor 997)	;original hand cursor
		(= debugging TRUE)	;Set this to FALSE to disable the debug features
		(HandsOff)
		(DisposeScript GAME_INIT)	;don't need this in memory anymore
	)
)