;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here

(include system.sh) (include sci2.sh)
(include views.sh) (include pics.sh)

;Game modules
(enum
	MAIN			;0
	GAME_WINDOW		;1
	DODISP			;2
	GAME_ROOM		;3
	SPEED_TEST		;4
	GAME_CONTROLS	;5
	GAME_INV		;6
	GAME_EGO		;7
	DEBUG			;8
	GAME_ABOUT		;9
	DEATHROOM		;10
	GAME_ICONBAR	;11
	GAME_INIT		;12
	WHERE_TO		;13
	DISPOSE_CODE	;14
	COLOR_INIT		;15
)

;Actual rooms
(define rTitle 100)
(define rTestRoom 110)

;Sound defines
(define sOpening 1)
(define sDeath 2)
(define sScore 3)

;Inventory items
(enum
	iCoin
	iBomb
)

;Icons
(enum
	ICON_WALK
	ICON_LOOK
	ICON_DO
	ICON_TALK
	ICON_ITEM
	ICON_INVENTORY
	ICON_SMELL
	ICON_TASTE
	ICON_CONTROL
	ICON_HELP
)

;Game-specific verbs
;(for generic verbs, refer to SYSTEM.SH)
(enum 10
	verbTaste
	verbSmell
)

;Event flags
(enum
	fIsVGA
)