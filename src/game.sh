;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here

(include system.sh) (include sci2.sh)

;View defines
(define vEgo 0)

;Script defines
(define rTestRoom 98)
(define rSpeedTest 99)
(define rTitle 100)

;Pic defines
(define pTestRoom 98)
(define pSpeedTest 99)

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
(define verbTaste 10)
(define verbSmell 11)

;Event flags
(enum
	fIsVGA
)