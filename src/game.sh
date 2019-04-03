;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here

;View defines
(define vEgo 0)
(define vEgoStand 4)

;Script defines
(define rTitle 1)
(define rTestRoom 98)
(define rSpeedTest 99)

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

;Verbs
(define V_WALK 1)
(define V_LOOK 2)
(define V_DO 3)
(define V_ITEM 4)
(define V_TALK 5)
(define V_HELP 6)
(define V_TASTE 10)
(define V_SMELL 11)

;Event flags