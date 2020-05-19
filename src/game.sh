;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here

(include system.sh) (include sci2.sh)
(include views.sh) (include pics.sh)

;Game modules
(define	MAIN			0)
(define	GAME_WINDOW		1)
(define	SPEED			4)
(define	GAME_CONTROLS	5)
(define	GAME_INV		6)
(define	GAME_EGO		7)
(define	DEBUG			8)
(define	GAME_ABOUT		9)
(define	GAME_ICONBAR	11)
(define	GAME_INIT		12)
(define	DISPOSE_CODE	14)

;Actual rooms
(define rTitle			100)
(define rTestRoom		110)

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