;;; Sierra Script 1.0 - (do not remove this comment)
;**************************************************************
;***
;***	GAME.SH--
;***	Put all the defines specific to your game in here
;***
;**************************************************************


(include graphics.sh) ;graphical defines
(include system.sh) (include sci2.sh) ;system and kernel functions
; Commonly-used header files are nested here, so most scripts only need to include this one.

;
; Global stuff
(define	MAIN			0)
(define GAME_WINDOW		1)
(define	DODISP			2)
(define	GAME_ROOM		3)
(define SPEED_TEST		4)
(define GAME_CONTROLS	5)
(define GAME_INV		6)
(define GAME_EGO		7)
(define DEBUG			8)
(define GAME_ABOUT		9)
(define DEATH			10)
(define GAME_ICONBAR	11)
(define GAME_INIT		12)
(define WHERE_TO		13)
(define DISPOSE			14)
(define PROCS			15)

;
; Actual rooms
(define rTitle			100)
(define rTestRoom		110)

;
; Indices for the icons in the icon bar
(enum
	ICON_WALK
	ICON_LOOK
	ICON_DO
	ICON_TALK
	ICON_ITEM
	ICON_INVENTORY
	ICON_CONTROL
	ICON_HELP
	ICON_SCORE
)

;
; Inventory items
;Make sure they are in the same order you put them in the inventory list in GAME_INV.SC.
;To avoid name conflicts, prefix the items with the letter "i".
(enum
	iMoney
	iLastInvItem	;this MUST be last
)
;(define NUM_INVITEMS (- iLastInvItem 1))

;
; Death reasons
(enum 1
	deathGENERIC
)

;
; Sound defines
(define sOpening	1)
(define sDeath		2)
(define sScore		3)

; howFast values for SpeedTest
(enum
	slow
	medium
	fast
)

; Event flags
	;These flags are used by Bset, Btst, and Bclr.
	;Example: fBabaFrog (original Sierra naming)
(define FLAG_ARRAY 10)	;used for the gameFlags array. If you need more flags, increase this.
						;each global can have 16 flags. 10 globals * 16 flags = 160 flags.
(enum
	fIsVGA
)