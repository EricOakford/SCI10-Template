;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEABOUT.SC
;
;	Displays a message in response to a call to "theGame showAbout:".
;	You can also switch between English and another language for
;	bilingual games.
;
;
(script# GAME_ABOUT)
(include game.sh) (include language.sh)
(use Main)
(use Intrface)
(use System)

(public
	aboutCode 0
)

(instance aboutCode of Code
	(method (doit &tmp printRet)
		;Uncomment these lines if you wish to have dual-language support.
;;;		(switch
;;;			(= printRet
;;;				(Print "SCI 1.0 Template Game"
;;;					#button {About game} 1
;;;					#button {Cancel} 0
;;;					#button {Switch Language} 2
;;;					#mode teJustCenter
;;;				)
;;;			)
;;;			(2
;;;				;foreign language can be either SPANISH, FRENCH, or GERMAN
;;;				; (Italian on the way)
;;;				(if (== (theGame printLang?) SPANISH)
;;;					(theGame
;;;						parseLang: ENGLISH
;;;						printLang: ENGLISH
;;;					)
;;;				else
;;;					(theGame
;;;						parseLang: SPANISH
;;;						printLang: SPANISH
;;;					)
;;;				)
;;;			)
;;;			(1
			;always have this line uncommented
				(Print "SCI1.0 Template Game\n
					by Eric Oakford"
				)
;;;			)
;;;		)
	)
)