;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEABOUT.SC
;
;	Displays a message in response to a call to "theGame showAbout:".
;
;
(script# GAME_ABOUT)
(include game.sh)
(use Intrface)
(use System)

(public
	aboutCode 0
)

(instance aboutCode of Code
	(method (doit)
		(Print "SCI1.0 Template Game\n
			by Eric Oakford"
		)
	)
)